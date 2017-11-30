package formula.stateFormula;

import formula.FormulaParser;
import formula.HelpMethods;
import formula.PathTree;
import formula.pathFormula.*;
import model.Model;
import model.State;
import model.Transition;

import java.util.ArrayList;
import java.util.Set;

public class ThereExists extends StateFormula {
    public final PathFormula pathFormula;
    public ThereExists(PathFormula pathFormula) {
        this.pathFormula = pathFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        buffer.append(FormulaParser.THEREEXISTS_TOKEN);
        pathFormula.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public void checker(ArrayList buffer) {
        buffer.add("(");
        buffer.add(FormulaParser.THEREEXISTS_TOKEN);
        pathFormula.checker(buffer);
        buffer.add(")");
    }

    @Override
    public State[] getStates(State[] allStates, Model model, PathTree pathTree) {
        pathTree.setFormulaPart(" E ");
        PathTree leftNode = new PathTree("");
        pathTree.leftTree = leftNode;
        if(pathFormula instanceof Until || pathFormula instanceof Next){
            State[] resultStates = pathFormula.getStates(allStates, model,leftNode);
            State[] checkedAllStates = checkForThereExists(HelpMethods.getInitialStates(allStates), resultStates);

            /*
            System.out.println("For there exists method");
            SimpleModelChecker.printStates(allStates);
            SimpleModelChecker.printStates(resultStates);
            SimpleModelChecker.printStates(checkedAllStates);
            System.out.println("End of For there exists method");
            */

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

            /*
            System.out.println("There exists always!!!!!");
            SimpleModelChecker.printStates(allStates);
            SimpleModelChecker.printStates(alwaysStates);
            SimpleModelChecker.printStates(resultStates);
            System.out.println("end There exists always!!!!!");
            */

            State[] findIntersecting = intersectingStates(alwaysStates, resultStates);
            if(findIntersecting.length > 0){
                pathTree.setModelHolds(true);
                pathTree.addAcceptedStates(findIntersecting);
            } else {
                pathTree.addAcceptedStates(HelpMethods.getInitialStates(allStates));
            }
            return findIntersecting;

        } else if(pathFormula instanceof Eventually){
            State[] rightStates = pathFormula.getStates(allStates, model,leftNode);
            Set<String> leftActions = ((Eventually) pathFormula).getLeftActions();
            Set<String> rightActions = ((Eventually) pathFormula).getRightActions();
            State[] resultStates = checkForThereExists(HelpMethods.getInitialStates(allStates), HelpMethods.getAllSatisfyingUntil(rightStates, allStates, leftActions, rightActions, model));
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

    private State[] intersectingStates(State[] set1, State[] set2){
        ArrayList<State> matchingStates = new ArrayList<>();
        boolean check;
        for(int i = 0; i < set1.length; i++){
            check = false;
            for(int j = 0; j < set2.length; j++){
                if(set1[i].equals(set2[j])){
                    check = true;
                    break;
                }
            }
            if(check){
                matchingStates.add(set1[i]);
            }
        }
        return matchingStates.toArray(new State[matchingStates.size()]);
    }

    private State[] checkForThereExists(State[] initialStates, State[] resultStates){
        ArrayList<State> thereExistsStates = new ArrayList<>();
        for(int i = 0; i < initialStates.length; i++){
            for(int j = 0; j < resultStates.length; j++){
                if(initialStates[i].equals(resultStates[j])){
                    thereExistsStates.add(initialStates[i]);
                    break;
                }
            }
        }
        return thereExistsStates.toArray(new State[thereExistsStates.size()]);
    }

}
