package formula.stateFormula;

import formula.*;
import formula.pathFormula.*;
import model.State;
import model.*;
import modelChecker.SimpleModelChecker;

import java.util.*;

public class ForAll extends StateFormula {
    public final PathFormula pathFormula;
    public Model model;

    public ForAll(PathFormula pathFormula, Model model) {
        this.pathFormula = pathFormula;
        this.model = model;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        buffer.append(FormulaParser.FORALL_TOKEN);
        pathFormula.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public void checker(ArrayList buffer) {
        buffer.add("(");
        buffer.add(FormulaParser.FORALL_TOKEN);
        pathFormula.checker(buffer);
        buffer.add(")");
    }

    @Override
    public State[] getStates(State[] allStates) {
        if(pathFormula instanceof Until || pathFormula instanceof Next){
            State[] resultStates = pathFormula.getStates(allStates);
            State[] checkedAllStates = checkForAll(allStates, resultStates);
            System.out.println("For all method");
            SimpleModelChecker.printStates(allStates);
            SimpleModelChecker.printStates(resultStates);
            SimpleModelChecker.printStates(checkedAllStates);
            System.out.println("End of For all method");
            return checkedAllStates;
        } else if(pathFormula instanceof Always){
            State[] alwaysStates = pathFormula.getStates(allStates);
            Set<String> actions = ((Always) pathFormula).getActions();
            Transition[] transitions;
            if(actions.size() == 0){
                transitions = model.getTransitions();
            } else {
                transitions = model.getTransitions(actions);
            }
            State[] resultStates = getForAllAlwaysStates(alwaysStates, transitions);

            System.out.println("For all always!!!!!");
            SimpleModelChecker.printStates(allStates);
            SimpleModelChecker.printStates(alwaysStates);
            SimpleModelChecker.printStates(resultStates);
            System.out.println("end For all always!!!!!");
            if(checkIfSubset(alwaysStates, resultStates)){
                return alwaysStates;
            } else {
                return new State[0];
            }
        } else if(pathFormula instanceof Eventually){

        }
        return new State[0];
    }

    private boolean checkIfSubset(State[] alwaysStates, State[] resultStates){
        boolean check;
        for(int i = 0; i < resultStates.length; i++){
            check = false;
            for(int j = 0; j < alwaysStates.length; j++){
                if(alwaysStates[j].equals(resultStates[i])){
                    check = true;
                    break;
                }
            }
            if(!check){
                return false;
            }
        }
        return true;
    }
    private State[] getForAllAlwaysStates(State[] alwaysStates, Transition[] transitions){
        ArrayList<State> resultStates = new ArrayList<>(Arrays.asList(alwaysStates));
        State[] states;
        for(int i = 0; i < alwaysStates.length; i++){
            for(int j = 0; j < transitions.length; j++){
                states = getStatesItCanReach(alwaysStates[i], transitions[j], transitions, new ArrayList<>());
                if (states.length != 0) {
                    resultStates = getUpdatedUntilStates(states, resultStates);
                }
            }
        }
        return resultStates.toArray(new State[resultStates.size()]);
    }

    private State[] getStatesItCanReach(State state, Transition t, Transition[] transitions, ArrayList<State> newStates){
        State[] states;
        if(t.getSource().equals(state.getName())){
            state = getActualState(t.getTarget());
            if(notInSet(state, newStates)) {
                newStates.add(state);
                for (int i = 0; i < transitions.length; i++) {
                    states = getStatesItCanReach(state, transitions[i], transitions, newStates);
                    if (states.length != 0) {
                        newStates = getUpdatedUntilStates(states, newStates);
                    }
                }
            }
            return newStates.toArray(new State[newStates.size()]);
        } else {
            return  newStates.toArray(new State[newStates.size()]);
        }
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

    private State[] checkForAll(State[] allStates, State[] resultStates){
        if(allStates.length != resultStates.length){
            return new State[0];
        } else {
            int check = 0;
            for(int i = 0; i < allStates.length; i++){
                for(int j = 0; j < resultStates.length; j++){
                    if(allStates[i].equals(resultStates[j])){
                        check++;
                        break;
                    }
                }
            }
            if(check == allStates.length){
                return allStates;
            } else {
                return new State[0];
            }
        }
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

    private boolean notInSet(State state, ArrayList allStates){
        for(int i = 0; i < allStates.size(); i++){
            if(state.equals(allStates.get(i))){
                return false;
            }
        }
        return true;
    }
}
