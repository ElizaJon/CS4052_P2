package modelChecker;

import formula.PathTree;
import formula.stateFormula.StateFormula;
import model.Model;
import model.State;

import java.util.ArrayList;

public class SimpleModelChecker implements ModelChecker {

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        // TODO Auto-generated method stub
        System.out.println(constraint.toString());
        System.out.println(query.toString());

        System.out.println("\nNewer stuff");

        State[] allStates = model.getStates();
        PathTree root = new PathTree("");
        PathTree leftNode = new PathTree("");
        root.leftTree = leftNode;
        State[] resultStates = constraint.getStates(allStates, model, leftNode);

        System.out.println("Resulting states:");
        printStates(resultStates);
        printTree(root);
        System.out.println("Has initial states: " + setOfStatesHasInitialState(resultStates));

        return setOfStatesHasInitialState(resultStates);
    }

    private boolean setOfStatesHasInitialState(State[] resultStates){
        for(int i = 0; i < resultStates.length; i++){
            if(resultStates[i].isInit()){
                return true;
            }
        }
        return false;
    }

    private void print(ArrayList result){
        for(int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i) + " ");
        }
        System.out.println();
    }

    public static void printStates(State[] result){
        for(int i = 0; i < result.length; i++) {
            System.out.print(result[i].getName() + " ");
        }
        System.out.println();
    }

    @Override
    public String[] getTrace() {
        // TODO Auto-generated method stub
        return null;
    }

    private void printTree(PathTree root){
        if(root == null){
            return;
        }
        printTree(root.getLeftTree());
        System.out.print(root.getFormulaPart());
        printTree(root.getRightTree());
    }

}
