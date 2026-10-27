/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model.decision;

import alternative.model.Criteria;
import alternative.model.ProblemTreeNode;
import java.util.Vector;
import javax.swing.tree.MutableTreeNode;

/**
 *
 * @author jiraffa
 */
public class ComparisonResult {

    protected Vector<? extends Criteria> criterias;
    /**
     * Матрица попарных сравнений
     */
    protected Matrix comparisons;
    /**
     * Вектор приоритетов
     */
    protected Vector<Double> priorityVector;

    /** Оценки результатов */
    /** lambda max */
    protected double lambdaMax;
    /** ИС - индекс согласованности */
    protected double consistencyIndex;
    /** ОС - отношение согласованности */
    protected double harmonization;

    public ComparisonResult(Vector<? extends Criteria> criterias,
                            Matrix comparisons, Vector<Double> priorityVector,
                            double lambdaMax, double consistencyIndex, double harmonization) {
        this.criterias = criterias;
        this.comparisons = comparisons;
        this.priorityVector = priorityVector;
        this.lambdaMax = lambdaMax;
        this.consistencyIndex = consistencyIndex;
        this.harmonization = harmonization;
    }

    public Vector<? extends Criteria> getCriterias() {
        return criterias;
    }

    /**
     * Матрица попарных сравнений
     * @return the comparisons
     */
    public Matrix getComparisons() {
        return comparisons;
    }

    /**
     * Вектор приоритетов
     * @return the priorityVector
     */
    public Vector<Double> getPriorityVector() {
        return priorityVector;
    }

    /**
     * lambda max
     * @return the lambdaMax
     */
    public double getLambdaMax() {
        return lambdaMax;
    }

    /**
     * ИС - индекс согласованности
     * @return the consistencyIndex
     */
    public double getConsistencyIndex() {
        return consistencyIndex;
    }

    /**
     * ОС - отношение согласованности
     * @return the harmonization
     */
    public double getHarmonization() {
        return harmonization;
    }

    public String toString() {
        return "\nComparisonResult:"
                + "\npriorityVector: "
                + this.getPriorityVector()
                + "\nlamda_max = " + lambdaMax
                + "\nIS = " + consistencyIndex
                + "\nOS = " + harmonization
                + "\n";
    }
}
