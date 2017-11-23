package formula.stateFormula;

import formula.FormulaParser;
import formula.pathFormula.PathFormula;

import java.util.ArrayList;

public class ThereExists extends StateFormula {
    public final PathFormula pathFormula;

    public ThereExists(PathFormula pathFormula) {
        this.pathFormula = pathFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        buffer.append(FormulaParser.THEREEXISTS_TOKEN);
        pathFormula.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public void checker(ArrayList buffer) {
        buffer.add("(");
        buffer.add(FormulaParser.THEREEXISTS_TOKEN);
        pathFormula.checker(buffer);
        buffer.add(")");
    }
}
