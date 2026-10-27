/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.controller.decision;

import alternative.controller.DecisionController;
import alternative.model.decision.ComparingTableModel;
import alternative.model.decision.ComparisonResult;
import alternative.model.decision.ConsistencyTableModel;
import alternative.view.NothingSelectedException;
import alternative.view.decision.ComparisonTab;

/**
 *
 * @author jiraffa
 */
public class CalculateTabTask extends org.jdesktop.application.Task<Object, Void> {
    protected ComparisonTab selectedTab;
    protected DecisionController controller;

    public CalculateTabTask(org.jdesktop.application.Application app, ComparisonTab selectedTab,
                        DecisionController controller) {
        // Runs on the EDT.  Copy GUI state that
        // doInBackground() depends on from parameters
        // to CalculateTabTask fields, here.
        super(app);
        this.selectedTab = selectedTab;
        this.controller = controller;
    }
    @Override protected Object doInBackground() {
        // Your Task's code here.  This method runs
        // on a background thread, so don't reference
        // the Swing GUI from here.

        // return your result
        return controller.calculate(
                (ComparingTableModel) selectedTab.getComparingTable().getModel());
    }
    @Override protected void succeeded(Object result) {
        // Runs on the EDT.  Update the GUI based on
        // the result computed by doInBackground().
        if (null != result) {
            ComparisonResult cr = (ComparisonResult) result;
            selectedTab.getComPairedTable().setModel(
                    controller.getComPairedTableModel(cr));
            selectedTab.getConsistencyTable().setModel(new ConsistencyTableModel(cr));
        }
    }
}
