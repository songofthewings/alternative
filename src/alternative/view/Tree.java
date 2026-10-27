/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.view;

import alternative.model.HierarchyException;
import alternative.model.PossibleDeletableTreeNode;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @param <T> tree node
 * @author jiraffa
 */

public abstract class Tree<T extends PossibleDeletableTreeNode> extends JTree {

    public abstract void add(T node) throws HierarchyException;

    public void startEditingTarget() {
        try {
            this.startEditingAtPath(this.getTargetPath());
        } catch (NothingSelectedException ex) {
            Logger.getLogger(Tree.class.getName()).log(Level.SEVERE, "Editing node had to find default node.", ex);
        }
    }

    public void removeSelected() throws NothingSelectedException {
        T target = this.getTargetNode(true);
        if (target.canBeDeleted()) {
            //System.out.println(targetAim.getUserObject());
            DefaultTreeModel tm = (DefaultTreeModel) this.getModel();
            tm.removeNodeFromParent(target);
        } else {
            Logger.getLogger(Tree.class.getName()).log(Level.SEVERE, "Root node can not be deleted.");
        }
    }

    public TreePath getTargetPath() throws NothingSelectedException {
        return this.getTargetPath(false);
    }
    
    public TreePath getTargetPath(Boolean isSelectionRequired) throws NothingSelectedException {
        TreePath selectedAimPath = this.getSelectionPath();
        if (null == selectedAimPath) {
            if (isSelectionRequired) {
                throw new NothingSelectedException("Nothing selected to delete.");
            }
            selectedAimPath = this.getPathForRow(0/*1*/);
        }
        return selectedAimPath;
    }

    public T getTargetNode() throws NothingSelectedException {
        return this.getTargetNode(false);
    }
    
    @SuppressWarnings("unchecked")
    public T getTargetNode(Boolean isSelectionRequired) throws NothingSelectedException {
        return (T) this.getTargetPath(isSelectionRequired).getLastPathComponent();
    }
}
