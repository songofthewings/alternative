/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.controller;

import alternative.AlternativeApp;
import java.io.File;
import java.io.IOException;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;

/**
 *
 * @author jiraffa
 */
public class ProblemController {

    protected ApplicationContext getAppContext() {
        return Application.getInstance(AlternativeApp.class).getContext();
    }

    protected String getFileSuffix() {
        return this.getAppContext().getResourceMap(AlternativeApp.class)
                .getString("problemFile.suffix");
    }

    /**
     *
     * @param problemName main aim title for choice (filename prefix)
     * @return full filename
     * @throws IOException
     */
    protected String getFilename(String problemName) throws IOException {
        return problemName + this.getFileSuffix();
    }

    protected String getFilePath(String problemName) throws IOException {
        return this.getStorageDir() + File.separator + this.getFilename(problemName);
    }

    protected File getStorageDir() {
        return this.getAppContext().getLocalStorage().getDirectory();
    }
}
