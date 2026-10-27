/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 *
 * @author Ann Popova
 */
@XmlRegistry
public class ObjectFactory {
    protected final static QName _Aim_QNAME = new QName("", "aim");
    protected final static QName _Alternative_QNAME = new QName("", "alternative");
    protected final static QName _Problem_QNAME = new QName("", "problem");

    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Aim}
     * @return Aim
     */
    /*
    public Aim createAim() {
        return new Aim();
    }
     * 
     */

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Aim }{@code >}
     *
     * @param value Aim
     * @return
     */
    @XmlElementDecl(namespace = "", name = "aim")
    public JAXBElement<Aim> createAim(Aim value) {
        return new JAXBElement<Aim>(_Aim_QNAME, Aim.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Alternative }{@code >}
     *
     * @param value Aim
     * @return
     */
    @XmlElementDecl(namespace = "", name = "alternative")
    public JAXBElement<Alternative> createAlternative(Alternative value) {
        return new JAXBElement<Alternative>(_Alternative_QNAME, Alternative.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Problem }{@code >}
     *
     * @param value Aim
     * @return
     */
    @XmlElementDecl(namespace = "", name = "problem")
    public JAXBElement<Problem> createProblem(Problem value) {
        return new JAXBElement<Problem>(_Problem_QNAME, Problem.class, null, value);
    }
}
