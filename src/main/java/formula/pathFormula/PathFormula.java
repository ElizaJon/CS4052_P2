package formula.pathFormula;

import formula.PathTree;
import model.Model;
import model.State;

public abstract class PathFormula {
    public abstract void writeToBuffer(StringBuilder buffer);
    public abstract State[] getStates(State[] allStates, Model model, PathTree pathTree);
}
