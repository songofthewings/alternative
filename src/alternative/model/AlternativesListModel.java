/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author jiraffa
 */
@SuppressWarnings("serial")
public class AlternativesListModel extends DefaultTreeModel {
    static final int MIN_ALTERNATIVES = 2;

    protected ArrayList<String> messageList;

    public AlternativesListModel(Collection<Alternative> alternatives) {
        super(null);
        ProblemTreeNode rootNode = new ProblemTreeNode("Not visible");
        for (Alternative a : alternatives) {
            rootNode.add(a);
        }
        this.setRoot(rootNode);
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

    @SuppressWarnings("unchecked")
    protected boolean isCorrect(boolean answerOnly) {
        Alternative root = (Alternative) this.getRoot();
        boolean isCorrect = true;
        if (root.getChildCount() > MIN_ALTERNATIVES) {
            if (answerOnly)
                return false;
            isCorrect = false;
            this.messageList.add("Please specify at least 2 alternatives.");
        }
        Enumeration<Alternative> alternatives = (Enumeration<Alternative>)root.children();
        Alternative a;
        while (alternatives.hasMoreElements()) {
            if (alternatives.nextElement().getTitle().isEmpty()) {
                if (answerOnly)
                    return false;
                isCorrect = false;
                this.messageList.add("Please set titles for alternatives.");
            }
        }
        return isCorrect;
        /*
        List<Aim> criterias = aim.getCriterias();
        int size = criterias.size();
        if (size < MIN_ALTERNATIVES || size > MAX_CRITERIAS) {
            if (answerOnly)
                return false;
            isCorrect = false;
            this.messageList.add("It must be from "
                    +MIN_ALTERNATIVES
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
        for (Aim ac : criterias) {
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

                System.out.println("NEXT: ");
                System.out.println((Aim) neighbour.getNextNode());
            }
        }
        return isCorrect;
         * 
         */
    }

    protected void cleanMessages() {
        messageList = new ArrayList<String>();
    }

}
