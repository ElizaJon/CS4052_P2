package formula.stateFormula;

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

}
