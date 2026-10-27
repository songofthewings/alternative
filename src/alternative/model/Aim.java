/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model;

import java.util.Vector;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author jiraffa
 */


@SuppressWarnings("serial")
@XmlRootElement(name="aim")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="Aim", propOrder={
    "title",
    "criterias"
})
public class Aim extends ProblemTreeNode implements PossibleDeletableTreeNode, Criteria {
        //DefaultMutableTreeNode {
    //implements Cloneable, MutableTreeNode, Serializable {

    public Aim(String title) {
        //node = new DefaultMutableTreeNode(title, true);
        super(title, true);
    }

    @SuppressWarnings("rawtypes")
    private Aim() {
        super(null);
    }

    @Override
    public boolean canBeDeleted() {
        return !this.isRoot();
    }

    public boolean setTitle(String title) {
        this.setUserObject(title);

        return true;
    }

    @XmlElement(name="title")
    public String getTitle() {
        return (String)(this.getUserObject());
    }

    /**
     * uset by JAXB for while marshalling and unmarshalling  (!)
     * @return
     */
    @SuppressWarnings("unchecked")
    @XmlElements({
        @XmlElement(name="aim", type=Aim.class)
    })
    @XmlElementWrapper(name="criterias")
    public Vector<Aim> getCriterias() {
        return (Vector<Aim>)this.children;
    }

    /**
     * created for JAXB but not used
     * @param criterias
     */
    protected void setCriterias(Vector<Aim> criterias) {
        for (Aim ac : criterias) {
           this.add(ac);
        }
    }

/*
    public void afterMarshal(Marshaller source) {
        System.out.println("afterMrshal: "+this+" ["+this.getChildCount()+"]");
    }
 * 
 */

    /**
     * callback for Unmarshal with JAXB
     * @param u
     * @param parent
     */
    private void afterUnmarshal(Unmarshaller u, Object parent) {
        //System.out.println("afterUnmarshal: "+parent+"->"+this+" ["+this.getChildCount()+"]");
        if (parent instanceof Aim) {
            ((Aim) parent).add(this);
        }
    }

    public String toStringWithAncestors() {
        if (this.isRoot())
            return "main aim";
        StringBuffer sb = new StringBuffer();
        for (Object o : this.getUserObjectPath()) {
            sb.append(
                    (sb.length() > 0 ? " > " : "")
                    + o
                    );
        }
        return "criteria"
                        + " ("+sb+")";
    }
}
