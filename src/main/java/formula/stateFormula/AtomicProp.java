package formula.stateFormula;

import formula.PathTree;
import model.Model;
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
    public State[] getStates(State[] allStates, Model model, PathTree pathTree) {
        pathTree.setFormulaPart(" " + label + " ");
        State [] APstates = statesWithLabel(allStates);
        pathTree.addAcceptedStates(APstates);
        if(APstates.length > 0){
            pathTree.setModelHolds(true);
        }
        return APstates;
    }

    //Method gets states which contains label in them
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
