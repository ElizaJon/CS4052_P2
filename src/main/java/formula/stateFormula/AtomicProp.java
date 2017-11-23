package formula.stateFormula;

import java.util.ArrayList;

public class AtomicProp extends StateFormula {
    public final String label;

    public AtomicProp(String label) {
        this.label = label;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(" " + label + " ");
    }

    @Override
    public void checker(ArrayList buffer) {
        buffer.add(" " + label + " ");
    }

}
