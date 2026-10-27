/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alternative.controller.decision;

import alternative.controller.DecisionController;
import alternative.view.Messager;
import javax.xml.bind.JAXBException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.jdesktop.application.Task;

/**
 *
 * @author jiraffa
 */
public class ExportToDocxTask extends Task<Object, Void> {

    DecisionController controller;
    Messager messager;

    public ExportToDocxTask(org.jdesktop.application.Application app,
            DecisionController controller, Messager messager) {
        // Runs on the EDT.  Copy GUI state that
        // doInBackground() depends on from parameters
        // to ExportToDocxTask fields, here.
        super(app);

        this.controller = controller;
        this.messager = messager;
    }

    @Override
    protected Object doInBackground() {
        // Your Task's code here.  This method runs
        // on a background thread, so don't reference
        // the Swing GUI from here.
        try {
            controller.exportResultToDocx();
        } catch (JAXBException je) {
            messager.error(je, "Error while creating docx format");
        } catch (InvalidFormatException ife) {
            messager.error(ife, "Invalid Format");
        } catch (Docx4JException dje) {
            messager.error(dje, "Error while writing docx format");
        }
        return null;  // return your result
    }

    @Override
    protected void succeeded(Object result) {
        // Runs on the EDT.  Update the GUI based on
        // the result computed by doInBackground().
    }
}
