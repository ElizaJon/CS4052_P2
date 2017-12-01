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

    //Tests for real life examples:

    //Tests for mechanism of a lamp
    @Test
    public void buildAndCheckModelLamp1() {
        try {
            Model model = Model.parseModel("src/test/resources/models/lamp.json");

            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/lampQuery1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void buildAndCheckModelLamp2() {
        try {
            Model model = Model.parseModel("src/test/resources/models/lamp.json");

            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/lampQuery2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void buildAndCheckModelLamp3() {
        try {
            Model model = Model.parseModel("src/test/resources/models/lamp.json");

            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/lampconstraint2.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/lampQuery2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Tests for mechanism of a PacMan game

    @Test
    public void buildAndCheckPacMan1() {
        try {
            Model model = Model.parseModel("src/test/resources/models/PacManGhosts.json");

            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/PacManGhostsQuery1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void buildAndCheckPacMan2() {
        try {
            Model model = Model.parseModel("src/test/resources/models/PacManGhosts.json");

            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/PacManGhostsConstraint1.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/PacManGhostsQuery1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void buildAndCheckPacMan3() {
        try {
            Model model = Model.parseModel("src/test/resources/models/PacManGhosts.json");

            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/PacManGhostsQuery2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void buildAndCheckPacMan4() {
        try {
            Model model = Model.parseModel("src/test/resources/models/PacManGhosts.json");

            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/PacManGhostsQuery3.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Tests for other models

    @Test
    public void buildAndCheckModel() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");

            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/constraint1.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/ctl1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            // TO IMPLEMENT
             assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which does not belong to any of the states for given model1
    @Test
    public void buildAndCheckAP1() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/atomicProp1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());

        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which belongs to some of the states for given model1
    @Test
    public void buildAndCheckAP2() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/atomicProp2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));

        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using constraint allowing only some paths and atomic proposition
    // which belongs to some of the states for given model1
    @Test
    public void buildAndCheckAP3() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/constraint3.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/atomicProp3.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));

        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using constraint allowing only some paths and atomic proposition
    // which belongs to some of the states for given model1 but do not belong so states satisfying constraint
    @Test
    public void buildAndCheckAP4() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/constraint5.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/atomicProp3.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which does not belong to any of the states for given model1 so next(X) can't reach it
    @Test
    public void buildAndCheckNext1() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EX1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());

        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using FALSE as constraint and atomic proposition
    // which does not belong to any of the states for given model1
    //(This must be true no matter what formula you get)
    @Test
    public void buildAndCheckNext2() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolFalseConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EX1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which is reachable for next(X) using any actions for given model1
    @Test
    public void buildAndCheckNext3() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EX2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which is reachable for next(X) using given action set for given model1
    @Test
    public void buildAndCheckNext4() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EX3.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which is not reachable for next(X) using given action set for given model1
    @Test
    public void buildAndCheckNext5() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EX4.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using constraint and atomic proposition
    // which is reachable for next(X) using given action set for given model1
    @Test
    public void buildAndCheckNext6() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/constraint3.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EX3.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using constraint and atomic proposition
    // which is not reachable for eventually(f) using given action set for given model1 under constraint
    @Test
    public void buildAndCheckEventually2() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/constraint3.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EF5.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which is reachable for eventually(F) using given action set for given model1
    @Test
    public void buildAndCheckEventually1() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EF5.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }


    //Testing using TRUE as constraint and boolean proposition true
    @Test
    public void buildAndCheckBoolP1() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/boolPropTrue.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and boolean proposition false
    @Test
    public void buildAndCheckBoolP2() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/boolPropFalse.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and there exists eventually(using any actions) path satisfying and(&&) condition
    @Test
    public void buildAndCheckAnd1() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/and1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and there exists eventually(using given actions) path not satisfying and(&&) condition
    @Test
    public void buildAndCheckAnd2() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/and2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using constraint restricting some paths and checking formula
    // for there exists eventually(using given actions) path not satisfying and(&&) condition
    @Test
    public void buildAndCheckAnd3() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/constraint5.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/and2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and for all eventually(using given actions) path satisfies or(||) condition
    @Test
    public void buildAndCheckOr1() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/or1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using constraint restricting some paths and checking formula
    // for there exists eventually(using given actions) path does not satisfies or(||) condition
    @Test
    public void buildAndCheckOr2() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");

            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/constraint5.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/or2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using True as constraint and checking formula
    // for there exists until(using given actions)
    @Test
    public void buildAndCheckUntil1() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EU1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using True as constraint and checking formula
    // for there exists until(using given actions)
    @Test
    public void buildAndCheckUntil2() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EU3.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));

        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using True as constraint and checking formula
    // for for all until(using given actions)
    @Test
    public void buildAndCheckUntil3() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/AU2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse( mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using constraint restricting some paths and checking formula
    // for there exists until(using given actions)
    @Test
    public void buildAndCheckUntil4() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/constraint4.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EU3.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue( mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Checking combined formulae bits
    @Test
    public void buildAndCheck1() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/ctl1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse( mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void buildAndCheck2() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/ctl6.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue( mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void buildAndCheck3() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/ctl7.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse( mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void buildAndCheck4() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model1.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/constraint1.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/ctl7.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue( mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which does not belong to any of the states for given model2
    @Test
    public void buildAndCheckAP1B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/atomicProp1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());

        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which does not belongs to the states for given model2
    @Test
    public void buildAndCheckAP2B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/atomicProp2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using constraint allowing only some paths and atomic proposition
    // which belongs to some of the states for given model2
    @Test
    public void buildAndCheckAP3B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/constraint3.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/atomicProp3.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));

        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using constraint allowing only some paths and atomic proposition
    // which belongs to some of the states for given model2
    @Test
    public void buildAndCheckAP4B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/constraint5.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/atomicProp3.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which does not belong to any of the states for given model2 so next(X) can't reach it
    @Test
    public void buildAndCheckNext1B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EX1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());

        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using FALSE as constraint and atomic proposition
    // which does not belong to any of the states for given model2
    //(This must be true no matter what formula you get)
    @Test
    public void buildAndCheckNext2B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolFalseConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EX1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which is not reachable for next(X) using any actions for given model2
    @Test
    public void buildAndCheckNext3B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EX2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which is not reachable for next(X) using given action set for given model2
    @Test
    public void buildAndCheckNext4B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EX3.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which is not reachable for next(X) using given action set for given model2
    @Test
    public void buildAndCheckNext5B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EX4.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using constraint and atomic proposition
    // which is reachable for next(X) using given action set for given model2
    @Test
    public void buildAndCheckNext6B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/constraint3.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EX3.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using constraint and atomic proposition
    // which is reachable for eventually(f) using given action set for given model2 under constraint
    @Test
    public void buildAndCheckEventually2B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/constraint3.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EF5.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and atomic proposition
    // which is reachable for eventually(F) using given action set for given model2
    @Test
    public void buildAndCheckEventually1B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EF5.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }


    //Testing using TRUE as constraint and boolean proposition true
    @Test
    public void buildAndCheckBoolP1B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/boolPropTrue.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and boolean proposition false
    @Test
    public void buildAndCheckBoolP2B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/boolPropFalse.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and there exists eventually(using any actions) path not satisfying and(&&) condition
    @Test
    public void buildAndCheckAnd1B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/and1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and there exists eventually(using given actions) path not satisfying and(&&) condition
    @Test
    public void buildAndCheckAnd2B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/and2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using constraint restricting some paths and checking formula
    // for there exists eventually(using given actions) path satisfying and(&&) condition
    @Test
    public void buildAndCheckAnd3B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/constraint5.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/and2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using TRUE as constraint and for all eventually(using given actions) path not satisfying or(||) condition
    @Test
    public void buildAndCheckOr1B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/or1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using constraint restricting some paths and checking formula
    // for there exists eventually(using given actions) path satisfies or(||) condition
    @Test
    public void buildAndCheckOr2B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");

            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/constraint5.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/or2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using True as constraint and checking formula
    // for there exists until(using given actions)
    @Test
    public void buildAndCheckUntil1B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EU1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue(mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using True as constraint and checking formula
    // for there exists until(using given actions)
    @Test
    public void buildAndCheckUntil2B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EU3.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse(mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using True as constraint and checking formula
    // for for all until(using given actions)
    @Test
    public void buildAndCheckUntil3B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/AU2.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse( mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing using constraint restricting some paths and checking formula
    // for there exists until(using given actions)
    @Test
    public void buildAndCheckUntil4B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/constraint4.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/EU3.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue( mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Checking combined formulae bits
    @Test
    public void buildAndCheck1B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/ctl1.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse( mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void buildAndCheck2B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/ctl6.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse( mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void buildAndCheck3B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/boolTrueConstraint.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/ctl7.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertFalse( mc.check(model, fairnessConstraint, query));
            System.out.println(mc.getTrace());
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    public void buildAndCheck4B() {
        try {
            Model model = Model.parseModel("src/test/resources/models/model2.json");
            StateFormula fairnessConstraint = new FormulaParser("src/test/resources/constraints/constraint1.json").parse();
            StateFormula query = new FormulaParser("src/test/resources/ctls/ctl7.json").parse();

            ModelChecker mc = new SimpleModelChecker();

            assertTrue( mc.check(model, fairnessConstraint, query));
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }


}
