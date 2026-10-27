/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.view;

import alternative.model.Aim;
import alternative.model.HierarchyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author jiraffa
 */

public class AimTree extends Tree<Aim> {

    @Override
    public void add(Aim node) throws HierarchyException {
        try {
            MutableTreeNode target = this.getTargetNode();
            System.out.println(target);
            ((DefaultTreeModel)(this.getModel())).insertNodeInto(
                    node,
                    target,
                    target.getChildCount());
            TreePath tp = new TreePath(node.getPath());
            this.scrollPathToVisible(tp);
            if (this.isEditable())
                this.startEditingAtPath(tp);
        } catch (NothingSelectedException ex) {
            Logger.getLogger(AimTree.class.getName()).log(Level.SEVERE, "Adding node action had to find default node.", ex);
        }
    }
    
}
