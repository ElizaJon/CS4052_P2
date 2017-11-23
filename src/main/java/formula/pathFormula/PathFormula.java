package formula.pathFormula;

import java.util.ArrayList;

public abstract class PathFormula {
    public abstract void writeToBuffer(StringBuilder buffer);
    public abstract void checker(ArrayList buffer);
}
