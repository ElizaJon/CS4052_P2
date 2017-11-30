package modelChecker;

import formula.FormulaParser;
import formula.stateFormula.StateFormula;
import model.Model;
import org.junit.*;

import java.io.IOException;

import static junit.framework.TestCase.*;

public class ModelCheckerTest {

    /*
     * An example of how to set up and call the model building methods and make
     * a call to the model checker itself. The contents of model.json,
     * constraint1.json and ctl.json are just examples, you need to add new
     * models and formulas for the mutual exclusion task.
     */
    @Test
    public void buildAndCheckModel() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");

            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/constraint1.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/ctl1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            // TO IMPLEMENT
             assertFalse(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which does not belong to any of the states for given model1
    @Test
    public void buildAndCheckAP1() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/atomicProp1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());

        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which belongs to some of the states for given model1
    @Test
    public void buildAndCheckAP2() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/atomicProp2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));

        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using constraint allowing only some paths and atomic proposition
    // which belongs to some of the states for given model1
    @Test
    public void buildAndCheckAP3() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/constraint3.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/atomicProp3.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));

        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using constraint allowing only some paths and atomic proposition
    // which belongs to some of the states for given model1
    @Test
    public void buildAndCheckAP4() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/constraint5.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/atomicProp3.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which does not belong to any of the states for given model1 so next(X) can't reach it
    @Test
    public void buildAndCheckNext1() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/EX1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());

        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using FALSE as constraint and atomic proposition
    // which does not belong to any of the states for given model1
    //(This must be true no matter what formula you get)
    @Test
    public void buildAndCheckNext2() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/boolFalseConstraint.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/EX1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which is reachable for next(X) using any actions for given model1
    @Test
    public void buildAndCheckNext3() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/EX2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which is reachable for next(X) using given action set for given model1
    @Test
    public void buildAndCheckNext4() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/EX3.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which is not reachable for next(X) using given action set for given model1
    @Test
    public void buildAndCheckNext5() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/EX4.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using constraint and atomic proposition
    // which is reachable for next(X) using given action set for given model1
    @Test
    public void buildAndCheckNext6() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/constraint3.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/EX3.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using constraint and atomic proposition
    // which is not reachable for eventually(f) using given action set for given model1 under constraint
    @Test
    public void buildAndCheckEventually2() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/constraint3.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/EF5.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which is reachable for eventually(F) using given action set for given model1
    @Test
    public void buildAndCheckEventually1() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/EF5.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }


    //Testing using TRUE as constraint and boolean proposition true
    @Test
    public void buildAndCheckBoolP1() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/boolPropTrue.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and boolean proposition false
    @Test
    public void buildAndCheckBoolP2() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/boolPropFalse.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and there exists eventually(using any actions) path satisfying and(&&) condition
    @Test
    public void buildAndCheckAnd1() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/and1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and there exists eventually(using given actions) path satisfying and(&&) condition
    @Test
    public void buildAndCheckAnd2() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/and2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using constraint restricting some paths and checking formula
    // for there exists eventually(using given actions) path satisfying and(&&) condition
    @Test
    public void buildAndCheckAnd3() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/constraint5.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/and2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and for all eventually(using given actions) path satisfies or(||) condition
    @Test
    public void buildAndCheckOr1() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/or1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    //Testing using constraint restricting some paths and checking formula
    // for there exists eventually(using given actions) path satisfies or(||) condition
    @Test
    public void buildAndCheckOr2() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");

            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/constraint5.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/or2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    @Test
    public void buildAndCheckOr3() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");

            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/constraint1.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/ctl1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            // TO IMPLEMENT
            assertFalse(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    @Test
    public void buildAndCheckOr4() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");

            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/constraint1.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/ctl1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            // TO IMPLEMENT
            assertFalse(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }

    @Test
    public void buildAndCheckOr5() {
        try {
            Model model = Model.parseModel("../../src/test/resources/models/model1.json");

            StateFormula fairnessConstraint = new FormulaParser("../../src/test/resources/constraints/constraint1.json").parse();
            StateFormula query = new FormulaParser("../../src/test/resources/ctls/ctl1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            // TO IMPLEMENT
            assertFalse(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            //fail(e.toString());
        }
    }


}
