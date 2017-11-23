package formula.stateFormula;

import java.util.ArrayList;

public abstract class StateFormula {
    public abstract void writeToBuffer(StringBuilder buffer);
    public abstract void checker(ArrayList buffer);

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        writeToBuffer(buffer);
        return buffer.toString();
    }

}
