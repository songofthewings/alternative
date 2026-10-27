/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.controller.decision;

import alternative.controller.DecisionController;
import alternative.model.decision.ComparingTableModel;
import alternative.model.decision.ComparisonResult;
import alternative.model.decision.ConsistencyTableModel;
import alternative.model.decision.GlobalResult;
import alternative.model.decision.ResultTableModel;
import alternative.view.decision.ComparisonTab;
import java.util.Collection;
import java.util.Vector;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author jiraffa
 */
public class CalculateAllTask extends org.jdesktop.application.Task<Object, Void> {
    DecisionController controller;
    JTable outputTable;
    Vector<ComparisonTab> tabsToUpdate;
    Vector<ComparisonResult> tabResults;
    Collection<JMenuItem> miToEnable;
    boolean exportToCSV;

    public CalculateAllTask(org.jdesktop.application.Application app, DecisionController controller,
            JTable outputTable, Vector<ComparisonTab> tabsToUpdate, Collection<JMenuItem> miToEnable) {
        this(app, controller,
            outputTable, tabsToUpdate, miToEnable,
            false);
    }
    
    public CalculateAllTask(org.jdesktop.application.Application app, DecisionController controller,
            JTable outputTable, Vector<ComparisonTab> tabsToUpdate, Collection<JMenuItem> miToEnable,
            boolean exportToCSV) {
        // Runs on the EDT.  Copy GUI state that
        // doInBackground() depends on from parameters
        // to CalculateAllTask fields, here.
        super(app);
        this.controller = controller;
        this.outputTable = outputTable;
        this.tabsToUpdate = tabsToUpdate;
        this.miToEnable = miToEnable;
        tabResults = new Vector<ComparisonResult>();
        this.exportToCSV = exportToCSV;
    }

    @Override protected Object doInBackground() {
        // Your Task's code here.  This method runs
        // on a background thread, so don't reference
        // the Swing GUI from here.

        GlobalResult gr = controller.calculateResult();
        for (ComparisonTab ct : tabsToUpdate) {
            tabResults.add(controller.calculate(
                        (ComparingTableModel) ct.getComparingTable().getModel())
                        );
        }
        return gr; // return your result
    }
    @Override protected void succeeded(Object result) {
        // Runs on the EDT.  Update the GUI based on
        // the result computed by doInBackground().
        Vector<TableModel> tModels = new Vector<TableModel>();
        Vector<String> titles = new Vector<String>();

        GlobalResult gr = (GlobalResult) result;
        TableModel resultModel = new ResultTableModel(gr);
        outputTable.setModel(resultModel);

        int size = tabResults.size();
        for (int i=0; i<size; i++) {
            ComparisonTab t = tabsToUpdate.get(i);
            ComparisonResult cr = tabResults.get(i);

            TableModel tabTableModel = controller.getComPairedTableModel(cr);
            t.getComPairedTable().setModel(tabTableModel);
            TableModel consistencyTableModel = new ConsistencyTableModel(cr);
            t.getConsistencyTable().setModel(consistencyTableModel);
            
            if (this.exportToCSV) {
                tModels.add(tabTableModel);
                tModels.add(consistencyTableModel);
                titles.add( ((ComparingTableModel) (t.getComparingTable().getModel())).getAim().toString() );
                titles.add("");
            }

        }
        for (JMenuItem mi : miToEnable) {
            mi.setEnabled(true);
        }

        if (this.exportToCSV) {
            tModels.add(resultModel);
            titles.add("Result");

            controller.exportTablesToCSV(tModels, titles);
        }
    }
}
