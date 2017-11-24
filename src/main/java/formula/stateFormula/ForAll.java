package formula.stateFormula;

import formula.*;
import formula.pathFormula.PathFormula;
import model.State;
import modelChecker.SimpleModelChecker;

import java.util.ArrayList;

public class ForAll extends StateFormula {
    public final PathFormula pathFormula;

    public ForAll(PathFormula pathFormula) {
        this.pathFormula = pathFormula;
    }

    @Override
    public void writeToBuffer(StringBuilder buffer) {
        buffer.append("(");
        buffer.append(FormulaParser.FORALL_TOKEN);
        pathFormula.writeToBuffer(buffer);
        buffer.append(")");
    }

    @Override
    public void checker(ArrayList buffer) {
        buffer.add("(");
        buffer.add(FormulaParser.FORALL_TOKEN);
        pathFormula.checker(buffer);
        buffer.add(")");
    }

    @Override
    public State[] getStates(State[] allStates) {
        State[] resultStates = pathFormula.getStates(allStates);
        State[] checkedAllStates = checkForAll(allStates, resultStates);

        System.out.println("For all method");
        SimpleModelChecker.printStates(allStates);
        SimpleModelChecker.printStates(resultStates);
        SimpleModelChecker.printStates(checkedAllStates);
        System.out.println("End of For all method");
        return checkedAllStates;
    }

    private State[] checkForAll(State[] allStates, State[] resultStates){
        if(allStates.length != resultStates.length){
            return new State[0];
        } else {
            int check = 0;
            for(int i = 0; i < allStates.length; i++){
                for(int j = 0; j < resultStates.length; j++){
                    if(allStates[i].equals(resultStates[j])){
                        check++;
                        break;
                    }
                }
            }
            if(check == allStates.length){
                return allStates;
            } else {
                return new State[0];
            }
        }
    }
}
