package formula.stateFormula;

import model.State;

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

    @Override
    public State[] getStates(State[] allStates) {
        return statesWithLabel(allStates);
    }

    private State[] statesWithLabel(State[] allStates){
        ArrayList<State> newStates = new ArrayList<>();
        State state;
        String[] labels;
        for(int i = 0; i < allStates.length; i++){
            state = allStates[i];
            labels = state.getLabel();
            for(int j = 0; j < labels.length; j++){
                if(labels[j].equals(label)){
                    newStates.add(state);
                    break;
                }
            }
        }
        return newStates.toArray(new State[newStates.size()]);
    }

}
