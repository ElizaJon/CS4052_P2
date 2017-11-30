package modelChecker;

import formula.HelpMethods;
import formula.PathTree;
import formula.stateFormula.StateFormula;
import model.Model;
import model.State;

import java.util.Set;

public class SimpleModelChecker implements ModelChecker {
    PathTree root;

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        // TODO Auto-generated method stub
        System.out.println(constraint.toString());
        System.out.println(query.toString());

        State[] allStates = model.getStates();
        PathTree constraintTree = new PathTree("");
        root = new PathTree("");
        State[] constraintStates = constraint.getStates(allStates, model, constraintTree);
        if(constraintStates.length == 0){
            return true;
        } else {
            PathTree leftNode = new PathTree("");
            root.leftTree = leftNode;
            State[] resultStates = query.getStates(constraintStates, model, leftNode);
            boolean result = setOfStatesHasInitialState(resultStates);
            root.setModelHolds(result);
            return result;
        }

    }

    private boolean setOfStatesHasInitialState(State[] resultStates){
        for(int i = 0; i < resultStates.length; i++){
            if(resultStates[i].isInit()){
                return true;
            }
        }
        return false;
    }

    public static void printStates(State[] result){
        for(int i = 0; i < result.length; i++) {
            System.out.print(result[i].getName() + " ");
        }
        System.out.println();
    }

    @Override
    public String getTrace() {
        String badPath = printFalseSide(root, "");
        return badPath;
    }

    private String printFalseSide(PathTree root, String st) {
        if (root != null && root.getFormulaPart().equals(" A ") && !root.getModelHolds()) {
            st = printTreeForA(root, st);
            return st;
        } else if (root != null && root.getFormulaPart().equals(" || ") && !root.getModelHolds()) {
            st = st + "<-- ||(or) fails, since: \n";
            st = printFalseSide(root.getLeftTree(), st) + " Also, \n";
            st = printFalseSide(root.getRightTree(), st);
            return st;
        } else if (root == null || root.getModelHolds()) {
            return st;
        } else {
            st = st + root.getFormulaPart();
            if (root.leftTree != null && root.leftTree.getModelHolds() || root.rightTree != null && root.rightTree.getModelHolds() || root.leftTree == null && root.rightTree == null) {
                st = st + reasons(root);
                return st;
            } else {
                st = printFalseSide(root.getLeftTree(), st);
                st = printFalseSide(root.getRightTree(), st);

                return st;
            }
        }
    }

    private String printTreeForA(PathTree root, String st){
        if(root == null){
            return st;
        }
        st = st + root.getFormulaPart();
        State[] statesWhichDoNotHold;
        boolean executed = false;
        if(root.leftTree != null && !root.getLeftTree().getModelHolds()) {
            st = printTreeForA(root.getLeftTree(), st);
        } else {
            statesWhichDoNotHold = HelpMethods.getStatesWhichNotInSet(root.getAcceptedStates(), root.leftTree.getAcceptedStates());
            st = st + " <-- A(for all) fails, since initial states " + getStates(statesWhichDoNotHold) + " are missing.";
            st = st + "\nThey are missing because :" + reasons(root.leftTree);
            executed = true;
        }
        if(root.rightTree != null && !root.rightTree.getModelHolds()){
            st = printTreeForA(root.getRightTree(), st);
        } else if(!executed){
            statesWhichDoNotHold = HelpMethods.getStatesWhichNotInSet(root.getAcceptedStates(), root.rightTree.getAcceptedStates());
            st = st + " <-- A(for all) fails, since initial states " + getStates(statesWhichDoNotHold) + " are missing.";
            st = st + "\nThey are missing because :" + reasons(root.leftTree);
        }

        return st;
    }

    private String reasons(PathTree root){
        String s = "";
        switch (root.getFormulaPart()){
            case "False":
                s = s + " <- Formula contains boolean False, which makes all paths to fail.";
                break;
            case " && ":
                s = s + " <-- &&(and) fails, since: ";
                if(root.leftTree != null && !root.leftTree.getModelHolds()){
                    s = printFalseSide(root.getLeftTree(), s);
                } else {
                    s = printFalseSide(root.getRightTree(), s);
                }
                break;
            case " E ":
                s = s + "<-- Formula fails E(there exists), since there are no paths satisfying required formula.";
                break;
            case " U ":
                s = s + root.leftTree.getFormulaPart() + " -> [ " + getStates(root.leftTree.getAcceptedStates()) + "] -> " + getActions(root.getLeftActions())
                        + " U " + getActions(root.getRightActions()) + " -> [" + getStates(root.rightTree.getAcceptedStates()) + "] - > [" + root.rightTree.getFormulaPart()
                        + "], these paths do not satisfy required conditions for all of the initial states.";
                break;
            case " X ":
                s = s + " <-- X(next) fails, since formula does not build any paths:\n " + root.getLeftActions() + " X -> [" + getStates(root.leftTree.getAcceptedStates()) + "], which satisfy: [" + root.leftTree.getFormulaPart()
                        + "]";
                break;
            default:
                s = " <-- Formula contains atomic proposition " + root.getFormulaPart() + ", which is not satisfied in given states, so all paths fail.";
                break;
        }
        return s;
    }

    private String getActions(Set<String> actions){
        if(actions.size() == 0){
            return "[All transitions]";
        } else {
            return actions.toString();
        }
    }

    private String getStates(State[] states){
        String s = "";
        for(int i = 0; i < states.length; i++){
            s = s + states[i].getName() + " ";
        }
        return s;
    }

}
