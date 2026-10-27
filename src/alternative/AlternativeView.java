/*
 * AlternativeView.java
 */

package alternative;

import alternative.controller.ATaskMonitor;
import alternative.controller.ProblemsListController;
import alternative.view.AimTreeDialog;
import alternative.view.AlternativeAboutBox;
import alternative.view.decision.DecisionMakingFrame;
import alternative.view.Messager;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.ListModel;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * The application's main frame.
 */
public class AlternativeView extends FrameView {

    protected ProblemsListController controller;
    protected ATaskMonitor taskMonitor;

    public AlternativeView(SingleFrameApplication app) {
        super(app);

        ResourceMap resourceMap = getResourceMap();
        DOMConfigurator.configure(resourceMap.getClassLoader().getResource(resourceMap.getResourcesDir()
                + "log4j.xml"));

        controller = new ProblemsListController();
        messager = new Messager(this.getRootPane(), AlternativeView.class.getName());

        if (controller.isLicenseDemo()) {
            if (controller.isLicenseExpired()) {
                messager.error(new Exception(getResourceMap().getString("SORRY_YOUR_DEMO_LICENSE_EXPIRED_CONTACT")),
                        getResourceMap().getString("SORRY_YOUR_DEMO_LICENSE_EXPIRED"));
                //this.getFrame().dispose();
                this.getApplication().exit();
            } else {
                messager.warn(new Exception(String.format(
                        getResourceMap().getString("YOU_ARE_USING_THIS_APPLICATION_BY_DEMO_LICENSE_IT_WOULD_EXPIRE"), controller.getExpireDate())),
                        getResourceMap().getString("YOU_ARE_USING_THIS_APPLICATION_BY_DEMO_LICENSE"));
            }
        }

        
        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        taskMonitor = new ATaskMonitor(getApplication().getContext(), resourceMap,
                progressBar, statusMessageLabel, statusAnimationLabel);
/*
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
        */
        aimList.addMouseListener(new MouseAdapter() {
            @Override
                public void mousePressed(MouseEvent e) {
                /*
                    if(e.getClickCount() == 1) {
                    }else if(e.getClickCount() == 2) {
                    }
                 */
                    refreshButtons();
                }
            }
        );
    }

    protected void refreshButtons() {
        boolean isProblemSelected = (-1 != aimList.getSelectedIndex());
        editProblemButton.setEnabled(isProblemSelected);
        startDecisionMakingButton.setEnabled(isProblemSelected);
        removeProblemButton.setEnabled(isProblemSelected);
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = AlternativeApp.getApplication().getMainFrame();
            aboutBox = new AlternativeAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        AlternativeApp.getApplication().show(aboutBox);
    }

    public void showAimTreeBox(String choiceName) {
        if (aimTreeBox == null) {
            JFrame mainFrame = AlternativeApp.getApplication().getMainFrame();
            aimTreeBox = new AimTreeDialog(mainFrame, true, choiceName);
            aimTreeBox.setLocationRelativeTo(mainFrame);
        } else {
            aimTreeBox.loadProblem(choiceName);
        }
        AlternativeApp.getApplication().show(aimTreeBox);
        aimList.setModel(this.getListModel());
        refreshButtons();
    }

    @Action
    public void showCreateTreeBox() {
        this.showAimTreeBox(null);
        /**
         *
java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AimTreeDialog dialog = new AimTreeDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
         */
    }

    @Action
    public void showEditTreeBox() {
        String problemName = (String) aimList.getSelectedValue();
        if (null == problemName)
            return;
        this.showAimTreeBox(problemName);
    }

    protected ListModel getListModel() {
        return this.controller.getListModel();
    }

    @Action
    public void showDecisionMakingBox() {
        try {
            String problemName = (String) aimList.getSelectedValue();
            this.getFrame().setVisible(false);
            JFrame dmf = new DecisionMakingFrame(problemName, taskMonitor);
            dmf.addWindowListener(new java.awt.event.WindowAdapter() {

                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    getFrame().setVisible(true);
                }
            });
            dmf.setLocationByPlatform(true);
            dmf.setVisible(true);
        } catch (JAXBException je) {
            messager.error(je, getResourceMap().getString("ERROR_IN_DATA_STRUCTURE_IN_PROBLEM_FILE"));
        } catch (IOException ioe) {
            messager.error(ioe, getResourceMap().getString("ERROR_WHILE_RESTORING_PROBLEM_FROM_FILE"));
        }
    }

    @Action
    public void removeProblem() {
        String problemName = (String) aimList.getSelectedValue();
        try {
            this.controller.removeProblem(problemName);
        } catch (IOException ioe) {
            messager.error(ioe, getResourceMap().getString("ERROR_WHILE_REMOVING_PROBLEM_FILE"));
        }
        aimList.setModel(this.getListModel());
        refreshButtons();
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
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        aimList = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        startDecisionMakingButton = new javax.swing.JButton();
        editProblemButton = new javax.swing.JButton();
        removeProblemButton = new javax.swing.JButton();
        createProblemButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerSize(3);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jScrollPane1.setBorder(null);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(150, 22));
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(alternative.AlternativeApp.class).getContext().getResourceMap(AlternativeView.class);
        aimList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("aimList.border.title"))); // NOI18N
        aimList.setModel(getListModel());
        aimList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        aimList.setName("aimList"); // NOI18N
        jScrollPane1.setViewportView(aimList);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jPanel1.setName("jPanel1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(alternative.AlternativeApp.class).getContext().getActionMap(AlternativeView.class, this);
        startDecisionMakingButton.setAction(actionMap.get("showDecisionMakingBox")); // NOI18N
        startDecisionMakingButton.setText(resourceMap.getString("startDecisionMakingButton.text")); // NOI18N
        startDecisionMakingButton.setEnabled(false);
        startDecisionMakingButton.setFocusable(false);
        startDecisionMakingButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        startDecisionMakingButton.setName("startDecisionMakingButton"); // NOI18N
        startDecisionMakingButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        editProblemButton.setAction(actionMap.get("showEditTreeBox")); // NOI18N
        editProblemButton.setText(resourceMap.getString("editProblemButton.text")); // NOI18N
        editProblemButton.setToolTipText(resourceMap.getString("editProblemButton.toolTipText")); // NOI18N
        editProblemButton.setEnabled(false);
        editProblemButton.setFocusable(false);
        editProblemButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        editProblemButton.setName("editProblemButton"); // NOI18N
        editProblemButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        removeProblemButton.setAction(actionMap.get("removeProblem")); // NOI18N
        removeProblemButton.setText(resourceMap.getString("removeProblemButton.text")); // NOI18N
        removeProblemButton.setEnabled(false);
        removeProblemButton.setFocusable(false);
        removeProblemButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeProblemButton.setName("removeProblemButton"); // NOI18N
        removeProblemButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        createProblemButton.setAction(actionMap.get("showCreateTreeBox")); // NOI18N
        createProblemButton.setText(resourceMap.getString("createProblemButton.text")); // NOI18N
        createProblemButton.setFocusable(false);
        createProblemButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        createProblemButton.setName("createProblemButton"); // NOI18N
        createProblemButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(editProblemButton, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeProblemButton))
                    .addComponent(createProblemButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(startDecisionMakingButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(startDecisionMakingButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(removeProblemButton)
                    .addComponent(editProblemButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createProblemButton)
                .addContainerGap(154, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel1);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList aimList;
    private javax.swing.JButton createProblemButton;
    private javax.swing.JButton editProblemButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton removeProblemButton;
    private javax.swing.JButton startDecisionMakingButton;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    //private final Timer messageTimer;
    //private final Timer busyIconTimer;
    //private final Icon idleIcon;
    //private final Icon[] busyIcons = new Icon[15];
    //private int busyIconIndex = 0;

    private JDialog aboutBox;
    private AimTreeDialog aimTreeBox;
    private Messager messager;
}
