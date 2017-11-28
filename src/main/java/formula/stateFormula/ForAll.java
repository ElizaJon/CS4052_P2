package formula.stateFormula;

import formula.*;
import formula.pathFormula.*;
import model.State;
import model.*;
import modelChecker.SimpleModelChecker;

import java.util.*;

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
    public void checker(ArrayList buffer) {
        buffer.add("(");
        buffer.add(FormulaParser.FORALL_TOKEN);
        pathFormula.checker(buffer);
        buffer.add(")");
    }

    @Override
    public State[] getStates(State[] allStates, Model model, PathTree pathTree) {
        pathTree.setFormulaPart(" A ");
        PathTree leftNode = new PathTree("");
        pathTree.leftTree = leftNode;
        if(pathFormula instanceof Until || pathFormula instanceof Next){
            State[] resultStates = pathFormula.getStates(allStates, model,leftNode);
            State[] checkedAllStates = checkForAll(HelpMethods.getInitialStates(allStates), resultStates);
            System.out.println("For all method");
            SimpleModelChecker.printStates(allStates);
            SimpleModelChecker.printStates(resultStates);
            SimpleModelChecker.printStates(checkedAllStates);
            System.out.println("End of For all method");
            pathTree.addAcceptedStates(checkedAllStates);
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

            System.out.println("For all always!!!!!");
            SimpleModelChecker.printStates(allStates);
            SimpleModelChecker.printStates(alwaysStates);
            SimpleModelChecker.printStates(resultStates);
            System.out.println("end For all always!!!!!");
            State [] findAll = checkForAll(HelpMethods.getInitialStates(allStates), resultStates);
            pathTree.addAcceptedStates(findAll);
            return findAll;
        } else if(pathFormula instanceof Eventually){
            State[] rightStates = pathFormula.getStates(allStates, model,leftNode);
            Set<String> leftActions = ((Eventually) pathFormula).getLeftActions();
            Set<String> rightActions = ((Eventually) pathFormula).getRightActions();
            State[] resultStates = checkForAll(HelpMethods.getInitialStates(allStates), HelpMethods.getAllSatisfyingUntil(rightStates, allStates, leftActions, rightActions, model));
            pathTree.addAcceptedStates(resultStates);
            return resultStates;
        }
        return new State[0];
    }

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
