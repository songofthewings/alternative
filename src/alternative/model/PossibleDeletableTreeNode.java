/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model;

import javax.swing.tree.MutableTreeNode;

/**
 *
 * @author jiraffa
 */
public interface PossibleDeletableTreeNode extends MutableTreeNode {

    public boolean canBeDeleted();
}
