package formula.stateFormula;

import formula.FormulaParser;
import formula.PathTree;
import model.Model;
import model.State;

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
    public State[] getStates(State[] allStates, Model model, PathTree pathTree) {
        pathTree.setFormulaPart(" ! ");
        PathTree leftNode = new PathTree("");
        pathTree.leftTree = leftNode;
        State[] newStates = stateFormula.getStates(allStates, model,leftNode);
        State[] notStates = getNotStates(allStates, newStates);

        pathTree.addAcceptedStates(notStates);
        if(notStates.length > 0){
            pathTree.setModelHolds(true);
        }
        return notStates;
    }

    //Method which gets states which satisfy formula on the left
    // and then returns states which where not satisfied
    private State[] getNotStates(State[] allStates, State[] newStates){
        ArrayList<State> notStates = new ArrayList<>();
        Boolean check;
        for(int i = 0; i < allStates.length; i++){
            check = false;
            for(int j = 0; j < newStates.length; j++){
                if(allStates[i].equals(newStates[j])){
                    check = true;
                    break;
                }
            }
            if(!check){
                notStates.add(allStates[i]);
            }
        }
        return notStates.toArray(new State[notStates.size()]);
    }

}
