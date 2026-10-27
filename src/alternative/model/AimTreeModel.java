/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model;

import alternative.controller.AimTreeModelListener;
import alternative.model.Aim;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author jiraffa
 */
public class AimTreeModel extends DefaultTreeModel {
    static final int MIN_CRITERIAS = 2;
    static final int MAX_CRITERIAS = 9;

    protected ArrayList<String> messageList;

    public AimTreeModel(Aim aimNode) {
        super(aimNode);
    }

    public boolean isCorrect() {
        return this.isCorrect(true);
    }

    public List<String> getCorrectMessages() {
        this.cleanMessages();
        this.isCorrect(false);
        List<String> ml = this.messageList;
        this.messageList = null;
        return ml;
    }

    protected boolean isCorrect(boolean answerOnly) {
        Aim rootAim = (Aim) this.getRoot();
        return this.checkAim(rootAim, answerOnly);
    }

    protected boolean checkAim(Aim aim, boolean answerOnly) {
        boolean isCorrect = true;
        if (aim.getTitle().isEmpty()) {
            if (answerOnly)
                return false;
            isCorrect = false;
            this.messageList.add("Please set title for "
                    + aim.toStringWithAncestors());
        }
        if (aim.isLeaf())
            return isCorrect;
        //Enumeration<Aim> criterias = aim.getCriterias().elements();
        int size = aim.getChildCount();
        if (size < MIN_CRITERIAS || size > MAX_CRITERIAS) {
            if (answerOnly)
                return false;
            isCorrect = false;
            this.messageList.add("It must be from "
                    +MIN_CRITERIAS
                    +" to "
                    +MAX_CRITERIAS
                    +" criterias for each aim."
                    + "\n"
                    +"If you need more, please separate them between several nodes."
                    + "\n"
                    +"Caused by "
                    +aim.toStringWithAncestors()
                    +"."
                    );
        }
        @SuppressWarnings("unchecked")
        Enumeration<Aim> children = (Enumeration<Aim>) aim.children();
        while (children.hasMoreElements()) {
            Aim ac = children.nextElement();
            isCorrect = this.checkAim(ac, answerOnly) && isCorrect;
            if( !isCorrect && answerOnly)
                return false;
            Aim neighbour = ac;
            Aim n = (Aim) neighbour.getNextNode();
            while(null != (neighbour = (Aim) neighbour.getNextNode())) {
                System.out.println(neighbour);
                if (neighbour.getTitle().equals(ac.getTitle())) {
                    if (answerOnly)
                        return false;
                    isCorrect = false;
                    this.messageList.add("Please rename one of equal criterias."
                            + "\n"
                            + "Caused by "
                            + ac.toStringWithAncestors()
                            + ".");
                    break;
                }

                //System.out.println("NEXT: ");
                //System.out.println((Aim) neighbour.getNextNode());
            }
        }
        return isCorrect;
    }

    protected void cleanMessages() {
        messageList = new ArrayList<String>();
    }

}
