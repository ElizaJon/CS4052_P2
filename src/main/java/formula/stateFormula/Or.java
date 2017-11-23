package formula.stateFormula;

import com.sun.org.apache.xpath.internal.operations.Mod;
import model.Model;
import model.State;

import java.util.ArrayList;

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
    public void checker(ArrayList buffer) {
        buffer.add(checkCondition(buffer));
    }

    private boolean checkCondition(ArrayList buffer){
        Object Left, Right;
        Left = null;
        Right = null;
        if(left instanceof BoolProp && right instanceof BoolProp){
            return ((BoolProp) left).value || ((BoolProp) right).value;
        } else if (left instanceof BoolProp || right instanceof BoolProp){
            if(left instanceof BoolProp){
                if(((BoolProp) left).value == true){
                    return true;
                } else {
                    Left = false;
                }
            } else if(right instanceof BoolProp){
                if(((BoolProp) right).value == true){
                    return true;
                } else {
                    Right = false;
                }
            }
        } else if(left instanceof AtomicProp && right instanceof AtomicProp){
            return thereIsAStateWithLabels(model.getStates(), (AtomicProp) left, (AtomicProp) right);
        } else if(left instanceof AtomicProp || right instanceof AtomicProp) {
            if(left instanceof AtomicProp) {
                Left = thereIsAStateWithLabel(model.getStates(), (AtomicProp) left);
                if(Left.equals(true)){
                    return true;
                }
            } else if(right instanceof AtomicProp){
                Right = thereIsAStateWithLabel(model.getStates(), (AtomicProp) right);
                if(Right.equals(true)){
                    return true;
                }
            }
        }
        if (!(left instanceof AtomicProp) && !(left instanceof BoolProp)) {
            left.checker(buffer);
            Left = buffer.get(buffer.size() - 1);
            buffer.remove(Left);
        }
        if (!(right instanceof AtomicProp) && !(right instanceof BoolProp)) {
            right.checker(buffer);
            Right = buffer.get(buffer.size() - 1);
            buffer.remove(Right);
        }
        if((Left != null && Right != null) && (Left.equals(true) || Right.equals(true))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean thereIsAStateWithLabel(State[] states, AtomicProp a){
        State state;
        String[] label;
        for(int i = 0; i < states.length; i++){
            state = states[i];
            label = state.getLabel();
            for(int j = 0; j < label.length; j++){
                if(label[j].equals(a.label)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean thereIsAStateWithLabels(State[] states, AtomicProp a, AtomicProp b){
        State state;
        String[] label;
        for(int i = 0; i < states.length; i++){
            state = states[i];
            label = state.getLabel();
            for(int j = 0; j < label.length; j++){
                if(label[j].equals(a.label) || label[j].equals(b.label)) {
                    return true;
                }
            }
        }
        return false;
    }
}