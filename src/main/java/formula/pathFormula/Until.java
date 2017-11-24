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
        Transition[] transitions = model.getTransitions();
        State state;
        for(int i = 0; i < leftStates.length; i++){
            for(int j = 0; j < transitions.length; j++){
                if(leftStates[i].getName().equals(transitions[j].getSource())){
                    state = recursiveUntilMethod(rightStates, leftStates, transitions[j], leftStates[i]);
                    if(state != null && notInSet(leftStates[i], untilStates)){
                        untilStates.add(leftStates[i]);
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
}
