/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model.decision;

import alternative.model.Criteria;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.MutableTreeNode;

/**
 *
 * @author jiraffa
 */
public class ComPairedTableModel extends DefaultTableModel {

    //protected Vector<? extends ProblemTreeNode> criterias;

    public ComPairedTableModel(Vector<? extends Criteria> criterias) {
        this(criterias, null);
    }

    public ComPairedTableModel(ComparisonResult result) {
        this(null, result);
    }

    protected ComPairedTableModel(Vector<? extends Criteria> criterias, ComparisonResult result) {
        super ();

        if (null == criterias && null == result) {
            Logger.getLogger(ComPairedTableModel.class.getName()).log(Level.SEVERE,
                    "Both criterias and result could not be null in ComPairedTableModel()");
            return;
        }
        if (null == criterias)
            criterias = result.getCriterias();

        //this.criterias = criterias;
        Criteria cr0 = criterias.get(0);

        Vector<String> header = new Vector<String>();
        header.add( null == cr0.getParent() ? "" : cr0.getParent().toString());
        for (Criteria ptn : criterias)
            header.add(ptn.toString());
        header.add("[Priorities Vector]");

        Vector<Vector<Object>> tData = new Vector<Vector<Object>>();
        int n = criterias.size();
        Matrix m = null;
        Vector<Double> pv = null;
        if (null != result) {
            m = result.getComparisons();
            pv = result.getPriorityVector();
        }
        for (int i = 0; i < n; i++) {
            Vector<Object> row = new Vector<Object>();
            row.add(criterias.get(i).toString());
            for (int j = 0; j < n; j++) {
                if (null != result)
                    row.add(String.format("%.3f", m.get(i, j)));
                else
                    row.add(1);
            }
            if (null != result)
                row.add(String.format("%.3f", pv.get(i)));
            else
                row.add(null);
            tData.add(row);
        }
        this.setDataVector(tData, header);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
}
