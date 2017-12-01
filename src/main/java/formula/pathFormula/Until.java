package formula.pathFormula;

import formula.FormulaParser;
import formula.HelpMethods;
import formula.PathTree;
import formula.stateFormula.StateFormula;
import model.Model;
import model.State;

import java.util.Set;

public class Until extends PathFormula {
    public final StateFormula left;
    public final StateFormula right;
    private Set<String> leftActions;
    private Set<String> rightActions;

    public Until(StateFormula left, StateFormula right, Set<String> leftActions, Set<String> rightActions) {
        super();
        this.left = left;
        this.right = right;
        this.leftActions = leftActions;
        this.rightActions = rightActions;
    }

    public Set<String> getLeftActions() {
        return leftActions;
    }

    public Set<String> getRightActions() {
        return rightActions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        left.writeToBuffer(buffer);
        buffer.append(" " + FormulaParser.UNTIL_TOKEN + " ");
        right.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public State[] getStates(State[] allStates, Model model, PathTree pathTree) {
        pathTree.setFormulaPart(" U ");
        PathTree leftNode = new PathTree("");
        pathTree.leftTree = leftNode;
        PathTree rightNode = new PathTree("");
        pathTree.rightTree = rightNode;
        State[] statesSatisfyingLeft = left.getStates(allStates, model, leftNode);
        State[] statesSatisfyingRight = right.getStates(model.getStates(), model, rightNode);

        State[] untilStates = HelpMethods.getAllSatisfyingUntil(statesSatisfyingRight, statesSatisfyingLeft, leftActions, rightActions, model);

        pathTree.addAcceptedStates(untilStates);
        pathTree.setLeftActions(leftActions);
        pathTree.setRightActions(rightActions);
        if(untilStates.length > 0){
            pathTree.setModelHolds(true);
        }
        return untilStates;
    }
}
