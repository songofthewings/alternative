/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alternative.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.TaskMonitor;

/**
 *
 * @author jiraffa
 */
public class ATaskMonitor extends TaskMonitor {
    private final JProgressBar progress;
    private final JLabel messageLabel;
    private final JLabel animationLabel;

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    public ATaskMonitor (ApplicationContext context, ResourceMap resourceMap, JProgressBar progressBar,
            JLabel statusMessageLabel, JLabel statusAnimationLabel) {
        super(context);

        this.progress = progressBar;
        this.messageLabel = statusMessageLabel;
        this.animationLabel = statusAnimationLabel;

        // status bar initialization - message timeout, idle icon and busy animation, etc
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getMessageLabel().setText("");
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
                getAnimationLabel().setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        animationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via ATaskMonitor
        this.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        getAnimationLabel().setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    getProgress().setVisible(true);
                    getProgress().setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    getAnimationLabel().setIcon(idleIcon);
                    getProgress().setVisible(false);
                    getProgress().setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    getMessageLabel().setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    getProgress().setVisible(true);
                    getProgress().setIndeterminate(false);
                    getProgress().setValue(value);
                }
            }
        });
    }

    /**
     * @return the progress
     */
    public JProgressBar getProgress() {
        return progress;
    }

    /**
     * @return the messageLabel
     */
    public JLabel getMessageLabel() {
        return messageLabel;
    }

    /**
     * @return the animationLabel
     */
    public JLabel getAnimationLabel() {
        return animationLabel;
    }
}
