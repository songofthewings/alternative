/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.controller;

import alternative.view.AimTree;
import alternative.view.AltList;
import alternative.view.NothingSelectedException;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.xml.bind.JAXBException;
import alternative.model.*;

/**
 *
 * @author jiraffa
 */
public class ProblemStructureController extends ProblemDataController {

    public ProblemStructureController() {
    }

    public String getNewSubAimTitle(AimTree tree) throws HierarchyException {
        try {
            Aim aimContainer = tree.getTargetNode();
            if (aimContainer.getChildCount() >= 9) {
                throw new HierarchyException("One aim must contain less than 10 sub aims.");
            }
            return this.getCriteriaTitle(aimContainer, true);
        } catch (NothingSelectedException ex) {
            Logger.getLogger(ProblemStructureController.class.getName()).log(Level.SEVERE, "While finding new sub aim default aim had to be found.", ex);
            return null;
        }
    }

    public String getCriteriaTitle(Aim aim, boolean isNew) {
        return (aim.isRoot() ? "" : (aim.getTitle() + " "))
                + "Criteria"
                + (aim.getChildCount() + (isNew ? 1 : 0));
    }

    @Override
    public TreeModel getHierarchyModel(String problemName) throws JAXBException, IOException {
        TreeModel tm = super.getHierarchyModel(problemName);
        if (null == tm)
            return this.getNewTreeModel();
        return tm;
    }

    @Override
    public TreeModel getAlternativesListModel(String problemName) throws JAXBException, IOException {
        TreeModel tm = super.getAlternativesListModel(problemName);
        if (null == tm)
            return this.getNewAlternativesModel();
        return tm;
    }

    public TreeModel getNewTreeModel() {
        Aim treeNode1 = new Aim("Main aim");
        Aim treeNode2 = new Aim("Criteria 1");
        Aim treeNode3 = new Aim("Subcriteria 1.1");
        treeNode2.add(treeNode3);
        treeNode3 = new Aim("Subcriteria 1.2");
        treeNode2.add(treeNode3);
        treeNode3 = new Aim("Subcriteria 1.3");
        treeNode2.add(treeNode3);
        treeNode3 = new Aim("Subcriteria 1.4");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new Aim("Criteria 2");
        treeNode1.add(treeNode2);
        treeNode2 = new Aim("Criteria 3");
        treeNode3 = new Aim("Subcriteria 3.1");
        treeNode2.add(treeNode3);
        treeNode3 = new Aim("Subcriteria 3.2");
        treeNode2.add(treeNode3);
        treeNode3 = new Aim("Subcriteria 3.3");
        treeNode2.add(treeNode3);
        treeNode3 = new Aim("Subcriteria 3.4");
        Aim treeNode4 = new Aim("Subcriteria 3.4.1");
        treeNode3.add(treeNode4);
        treeNode4 = new Aim("Subcriteria 3.4.2");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        return new AimTreeModel(treeNode1);
    }

    public TreeModel getNewAlternativesModel() {
        return new AlternativesListModel(Arrays.asList(
                    new Alternative("Alternative 1"),
                    new Alternative("Alternative 2"),
                    new Alternative("Alternative 3")
                ));
    }

    public String getNewAlternativeTitle(AltList alternativesList) {
        return "Alternative"
                + " " + (((TreeNode) alternativesList.getModel().getRoot()).getChildCount() + 1);
    }

    public void addAim(AimTree aimTree) throws HierarchyException {
        String title = this.getNewSubAimTitle(aimTree);
        aimTree.add(new Aim(title));
    }

    public void addAlternative(AltList alternativesList) {
        String title = this.getNewAlternativeTitle(alternativesList);
        alternativesList.add(new Alternative(title));
    }
}
