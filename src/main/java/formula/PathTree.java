package formula;

import model.State;

import java.util.Set;

/**
 * Class which lets us create a path tree with needed information.
 * This helps us, when we need to return a trace of failed formula
 */
public class PathTree {
    String formulaPart;
    private State[] acceptedStates;
    private boolean modelHolds;
    public PathTree leftTree;
    public PathTree rightTree;
    private Set<String> leftActions;
    private Set<String> rightActions;

    public PathTree(String formulaPart){
        this.formulaPart = formulaPart;
        this.acceptedStates = new State[0];
        this.leftTree = null;
        this.rightTree = null;
        this.modelHolds = false;
        this.leftActions = null;
        this.rightActions = null;
    }

    public void addAcceptedStates(State[] acceptedStates){
        this.acceptedStates = acceptedStates;
    }

    public void setFormulaPart(String formulaPart){
        this.formulaPart = formulaPart;
    }

    public void setModelHolds(Boolean holds){
        this.modelHolds = holds;
    }

    public String getFormulaPart() {
        return formulaPart;
    }

    public Boolean getModelHolds() { return modelHolds; }

    public State[] getAcceptedStates() {
        return acceptedStates;
    }

    public PathTree getLeftTree() {
        return leftTree;
    }

    public PathTree getRightTree() {
        return rightTree;
    }

    public void setLeftActions(Set<String> leftActions) {
        this.leftActions = leftActions;
    }

    public void setRightActions(Set<String> rightActions) {
        this.rightActions = rightActions;
    }

    public Set<String> getLeftActions() {
        return leftActions;
    }

    public Set<String> getRightActions() {
        return rightActions;
    }
}
