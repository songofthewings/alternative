/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.controller;

import alternative.controller.decision.TableToCSVExporter;
import alternative.model.decision.DocxExporter;
import alternative.model.Aim;
import alternative.model.Alternative;
import alternative.model.Criteria;
import alternative.model.Problem;
import alternative.model.ProblemTreeNode;
import alternative.model.decision.ComPairedTableModel;
import alternative.model.decision.ComparingTableModel;
import alternative.model.decision.ComparisonResult;
import alternative.model.decision.ConsistencyTableModel;
import alternative.model.decision.GlobalResult;
import alternative.model.decision.Matrix;
import alternative.model.decision.Priority;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.table.TableModel;
import javax.swing.tree.MutableTreeNode;
import javax.xml.bind.JAXBException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;

/**
 *
 * @author jiraffa
 */
public class DecisionController extends ProblemDataController {

    Problem problem;
    HashMap<Aim, ComparingTableModel> hm;
    GlobalResult lastResult;

    /**
     * Случайные согласованности
     * (те, которые получились бы при случайном выборе количественных суждений
     * из шкалы 1/9, 1/8, 1/7, ... , 1, 2, 9,
     * но образовании обратносимметричной матрицы
     */
    protected final static Vector<Double> randomConsistency = new Vector<Double>(
            Arrays.asList(
                new Double(0), //0
                new Double(0), //1
                new Double(0), //2
                new Double(0.58), //3
                new Double(0.90), //4
                new Double(1.12), //5
                new Double(1.24), //6
                new Double(1.32), //7
                new Double(1.41), //8
                new Double(1.45), //9
                new Double(1.49) //10
            ));

    /**
     *
     * @param problemName Problem title
     * @throws JAXBException if data file is not correct
     * @throws IOException if error while reading file
     */
    public DecisionController(String problemName) throws JAXBException, IOException {
        this.problem = this.restore(problemName);
        hm = new HashMap<Aim, ComparingTableModel>();
        this.putCriterias(problem.getAim());
    }

    /**
     * Recursively puts to hm ComparingTableModels
     * for aim and all its children
     * @param aim aim from the top of hierarchy
     */
    protected void putCriterias(Aim aim) {
        if (aim.isLeaf()) {
            hm.put(aim, new ComparingTableModel(problem.listAlternatives(), aim));
            return;
        }
        Vector<Aim> criterias = aim.getCriterias();
        hm.put(aim, new ComparingTableModel(criterias, aim));
        for (Aim ch : criterias)
            putCriterias(ch);

        TreeMap<Aim, ComparingTableModel> tm = new TreeMap<Aim, ComparingTableModel>();
    }

    /**
     *
     * @return collection of ComparingTableModel with permanent order
     */
    public Vector<ComparingTableModel> getComparisonEntries() {
        Vector<ComparingTableModel> es = new Vector<ComparingTableModel>();
        this.addCriterias(problem.getAim(), es);
        return es;
    }

    /**
     * recursively adds ComparingTableModels for aim and its criterias
     * to es collection
     * @param aim aim from the top of hierarchy
     * @param es collection of already added ComparingTableModels
     */
    protected void addCriterias(Aim aim, Vector<ComparingTableModel> es) {
        es.add(hm.get(aim));
        if (aim.isLeaf())
            return;
        for (Aim ch : aim.getCriterias())
            this.addCriterias(ch, es);
    }

    /**
     *
     * @return (ComparingTableModel) for aim in the top of hierarchy
     */
    public TableModel getComparingModel() {
        Aim a = problem.getAim();
        return new ComparingTableModel(a.getCriterias(), a);
    }

    /**
     * calculates result for specified CommparingTableModel only
     * @param model single ComparingTableModel to calculate partial result
     * @return ComparisonResult - partial result
     */
    public ComparisonResult calculate(ComparingTableModel model) {
        Matrix m = model.getComparison();

        /** Вектор приоритетов */
        Vector<Double> priorityVector = new Vector<Double>();
        int n = m.getRows();
        double compSum = 0;
        for (int i=0; i<n; i++) {
            double rowComponent = 1;
            for (int j=0; j<n; j++) {
                rowComponent *= m.get(i, j);
            }
            double comp = Math.pow(rowComponent, 1.0/n);
            priorityVector.add(comp);
            compSum += comp;
        }
        for (int i=0; i<n; i++) {
            priorityVector.set(i, priorityVector.get(i)/compSum);
        }

        /**
         * lambda max
         * (lambda_max >= n)
         */
        double lambdaMax = 0;
        for (int i=0; i<n; i++) {
            double columnSum = 0;
            for (int j=0; j<n; j++) {
                columnSum += m.get(j, i);
            }
            lambdaMax += columnSum * priorityVector.get(i);
        }

        /**
         * ИС
         * Индекс согласованности
         */
        double consistencyIndex = (lambdaMax - n)/(n - 1);
        if (consistencyIndex < 0)
            consistencyIndex *= -1;

        /**
         * ОС
         * Отношение согласованности
         * должна быть порядка 10%, чтобы быть приемлемой.
         * В некоторых случаях можно допустить 20%, но не более.
         * Если ОС выходит из этих пределов, 
         * то участникам нужно исследовать задачу и проверить свои суждения.
         */
        double harmonization = (Math.abs(consistencyIndex) < 1.0E-6) ? 0
                                                    : (consistencyIndex / randomConsistency.get(n));

        ComparisonResult cr = new ComparisonResult(model.getCriterias(),
                m, priorityVector, lambdaMax, consistencyIndex, harmonization);
        //System.out.println(cr);

        return cr;
    }

    /**
     *
     * @param comparisonResult partial result for single comparing set
     * @return (ComPairedTableModel) single talbe for partial result
     */
    public TableModel getComPairedTableModel(ComparisonResult comparisonResult) {
        return new ComPairedTableModel(comparisonResult);
    }

    /**
     *
     * @param criterias criterias to display in header and cells
     * @return empty table model
     */
    public TableModel getEmptyComPairedTableModel(Vector<? extends Criteria> criterias) {

        if (null == criterias)
            criterias = problem.getAim().getCriterias();
            //criterias = problem.listAlternatives();

        return new ComPairedTableModel(criterias);
    }

    /**
     *
     * @return (ConsistencyTableModel) empty model for characteristics of result
     */
    public TableModel getEmptyConsistencyTableModel() {
        return new ConsistencyTableModel(null);
    }

    /**
     * calculates global result
     * @return global result calculated for all problem's ComparisonTableModels
     */
    public GlobalResult calculateResult() {
        Vector<Double> leafPriorities = new Vector<Double>();
        Vector<Aim> leafs = new Vector<Aim>();
        
        this.calcChildrenPriorities(problem.getAim(), 1, leafs, leafPriorities);

        int cols = leafs.size();
        Vector<Alternative> alternatives = problem.listAlternatives();
        int rows = alternatives.size();
        Matrix finalPriorities = new Matrix(rows, cols);

        for (int i=0; i<cols; i++) {
            ComparisonResult result = this.calculate(hm.get(leafs.get(i)));
            Vector<Double> pV = result.getPriorityVector();
            for (int j=0; j<rows; j++) {
                finalPriorities.setElementAt(pV.get(j), j, i);
            }
        }

        //Считаем глобальные приоритеты
        Vector<Double> globalPriorities = finalPriorities.multiplicate(leafPriorities);

        lastResult = new GlobalResult(leafPriorities, finalPriorities, globalPriorities,
                    leafs, alternatives);
        return lastResult;
    }

    /**
     * recursively calculates final priorities (taking to mind parents in hierarchy)
     * for this aim and all its children
     * @param aim aim (may not top) to get children from
     * @param aimPriority this aim's calculated priority
     * @param calculatedLeafs already calculated leafs collection to add detected children to
     * @param calculatedPriorities priorities collection to add calculated here priorities to
     */
    protected void calcChildrenPriorities(Aim aim, double aimPriority, Vector<Aim> calculatedLeafs,
                                              Vector<Double> calculatedPriorities) {
        if (aim.isLeaf())
            return;
        ComparisonResult result = this.calculate(hm.get(aim));
        Vector<Double> pV = result.getPriorityVector();
        Vector<Aim> criterias = aim.getCriterias();
        int size = criterias.size();
        for (int i=0; i<size; i++) {
            Aim cr = criterias.get(i);
            if (cr.isLeaf()) {
                calculatedLeafs.add(cr);
                calculatedPriorities.add(aimPriority * pV.get(i));
            } else {
                this.calcChildrenPriorities(cr, pV.get(i), calculatedLeafs, calculatedPriorities);
            }
        }
    }

    public void exportResultToDocx() throws JAXBException, InvalidFormatException, Docx4JException {
        DocxExporter de = new DocxExporter(lastResult, problem);
        de.export();
    }

    public void exportTablesToCSV(Vector<TableModel> tModels, Vector<String> titles) {
        new TableToCSVExporter(tModels, "Decision-making on " + problem.getTitle(), false, titles);
    }
}
