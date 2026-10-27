/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alternative.controller;

import alternative.controller.DecisionController;
import alternative.model.Aim;
import alternative.model.Alternative;
import alternative.model.Criteria;
import alternative.model.Problem;
import alternative.model.decision.ComPairedTableModel;
import alternative.model.decision.ComparingTableModel;
import alternative.model.decision.ComparisonResult;
import alternative.model.decision.ConsistencyTableModel;
import alternative.model.decision.CriteriaPriority;
import alternative.model.decision.GlobalResult;
import alternative.model.decision.Matrix;
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
@RunWith(Parameterized.class)
public class DecisionControllerParameterizedTest {

    static protected DecisionController dc;

    /**
     * for parameters
     */
    private Problem problem;
    private int expectedTreeNodesCount;
    private Vector<Aim> expectedOrderedNodes;
    private ComparingTableModel tableToCalculate;
    private ComparisonResult expectedCalculateResult;
    private HashMap<Aim, ComparingTableModel> prioritiesData;
    private GlobalResult expectedGlobalResult;

    private ComPairedTableModel expectedComPairedTableModel;
    private Vector<? extends Criteria> emptyCPTMCriterias;
    private ComPairedTableModel expectedEmptyCPTM;
    private ConsistencyTableModel expectedEmptyCTM;

    public DecisionControllerParameterizedTest(Problem problem/*, int expectedTreeNodesCount*/, Vector<Aim> expectedOrderedNodes,
                /* does not depend on problem */
                ComparingTableModel tableToCalculate, ComparisonResult expectedCalculateResult,
                /* vallues for global result*/
                HashMap<Aim, ComparingTableModel> prioritiesData, GlobalResult expectedGlobalResult,
                /* not important at all */
                ComPairedTableModel expectedComPairedTableModel,
                Vector<? extends Criteria> emptyCPTMCriterias,
                ComPairedTableModel expectedEmptyCPTM,
                ConsistencyTableModel expectedEmptyCTM
            ) {
        this.problem = problem;
        this.expectedTreeNodesCount = expectedOrderedNodes.size();
        this.expectedOrderedNodes = expectedOrderedNodes;

        this.tableToCalculate = tableToCalculate;
        this.expectedCalculateResult = expectedCalculateResult;

        this.prioritiesData = prioritiesData;
        this.expectedGlobalResult = expectedGlobalResult;

        this.expectedComPairedTableModel = expectedComPairedTableModel;
        this.emptyCPTMCriterias = emptyCPTMCriterias;
        this.expectedEmptyCPTM = expectedEmptyCPTM;
        this.expectedEmptyCTM = expectedEmptyCTM;

        dc.problem = problem;
        dc.hm = new HashMap<Aim, ComparingTableModel>();
        dc.putCriterias(problem.getAim());
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
        dc.hm = new HashMap<Aim, ComparingTableModel>();
        dc.putCriterias(problem.getAim());
        for (Aim node : expectedOrderedNodes) {
            assertNotNull("Missing Controller's hm ComparingTableModel", dc.hm.get(node));
        }
        assertNotNull("hm must not be null", dc.hm);
        assertEquals("hm size is not equal to nodesCount", expectedTreeNodesCount, dc.hm.size());
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getComparisonEntries method, of class DecisionController.
     */
    @Test
    public void testGetComparisonEntries() {
        System.out.println("getComparisonEntries");
        Vector<ComparingTableModel> tableModels = dc.getComparisonEntries();
        assertNotNull("ComparingTableModels collection must not be null", tableModels);
        assertEquals("ComparingTableModels collection size is not equal to nodesCount",
                tableModels.size(), expectedTreeNodesCount);
        for (int i=0; i<tableModels.size(); i++) {
            assertEquals("ComparingTableModel's aim is not correct", tableModels.get(i).getAim(), expectedOrderedNodes.get(i));
        }
    }

    /**
     * Test of getComparingModel method, of class DecisionController.
     */
    @Test
    public void testGetComparingModel() {
        System.out.println("getComparingModel");
        ComparingTableModel expectedTopAimTableModel = dc.hm.get(problem.getAim());
        TableModel tableModel = dc.getComparingModel();
        assertNotNull("Top aim ComparingTableModel is null", tableModel);
        assertTrue("Table model for comparing is not ComparingTableModel",
                tableModel instanceof ComparingTableModel);
        Aim aim = ((ComparingTableModel) tableModel).getAim();
        assertNotNull("Default ComparingTableModel aim is null", aim);
        assertEquals("Default ComparingTableModel aim is not top aim", 
                expectedTopAimTableModel.getAim(), aim);
    }

    /**
     * Test of calculate method, of class DecisionController.
     */
    @Test
    public void testCalculate() {
        System.out.println("calculate");

        ComparisonResult result = dc.calculate(tableToCalculate);
        assertNotNull(result);
        int n = expectedCalculateResult.getPriorityVector().size();
        Matrix resultMatrix = result.getComparisons();
        Matrix expectedMatrix = expectedCalculateResult.getComparisons();
        for (int i = 0; i<n; i++) {
            for (int j = 0; j<n; j++) {
                assertEquals("Incorrect Comparison value", expectedMatrix.get(i, j),
                        resultMatrix.get(i, j), 0.000001);
            }
        }
        assertArrayEquals("Priority vector is incorrect",
                expectedCalculateResult.getPriorityVector().toArray(), result.getPriorityVector().toArray());

        assertEquals("Lambda max is incorrect", expectedCalculateResult.getLambdaMax(),
                result.getLambdaMax(), 0.000001);
        assertEquals("Consistency Index is incorrect", expectedCalculateResult.getConsistencyIndex(),
                result.getConsistencyIndex(), 0.00000001);
        assertEquals("Harmonization is incorrect", expectedCalculateResult.getHarmonization(),
                result.getHarmonization(), 0.00000001);
    }

    /**
     * Test of getComPairedTableModel method, of class DecisionController.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testGetComPairedTableModel() {
        System.out.println("getComPairedTableModel");
        TableModel tm = dc.getComPairedTableModel(expectedCalculateResult);
        assertNotNull("ComPairedTableModel became null", tm);
        assertTrue("Incorrect type instead of ComPairedTableModel", tm instanceof ComPairedTableModel);
        this.checkTableDataVectors(expectedComPairedTableModel.getDataVector(), ((ComPairedTableModel) tm).getDataVector());
    }

    protected void checkTableDataVectors(Vector<Vector<Object>> expectedData, Vector<Vector<Object>> actualData) {
        for (int i=0; i<expectedData.size(); i++) {
            Vector<Object> expRow = expectedData.get(i);
            Vector<Object> resRow = actualData.get(i);
            for (int j=0; j<expRow.size(); j++) {
                if (expRow.get(j) instanceof Double) {
                    assertEquals("Incorrect value in table", (Double)(expRow.get(j)), (Double)(resRow.get(j)), 1.0E-6);
                }
            }
        }
    }

    /**
     * Test of getEmptyComPairedTableModel method, of class DecisionController.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testGetEmptyComPairedTableModel() {
        System.out.println("getEmptyComPairedTableModel");
        TableModel tm = dc.getEmptyComPairedTableModel(this.emptyCPTMCriterias);
        assertNotNull("ComPairedTableModel became null", tm);
        assertTrue("Incorrect type instead of ComPairedTableModel", tm instanceof ComPairedTableModel);
        this.checkTableDataVectors(expectedEmptyCPTM.getDataVector(), ((ComPairedTableModel) tm).getDataVector());
    }

    /**
     * Test of getEmptyConsistencyTableModel method, of class DecisionController.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testGetEmptyConsistencyTableModel() {
        System.out.println("getEmptyConsistencyTableModel");
        TableModel tm = dc.getEmptyConsistencyTableModel();
        assertNotNull("ConsistencyTableModel became null", tm);
        assertTrue("Incorrect type instead of ConsistencyTableModel", tm instanceof ConsistencyTableModel);
        this.checkTableDataVectors(expectedEmptyCTM.getDataVector(), ((ConsistencyTableModel) tm).getDataVector());
    }

    /**
     * Test of calculateResult method, of class DecisionController.
     */
    @Test
    public void testCalculateResult() {
        System.out.println("calculateResult");
        dc.hm = this.prioritiesData;
        GlobalResult result = dc.calculateResult();
        assertNotNull("Global Result have became null", result);
        assertArrayEquals("Alternatives collections are not equal",
                expectedGlobalResult.getAlternatives().toArray(), result.getAlternatives().toArray());
        Matrix resultFinalPriorities = result.getFinalPriorities();
        Matrix expectedFinalPriorities = expectedGlobalResult.getFinalPriorities();
        assertNotNull("Global Result final priorities became null", resultFinalPriorities);
        int cols = expectedFinalPriorities.getCols();
        assertEquals("Final priorities matrix cols do not match",
                cols, resultFinalPriorities.getCols());
        int rows=expectedFinalPriorities.getRows();
        assertEquals("Final priorities matrix rows do not match",
                rows, resultFinalPriorities.getRows());
        for (int i = 0; i<rows; i++) {
            for (int j = 0; j<cols; j++) {
                assertEquals("Incorrect Final Priority",
                        expectedFinalPriorities.get(i, j), resultFinalPriorities.get(i, j), 1.0E-3);
            }
        }
        Vector<Double> exPr = expectedGlobalResult.getGlobalPriorities();
        Vector<Double> rPr = result.getGlobalPriorities();
        for (int i=0; i<exPr.size(); i++) {
            assertEquals("Incorrect global priority", exPr.get(i), rPr.get(i), 1.0E-3);
        }
        Vector<Double> exLPr = expectedGlobalResult.getLeafPriorities();
        Vector<Double> rLPr = result.getLeafPriorities();
        for (int i=0; i<exPr.size(); i++) {
            assertEquals("Incorrect leaf priority", exLPr.get(i), rLPr.get(i), 1.0E-3);
        }
        assertArrayEquals("Leafs do not match with expected",
                expectedGlobalResult.getLeafs().toArray(), result.getLeafs().toArray());
        
    }


    @SuppressWarnings("rawtypes")
    @Parameters
    public static Collection getParameters() {
        try {
            /**
             * Aim1
             *  +--Aim3
             *  |   +--Aim6
             *  |   +--Aim7
             *  |   +--Aim4
             *  |       +--Aim5
             *  |       +--Aim9
             *  +--Aim2
             *  +--Aim8
             *
             */
            Aim aim1 = new Aim("Aim1");
            Aim aim2 = new Aim("Aim2");
            Aim aim3 = new Aim("Aim3");
            Aim aim4 = new Aim("Aim4");
            Aim aim5 = new Aim("Aim5");
            aim4.add(aim5);
            Aim aim9 = new Aim("Aim9");
            aim4.add(aim9);
            Aim aim6 = new Aim("aim6");
            aim3.add(aim6);
            Aim aim7 = new Aim("aim7");
            aim3.add(aim7);
            aim3.add(aim4);
            aim1.add(aim3);
            aim1.add(aim2);
            Aim aim8 = new Aim("aim8");
            aim1.add(aim8);
            Vector<Alternative> alternatives1 = new Vector<Alternative>(Arrays.asList(
                    new Alternative("Alternative1"),
                    new Alternative("Alternative2"),
                    new Alternative("Alternative3"),
                    new Alternative("Alternative4")));
            Problem problem1 = new Problem(aim1, alternatives1);
            Vector<Aim> orderedNodes1 = new Vector<Aim>(Arrays.asList(
                    aim1, aim3, aim6, aim7, aim4, aim5, aim9, aim2, aim8));

            Vector<Aim> criterias = aim3.getCriterias();
            ComparingTableModel cm1 = new ComparingTableModel(criterias, aim3);
            cm1.setDataVector(
                    new Object [][] {
                        {null, new CriteriaPriority(0.5), null},
                        {null, new CriteriaPriority(0.9), null},
                        {null, new CriteriaPriority(0.8), null},
                    },
                    new String[] {
                        "1", "2", "3"
                    }
            );
            Matrix tMatrix1 = new Matrix(3, 3);
            tMatrix1.setElementAt(1, 0, 0);
            tMatrix1.setElementAt(0.5, 0, 1);
            tMatrix1.setElementAt(0.9, 0, 2);
            tMatrix1.setElementAt(2, 1, 0);
            tMatrix1.setElementAt(1, 1, 1);
            tMatrix1.setElementAt(0.8, 1, 2);
            tMatrix1.setElementAt(1.0/0.9, 2, 0);
            tMatrix1.setElementAt(1.0/0.8, 2, 1);
            tMatrix1.setElementAt(1, 2, 2);
            /**
             * ComparisonResult:
priorityVector: [0.2511141244415208, 0.3832718863393582, 0.3656139892191209]
lamda_max = 3.0735135254733357
IS = 0.036756762736667836
OS = 0.06337372885632386
             */
            ComparisonResult calculateResult1 = new ComparisonResult(criterias,
                    tMatrix1,
                    new Vector<Double>(Arrays.asList(0.2511141244415208, 0.3832718863393582, 0.3656139892191209)),
                    3.0735135254733357, 0.036756762736667836, 0.06337372885632386);


            HashMap<Aim, ComparingTableModel> resultHm1 = new HashMap<Aim, ComparingTableModel>();
            ComparingTableModel ctm1 = new ComparingTableModel(aim1.getCriterias(), aim1);
            ctm1.setDataVector(
                    new Object [][] {
                        {null, new CriteriaPriority(0.389), null},
                        {null, new CriteriaPriority(2.704), null},
                        {null, new CriteriaPriority(1), null},
                    },
                    new String[] {
                        "1", "2", "3"
                    }
            );
            resultHm1.put(aim1, ctm1);
            ComparingTableModel ctm3 = new ComparingTableModel(aim3.getCriterias(), aim3);
            ctm3.setDataVector(
                    new Object [][] {
                        {null, new CriteriaPriority(0.563), null},
                        {null, new CriteriaPriority(2.226), null},
                        {null, new CriteriaPriority(1), null},
                    },
                    new String[] {
                        "1", "2", "3"
                    }
            );
            resultHm1.put(aim3, ctm3);
            ComparingTableModel ctm6 = new ComparingTableModel(alternatives1, aim6);
            ctm6.setDataVector(
                    new Object [][] {
                        {null, new CriteriaPriority(0.449), null},
                        {null, new CriteriaPriority(1), null},
                        {null, new CriteriaPriority(0.370), null},
                        {null, new CriteriaPriority(1.703), null},
                        {null, new CriteriaPriority(0.667), null},
                        {null, new CriteriaPriority(1), null},
                    },
                    new String[] {
                        "1", "2", "3"
                    }
            );
            resultHm1.put(aim6, ctm6);
            ComparingTableModel ctm7 = new ComparingTableModel(alternatives1, aim7);
            ctm7.setDataVector(
                    new Object [][] {
                        {null, new CriteriaPriority(1), null},
                        {null, new CriteriaPriority(2.226), null},
                        {null, new CriteriaPriority(0.408), null},
                        {null, new CriteriaPriority(10.111), null},
                        {null, new CriteriaPriority(1), null},
                        {null, new CriteriaPriority(1), null},
                    },
                    new String[] {
                        "1", "2", "3"
                    }
            );
            resultHm1.put(aim7, ctm7);
            ComparingTableModel ctm4 = new ComparingTableModel(aim4.getCriterias(), aim4);
            ctm4.setDataVector(
                    new Object [][] {
                        {null, new CriteriaPriority(0.124), null},
                    },
                    new String[] {
                        "1", "2", "3"
                    }
            );
            resultHm1.put(aim4, ctm4);
            ComparingTableModel ctm5 = new ComparingTableModel(alternatives1, aim5);
            ctm5.setDataVector(
                    new Object [][] {
                        {null, new CriteriaPriority(0.266), null},
                        {null, new CriteriaPriority(1), null},
                        {null, new CriteriaPriority(0.408), null},
                        {null, new CriteriaPriority(1), null},
                        {null, new CriteriaPriority(1), null},
                        {null, new CriteriaPriority(6.692), null},
                    },
                    new String[] {
                        "1", "2", "3"
                    }
            );
            resultHm1.put(aim5, ctm5);
            ComparingTableModel ctm9 = new ComparingTableModel(alternatives1, aim9);
            ctm9.setDataVector(
                    new Object [][] {
                        {null, new CriteriaPriority(1), null},
                        {null, new CriteriaPriority(1), null},
                        {null, new CriteriaPriority(0.111), null},
                        {null, new CriteriaPriority(1), null},
                        {null, new CriteriaPriority(9.000), null},
                        {null, new CriteriaPriority(1.632), null},
                    },
                    new String[] {
                        "1", "2", "3"
                    }
            );
            resultHm1.put(aim9, ctm9);
            ComparingTableModel ctm2 = new ComparingTableModel(alternatives1, aim2);
            ctm2.setDataVector(
                    new Object [][] {
                        {null, new CriteriaPriority(1), null},
                        {null, new CriteriaPriority(3.348), null},
                        {null, new CriteriaPriority(1), null},
                        {null, new CriteriaPriority(1), null},
                        {null, new CriteriaPriority(0.111), null},
                        {null, new CriteriaPriority(10.111), null},
                    },
                    new String[] {
                        "1", "2", "3"
                    }
            );
            resultHm1.put(aim2, ctm2);
            ComparingTableModel ctm8 = new ComparingTableModel(alternatives1, aim8);
            ctm8.setDataVector(
                    new Object [][] {
                        {null, new CriteriaPriority(1), null},
                        {null, new CriteriaPriority(1), null},
                        {null, new CriteriaPriority(0.053), null},
                        {null, new CriteriaPriority(6.692), null},
                        {null, new CriteriaPriority(1), null},
                        {null, new CriteriaPriority(0.282), null},
                    },
                    new String[] {
                        "1", "2", "3"
                    }
            );
            resultHm1.put(aim8, ctm8);
            /** GlobalResult
             * 	0,116	0,130	0,028	0,223	0,441	0,231
Alternative1	0,152	0,218	0,132	0,134	0,321	0,089	0,241
Alternative2	0,301	0,399	0,320	0,401	0,137	0,298	0,314
Alternative3	0,209	0,103	0,370	0,261	0,312	0,084	0,263
Alternative4	0,338	0,280	0,179	0,205	0,230	0,530	0,350
             */
            Matrix finalPriorities1 = new Matrix(4, 6);
            finalPriorities1.setElementAt(0.152, 0, 0);
            finalPriorities1.setElementAt(0.218, 0, 1);
            finalPriorities1.setElementAt(0.132, 0, 2);
            finalPriorities1.setElementAt(0.134, 0, 3);
            finalPriorities1.setElementAt(0.321, 0, 4);
            finalPriorities1.setElementAt(0.089, 0, 5);
            finalPriorities1.setElementAt(0.301, 1, 0);
            finalPriorities1.setElementAt(0.399, 1, 1);
            finalPriorities1.setElementAt(0.320, 1, 2);
            finalPriorities1.setElementAt(0.401, 1, 3);
            finalPriorities1.setElementAt(0.137, 1, 4);
            finalPriorities1.setElementAt(0.298, 1, 5);
            finalPriorities1.setElementAt(0.209, 2, 0);
            finalPriorities1.setElementAt(0.103, 2, 1);
            finalPriorities1.setElementAt(0.370, 2, 2);
            finalPriorities1.setElementAt(0.261, 2, 3);
            finalPriorities1.setElementAt(0.312, 2, 4);
            finalPriorities1.setElementAt(0.084, 2, 5);
            finalPriorities1.setElementAt(0.338, 3, 0);
            finalPriorities1.setElementAt(0.280, 3, 1);
            finalPriorities1.setElementAt(0.179, 3, 2);
            finalPriorities1.setElementAt(0.205, 3, 3);
            finalPriorities1.setElementAt(0.230, 3, 4);
            finalPriorities1.setElementAt(0.530, 3, 5);
            GlobalResult gr1 = new GlobalResult(
                     new Vector<Double>(Arrays.asList(0.116, 0.130, 0.028, 0.223, 0.441, 0.231)),
                     finalPriorities1,
                     new Vector<Double>(Arrays.asList(0.241, 0.314, 0.263, 0.350)),
                     new Vector<Aim>(Arrays.asList(aim6, aim7, aim5, aim9, aim2, aim8)),
                     alternatives1);

            ComPairedTableModel expCPTM1 = new ComPairedTableModel(calculateResult1);
            Vector<Alternative> emptyCPTMCriterias1 = alternatives1;
            ComPairedTableModel expectedCPTM1 = new ComPairedTableModel(emptyCPTMCriterias1);
            ConsistencyTableModel expectedCTM1 = new ConsistencyTableModel(null);

            /**
             * Aim1
             *  +--Aim2
             *  +--Aim3
             *
             */
            aim1 = new Aim("Aim1");
            aim2 = new Aim("Aim2");
            aim3 = new Aim("Aim3");
            aim1.add(aim2);
            aim1.add(aim3);
            Vector<Alternative> alternatives2 = new Vector<Alternative>(Arrays.asList(
                    new Alternative("Alternative1"),
                    new Alternative("Alternative2")));
            Problem problem2 = new Problem(aim1, alternatives2);
            Vector<Aim> orderedNodes2 = new Vector<Aim>(Arrays.asList(aim1, aim2, aim3));

            ComparingTableModel cm2 = new ComparingTableModel(new Vector<Alternative>(alternatives2), aim3);
            cm2.setDataVector(
                    new Object [][] {
                        {null, new CriteriaPriority(0.7), null},
                    },
                    new String[] {
                        "1", "2", "3"
                    }
            );
            Matrix tMatrix2 = new Matrix(2, 2);
            tMatrix2.setElementAt(1, 0, 0);
            tMatrix2.setElementAt(0.7, 0, 1);
            tMatrix2.setElementAt(1.0/0.7, 1, 0);
            tMatrix2.setElementAt(1, 1, 1);
            /**
             * ComparisonResult:
priorityVector: [0.4117647058823529, 0.588235294117647]
lamda_max = 1.9999999999999998
IS = 2.220446049250313E-16
OS = 0.0
             */
            ComparisonResult calculateResult2 = new ComparisonResult(alternatives2, tMatrix2,
                    new Vector<Double>(Arrays.asList(0.4117647058823529, 0.588235294117647)),
                    1.9999999999999998, 0, 0);


            HashMap<Aim, ComparingTableModel> resultHm2 = new HashMap<Aim, ComparingTableModel>();
            ctm1 = new ComparingTableModel(aim1.getCriterias(), aim1);
            ctm1.setDataVector(
                    new Object [][] {
                        {null, new CriteriaPriority(1.941), null},
                    },
                    new String[] {
                        "1", "2", "3"
                    }
            );
            resultHm2.put(aim1, ctm1);
            ctm2 = new ComparingTableModel(alternatives2, aim2);
            ctm2.setDataVector(
                    new Object [][] {
                        {null, new CriteriaPriority(1), null},
                    },
                    new String[] {
                        "1", "2", "3"
                    }
            );
            resultHm2.put(aim2, ctm2);
            ctm3 = new ComparingTableModel(alternatives2, aim3);
            ctm3.setDataVector(
                    new Object [][] {
                        {null, new CriteriaPriority(6.143), null},
                    },
                    new String[] {
                        "1", "2", "3"
                    }
            );
            resultHm2.put(aim3, ctm3);
            /**
             * GlobalResult
             * 	0,660	0,340
Alternative1	0,500	0,860	0,622
Alternative2	0,500	0,140	0,378
             */
            Matrix finalPriorities2 = new Matrix(2, 2);
            finalPriorities2.setElementAt(0.500, 0, 0);
            finalPriorities2.setElementAt(0.860, 0, 1);
            finalPriorities2.setElementAt(0.500, 1, 0);
            finalPriorities2.setElementAt(0.140, 1, 1);
            GlobalResult gr2 = new GlobalResult(
                     new Vector<Double>(Arrays.asList(0.660, 0.340)),
                     finalPriorities2,
                     new Vector<Double>(Arrays.asList(0.622, 0.378)),
                     new Vector<Aim>(Arrays.asList(aim2, aim3)),
                     alternatives2);

            ComPairedTableModel expCPTM2 = new ComPairedTableModel(calculateResult2);
            Vector<Alternative> emptyCPTMCriterias2 = null;//alternatives2;
            ComPairedTableModel expectedCPTM2 = new ComPairedTableModel(emptyCPTMCriterias2);
            ConsistencyTableModel expectedCTM2 = new ConsistencyTableModel(null);


            return Arrays.asList(new Object[][]{
                {problem1/*, 9*/, orderedNodes1, cm1, calculateResult1, resultHm1, gr1,
                    expCPTM1, emptyCPTMCriterias1, expectedCPTM1, expectedCTM1},
                {problem2/*, 3*/, orderedNodes2, cm2, calculateResult2, resultHm2, gr2,
                    expCPTM2, emptyCPTMCriterias2, expectedCPTM2, expectedCTM2}
            });

        } catch (Exception e) {
            Logger.getLogger(DecisionControllerParameterizedTest.class.getName()).log(Level.SEVERE,
                    "Wrong params in TEST - not testing error. Must be corrected", e);
            return null;
        }
    }

}
