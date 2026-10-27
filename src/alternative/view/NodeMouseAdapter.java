
package alternative.view;

import alternative.model.PossibleDeletableTreeNode;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 *
 * @author jiraffa
 */
public class NodeMouseAdapter extends MouseAdapter {
    JTree tree;
    JPopupMenu popup;
    JMenuItem removeMenuItem;

    public NodeMouseAdapter(JTree tree, JPopupMenu popup, JMenuItem removeMenuItem) {
        this.tree = tree;
        this.popup = popup;
        this.removeMenuItem = removeMenuItem;
    }


    protected void togglePopup(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        //JTree aimTree = (JTree)e.getSource();
        TreePath selPath = tree.getPathForLocation(x, y);
        if (selPath == null) {
            return;
        }
        tree.setSelectionPath(selPath);

        PossibleDeletableTreeNode node = (PossibleDeletableTreeNode)selPath.getLastPathComponent();
        removeMenuItem.setEnabled(node.canBeDeleted());

        popup.show(tree, x, y);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            togglePopup(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            togglePopup(e);
        }
    }
}
