/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alternative.controller;

import alternative.model.Aim;
import alternative.model.Criteria;
import alternative.model.decision.ComparingTableModel;
import alternative.model.decision.ComparisonResult;
import alternative.model.decision.GlobalResult;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableModel;
import javax.swing.tree.MutableTreeNode;
import javax.xml.bind.JAXBException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

/**
 *
 * @author jiraffa
 *
 */
public class DecisionControllerTest {

    static protected DecisionController dc;

    /**
     * for parameters
     */
    private Aim aim;
    private int expectedSize;

    public DecisionControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        try {
            dc = new DecisionController("Test_DecisionMaking_Existing");
        } catch (JAXBException ex) {
            fail(ex.toString());
        } catch (IOException ex) {
            fail(ex.toString());
        }
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of putCriterias method, of class DecisionController.
     */
    @Test
    public void testPutCriterias() {
        System.out.println("putCriterias");

        Aim aim = new Aim("test");
        dc.hm = new HashMap<Aim, ComparingTableModel>();
        dc.putCriterias(aim);
        assertEquals("hm size is not equal", 1, dc.hm.size());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getComparisonEntries method, of class DecisionController.
     */
    @Test
    public void testGetComparisonEntries() {
        System.out.println("getComparisonEntries");
        DecisionController instance = null;
        Vector expResult = null;
        Vector result = instance.getComparisonEntries();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addCriterias method, of class DecisionController.
     */
    @Test
    public void testAddCriterias() {
        System.out.println("addCriterias");
        Aim aim = null;
        Vector<ComparingTableModel> es = null;
        DecisionController instance = null;
        instance.addCriterias(aim, es);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getComparingModel method, of class DecisionController.
     */
    @Test
    public void testGetComparingModel() {
        System.out.println("getComparingModel");
        DecisionController instance = null;
        TableModel expResult = null;
        TableModel result = instance.getComparingModel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculate method, of class DecisionController.
     */
    @Test
    public void testCalculate() {
        System.out.println("calculate");
        ComparingTableModel model = null;
        DecisionController instance = null;
        ComparisonResult expResult = null;
        ComparisonResult result = instance.calculate(model);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getComPairedTableModel method, of class DecisionController.
     */
    @Test
    public void testGetComPairedTableModel() {
        System.out.println("getComPairedTableModel");
        ComparisonResult comparisonResult = null;
        DecisionController instance = null;
        TableModel expResult = null;
        TableModel result = instance.getComPairedTableModel(comparisonResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEmptyComPairedTableModel method, of class DecisionController.
     */
    @Test
    public void testGetEmptyComPairedTableModel() {
        System.out.println("getEmptyComPairedTableModel");
        Vector<? extends Criteria> criterias = null;
        DecisionController instance = null;
        TableModel expResult = null;
        TableModel result = instance.getEmptyComPairedTableModel(criterias);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEmptyConsistencyTableModel method, of class DecisionController.
     */
    @Test
    public void testGetEmptyConsistencyTableModel() {
        System.out.println("getEmptyConsistencyTableModel");
        DecisionController instance = null;
        TableModel expResult = null;
        TableModel result = instance.getEmptyConsistencyTableModel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateResult method, of class DecisionController.
     */
    @Test
    public void testCalculateResult() {
        System.out.println("calculateResult");
        DecisionController instance = null;
        GlobalResult expResult = null;
        GlobalResult result = instance.calculateResult();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calcChildrenPriorities method, of class DecisionController.
     */
    @Test
    public void testCalcChildrenPriorities() {
        System.out.println("calcChildrenPriorities");
        Aim aim = null;
        double aimPriority = 0.0;
        Vector<Aim> calculatedLeafs = null;
        Vector<Double> calculatedPriorities = null;
        DecisionController instance = null;
        instance.calcChildrenPriorities(aim, aimPriority, calculatedLeafs, calculatedPriorities);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
