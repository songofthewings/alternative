/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alternative.model.decision;

import alternative.AlternativeApp;
import alternative.model.Aim;
import alternative.model.Alternative;
import alternative.model.Problem;
import alternative.model.decision.GlobalResult;
import java.awt.Component;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.docx4j.convert.out.flatOpcXml.FlatOpcXmlCreator;
import org.docx4j.jaxb.Context;
import org.docx4j.jaxb.NamespacePrefixMapperUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.jdesktop.application.ResourceMap;

/**
 *
 * @author jiraffa
 */
public class DocxExporter {

    protected GlobalResult source;
    protected Problem problem;

    protected JFileChooser chooser;
    protected File file;

    public DocxExporter(GlobalResult source, Problem problem) {
        super();

        this.source = source;
        this.problem = problem;
    }

    public void export() throws JAXBException, InvalidFormatException, Docx4JException {
        if (this.obtainFileName()) {
            this.write();
        }
    }

    protected boolean obtainFileName()/* throws JAXBException, InvalidFormatException, Docx4JException*/ {

        ResourceMap rm = AlternativeApp.getApplication().getContext().getResourceMap(this.getClass());
        FileNameExtensionFilter filter = new FileNameExtensionFilter(rm.getString("DOCX_FORMAT"), "docx");
        if (chooser == null) {
            chooser = new JFileChooser();
            chooser.setDialogTitle(rm.getString("SAVING_REPORT"));
            chooser.setFileFilter(filter);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setSelectedFile(new File("report.docx"));//#NOI18N
            chooser.setAcceptAllFileFilterUsed(false);
        }

        int val = chooser.showSaveDialog((Component) null);

        if (val == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            return true;
            //boolean fixed = fixExtension(file, "csv");
/*
            if(!fixed && !cancelOp) {
            JOptionPane.showMessageDialog(null,"File Name Specified Not Supported",
            "File Name Error", JOptionPane.ERROR_MESSAGE);
            obtainFileName();
            return;
            }*/
            /*
            if(!cancelOp) {
            //storeTableAsCSV(file, source);
            //new TableToCSVExporter.ProgressDialog(source, file).setVisible(true);
            this.write();
            }
             *
             */
        }
        return false;
    }

    protected StringBuffer appendChildren(StringBuffer sb, Aim aim, String prefix) {
        if (aim.isLeaf())
            return sb;
        int size = aim.getCriterias().size();
        for (int i=0; i < size; i++) {
            Aim ch = aim.getCriterias().get(i);
            String chPrefix = prefix + (prefix.isEmpty() ? "" : ".") + (i+1);
            sb.append(chPrefix + " " + ch + "\n");
            this.appendChildren(sb, ch, chPrefix);
        }
        return sb;
    }

    protected void write() throws JAXBException, InvalidFormatException, Docx4JException {

        boolean save = true;
        ResourceMap rm = AlternativeApp.getApplication().getContext().getResourceMap(this.getClass());

        //System.out.println("Creating package..");
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();

        wordMLPackage.getMainDocumentPart().addStyledParagraphOfText(
                "Heading1", String.format(rm.getString("REPORT_ON_DECISIONMAKING"), problem.getTitle()));

        //wordMLPackage.getMainDocumentPart().addParagraphOfText("from docx4j!");


        MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();

        StringBuffer sb = new StringBuffer();
        mdp.addObject(this.getBoldP(rm.getString("CRITERIAS")));
        this.appendChildren(sb, problem.getAim(), "");
        mdp.addParagraphOfText(sb.toString());
        sb.delete(0, sb.length());


        HashMap<Alternative, Integer> hm = new HashMap<Alternative, Integer>();
        //mdp.addObject(this.getBoldP("Alternatives:"));
        int size = problem.listAlternatives().size();
        for (int i=0; i < size; i++) {
            Alternative alternative = problem.listAlternatives().get(i);
            //sb.append(alternative + "\n");

            hm.put(alternative, (int) Math.round(source.getGlobalPriorities().get(i)*100));
        }
        //mdp.addParagraphOfText(sb.toString());
        //sb.delete(0, sb.length());

        mdp.addObject(this.getBoldP(rm.getString("ACCORDING_TO_GIVEN_PRIORITIES_SUCH_RECOMMENDATIONS")));
        source.getGlobalPriorities();
        Integer[] priorities = new Integer[hm.size()];
        hm.values().toArray(priorities);
        Arrays.sort(priorities);
        Vector<Alternative> alternatives = problem.listAlternatives();
        for (int i=priorities.length-1; i>=0; i--) {
            Integer p = priorities[i];
            for (Alternative alt : alternatives) {
                if (null != hm.get(alt) && hm.get(alt).equals(p)) {
                    sb.append(alt + " - " + p + "%\n");
                    hm.remove(alt);
                    break;
                }
            }
        }
        mdp.addParagraphOfText(sb.toString());


        // To get bold text, you must set the run's rPr@w:b,
        // so you can't use the createParagraphOfText convenience method

        //org.docx4j.wml.P p = wordMLPackage.getMainDocumentPart().createParagraphOfText("text");
/*
        org.docx4j.wml.ObjectFactory factory = new org.docx4j.wml.ObjectFactory();
        org.docx4j.wml.P p = factory.createP();

        org.docx4j.wml.Text t = factory.createText();
        t.setValue("text\ntext1\ntext2\n\ntext3");

        org.docx4j.wml.R run = factory.createR();
        run.getRunContent().add(t);

        p.getParagraphContent().add(run);


        org.docx4j.wml.RPr rpr = factory.createRPr();
        org.docx4j.wml.BooleanDefaultTrue b = new org.docx4j.wml.BooleanDefaultTrue();
        b.setVal(true);
        rpr.setB(b);

        run.setRPr(rpr);

        // Optionally, set pPr/rPr@w:b
        org.docx4j.wml.PPr ppr = factory.createPPr();
        p.setPPr(ppr);
        org.docx4j.wml.ParaRPr paraRpr = factory.createParaRPr();
        ppr.setRPr(paraRpr);
        rpr.setB(b);
 * 
 */


        //wordMLPackage.getMainDocumentPart().addObject(this.getBoldP("text\ntext1\ntext2\n\ntext3"));

/*
        // Here is an easier way:
        String str = "<w:p xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" ><w:r><w:rPr><w:b /></w:rPr><w:t>Bold, just at w:r level</w:t></w:r></w:p>";

        wordMLPackage.getMainDocumentPart().addObject(
                org.docx4j.XmlUtils.unmarshalString(str));

        // Let's add a table
        int writableWidthTwips = wordMLPackage.getDocumentModel().getSections().get(0).getPageDimensions().getWritableWidthTwips();
        int cols = 3;
        int cellWidthTwips = new Double(
                Math.floor((writableWidthTwips / cols))).intValue();

        Tbl tbl = TblFactory.createTable(3, 3, cellWidthTwips);
        wordMLPackage.getMainDocumentPart().addObject(tbl);


        // Add an altChunk
        // .. the part
        String html = "<html><head><title>Import me</title></head><body><p>Hello World!</p></body></html>";
        AlternativeFormatInputPart afiPart = new AlternativeFormatInputPart(new PartName("/hw.html"));
        afiPart.setBinaryData(html.getBytes());
        afiPart.setContentType(new ContentType("text/html"));
        Relationship altChunkRel = wordMLPackage.getMainDocumentPart().addTargetPart(afiPart);
        // .. the bit in document body
        CTAltChunk ac = Context.getWmlObjectFactory().createCTAltChunk();
        ac.setId(altChunkRel.getId());
        wordMLPackage.getMainDocumentPart().addObject(ac);

        // .. content type
        wordMLPackage.getContentTypeManager().addDefaultContentType("html", "text/html");
 * 
 */

        //injectDocPropsCustomPart(wordMLPackage);

        // Now save it
        if (save) {
            //wordMLPackage.save(new java.io.File(System.getProperty("user.dir") + "/ad.docx"));
            wordMLPackage.save(file);
            //System.out.println("Saved.");
        } else {
            // Create a org.docx4j.wml.Package object
            FlatOpcXmlCreator worker = new FlatOpcXmlCreator(wordMLPackage);
            org.docx4j.xmlPackage.Package pkg = worker.get();

            // Now marshall it
            JAXBContext jc = Context.jcXmlPackage;
            Marshaller marshaller = jc.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            NamespacePrefixMapperUtils.setProperty(marshaller,
                    NamespacePrefixMapperUtils.getPrefixMapper());
            System.out.println("\n\n OUTPUT ");
            System.out.println("====== \n\n ");
            marshaller.marshal(pkg, System.out);

        }

        //System.out.println("Done.");

    }

    protected org.docx4j.wml.P getBoldP(String pText) {
        org.docx4j.wml.ObjectFactory factory = new org.docx4j.wml.ObjectFactory();
        org.docx4j.wml.P p = factory.createP();

        org.docx4j.wml.Text t = factory.createText();
        t.setValue(pText);

        org.docx4j.wml.R run = factory.createR();
        run.getRunContent().add(t);

        p.getParagraphContent().add(run);


        org.docx4j.wml.RPr rpr = factory.createRPr();
        org.docx4j.wml.BooleanDefaultTrue b = new org.docx4j.wml.BooleanDefaultTrue();
        b.setVal(true);
        rpr.setB(b);

        run.setRPr(rpr);

        // Optionally, set pPr/rPr@w:b
        org.docx4j.wml.PPr ppr = factory.createPPr();
        p.setPPr(ppr);
        org.docx4j.wml.ParaRPr paraRpr = factory.createParaRPr();
        ppr.setRPr(paraRpr);
        rpr.setB(b);

        return p;
    }

    public static void injectDocPropsCustomPart(WordprocessingMLPackage wordMLPackage) {

        try {
            org.docx4j.openpackaging.parts.DocPropsCustomPart docPropsCustomPart = new org.docx4j.openpackaging.parts.DocPropsCustomPart();

            java.io.InputStream is = new java.io.FileInputStream("/tmp/custompart.xml");

            docPropsCustomPart.unmarshal(is);

            wordMLPackage.addTargetPart(docPropsCustomPart);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
