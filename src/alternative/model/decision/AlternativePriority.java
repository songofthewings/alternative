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
public class AlternativePriority extends Priority {

    protected static Vector<String> statusMessages;

    public AlternativePriority() {
        super();
    }

    public AlternativePriority(double value) throws PriorityException {
        super(value);
    }

    @Override
    public Priority getCopy() {
        try {
            return new AlternativePriority(this.getValue());
        } catch (PriorityException ex) {
            Logger.getLogger(AlternativePriority.class.getName()).log(Level.SEVERE,
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
