package formula.pathFormula;

import formula.FormulaParser;
import formula.PathTree;
import model.*;
import formula.stateFormula.*;
import java.util.*;

public class Always extends PathFormula {
    public final StateFormula stateFormula;
    private Set<String> actions = new HashSet<String>();

    public Always(StateFormula stateFormula, Set<String> actions) {
        this.stateFormula = stateFormula;
        this.actions = actions;
    }

    public Set<String> getActions() {
        return actions;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.ALWAYS_TOKEn);
        stateFormula.writeToBuffer(buffer);

    }

    @Override
    public State[] getStates(State[] allStates, Model model, PathTree pathTree) {
        pathTree.addAcceptedStates(allStates);
        pathTree.setFormulaPart(" G ");
        PathTree leftNode = new PathTree("");
        pathTree.leftTree = leftNode;
        State[] resultStates = stateFormula.getStates(allStates, model, leftNode);
        pathTree.setLeftActions(actions);
        if(resultStates.length > 0){
            pathTree.setModelHolds(true);
        }
        return resultStates;

    }
}
