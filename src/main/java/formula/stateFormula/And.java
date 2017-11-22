package formula.stateFormula;
import model.*;

public class And extends StateFormula {
    public final StateFormula left;
    public final StateFormula right;
    public final Model model;

    public And(StateFormula left, StateFormula right, Model model) {
        this.left = left;
        this.right = right;
        this.model = model;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        left.writeToBuffer(buffer);
        buffer.append(" && ");
        //buffer.append(checkCondition());
        right.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public void checker(StringBuilder buffer) {
        buffer.append("(");
        left.checker(buffer);
        buffer.append(" && ");
        //buffer.append(checkCondition());
        right.checker(buffer);
        buffer.append(")");
    }

/*    private boolean checkCondition(){
        if(left instanceof BoolProp && right instanceof BoolProp) {
            return ((BoolProp) left).value && ((BoolProp) right).value;
        } else if (left instanceof BoolProp || right instanceof BoolProp) {
            if(left instanceof BoolProp && ((BoolProp) left).value == false) {
                return false;
            } else if (left instanceof BoolProp && ((BoolProp) left).value == false)
        }
        if(left instanceof AtomicProp && right instanceof AtomicProp) {
            System.out.println("yayayaya " + model.getStates()[0].getLabel()[0]);
            return true;
        }
        return false;
    }
*/
}