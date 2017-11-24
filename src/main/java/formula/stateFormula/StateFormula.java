package formula.stateFormula;

import model.*;
import java.util.ArrayList;

public abstract class StateFormula {
    public abstract void writeToBuffer(StringBuilder buffer);
    public abstract void checker(ArrayList buffer);
    public abstract State[] getStates(State[] allStates);

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        writeToBuffer(buffer);
        return buffer.toString();
    }

}
