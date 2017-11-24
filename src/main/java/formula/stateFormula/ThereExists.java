package formula.stateFormula;

import formula.FormulaParser;
import formula.pathFormula.PathFormula;
import model.State;
import modelChecker.SimpleModelChecker;

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

    @Override
    public State[] getStates(State[] allStates) {
        State[] resultStates = pathFormula.getStates(allStates);
        State[] resultThereExists = checkForThereExists(allStates, resultStates);

        System.out.println("There exists method");
        SimpleModelChecker.printStates(allStates);
        SimpleModelChecker.printStates(resultStates);
        SimpleModelChecker.printStates(resultThereExists);
        System.out.println("End of There exists method");

        return resultThereExists;
    }

    private State[] checkForThereExists(State[] allStates, State[] resultStates){
        ArrayList<State> thereExistsStates = new ArrayList<>();
        for(int i = 0; i < allStates.length; i++){
            for(int j = 0; j < resultStates.length; j++){
                if(allStates[i].equals(resultStates[j])){
                    thereExistsStates.add(allStates[i]);
                    break;
                }
            }
        }
        return thereExistsStates.toArray(new State[thereExistsStates.size()]);
    }
}
