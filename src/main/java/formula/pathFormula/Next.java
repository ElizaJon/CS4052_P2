package formula.pathFormula;

import formula.FormulaParser;
import formula.HelpMethods;
import formula.PathTree;
import formula.stateFormula.*;
import model.Model;
import model.State;
import model.Transition;
import modelChecker.SimpleModelChecker;

import java.util.ArrayList;
import java.util.Set;

public class Next extends PathFormula {
    public final StateFormula stateFormula;
    private Set<String> actions;

    public Next(StateFormula stateFormula, Set<String> actions) {
        this.stateFormula = stateFormula;
        this.actions = actions;
    }

    public Set<String> getActions() {
        return actions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.NEXT_TOKEN);
        stateFormula.writeToBuffer(buffer);
    }

    @Override
    public void checker(ArrayList buffer) {
        buffer.add(FormulaParser.NEXT_TOKEN);
        stateFormula.checker(buffer);
    }

    @Override
    public State[] getStates(State[] allStates, Model model, PathTree pathTree) {
        pathTree.setFormulaPart(" X ");
        PathTree leftNode = new PathTree("");
        pathTree.leftTree = leftNode;
        State[] resultStates = stateFormula.getStates(allStates, model, leftNode);
        State[] nextStates = getNextStates(allStates, model);
        State[] matchingStates = getMatchingStates(resultStates, nextStates);

        System.out.println("Next method");
        SimpleModelChecker.printStates(allStates);
        SimpleModelChecker.printStates(resultStates);
        SimpleModelChecker.printStates(nextStates);
        SimpleModelChecker.printStates(matchingStates);
        System.out.println("End of Next method");

        pathTree.addAcceptedStates(matchingStates);
        System.out.println(pathTree.getFormulaPart() + "vatvat");
        System.out.println(pathTree.getLeftTree().getFormulaPart() + "vatvat");
        return matchingStates;
    }

    private State[] getMatchingStates(State[] resultStates, State[] nextStates){
        ArrayList<State> matchingStates = new ArrayList<>();
        for(int i = 0; i < resultStates.length; i++){
            for(int j = 0; j < nextStates.length; j++){
                if(resultStates[i].equals(nextStates[j])){
                    matchingStates.add(resultStates[i]);
                }
            }
        }
        return matchingStates.toArray(new State[matchingStates.size()]);
    }

    private State[] getNextStates(State[] allStates, Model model){
        ArrayList<State> nextStates = new ArrayList<>();
        Transition[] transitionsToUse = model.getTransitions(actions);
        for(int i = 0; i < allStates.length; i++){
            for(int j = 0; j < transitionsToUse.length; j++){
                if(transitionsToUse[j].getSource().equals(allStates[i].getName())){
                    State state = HelpMethods.getActualState(transitionsToUse[j].getTarget(), model);
                    if(state != null && HelpMethods.notInSet(state, nextStates)) {
                        nextStates.add(state);
                        break;
                    }
                }
            }
        }
        return nextStates.toArray(new State[nextStates.size()]);
    }

}
