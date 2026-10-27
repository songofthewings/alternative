/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.controller;

import alternative.model.Aim;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

/**
 *
 * @author jiraffa
 */
public class AimTreeModelListener implements TreeModelListener {

    ProblemStructureController controller;

    public AimTreeModelListener(ProblemStructureController ac) {
        controller = ac;
    }

    @Override
    public void treeNodesChanged(TreeModelEvent e) {
        Aim criteria = (Aim) e.getSource();
        if ("".equals(criteria.getTitle())) {
            if (criteria.isRoot()) {
                criteria.setTitle("The main aim");
            }
            criteria.setTitle(controller.getCriteriaTitle((Aim) criteria.getParent(), false));
        }
    }

    @Override
    public void treeNodesInserted(TreeModelEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void treeNodesRemoved(TreeModelEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void treeStructureChanged(TreeModelEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
