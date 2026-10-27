/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model;

import java.io.Serializable;
import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author jiraffa
 */


@XmlRootElement(name="alternative")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="alternative", propOrder={
    "title"
})
public class Alternative //extends DefaultMutableTreeNode
        implements Cloneable, MutableTreeNode, Serializable, PossibleDeletableTreeNode,
                    Criteria {

    /** this node's parent, or null if this node has no parent */
    protected MutableTreeNode parent;
    @XmlElement
    protected String title;

    public Alternative(String title) {
        super();
	parent = null;
        this.title = title;
    }

    public Alternative() {
        this(null);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
        //return (String)(this.getUserObject());
    }

    @Override
    public boolean canBeDeleted() {
        return this.isLeaf();
    }

    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        throw new ArrayIndexOutOfBoundsException("node has no children");
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    /**
     * Sets this node's parent to <code>newParent</code> but does not
     * change the parent's child array.  This method is called from
     * <code>insert()</code> and <code>remove()</code> to
     * reassign a child's parent, it should not be messaged from anywhere
     * else.
     *
     * @param	newParent	this node's new parent
     */
    @Override
    public void setParent(MutableTreeNode newParent) {
	parent = newParent;
    }

    /**
     * Returns this node's parent or null if this node has no parent.
     *
     * @return	this node's parent TreeNode, or null if this node has no parent
     */
    @Override
    public TreeNode getParent() {
	return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return -1;
    }

    @Override
    public boolean getAllowsChildren() {
        return false;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    //@SuppressWarnings("rawtypes")
    public Enumeration children() {
        return DefaultMutableTreeNode.EMPTY_ENUMERATION;
    }

    @Override
    public void insert(MutableTreeNode child, int index) {
    }

    @Override
    public void remove(int index) {
    }

    @Override
    public void remove(MutableTreeNode node) {
    }

    @Override
    public void setUserObject(Object object) {
        this.setTitle(object.toString());
    }

    @Override
    public void removeFromParent() {
        MutableTreeNode parent = (MutableTreeNode)getParent();
	if (parent != null) {
	    parent.remove(this);
	}
    }

}
