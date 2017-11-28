package formula.stateFormula;

import formula.PathTree;
import model.Model;
import model.State;

import java.util.ArrayList;

public class BoolProp extends StateFormula {
    public final boolean value;

    public BoolProp(boolean value) {
        this.value = value;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        String stringValue = (value) ? "True" : "False";
        buffer.append(" " + stringValue + " ");
    }

    @Override
    public void checker(ArrayList buffer) {
        String stringValue = (value) ? "True" : "False";
        buffer.add(" " + stringValue + " ");
    }

    @Override
    public State[] getStates(State[] allStates, Model model, PathTree pathTree) {
        pathTree.setFormulaPart((value) ? "True" : "False");
        if(value) {
            pathTree.addAcceptedStates(allStates);
            return allStates;
        } else {
            return new State[0];
        }
    }

}
