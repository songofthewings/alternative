/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author jiraffa
 */
public class FileListSuffixFilter implements FilenameFilter {

    protected String suffix;

    public FileListSuffixFilter(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public boolean accept(File dir, String name) {
        return ( name.endsWith(suffix) && !name.equals(suffix)
                    || (null == suffix))
                && !new File(dir, name).isDirectory();
    }

}
