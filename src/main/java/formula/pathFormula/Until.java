package formula.pathFormula;

import formula.FormulaParser;
import formula.HelpMethods;
import formula.PathTree;
import formula.stateFormula.StateFormula;
import model.Model;
import model.State;
import model.Transition;
import modelChecker.SimpleModelChecker;

import java.util.ArrayList;
import java.util.Set;

public class Until extends PathFormula {
    public final StateFormula left;
    public final StateFormula right;
    private Set<String> leftActions;
    private Set<String> rightActions;

    public Until(StateFormula left, StateFormula right, Set<String> leftActions, Set<String> rightActions) {
        super();
        this.left = left;
        this.right = right;
        this.leftActions = leftActions;
        this.rightActions = rightActions;
    }

    public Set<String> getLeftActions() {
        return leftActions;
    }

    public Set<String> getRightActions() {
        return rightActions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        left.writeToBuffer(buffer);
        buffer.append(" " + FormulaParser.UNTIL_TOKEN + " ");
        right.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public void checker(ArrayList buffer) {
        buffer.add("(");
        left.checker(buffer);
        buffer.add(" " + FormulaParser.UNTIL_TOKEN + " ");
        right.checker(buffer);
        buffer.add(")");
    }

    @Override
    public State[] getStates(State[] allStates, Model model, PathTree pathTree) {
        pathTree.setFormulaPart(" U ");
        PathTree leftNode = new PathTree("");
        pathTree.leftTree = leftNode;
        PathTree rightNode = new PathTree("");
        pathTree.rightTree = rightNode;
        State[] statesSatisfyingLeft = left.getStates(allStates, model, leftNode);
        State[] statesSatisfyingRight = right.getStates(allStates, model, rightNode);

        State[] untilStates = getAllSatisfyingUntil(statesSatisfyingRight, statesSatisfyingLeft, model);

        System.out.println("Until method");
        SimpleModelChecker.printStates(allStates);
        SimpleModelChecker.printStates(statesSatisfyingLeft);
        SimpleModelChecker.printStates(statesSatisfyingRight);
        SimpleModelChecker.printStates(untilStates);
        System.out.println("End of Until method");
        pathTree.addAcceptedStates(untilStates);
        return untilStates;
    }

    private State[] getAllSatisfyingUntil(State[] rightStates, State[] leftStates, Model model){
        ArrayList<State> untilStates = new ArrayList<>();
        State state;
        if(leftActions.size() == 0 && rightActions.size() == 0) {
            Transition[] transitions = model.getTransitions();
            for (int i = 0; i < leftStates.length; i++) {
                for (int j = 0; j < transitions.length; j++) {
                    if (leftStates[i].getName().equals(transitions[j].getSource())) {
                        state = recursiveUntilMethod(rightStates, leftStates, transitions[j], transitions, leftStates[i], model);
                        if (state != null && HelpMethods.notInSet(leftStates[i], untilStates)) {
                            untilStates.add(leftStates[i]);
                        }
                    }
                }
            }
        } else {
            Transition[] leftTransitions = model.getTransitions(leftActions);
            Transition[] rightTransitions = model.getTransitions(rightActions);
            State[] states;
            if(leftActions.size() == 0){
                for(int i = 0; i <= leftTransitions.length; i++){
                    states = getActionPossibilityStates(HelpMethods.getSubTransitions(leftTransitions, i), rightTransitions, rightStates, leftStates, model);
                    if (states.length != 0) {
                        untilStates = HelpMethods.getUpdatedUntilStates(states, untilStates);
                    }
                }
            } else if(rightActions.size() == 0){
                for(int i = 0; i <= rightTransitions.length; i++){
                    states = getActionPossibilityStates(leftTransitions, HelpMethods.getSubTransitions(rightTransitions, i), rightStates, leftStates, model);
                    if (states.length != 0) {
                        untilStates = HelpMethods.getUpdatedUntilStates(states, untilStates);
                    }
                }
            } else {
                for(int j = 1; j <= leftTransitions.length; j++) {
                    for (int i = 1; i <= rightTransitions.length; i++) {
                        states = getActionPossibilityStates(HelpMethods.getSubTransitions(leftTransitions, j), HelpMethods.getSubTransitions(rightTransitions, i), rightStates, leftStates, model);
                        if (states.length != 0) {
                            untilStates = HelpMethods.getUpdatedUntilStates(states, untilStates);
                        }
                    }
                }
            }
        }

        return untilStates.toArray(new State[untilStates.size()]);
    }

    private State[] getActionPossibilityStates(Transition[] leftTransitions, Transition[] rightTransitions, State[] rightStates, State[] leftStates, Model model){
        ArrayList<State> untilStates = new ArrayList<>();
        ArrayList<State> helpStates = new ArrayList<>();
        ArrayList<State> possibleToGet = new ArrayList<>();
        State state;
        for(int i = 0; i < leftTransitions.length; i++){
            for(int j = 0; j < leftStates.length; j++){
                if(leftStates[j].getName().equals(leftTransitions[i].getSource())){
                    state = recursiveUntilMethod(leftStates, leftStates, leftTransitions[i], leftTransitions, leftStates[j], model);
                    State actualState = HelpMethods.getActualState(leftTransitions[i].getTarget(), model);
                    if (state != null && HelpMethods.notInSet(actualState, possibleToGet)) {
                        possibleToGet.add(actualState);
                        helpStates.add(leftStates[j]);
                    }
                }
            }
        }
        State[] helpStatesGood = helpStates.toArray(new State[helpStates.size()]);
        State[] possibleToGetGood = possibleToGet.toArray(new State[possibleToGet.size()]);

        if(rightTransitions.length == 0){
            return helpStatesGood;
        }
        if(leftTransitions.length == 0){
            possibleToGetGood = leftStates;
            helpStatesGood = leftStates;
        }
        for(int i = 0; i < rightTransitions.length; i++){
            for(int j = 0; j < possibleToGetGood.length; j++){
                if(possibleToGetGood[j].getName().equals(rightTransitions[i].getSource())){
                    state = recursiveUntilMethod(rightStates, possibleToGetGood, rightTransitions[i], rightTransitions, possibleToGetGood[j], model);
                    if (state != null && HelpMethods.notInSet(helpStatesGood[j], untilStates)) {
                        untilStates.add(HelpMethods.getRealPath(helpStatesGood[j], helpStatesGood, possibleToGetGood));
                    }
                }
            }
        }
        return untilStates.toArray(new State[untilStates.size()]);
    }

    private State recursiveUntilMethod(State[] rightStates, State[] leftStates, Transition act, Transition[] acts, State currentState, Model model){
        State newState = HelpMethods.getActualState(act.getTarget(), model);
        if(newState.equals(currentState)){
            return null;
        }
        for(int i = 0; i < rightStates.length; i++){
            if(rightStates[i].equals(newState)){
                return currentState;
            }
        }
        for(int i = 0; i < leftStates.length; i++){
            if(leftStates[i].equals(newState)){
                leftStates = HelpMethods.removeElement(leftStates, newState);
                for(int j = 0; j < acts.length; j++) {
                    if (acts[j].getSource().equals(newState.getName())) {
                        return recursiveUntilMethod(rightStates, leftStates, acts[j], acts, newState, model);
                    } else {
                        return null;
                    }
                }
            }
        }
        return null;
    }
}
