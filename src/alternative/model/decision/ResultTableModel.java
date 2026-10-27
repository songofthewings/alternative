/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model.decision;

import alternative.model.Alternative;
import alternative.model.Criteria;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jiraffa
 */
public class ResultTableModel extends DefaultTableModel {

    //protected Vector<? extends ProblemTreeNode> criterias;

    public ResultTableModel(GlobalResult result) {
        super ();

        Vector<String> header = new Vector<String>();
        header.add("");
        for (Criteria c : result.getLeafs())
            header.add(c.getTitle());
        header.add("[Global priorities]");

        Vector<Vector<Object>> tData = new Vector<Vector<Object>>();
        Vector<Object> firstRow = new Vector<Object>();
        firstRow.add("");
        for (Double leafP : result.getLeafPriorities()) {
            firstRow.add(String.format("%.3f", leafP));
        }
        firstRow.add("");
        tData.add(firstRow);

        Vector<Alternative> alternatives = result.getAlternatives();
        Vector<Double> globalPriorities = result.getGlobalPriorities();
        Matrix finalPriorities = result.getFinalPriorities();
        int n = alternatives.size();
        for (int i=0; i<n; i++) {
            Vector<Object> row = new Vector<Object>();
            row.add(alternatives.get(i).getTitle());
            for (int j=0; j<finalPriorities.getCols(); j++) {
                row.add(String.format("%.3f", finalPriorities.get(i, j)));
            }
            row.add(String.format("%.3f", globalPriorities.get(i)));
            
            tData.add(row);
        }

        this.setDataVector(tData, header);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
}
