package formula.stateFormula;

import formula.PathTree;
import model.Model;
import model.State;

import java.util.ArrayList;

public class Or extends StateFormula {
    public final StateFormula left;
    public final StateFormula right;

    public Or(StateFormula left, StateFormula right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        left.writeToBuffer(buffer);
        buffer.append(" || ");
        right.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public State[] getStates(State[] allStates, Model model, PathTree pathTree) {
        pathTree.setFormulaPart(" || ");
        PathTree leftNode = new PathTree("");
        pathTree.leftTree = leftNode;
        PathTree rightNode = new PathTree("");
        pathTree.rightTree = rightNode;
        State[] leftStates, rightStates, orStates;
        leftStates = left.getStates(allStates, model,leftNode);
        rightStates = right.getStates(allStates, model,rightNode);
        orStates = getOrStates(leftStates, rightStates, allStates);

        /*
        System.out.println("Or states");
        SimpleModelChecker.printStates(leftStates);
        SimpleModelChecker.printStates(rightStates);
        SimpleModelChecker.printStates(orStates);
        System.out.println("End of Or states");
        */

        pathTree.addAcceptedStates(orStates);
        if(orStates.length > 0){
            pathTree.setModelHolds(true);
        }
        return orStates;
    }

    private State[] getOrStates(State[] leftStates, State[] rightStates, State[] allStates){
        ArrayList<State> orStates = new ArrayList<>();
        boolean check;
        for(int i = 0; i < allStates.length; i++){
            check = false;
            for(int j = 0; j < leftStates.length; j++){
                if(allStates[i].equals(leftStates[j])){
                    orStates.add(allStates[i]);
                    check = true;
                    break;
                }
            }
            if(!check){
                for(int j = 0; j < rightStates.length; j++){
                    if(allStates[i].equals(rightStates[j])){
                        orStates.add(allStates[i]);
                        break;
                    }
                }
            }
        }
        return orStates.toArray(new State[orStates.size()]);
    }

    @Override
    public void checker(ArrayList buffer) {
        buffer.add((buffer));
    }

}