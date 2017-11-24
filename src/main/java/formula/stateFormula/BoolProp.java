package formula.stateFormula;

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
    public State[] getStates(State[] allStates) {
        if(value) {
            return allStates;
        } else {
            return new State[0];
        }
    }

}
