package formula;

import model.State;

/**
 * Created by Eliza on 28/11/2017.
 */
public class PathTree {
    String formulaPart;
    private State[] acceptedStates;
    public PathTree leftTree;
    public PathTree rightTree;

    public PathTree(String formulaPart){
        this.formulaPart = formulaPart;
        this.acceptedStates = new State[0];
        this.leftTree = null;
        this.rightTree = null;
    }

    public void addAcceptedStates(State[] acceptedStates){
        this.acceptedStates = acceptedStates;
    }

    public void setFormulaPart(String formulaPart){
        this.formulaPart = formulaPart;
    }

    public String getFormulaPart() {
        return formulaPart;
    }

    public State[] getAcceptedStates() {
        return acceptedStates;
    }

    public PathTree getLeftTree() {
        return leftTree;
    }

    public PathTree getRightTree() {
        return rightTree;
    }

}
