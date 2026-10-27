/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model.decision;

import alternative.model.Aim;
import alternative.model.Alternative;
import alternative.model.Criteria;
import alternative.model.ProblemTreeNode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.MutableTreeNode;

/**
 *
 * @author jiraffa
 */
public class ComparingTableModel extends DefaultTableModel{

    protected Vector<? extends Criteria> criterias;
    protected Aim aim;

    boolean[] canEdit = new boolean [] {
        false, true, false
    };

    public ComparingTableModel(Vector<? extends Criteria> criterias, Aim aim) {
        super ();
        this.criterias = criterias;
        this.aim = aim;
        Vector<Vector<Object>> tData = new Vector<Vector<Object>>();
        Boolean isAlternative = null;
        for (int i = 0; i < criterias.size(); i++) {
            Criteria compareFrom = criterias.elementAt(i);
            if (null == isAlternative)
                isAlternative = compareFrom instanceof Alternative;
            for (int j = i+1; j < criterias.size(); j++) {
                Criteria compareTo = criterias.elementAt(j);
                tData.add(new Vector<Object>(Arrays.asList(
                        compareFrom,
                        //" is...",
                        //new JPanel(),
                        isAlternative ? new AlternativePriority()
                                      : new CriteriaPriority(),
                        compareTo)));
            }
        }
        this.setDataVector(
            tData,
            new Vector<String>(Arrays.asList("Criteria", "Priority", "Criteria"))
        );
    }

    @SuppressWarnings("unchecked")
    public Matrix getComparison() {
        Vector<Object> v = this.getDataVector();
        int size = v.size();
        /** count criterias */
        int critCount = (int) ((1 + Math.sqrt(1 + 8*size)) / 2);

        Matrix m = new Matrix(critCount, critCount);
        int vi = 0;
        Vector<Object> row;
        for (int i = 0; i < critCount; i++) {
            for (int j = 0; j < critCount; j++) {
                if (j == i) {
                    m.setElementAt(1, i, j);
                    continue;
                } else if (j < i) {
                    continue;
                } else {
                    row = (Vector<Object>) v.get(vi);
                    Priority p = (Priority) row.get(1);
                    m.setElementAt(p.getValue(), i, j);
                    m.setElementAt( 1.0/p.getValue(), j, i);
                    vi++;
                }
            }
        }
        return m;
    }

    public Vector<? extends Criteria> getCriterias() {
        return criterias;
    }

    public Aim getAim() {
        return this.aim;
    }

    private DefaultTableModel getDefault() {
        return new DefaultTableModel (
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Criteria", "Priority", "Criteria"
            }
        );
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
    }

    /**
     * for determining class for each cell
     * used for renerer and editor
     * @param c
     * @return
     */
    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        /*
        System.out.println("Setting value at " + row + "," + col
                            + " to " + value
                            + " (an instance of "
                            + value.getClass() + ")");
         * 
         */
        super.setValueAt(value, row, col);
    }
}
