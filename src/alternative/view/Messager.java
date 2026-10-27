/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.view;

import java.awt.Component;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jiraffa
 */
public class Messager {

    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Component parent;
    protected String loggerName;

    public Messager(Component parentComponent, String loggerName) {
        this.parent = parentComponent;
        this.loggerName = loggerName;
    }

    public void warn(Exception e, String dialogTitle) {
        this.logAndShowMessage(e, dialogTitle, false);
    }

    public void error(Exception e, String dialogTitle) {
        this.logAndShowMessage(e, dialogTitle, true);
    }

    public void log(Exception e) {
        toolkit.beep();
        Logger.getLogger(loggerName).log(Level.SEVERE, null, e);
    }

    protected void logAndShowMessage(Exception e, String dialogTitle, boolean isError) {
        if (isError)
            this.log(e);
        else
            toolkit.beep();
        JOptionPane.showMessageDialog(
                parent, e.getMessage(),
                dialogTitle,
                isError ? JOptionPane.ERROR_MESSAGE : JOptionPane.WARNING_MESSAGE
            );
    }
}
