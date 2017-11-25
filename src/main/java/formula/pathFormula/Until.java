package formula.pathFormula;

import formula.*;
import formula.stateFormula.*;
import model.*;
import modelChecker.SimpleModelChecker;

import java.util.*;

public class Until extends PathFormula {
    public final StateFormula left;
    public final StateFormula right;
    private Set<String> leftActions;
    private Set<String> rightActions;
    private Model model;

    public Until(StateFormula left, StateFormula right, Set<String> leftActions, Set<String> rightActions, Model model) {
        super();
        this.left = left;
        this.right = right;
        this.leftActions = leftActions;
        this.rightActions = rightActions;
        this.model = model;
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
    public State[] getStates(State[] allStates) {
        State[] statesSatisfyingRight = right.getStates(allStates);
        State[] statesSatisfyingLeft = left.getStates(allStates);
        State[] untilStates = getAllSatisfyingUntil(statesSatisfyingRight, statesSatisfyingLeft);

        System.out.println("Until method");
        SimpleModelChecker.printStates(allStates);
        SimpleModelChecker.printStates(statesSatisfyingLeft);
        SimpleModelChecker.printStates(statesSatisfyingRight);
        SimpleModelChecker.printStates(untilStates);
        System.out.println("End of Until method");

        return untilStates;
    }

    private State[] getAllSatisfyingUntil(State[] rightStates, State[] leftStates){
        ArrayList<State> untilStates = new ArrayList<>();
        State state;
        if(leftActions.size() == 0 && rightActions.size() == 0) {
            Transition[] transitions = model.getTransitions();
            for (int i = 0; i < leftStates.length; i++) {
                for (int j = 0; j < transitions.length; j++) {
                    if (leftStates[i].getName().equals(transitions[j].getSource())) {
                        state = recursiveUntilMethod(rightStates, leftStates, transitions[j], leftStates[i]);
                        if (state != null && notInSet(leftStates[i], untilStates)) {
                            untilStates.add(leftStates[i]);
                        }
                    }
                }
            }
        } else {
            Transition[] leftTransitions = getTransitions(leftActions);
            Transition[] rightTransitions = getTransitions(rightActions);
            State[] states;
            if(leftActions.size() == 0){
                for(int i = 0; i <= leftTransitions.length; i++){
                    states = getActionPossibilityStates(getSubTransitions(leftTransitions, i), rightTransitions, rightStates, leftStates);
                    if (states.length != 0) {
                        untilStates = getUpdatedUntilStates(states, untilStates);
                    }
                }
            } else if(rightActions.size() == 0){
                for(int i = 0; i <= rightTransitions.length; i++){
                    states = getActionPossibilityStates(leftTransitions, getSubTransitions(rightTransitions, i), rightStates, leftStates);
                    if (states.length != 0) {
                        untilStates = getUpdatedUntilStates(states, untilStates);
                    }
                }
            } else {
                for(int j = 1; j <= leftTransitions.length; j++) {
                    for (int i = 1; i <= rightTransitions.length; i++) {
                        states = getActionPossibilityStates(getSubTransitions(leftTransitions, j), getSubTransitions(rightTransitions, i), rightStates, leftStates);
                        if (states.length != 0) {
                            untilStates = getUpdatedUntilStates(states, untilStates);
                        }
                    }
                }
            }
        }

        return untilStates.toArray(new State[untilStates.size()]);
    }

    private State[] getActionPossibilityStates(Transition[] leftTransitions, Transition[] rightTransitions, State[] rightStates, State[] leftStates){
        ArrayList<State> untilStates = new ArrayList<>();
        ArrayList<State> helpStates = new ArrayList<>();
        ArrayList<State> possibleToGet = new ArrayList<>();
        State state;
        for(int i = 0; i < leftTransitions.length; i++){
            for(int j = 0; j < leftStates.length; j++){
                if(leftStates[j].getName().equals(leftTransitions[i].getSource())){
                    state = recursiveUntilMethod(leftStates, leftStates, leftTransitions[i], leftStates[j]);
                    State actualState = getActualState(leftTransitions[i].getTarget());
                    if (state != null && notInSet(actualState, possibleToGet)) {
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
                    state = recursiveUntilMethod(rightStates, possibleToGetGood, rightTransitions[i], possibleToGetGood[j]);
                    if (state != null && notInSet(helpStatesGood[j], untilStates)) {
                        untilStates.add(helpStatesGood[j]);
                    }
                }
            }
        }
        return untilStates.toArray(new State[untilStates.size()]);
    }

    private State recursiveUntilMethod(State[] rightStates, State[] leftStates, Transition act, State currentState){
        State newState = getActualState(act.getTarget());
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
                leftStates = removeElement(leftStates, newState);
                if(act.getSource().equals(newState.getName())){
                    return recursiveUntilMethod(rightStates, leftStates, act, newState);
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    private Transition[] getTransitions(Set<String> actions){
        Transition[] allTransitions = model.getTransitions();
        if(actions.size() == 0){
            return allTransitions;
        }
        String[] transitionActions;
        boolean check;
        ArrayList<Transition> newTransitions = new ArrayList<>();
        for(int i = 0; i < allTransitions.length; i++){
            transitionActions = allTransitions[i].getActions();
            check = false;
            for(int k = 0; k < transitionActions.length; k++){
                for(int j = 0; j < actions.size(); j++){
                    if(actions.contains(transitionActions[k])) {
                        newTransitions.add(allTransitions[i]);
                        check = true;
                        break;
                    }
                }
                if(check){
                    break;
                }
            }
        }
        return newTransitions.toArray(new Transition[newTransitions.size()]);
    }

    private State getActualState(String label){
        State[] allStates = model.getStates();
        for(int i = 0; i < allStates.length; i++){
            if(label.equals(allStates[i].getName())){
                return  allStates[i];
            }
        }
        return null;
    }

    private State[] removeElement(State[] allStates, State state){
        ArrayList<State> newStates = new ArrayList<>();
        for(int i = 0; i < allStates.length; i++){
            if(!allStates[i].equals(state)){
                newStates.add(allStates[i]);
            }
        }
        return newStates.toArray(new State[newStates.size()]);
    }

    private boolean notInSet(State state, ArrayList allStates){
        for(int i = 0; i < allStates.size(); i++){
            if(state.equals(allStates.get(i))){
                return false;
            }
        }
        return true;
    }

    private ArrayList<State> getUpdatedUntilStates(State[] newStates, ArrayList<State> untilStates){
        int exists;
        for(int j = 0; j < newStates.length; j++){
            exists = -1;
            for(int i = 0; i < untilStates.size(); i++){
                if(untilStates.get(i).equals(newStates[j])){
                    exists = j;
                    break;
                }
            }
            if(exists == -1){
                untilStates.add(newStates[j]);
            }
        }
        return untilStates;
    }

    private Transition[] getSubTransitions(Transition[] transitions, int i){
        Transition[] newTransitions = new Transition[i];
        for(int j = 0; j < i; j++){
            newTransitions[j] = transitions[j];
        }
        return newTransitions;
    }
}
