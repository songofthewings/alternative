/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DecisionMakingFrame.java
 *
 * Created on 05.02.2010, 21:27:05
 */

package alternative.view.decision;

import alternative.controller.ATaskMonitor;
import alternative.controller.DecisionController;
import alternative.controller.decision.CalculateAllTask;
import alternative.controller.decision.CalculateTabTask;
import alternative.model.decision.DocxExporter;
import alternative.controller.decision.ExportToDocxTask;
import alternative.model.Aim;
import alternative.model.Criteria;
import alternative.model.decision.ComparingTableModel;
import alternative.model.decision.ComparisonResult;
import alternative.model.decision.ConsistencyTableModel;
import alternative.model.decision.CriteriaPriority;
import alternative.view.Messager;
import alternative.view.NothingSelectedException;
import java.awt.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeModel;
import javax.xml.bind.JAXBException;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.jdesktop.application.Action;
import org.jdesktop.application.Task;

/**
 *
 * @author jiraffa
 */
public class DecisionMakingFrame extends javax.swing.JFrame {

    protected DecisionController controller;
    protected String problemName;
    protected static final ResourceBundle resourceBundle = ResourceBundle.getBundle("alternative.view.decision.resources.DecisionMakingFrame");
    protected ATaskMonitor taskMonitor;

    /** Creates new form DecisionMakingFrame
     * @param problemName Problem title - filename
     * @throws IOException if could not read Problem file
     * @throws JAXBException if error in xml file detected
     */
    public DecisionMakingFrame(String problemName, ATaskMonitor taskMonitor) throws JAXBException, IOException {
        super();
        
        this.controller = new DecisionController(problemName);
        this.problemName = problemName;
        this.messager = new Messager(this.getRootPane(), DecisionMakingFrame.class.getName());
        this.taskMonitor = taskMonitor;

        initComponents();

        /*
        comparingTable.setDefaultRenderer(CriteriaPriority.class, new PriorityRenderer(true));
        comparingTable.setDefaultEditor(CriteriaPriority.class, new PriorityEditor());
         * 
         */

        comPairedTable.setModel(getComPairedModel());
        consistencyTable.setModel(getConsistencyModel());

        for (ComparingTableModel tm : controller.getComparisonEntries()) {
            tabbedPane.addTab(tm.getAim().getTitle(),
                    new ComparisonTab(tm, getComPairedModel(), getConsistencyModel()));
        }

        tabbedPane.remove(tabSplitPane);

        tabbedPane.remove(resultSplitPane);

        menuBar.remove(helpMenu);
        //tabbedPane.addTab(resourceMap.getString("resultSplitPane.TabConstraints.tabTitle"), resultSplitPane); // NOI18N
        //tabbedPane.add(resultSplitPane); // NOI18N

        //progressBar = taskMonitor.getProgress();
        //statusAnimationLabel = taskMonitor.getAnimationLabel();
        //statusMessageLabel = taskMonitor.getMessageLabel();
        //statusMessageLabel.setText(problemName);

    }

    @Override
    public String getTitle() {
        //System.out.println(resourceBundle.getString("WINDOW_TITLE"));
        return String.format(resourceBundle.getString("WINDOW_TITLE"), problemName);
    }

    public TreeModel getTree() {
        try {
            return this.controller.getHierarchyModel(this.problemName);
        } catch (JAXBException je) {
            messager.log(je);
        }catch (IOException ioe) {
            messager.error(ioe, resourceBundle.getString("ERROR_WHILE_RESTORING_AIM-CRITERIA_TREE"));
        }
        this.dispose();
        return null;
    }

    public TreeModel getAlternativesModel() {
        try {
            return this.controller.getAlternativesListModel(this.problemName);
        } catch (JAXBException je) {
            messager.log(je);
        }catch (IOException ioe) {
            messager.error(ioe, resourceBundle.getString("ERROR_WHILE_RESTORING_ALTERNATIVES_LIST"));
        }
        this.dispose();
        return null;
    }

    protected TableModel getComparingModel() {
        return this.controller.getComparingModel();
    }

    protected TableModel getComPairedModel() {
        return this.controller.getEmptyComPairedTableModel(null);
    }

    protected TableModel getConsistencyModel() {
        return this.controller.getEmptyConsistencyTableModel();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        horizontalSplitPane = new javax.swing.JSplitPane();
        verticalSplitPane = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        aimTree1 = new alternative.view.AimTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        altList1 = new alternative.view.AltList();
        tabbedPane = new javax.swing.JTabbedPane();
        resultSplitPane = new javax.swing.JSplitPane();
        resultScrollPane = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        resultTextPane = new javax.swing.JTextPane();
        tabSplitPane = new javax.swing.JSplitPane();
        tabComparisonScrollPane = new javax.swing.JScrollPane();
        comparingTable = new javax.swing.JTable();
        tabVerticalSplitPane = new javax.swing.JSplitPane();
        comparingResultScrollPane = new javax.swing.JScrollPane();
        comPairedTable = new javax.swing.JTable();
        comparingHarmonizationScrollPane = new javax.swing.JScrollPane();
        consistencyTable = new javax.swing.JTable();
        panelsSeparator = new javax.swing.JSeparator();
        bottomPanel = new javax.swing.JPanel();
        statusAnimationLabel = new javax.swing.JLabel();
        statusAnimationLabel = taskMonitor.getAnimationLabel();
        progressBar = new javax.swing.JProgressBar();
        statusMessageLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        closeMenuItem = new javax.swing.JMenuItem();
        calculateMenu = new javax.swing.JMenu();
        calculateTabMenuItem = new javax.swing.JMenuItem();
        calculateAllMenuItem = new javax.swing.JMenuItem();
        exportMenu = new javax.swing.JMenu();
        toDocxMenuItem = new javax.swing.JMenuItem();
        toCsvMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();
        helpMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        mainPanel.setName("mainPanel"); // NOI18N

        horizontalSplitPane.setBorder(null);
        horizontalSplitPane.setDividerLocation(150);
        horizontalSplitPane.setDividerSize(3);
        horizontalSplitPane.setName("horizontalSplitPane"); // NOI18N

        verticalSplitPane.setBorder(null);
        verticalSplitPane.setDividerLocation(300);
        verticalSplitPane.setDividerSize(3);
        verticalSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        verticalSplitPane.setName("verticalSplitPane"); // NOI18N

        jScrollPane1.setBorder(null);
        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(100, 300));

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(DecisionMakingFrame.class);
        aimTree1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("aimTree1.border.title"))); // NOI18N
        aimTree1.setModel(getTree());
        aimTree1.setName("aimTree1"); // NOI18N
        jScrollPane1.setViewportView(aimTree1);

        verticalSplitPane.setTopComponent(jScrollPane1);

        jScrollPane2.setBorder(null);
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        altList1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("altList1.border.title"))); // NOI18N
        altList1.setModel(getAlternativesModel());
        altList1.setName("altList1"); // NOI18N
        altList1.setRootVisible(false);
        jScrollPane2.setViewportView(altList1);

        verticalSplitPane.setRightComponent(jScrollPane2);

        horizontalSplitPane.setLeftComponent(verticalSplitPane);

        tabbedPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabbedPane.setName("tabbedPane"); // NOI18N

        resultSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        resultSplitPane.setName("resultSplitPane"); // NOI18N

        resultScrollPane.setToolTipText(resourceMap.getString("resultScrollPane.toolTipText")); // NOI18N
        resultScrollPane.setName("resultScrollPane"); // NOI18N

        resultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        resultTable.setName("resultTable"); // NOI18N
        resultScrollPane.setViewportView(resultTable);

        resultSplitPane.setBottomComponent(resultScrollPane);

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        resultTextPane.setName("resultTextPane"); // NOI18N
        jScrollPane4.setViewportView(resultTextPane);

        resultSplitPane.setLeftComponent(jScrollPane4);

        tabbedPane.addTab(resourceMap.getString("resultSplitPane.TabConstraints.tabTitle"), resultSplitPane); // NOI18N

        tabSplitPane.setDividerLocation(350);
        tabSplitPane.setDividerSize(3);
        tabSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        tabSplitPane.setName("tabSplitPane"); // NOI18N

        tabComparisonScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("tabComparisonScrollPane.border.title"))); // NOI18N
        tabComparisonScrollPane.setName("tabComparisonScrollPane"); // NOI18N

        comparingTable.setModel(getComparingModel());
        comparingTable.setCellSelectionEnabled(true);
        comparingTable.setName("comparingTable"); // NOI18N
        comparingTable.getTableHeader().setReorderingAllowed(false);
        tabComparisonScrollPane.setViewportView(comparingTable);

        tabSplitPane.setLeftComponent(tabComparisonScrollPane);

        tabVerticalSplitPane.setDividerLocation(380);
        tabVerticalSplitPane.setDividerSize(3);
        tabVerticalSplitPane.setName("tabVerticalSplitPane"); // NOI18N

        comparingResultScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("comparingResultScrollPane.border.title"))); // NOI18N
        comparingResultScrollPane.setName("comparingResultScrollPane"); // NOI18N

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
        comPairedTable.setCellSelectionEnabled(true);
        comPairedTable.setName("comPairedTable"); // NOI18N
        comPairedTable.getTableHeader().setReorderingAllowed(false);
        comparingResultScrollPane.setViewportView(comPairedTable);

        tabVerticalSplitPane.setLeftComponent(comparingResultScrollPane);

        comparingHarmonizationScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("comparingHarmonizationScrollPane.border.title"))); // NOI18N
        comparingHarmonizationScrollPane.setName("comparingHarmonizationScrollPane"); // NOI18N

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
        consistencyTable.setCellSelectionEnabled(true);
        consistencyTable.setMinimumSize(new java.awt.Dimension(130, 48));
        consistencyTable.setName("consistencyTable"); // NOI18N
        consistencyTable.getTableHeader().setReorderingAllowed(false);
        comparingHarmonizationScrollPane.setViewportView(consistencyTable);

        tabVerticalSplitPane.setRightComponent(comparingHarmonizationScrollPane);

        tabSplitPane.setRightComponent(tabVerticalSplitPane);

        tabbedPane.addTab(resourceMap.getString("tabSplitPane.TabConstraints.tabTitle"), tabSplitPane); // NOI18N

        horizontalSplitPane.setRightComponent(tabbedPane);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(horizontalSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(horizontalSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
        );

        panelsSeparator.setName("panelsSeparator"); // NOI18N

        bottomPanel.setName("bottomPanel"); // NOI18N

        statusAnimationLabel.setText(resourceMap.getString("statusAnimationLabel.text")); // NOI18N
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N
        progressBar = taskMonitor.getProgress();

        statusMessageLabel.setText(resourceMap.getString("statusMessageLabel.text")); // NOI18N
        statusMessageLabel.setName("statusMessageLabel"); // NOI18N
        statusMessageLabel = taskMonitor.getMessageLabel();

        javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bottomPanelLayout.createSequentialGroup()
                .addContainerGap(494, Short.MAX_VALUE)
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bottomPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statusAnimationLabel)
                    .addComponent(statusMessageLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        closeMenuItem.setText(resourceMap.getString("closeMenuItem.text")); // NOI18N
        closeMenuItem.setName("closeMenuItem"); // NOI18N
        fileMenu.add(closeMenuItem);

        menuBar.add(fileMenu);

        calculateMenu.setText(resourceMap.getString("calculateMenu.text")); // NOI18N
        calculateMenu.setName("calculateMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(DecisionMakingFrame.class, this);
        calculateTabMenuItem.setAction(actionMap.get("calculateTab")); // NOI18N
        calculateTabMenuItem.setText(resourceMap.getString("calculateTabMenuItem.text")); // NOI18N
        calculateTabMenuItem.setName("calculateTabMenuItem"); // NOI18N
        calculateMenu.add(calculateTabMenuItem);

        calculateAllMenuItem.setAction(actionMap.get("calculateAll")); // NOI18N
        calculateAllMenuItem.setText(resourceMap.getString("calculateAllMenuItem.text")); // NOI18N
        calculateAllMenuItem.setName("calculateAllMenuItem"); // NOI18N
        calculateMenu.add(calculateAllMenuItem);

        menuBar.add(calculateMenu);

        exportMenu.setAction(actionMap.get("exportToCSV")); // NOI18N
        exportMenu.setText(resourceMap.getString("exportMenu.text")); // NOI18N
        exportMenu.setName("exportMenu"); // NOI18N

        toDocxMenuItem.setAction(actionMap.get("exportToDocx")); // NOI18N
        toDocxMenuItem.setText(resourceMap.getString("toDocxMenuItem.text")); // NOI18N
        toDocxMenuItem.setEnabled(false);
        toDocxMenuItem.setName("toDocxMenuItem"); // NOI18N
        exportMenu.add(toDocxMenuItem);

        toCsvMenuItem.setAction(actionMap.get("exportToCSV")); // NOI18N
        toCsvMenuItem.setText(resourceMap.getString("toCsvMenuItem.text")); // NOI18N
        toCsvMenuItem.setName("toCsvMenuItem"); // NOI18N
        exportMenu.add(toCsvMenuItem);

        menuBar.add(exportMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        helpMenuItem.setText(resourceMap.getString("helpMenuItem.text")); // NOI18N
        helpMenuItem.setName("helpMenuItem"); // NOI18N
        helpMenu.add(helpMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelsSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(bottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelsSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public Task calculateTab() {
        Component tab = tabbedPane.getSelectedComponent();
        if (null == tab || !(tab instanceof ComparisonTab)) {
            messager.warn(new NothingSelectedException("Please select tab to calculate"), "No current tab");
            return null;
        }
        return new CalculateTabTask(org.jdesktop.application.Application.getInstance(alternative.AlternativeApp.class),
                (ComparisonTab) tab, controller);
    }

    @Action
    public Task calculateAll() {
        return calculateAll(false);
    }

    @Action
    protected Task calculateAll(boolean exportToCSV) {
        Vector<ComparisonTab> tabsToUpdate = new Vector<ComparisonTab>();
        for (Component c : tabbedPane.getComponents()) {
            if (c instanceof ComparisonTab) {
                tabsToUpdate.add((ComparisonTab) c);
            }
        }
        Vector<JMenuItem> miToEnable = new Vector<JMenuItem>(Arrays.asList(toDocxMenuItem));

        if (-1 == tabbedPane.indexOfComponent(resultSplitPane)) {
            org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(alternative.AlternativeApp.class).getContext().getResourceMap(DecisionMakingFrame.class);
            tabbedPane.addTab(resourceMap.getString("resultSplitPane.TabConstraints.tabTitle"), resultSplitPane); // NOI18N
        }

        return new CalculateAllTask(org.jdesktop.application.Application.getInstance(alternative.AlternativeApp.class),
                controller, resultTable, tabsToUpdate, miToEnable,
                exportToCSV);
    }

    @Action
    public Task exportToDocx() {
        return new ExportToDocxTask(org.jdesktop.application.Application.getInstance(),
                controller, messager);
    }

    @Action
    public Task exportToCSV() {
        return calculateAll(true);
    }


    /*
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DecisionMakingFrame().setVisible(true);
            }
        });
    }
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private alternative.view.AimTree aimTree1;
    private alternative.view.AltList altList1;
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JMenuItem calculateAllMenuItem;
    private javax.swing.JMenu calculateMenu;
    private javax.swing.JMenuItem calculateTabMenuItem;
    private javax.swing.JMenuItem closeMenuItem;
    private javax.swing.JTable comPairedTable;
    private javax.swing.JScrollPane comparingHarmonizationScrollPane;
    private javax.swing.JScrollPane comparingResultScrollPane;
    private javax.swing.JTable comparingTable;
    private javax.swing.JTable consistencyTable;
    private javax.swing.JMenu exportMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem helpMenuItem;
    private javax.swing.JSplitPane horizontalSplitPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JSeparator panelsSeparator;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JScrollPane resultScrollPane;
    private javax.swing.JSplitPane resultSplitPane;
    private javax.swing.JTable resultTable;
    private javax.swing.JTextPane resultTextPane;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JScrollPane tabComparisonScrollPane;
    private javax.swing.JSplitPane tabSplitPane;
    private javax.swing.JSplitPane tabVerticalSplitPane;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JMenuItem toCsvMenuItem;
    private javax.swing.JMenuItem toDocxMenuItem;
    private javax.swing.JSplitPane verticalSplitPane;
    // End of variables declaration//GEN-END:variables

    private Messager messager;
}
