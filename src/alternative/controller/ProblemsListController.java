/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.controller;

import alternative.model.FileListSuffixFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ListModel;

/**
 *
 * @author jiraffa
 */
public class ProblemsListController extends ProblemController {

    private static final Calendar expireDate = null;//new GregorianCalendar(2010, Calendar.MARCH, 8);
    //private static final Calendar expireDate = new GregorianCalendar(2010, Calendar.FEBRUARY, 8);

    public ListModel getListModel() {
        File lsDir = this.getStorageDir();
        final String[] pTitles;
        if ( !lsDir.isDirectory()) {
            pTitles = new String[0];
        } else {
            String suffix = this.getFileSuffix();
            //final String[] matched = lsDir.list(new FileListSuffixFilter(suffix));
            File[] files = lsDir.listFiles(new FileListSuffixFilter(suffix));
            int fl = files.length;
            String[] problems = new String[fl];
            for (int i=0; i<fl; i++) {
                String fn = files[i].getName();
                problems[i] = fn.substring(0, fn.lastIndexOf(suffix));
            }
            pTitles = problems;
        }
        return new javax.swing.AbstractListModel() {
            String[] strings = pTitles;
                    //{ "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            @Override
            public int getSize() { return strings.length; }
            @Override
            public Object getElementAt(int i) { return strings[i]; }
        };
    }

    public boolean removeProblem(String problemName) throws IOException {
        File toRemove = new File(this.getFilePath(problemName));
        if ( !toRemove.exists() || !toRemove.isFile())
            throw new FileNotFoundException("Alternative Problem file not found: "+toRemove);
        return toRemove.delete();
    }

    public boolean isLicenseExpired() {
        return null != expireDate && expireDate.before(new GregorianCalendar());
    }

    public boolean isLicenseDemo() {
        return null != expireDate;
    }

    public Calendar getExpireDate() {
        return this.expireDate;
    }

}
