/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.view;

import alternative.model.Alternative;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author jiraffa
 */

public class AltList extends Tree<Alternative> {

    @Override
    public void add(Alternative node) {
        DefaultTreeModel m = (DefaultTreeModel)(this.getModel());
        MutableTreeNode target = (MutableTreeNode) m.getRoot();
        ((DefaultTreeModel)(this.getModel())).insertNodeInto(
                node,
                target,
                target.getChildCount());
        TreePath tp = new TreePath(new MutableTreeNode[] {target, node});
        this.scrollPathToVisible(tp);
        if (this.isEditable())
            this.startEditingAtPath(tp);
    }
    
}
