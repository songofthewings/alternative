/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.view.decision;

import alternative.model.Aim;
import alternative.model.decision.AlternativePriority;
import alternative.model.decision.ComparingTableModel;
import alternative.model.decision.CriteriaPriority;
import javax.swing.JSplitPane;
import javax.swing.table.TableModel;

/**
 *
 * @author jiraffa
 */
public class ComparisonTab extends JSplitPane {

    //protected Aim aim;

    public ComparisonTab(ComparingTableModel comparingTableModel, TableModel comPairedModel, TableModel consistencyModel) {
        super();

        //this.aim = aim;

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(alternative.AlternativeApp.class).getContext().getResourceMap(ComparisonTab.class);


        this.setDividerLocation(350);
        this.setDividerSize(3);
        this.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        this.setName("tabSplitPane"); // NOI18N

        tabComparisonScrollPane = new javax.swing.JScrollPane();
        comparingTable = new javax.swing.JTable();
        tabVerticalSplitPane = new javax.swing.JSplitPane();
        comparingResultScrollPane = new javax.swing.JScrollPane();
        comPairedTable = new javax.swing.JTable();
        comparingHarmonizationScrollPane = new javax.swing.JScrollPane();
        consistencyTable = new javax.swing.JTable();

        Aim aim = comparingTableModel.getAim();
        tabComparisonScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(
                String.format(
                resourceMap.getString(
                    "tabComparisonScrollPane.border.title."+(aim.isLeaf() ? "Alternative" : "Criteria") // NOI18N
                ), aim.getTitle())));
        tabComparisonScrollPane.setName("tabComparisonScrollPane"); // NOI18N

        //comparingTable.setModel(getComparingModel());
        comparingTable.setModel(comparingTableModel);
        PriorityRenderer pr = new PriorityRenderer(true);
        //PriorityEditor pe = new PriorityEditor();
        comparingTable.setDefaultRenderer(CriteriaPriority.class, pr);
        comparingTable.setDefaultEditor(CriteriaPriority.class, new PriorityEditor(new CriteriaPriority()));
        comparingTable.setDefaultRenderer(AlternativePriority.class, pr);
        comparingTable.setDefaultEditor(AlternativePriority.class, new PriorityEditor(new AlternativePriority()));

        comparingTable.setCellSelectionEnabled(true);
        comparingTable.setName("comparingTable"); // NOI18N
        comparingTable.getTableHeader().setReorderingAllowed(false);
        tabComparisonScrollPane.setViewportView(comparingTable);

        this.setLeftComponent(tabComparisonScrollPane);

        tabVerticalSplitPane.setDividerLocation(380);
        tabVerticalSplitPane.setDividerSize(3);
        tabVerticalSplitPane.setName("tabVerticalSplitPane"); // NOI18N

        comparingResultScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("comparingResultScrollPane.border.title"))); // NOI18N
        comparingResultScrollPane.setName("comparingResultScrollPane"); // NOI18N

        /*
        comPairedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
         *
         */
        comPairedTable.setCellSelectionEnabled(true);
        comPairedTable.setName("comPairedTable"); // NOI18N
        comPairedTable.getTableHeader().setReorderingAllowed(false);
        comPairedTable.setModel(comPairedModel);

        comparingResultScrollPane.setViewportView(comPairedTable);

        tabVerticalSplitPane.setLeftComponent(comparingResultScrollPane);

        comparingHarmonizationScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("comparingHarmonizationScrollPane.border.title"))); // NOI18N
        comparingHarmonizationScrollPane.setName("comparingHarmonizationScrollPane"); // NOI18N

        /*
        consistencyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Lambda max", null},
                {"Consistency Index", null},
                {"Harmonization", null}
            },
            new String [] {
                "Parameter", "Value"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
         * 
         */
        consistencyTable.setCellSelectionEnabled(true);
        consistencyTable.setMinimumSize(new java.awt.Dimension(130, 48));
        consistencyTable.setName("consistencyTable"); // NOI18N
        consistencyTable.getTableHeader().setReorderingAllowed(false);
        consistencyTable.setModel(consistencyModel);

        comparingHarmonizationScrollPane.setViewportView(consistencyTable);

        tabVerticalSplitPane.setRightComponent(comparingHarmonizationScrollPane);

        this.setRightComponent(tabVerticalSplitPane);

    }

    /**
     * @return the comPairedTable
     */
    public javax.swing.JTable getComPairedTable() {
        return comPairedTable;
    }

    /**
     * @return the comparingTable
     */
    public javax.swing.JTable getComparingTable() {
        return comparingTable;
    }

    /**
     * @return the consistencyTable
     */
    public javax.swing.JTable getConsistencyTable() {
        return consistencyTable;
    }


    private javax.swing.JTable comPairedTable;
    private javax.swing.JScrollPane comparingHarmonizationScrollPane;
    private javax.swing.JScrollPane comparingResultScrollPane;
    private javax.swing.JTable comparingTable;
    private javax.swing.JTable consistencyTable;
    private javax.swing.JScrollPane tabComparisonScrollPane;
    //private javax.swing.JSplitPane 
    private javax.swing.JSplitPane tabVerticalSplitPane;
}
