/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model.decision;

import alternative.model.decision.Priority;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jiraffa
 */
public class CriteriaPriority extends Priority {

    protected static Vector<String> statusMessages;

    public CriteriaPriority(double value) throws PriorityException {
        super(value);
    }

    public CriteriaPriority() {
        super();
    }

    @Override
    public Priority getCopy() {
        try {
            return new CriteriaPriority(this.getValue());
        } catch (PriorityException ex) {
            Logger.getLogger(CriteriaPriority.class.getName()).log(Level.SEVERE,
                    "Copying must not throw exception", ex);
            return null;
        }
    }
    
    @Override
    protected Vector<String> getStatusMessages() {
        return statusMessages;
    }

    @Override
    protected void initStatusMessages() {
        statusMessages = new Vector<String>();
    }

}
