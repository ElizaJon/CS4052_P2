package formula.stateFormula;

import formula.FormulaParser;
import formula.HelpMethods;
import formula.PathTree;
import formula.pathFormula.*;
import model.Model;
import model.State;
import model.Transition;

import java.util.Set;

public class ForAll extends StateFormula {
    public final PathFormula pathFormula;

    public ForAll(PathFormula pathFormula) {
        this.pathFormula = pathFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        buffer.append(FormulaParser.FORALL_TOKEN);
        pathFormula.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public State[] getStates(State[] allStates, Model model, PathTree pathTree) {
        pathTree.setFormulaPart(" A ");
        PathTree leftNode = new PathTree("");
        pathTree.leftTree = leftNode;
        if(pathFormula instanceof Until || pathFormula instanceof Next){
            State[] resultStates = pathFormula.getStates(allStates, model,leftNode);
            State[] checkedAllStates = checkForAll(HelpMethods.getInitialStates(allStates), resultStates);

            if(checkedAllStates.length > 0){
                pathTree.setModelHolds(true);
                pathTree.addAcceptedStates(checkedAllStates);
            } else {
                pathTree.addAcceptedStates(HelpMethods.getInitialStates(allStates));
            }
            return checkedAllStates;
        } else if(pathFormula instanceof Always){
            State[] alwaysStates = pathFormula.getStates(allStates, model,leftNode);
            Set<String> actions = ((Always) pathFormula).getActions();
            Transition[] transitions;
            if(actions.size() == 0){
                transitions = model.getTransitions();
            } else {
                transitions = model.getTransitions(actions);
            }
            State[] resultStates = HelpMethods.getAlwaysStates(alwaysStates, transitions, model);

            State [] findAll = checkForAll(HelpMethods.getInitialStates(allStates), resultStates);
            if(findAll.length > 0){
                pathTree.setModelHolds(true);
                pathTree.addAcceptedStates(findAll);
            } else {
                pathTree.addAcceptedStates(HelpMethods.getInitialStates(allStates));
            }
            return findAll;
        } else if(pathFormula instanceof Eventually){
            State[] rightStates = pathFormula.getStates(model.getStates(), model,leftNode);
            Set<String> leftActions = ((Eventually) pathFormula).getLeftActions();
            Set<String> rightActions = ((Eventually) pathFormula).getRightActions();
            State[] resultStates = checkForAll(HelpMethods.getInitialStates(allStates), HelpMethods.getAllSatisfyingUntil(rightStates, allStates, leftActions, rightActions, model));
            if(resultStates.length > 0){
                pathTree.setModelHolds(true);
                pathTree.addAcceptedStates(resultStates);
            } else {
                pathTree.addAcceptedStates(HelpMethods.getInitialStates(allStates));
            }
            return resultStates;
        }
        return new State[0];
    }

    //Method checks if all initial states are satisfied by the following formulas
    private State[] checkForAll(State[] initialStates, State[] resultStates){
        int check = 0;
        for(int i = 0; i < initialStates.length; i++){
            for(int j = 0; j < resultStates.length; j++){
                if(initialStates[i].equals(resultStates[j])){
                    check++;
                    break;
                }
            }
        }
        if(check == initialStates.length){
            return initialStates;
        } else {
            return new State[0];
        }
    }
}
