/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.view.decision;

import alternative.model.decision.CriteriaPriority;
import alternative.model.decision.Priority;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author jiraffa
 */
public class PriorityEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

    Priority currentPriority;
    JButton button;
    JSlider slider;
    PriorityChooser dialog;
    protected static final String EDIT_PRIORITY_COMMAND = "editPriority";

    public PriorityEditor(Priority p) {
        super();

        button = new JButton();
        button.setActionCommand(EDIT_PRIORITY_COMMAND);
        button.addActionListener(this);
        button.setBorderPainted(false);

        slider = new JSlider();

        currentPriority = p;//new CriteriaPriority();
        dialog = new PriorityChooser(null, true, currentPriority, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (EDIT_PRIORITY_COMMAND.equals(e.getActionCommand())) {
            //button.setBackground(Color.red);
            button.setBackground(currentPriority.getColor());
            button.setText(currentPriority.toString());
            dialog.setPriority(currentPriority);
            dialog.setVisible(true);

            fireEditingStopped();
        } else {
            currentPriority = dialog.getPriority();
        }
    }

    @Override
    public Object getCellEditorValue() {
        return currentPriority;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                                    int row, int column) {
        if (value instanceof Priority)
            currentPriority = (Priority) value;
        return button;
    }

}
