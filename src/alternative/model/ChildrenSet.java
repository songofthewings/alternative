/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model;

import java.util.Vector;

/**
 * Node's Children setter for JAXB Collection
 *
 * @param <E> real container for elements
 * @author jiraffa
 */
public class ChildrenSet<E extends ProblemTreeNode> extends Vector<E> {
    /**
     * parent for these children
     */
    ProblemTreeNode realContainer;

    protected ChildrenSet(ProblemTreeNode realContainer) {
        super();
        this.realContainer = realContainer;
    }
    
    /**
     * Appends the specified element to the end of this Vector.
     *
     * @param e element to be appended to this Vector
     * @return {@code true} (as specified by {@link Collection#add})
     * @since 1.2
     */
    @Override
    public synchronized boolean add(E e) {
        this.realContainer.add(e);
        //return super.add(e);
        return true;
    }
}
