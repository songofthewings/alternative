/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model.decision;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jiraffa
 */
public class ConsistencyTableModel extends DefaultTableModel {

    //protected Vector<? extends ProblemTreeNode> criterias;

    public ConsistencyTableModel(ComparisonResult result) {
        super ();

        this.setDataVector(
            new Object [][] {
                {
                    "Lambda max",
                    (null == result) ? null
                                     : String.format("%.3f", result.getLambdaMax())
                },
                {
                    "Consistency Index",
                    (null == result) ? null
                                     : String.format("%.3f", result.getConsistencyIndex())
                },
                {
                    "Harmonization",
                    (null == result) ? null
                                     : String.format("%.3f", result.getHarmonization())
                }
            },
            new Object[] {"Parameter", "Value"}
            //new Vector<String>(Arrays.asList("Parameter", "Value"))
        );
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
}
