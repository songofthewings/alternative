/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model.decision;

import java.awt.Color;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jiraffa
 */
public abstract class Priority {
    final static double MIN_VALUE = 0.001;
    final static double dValue = 0.000001;
    final static Color LESS = new Color(204, 255, 255);
    final static Color EQUAL = new Color(204, 255, 204);
    final static Color MORE = new Color(255, 204, 204);
    final static int SCALE = 100;

    protected final ResourceBundle resourceBundle;
    
    //protected static Vector<String> statusMessages;

    double value;

    public Priority() {
        resourceBundle = ResourceBundle.getBundle("alternative.model.decision.resources."+this.getClass().getSimpleName());
        this.value = 1;
    }

    public Priority(double value) throws PriorityException {
        resourceBundle = ResourceBundle.getBundle("alternative.model.decision.resources."+this.getClass().getSimpleName());
        this.setValue(value);
    }

    public void setValue(double value) throws PriorityException {
        if (value >= ((double)SCALE)/MIN_VALUE) {
            String message = "Priority value > max";
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, message, this);
            //throw new PriorityException(message);
            this.value = ((double)SCALE) / (MIN_VALUE+dValue);
        } else if (value <= MIN_VALUE) {
            String message = "Priority value < min";
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, message, this);
            //throw new PriorityException(message);
            this.value = MIN_VALUE+dValue;
        } else {
            this.value = value;
        }
    }

    public double getValue() {
        return value;
    }

    public double getLeft() {
        return getValue() * SCALE / (getValue() + 1);
    }

    public double getRight() {
//        System.out.println("Left: "+getLeft());
        return ((double) SCALE) / (getValue() + 1);
    }

    protected String getMessage() {
        if (null == this.getStatusMessages()) {
            this.initStatusMessages();
            int i = 0;
            try {
                while (true) {
                    this.getStatusMessages().add(resourceBundle.getString("Priority.statusMessages["+ (i++) +"]"));//#NOI18N
                }
            } catch (Exception e) {
            }
            //String[] messages = resourceBundle.getStringArray("Priority.statusMessages[]");//#NOI18N
        }
        if (this.getStatusMessages().isEmpty())
            return "%.3f";
        int indexInArray = (int) Math.round((this.getStatusMessages().size()-1)
                                            / (this.getValue() + 1));
        return this.getStatusMessages().get(indexInArray);
    }

    @Override
    public String toString() {
        return String.format(this.getMessage(), this.getLeft(), this.getRight(), this.getValue());
    }

    public boolean equals(Priority other) {
        return Math.abs(other.getValue() - this.getValue()) < dValue;
    }

    public Color getColor() {
        return (Math.abs(this.getValue() - 1) < dValue) ? EQUAL
                                                    : (this.getValue() > 1 ? MORE
                                                                            : LESS);
    }

    public abstract Priority getCopy();

    protected abstract Vector<String> getStatusMessages();
    protected abstract void initStatusMessages();

}
