package formula.stateFormula;

import com.sun.org.apache.xpath.internal.operations.Mod;
import model.Model;

public class Or extends StateFormula {
    public final StateFormula left;
    public final StateFormula right;
    public final Model model;

    public Or(StateFormula left, StateFormula right, Model model) {
        this.left = left;
        this.right = right;
        this.model = model;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        left.writeToBuffer(buffer);
        buffer.append(" || ");
        right.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public void checker(StringBuilder buffer) {
        buffer.append("(");
        left.checker(buffer);
        buffer.append(" |lalala| ");
        right.checker(buffer);
        buffer.append(")");
    }



}