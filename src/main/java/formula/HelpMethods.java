package formula;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by Eliza on 28/11/2017.
 */
public class HelpMethods {

    public static State[] getAlwaysStates(State[] alwaysStates, Transition[] transitions, Model model){
        ArrayList<State> resultStates = new ArrayList<>(Arrays.asList(alwaysStates));
        State[] states;
        for(int i = 0; i < alwaysStates.length; i++){
            for(int j = 0; j < transitions.length; j++){
                states = getStatesItCanReach(alwaysStates[i], transitions[j], transitions, new ArrayList<>(), model);
                if (states.length != 0) {
                    resultStates = HelpMethods.getUpdatedUntilStates(states, resultStates);
                }
            }
        }
        return resultStates.toArray(new State[resultStates.size()]);
    }

    public static State[] getStatesItCanReach(State state, Transition t, Transition[] transitions, ArrayList<State> newStates, Model model){
        State[] states;
        if(t.getSource().equals(state.getName())){
            state = HelpMethods.getActualState(t.getTarget(), model);
            if(HelpMethods.notInSet(state, newStates)) {
                newStates.add(state);
                for (int i = 0; i < transitions.length; i++) {
                    states = getStatesItCanReach(state, transitions[i], transitions, newStates, model);
                    if (states.length != 0) {
                        newStates = HelpMethods.getUpdatedUntilStates(states, newStates);
                    }
                }
            }
            return newStates.toArray(new State[newStates.size()]);
        } else {
            return  newStates.toArray(new State[newStates.size()]);
        }
    }
    public static ArrayList<State> getUpdatedUntilStates(State[] newStates, ArrayList<State> untilStates){
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

    public static State getActualState(String label, Model model){
        State[] allStates = model.getStates();
        for(int i = 0; i < allStates.length; i++){
            if(label.equals(allStates[i].getName())){
                return  allStates[i];
            }
        }
        return null;
    }

    public static boolean notInSet(State state, ArrayList allStates){
        for(int i = 0; i < allStates.size(); i++){
            if(state.equals(allStates.get(i))){
                return false;
            }
        }
        return true;
    }

    public static Transition[] getSubTransitions(Transition[] transitions, int i){
        Transition[] newTransitions = new Transition[i];
        for(int j = 0; j < i; j++){
            newTransitions[j] = transitions[j];
        }
        return newTransitions;
    }

    public static State[] removeElement(State[] allStates, State state){
        ArrayList<State> newStates = new ArrayList<>();
        for(int i = 0; i < allStates.length; i++){
            if(!allStates[i].equals(state)){
                newStates.add(allStates[i]);
            }
        }
        return newStates.toArray(new State[newStates.size()]);
    }

    public static boolean checkIfSubset(State[] alwaysStates, State[] resultStates){
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

    public static State[] getAllSatisfyingUntil(State[] rightStates, State[] leftStates, Set<String> leftActions, Set<String> rightActions, Model model){
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
                    states = getActionPossibilityStates(getSubTransitions(leftTransitions, i), rightTransitions, rightStates, leftStates, model);
                    if (states.length != 0) {
                        untilStates = HelpMethods.getUpdatedUntilStates(states, untilStates);
                    }
                }
            } else if(rightActions.size() == 0){
                for(int i = 0; i <= rightTransitions.length; i++){
                    states = getActionPossibilityStates(leftTransitions, getSubTransitions(rightTransitions, i), rightStates, leftStates, model);
                    if (states.length != 0) {
                        untilStates = HelpMethods.getUpdatedUntilStates(states, untilStates);
                    }
                }
            } else {
                for(int j = 1; j <= leftTransitions.length; j++) {
                    for (int i = 1; i <= rightTransitions.length; i++) {
                        states = getActionPossibilityStates(getSubTransitions(leftTransitions, j), HelpMethods.getSubTransitions(rightTransitions, i), rightStates, leftStates, model);
                        if (states.length != 0) {
                            untilStates = HelpMethods.getUpdatedUntilStates(states, untilStates);
                        }
                    }
                }
            }
        }

        return untilStates.toArray(new State[untilStates.size()]);
    }

    private static State[] getActionPossibilityStates(Transition[] leftTransitions, Transition[] rightTransitions, State[] rightStates, State[] leftStates, Model model){
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
                        untilStates.add(getRealPath(helpStatesGood[j], helpStatesGood, possibleToGetGood));
                    }
                }
            }
        }
        return untilStates.toArray(new State[untilStates.size()]);
    }

    public static State getRealPath(State state, State[] helpStates, State[] possibleToGetGood){
        if(state.isInit()){
            return state;
        } else {
            int t = 0;
            for(int i = 0; i < possibleToGetGood.length; i++){
                if(possibleToGetGood[i].equals(state)){
                    t = i;
                    break;
                }
            }
            return getRealPath(helpStates[t], helpStates, possibleToGetGood);
        }

    }

    public static State[] getInitialStates(State[] states){
        ArrayList<State> initialStates = new ArrayList<>();
        for(int i = 0; i < states.length; i++){
            if(states[i].isInit()){
                initialStates.add(states[i]);
            }
        }
        return initialStates.toArray(new State[initialStates.size()]);
    }

    private static State recursiveUntilMethod(State[] rightStates, State[] leftStates, Transition act, Transition[] acts, State currentState, Model model){
        State newState = getActualState(act.getTarget(), model);
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
