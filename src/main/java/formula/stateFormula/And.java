package formula.stateFormula;
import formula.PathTree;
import model.Model;
import model.State;

import java.util.ArrayList;

public class And extends StateFormula {
    public final StateFormula left;
    public final StateFormula right;

    public And(StateFormula left, StateFormula right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        left.writeToBuffer(buffer);
        buffer.append(" && ");
        right.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public State[] getStates(State[] allStates, Model model, PathTree pathTree) {
        pathTree.setFormulaPart(" && ");
        PathTree leftNode = new PathTree("");
        pathTree.leftTree = leftNode;
        PathTree rightNode = new PathTree("");
        pathTree.rightTree = rightNode;
        State[] leftStates, rightStates, andStates;
        leftStates = left.getStates(allStates, model,leftNode);
        rightStates = right.getStates(allStates, model, rightNode);
        andStates = getAndStates(leftStates, rightStates);

        pathTree.addAcceptedStates(andStates);
        if(andStates.length > 0){
            pathTree.setModelHolds(true);
        }
        return andStates;
    }

    //Method which gets states which satisfy left side and right side
    private State[] getAndStates(State[] leftStates, State[] rightStates){
        ArrayList<State> andStates = new ArrayList<>();
        for(int i = 0; i < leftStates.length; i++){
            for(int j = 0; j < rightStates.length; j++){
                if(leftStates[i].equals(rightStates[j])){
                    andStates.add(leftStates[i]);
                    break;
                }
            }
        }
        return andStates.toArray(new State[andStates.size()]);
    }

}