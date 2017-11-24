package modelChecker;

import formula.stateFormula.StateFormula;
import model.Model;
import model.State;

import java.util.ArrayList;

public class SimpleModelChecker implements ModelChecker {

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        // TODO Auto-generated method stub
        System.out.println(constraint.toString());
        System.out.println(query.toString());

/*       System.out.println("\nNew stuff\n");

        ArrayList result1 = new ArrayList();
        ArrayList result2 = new ArrayList();
        constraint.checker(result1);
        query.checker(result2);

        print(result1);
        print(result2);
*/
        System.out.println("\nNewer stuff");

        State[] allStates = model.getStates();
        State[] resultStates = constraint.getStates(allStates);

        System.out.println("Resulting states:");
        printStates(resultStates);
        System.out.println("Has initial states: " + setOfStatesHasInitialState(resultStates));

        return false;
    }

    private boolean setOfStatesHasInitialState(State[] resultStates){
        for(int i = 0; i < resultStates.length; i++){
            if(resultStates[i].isInit()){
                return true;
            }
        }
        return false;
    }

    private void print(ArrayList result){
        for(int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i) + " ");
        }
        System.out.println();
    }

    public static void printStates(State[] result){
        for(int i = 0; i < result.length; i++) {
            System.out.print(result[i].getName() + " ");
        }
        System.out.println();
    }

    @Override
    public String[] getTrace() {
        // TODO Auto-generated method stub
        return null;
    }

}
