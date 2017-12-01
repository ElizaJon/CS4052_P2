package formula.pathFormula;

import formula.FormulaParser;
import formula.PathTree;
import formula.stateFormula.*;
import model.Model;
import model.State;

import java.util.*;

public class Eventually extends PathFormula {
    public final StateFormula stateFormula;
    private Set<String> leftActions;
    private Set<String> rightActions;

    public Eventually(StateFormula stateFormula, Set<String> leftActions, Set<String> rightActions) {
        super();
        this.stateFormula = stateFormula;
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
        buffer.append(FormulaParser.EVENTUALLY_TOKEN);
        stateFormula.writeToBuffer(buffer);
    }

    @Override
    public State[] getStates(State[] allStates, Model model, PathTree pathTree) {
        pathTree.addAcceptedStates(allStates);
        pathTree.setFormulaPart(" F ");
        PathTree leftNode = new PathTree("");
        pathTree.leftTree = leftNode;
        pathTree.setLeftActions(leftActions);
        pathTree.setRightActions(rightActions);
        State[] resultStates = stateFormula.getStates(allStates, model, leftNode);
        if(resultStates.length > 0){
            pathTree.setModelHolds(true);
        }
        return resultStates;
    }
}
