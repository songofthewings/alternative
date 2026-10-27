/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import javax.swing.tree.TreeModel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import alternative.model.*;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author jiraffa
 */
public class ProblemDataController extends ProblemController {
    
    public ProblemDataController() {
    }

    protected Object unMarshall(String filename) throws JAXBException, IOException {
        JAXBContext jc = JAXBContext.newInstance("alternative.model");
        Unmarshaller u = jc.createUnmarshaller();
        //File f = new File(path);
        InputStream is = this.getAppContext().getLocalStorage().openInputFile(filename);
        Object o = u.unmarshal(is);
        is.close();
        return o;
    }

    public TreeModel getHierarchyModel(String problemName) throws JAXBException, IOException {
        if (null == problemName || problemName.isEmpty())
            return null;
        Problem p = this.restore(problemName);
        return new AimTreeModel(p.getAim());
    }

    public TreeModel getAlternativesListModel(String problemName) throws JAXBException, IOException {
        if (null == problemName || problemName.isEmpty())
            return null;
        Problem p = this.restore(problemName);
        return new AlternativesListModel(Collections.list(p.getAlternatives()));
    }

    public Problem restore(String problemName) throws JAXBException, IOException {
        if (null == problemName)
            return null;
        String filename = this.getFilename(problemName);
        //Aim aim = (Aim) (ls.load(filename));
        @SuppressWarnings("unchecked")
        Problem p = ((JAXBElement<Problem>) this.unMarshall(filename)).getValue();
        if (null == p)
            throw new IOException("Could not load Problem from "+filename+".");
        return p;
    }

    protected void marshall(Object o, String filename) throws JAXBException, FileNotFoundException, IOException {
        System.out.println(filename);
        JAXBContext context = JAXBContext.newInstance("alternative.model");
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        //m.marshal(o, new FileOutputStream(path));
        OutputStream os = this.getAppContext().getLocalStorage().openOutputFile(filename);
        m.marshal(o, os);
        os.close();
        /*
        @SuppressWarnings("unchecked")
        Problem up = ((JAXBElement<Problem>) this.unMarshall(path)).getValue();
        System.out.println("UNMARSHALL: "+up);
         */
    }

    public void store(AimTreeModel treeModel, TreeModel alternativesModel) throws JAXBException, FileNotFoundException, IOException, HierarchyException {
        if( !treeModel.isCorrect()) {
            StringBuffer sb = new StringBuffer();
            for (String m : treeModel.getCorrectMessages())
                sb.append(m + "\n\n");
            throw new HierarchyException(sb.toString());
        }
        Aim rootAim = (Aim)(treeModel.getRoot());
        //ls.save(rootAim, this.getFilename(rootAim.getTitle()));

        @SuppressWarnings("unchecked")
        Problem p = new Problem(rootAim, 
                (Collections.list((Enumeration<Alternative>)
                    ((ProblemTreeNode) alternativesModel.getRoot()).children()))
                );
        this.marshall(p, this.getFilename(p.getTitle()));
    }

}
