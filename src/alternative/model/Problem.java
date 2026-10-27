/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@XmlRootElement(name="problem")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="problem", propOrder={
    "aim",
    "alternatives"
})
public class Problem extends Object
        implements Cloneable, Serializable {

    protected Aim aim;
    @XmlElementWrapper
    @XmlElements({
        @XmlElement(name="alternative", type=Alternative.class)
    })
    protected Vector<Alternative> alternatives;

    public Problem(Aim aim, Collection<Alternative> alts) throws HierarchyException {
        super();

        if (null == aim || null == alts
                || !aim.isRoot() || aim.getChildCount() < 2
                || alts.size() < 2) {
            throw new HierarchyException("Wrong parameters passed to Problem constructor");
        }
        //System.out.println("IN Problem (aim, alts)");
        //System.out.println(aim);
        this.aim = aim;
        //System.out.println(alts);
        this.alternatives = new Vector<Alternative>(alts);
    }

    /**
     * constructor for JAXB
     */
    private Problem() {
        super();
        //this.alternatives = new Vector<Alternative>();
    }

    public Aim getAim() {
        return aim;
    }

    static public final Enumeration<Alternative> EMPTY_ENUMERATION
	= new Enumeration<Alternative>() {
        @Override
	    public boolean hasMoreElements() { return false; }
        @Override
	    public Alternative nextElement() {
		throw new NoSuchElementException("No more elements");
	    }
    };

    public Enumeration<Alternative> getAlternatives() {
        if (null == alternatives)
            return EMPTY_ENUMERATION;
        return alternatives.elements();
    }

    public Vector<Alternative> listAlternatives() {
        return alternatives;
    }

    public String getTitle() {
        return this.aim.getTitle();
    }


    /**
     * for JAXB
     * @param aim
     */
    @XmlElement(name="aim")
    private void setAim(Aim aim) {
        this.aim = aim;
    }

    /**
     * callback for Unmarshal with JAXB
     * @param u
     * @param parent
     */
    private void beforeUnmarshal(Unmarshaller u, Object parent) {
        //System.out.println("beforeUnmarshal: "+parent+"->"+this+" ["+this.alternatives+"]");
        alternatives = new Vector<Alternative>();
    }

    /**
     * Created for JAXB but not used
     * @param alternatives
     */
    private void setAlternatives(Collection<Alternative> alternatives) {
        for (Alternative a : alternatives) {
            this.add(a);
        }
    }

    public void add(Alternative a) {
        if (null == this.alternatives)
            alternatives = new Vector<Alternative>();
        this.alternatives.add(a);
    }

}
