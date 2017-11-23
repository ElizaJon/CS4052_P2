package formula.stateFormula;

import formula.FormulaParser;

import java.util.ArrayList;

public class Not extends StateFormula {
    public final StateFormula stateFormula;

    public Not(StateFormula stateFormula) {
        this.stateFormula = stateFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append(FormulaParser.NOT_TOKEN);
        buffer.append("(");
        stateFormula.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public void checker(ArrayList buffer) {
        stateFormula.checker(buffer);
        if(buffer.get(buffer.size()-1).equals(true)){
            buffer.remove(buffer.get(buffer.size()-1));
            buffer.add(false);
        } else {
            buffer.remove(buffer.get(buffer.size()-1));
            buffer.add(true);
        }
    }


}
