package modelChecker;

import formula.stateFormula.StateFormula;
import model.Model;

import java.util.ArrayList;

public class SimpleModelChecker implements ModelChecker {

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        // TODO Auto-generated method stub
        System.out.println(constraint.toString());
        System.out.println(query.toString());

        System.out.println("\nNew stuff\n");

        ArrayList result1 = new ArrayList();
        ArrayList result2 = new ArrayList();
        constraint.checker(result1);
        //query.checker(result2);

        print(result1);
        print(result2);

        System.out.println(result1.size());
        System.out.println(result2.size());


        System.out.println(model.getStates()[0].getLabel()[0]);

        return false;
    }

    private void print(ArrayList result){
        for(int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i) + " ");
        }
        System.out.println();
    }

    @Override
    public String[] getTrace() {
        // TODO Auto-generated method stub
        return null;
    }

}
