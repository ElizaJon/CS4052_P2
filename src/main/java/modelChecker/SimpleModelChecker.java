package modelChecker;

import formula.stateFormula.StateFormula;
import model.Model;

public class SimpleModelChecker implements ModelChecker {

    @Override
    public boolean check(Model model, StateFormula constraint, StateFormula query) {
        // TODO Auto-generated method stub
        System.out.println(constraint.toString());

        System.out.println(query.toString());
        System.out.println(query.toString());

        System.out.println(model.getStates()[0].getLabel()[0]);

        return false;
    }

    @Override
    public String[] getTrace() {
        // TODO Auto-generated method stub
        return null;
    }

}
