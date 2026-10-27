/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.view.decision;

import alternative.model.decision.Priority;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author jiraffa
 */
public class PriorityRenderer extends JLabel implements TableCellRenderer {

    Border unselectedBorder= null;
    Border selectedBorder = null;
    boolean isBordered = true;

    public PriorityRenderer(boolean isBordered) {
        this.isBordered = isBordered;
        setOpaque(true); // for background to show up
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object priority,
                                                    boolean isSelected, boolean hasFocus,
                                                    int row, int column) {
        Priority newPriority = (Priority) priority;
        this.setText(priority.toString());
        setBackground(newPriority.getColor());
        if (isBordered) {
            if (isSelected) {
                if (null == selectedBorder) {
                    selectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5,
                                                        table.getSelectionBackground());
                }
                setBorder(selectedBorder);
            } else {
                if (null == unselectedBorder) {
                    unselectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5,
                                                        table.getBackground());
                }
                setBorder(unselectedBorder);
            }
        }

        setToolTipText(newPriority.toString());
        return this;
    }
}
