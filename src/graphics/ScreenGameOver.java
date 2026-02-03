package graphics;

import engine.MainEngine;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScreenGameOver extends JPanel {
    Branding branding;
    MainEngine mainEngine;

    public ScreenGameOver(MainEngine mainEngine, Branding branding) {
        this.branding = branding;
        this.mainEngine = mainEngine;
        setBackground(branding.blue);
        setLayout(new GridBagLayout());

        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                System.out.println("Key Pressed - Restarting...");
                mainEngine.guiChangeScreen("ScreenStart");
            }
        });

        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel bannerPanel = new JPanel(new BorderLayout());
        JPanel headlinePanel = new JPanel(new BorderLayout());
        JPanel messagePanel = new JPanel(new BorderLayout());

        //headerPanel.setBackground(branding.green); bannerPanel.setBackground(branding.white); headlinePanel.setBackground(branding.black); messagePanel.setBackground(branding.gray2);

        headerPanel.setOpaque(false);
        bannerPanel.setOpaque(false);
        headlinePanel.setOpaque(false);
        messagePanel.setOpaque(false);

        JLabel headerLabel = new JLabel("Windows", JLabel.CENTER);
        headerLabel.setFont(branding.windowsFont2Large);
        headerLabel.setBorder(BorderFactory.createLineBorder(branding.windowColor, 10));
        headerLabel.setForeground(branding.white);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(branding.windowColor);
        JPanel headerBackground = new JPanel();
        headerBackground.setOpaque(false);
        headerBackground.add(headerLabel);
        headerPanel.add(headerBackground);

        JLabel bannerLabel = new JLabel(":(", JLabel.CENTER);
        bannerLabel.setFont(branding.windowsFontGiant);
        bannerLabel.setForeground(branding.white);
        bannerLabel.setHorizontalAlignment(JLabel.LEFT);
        bannerLabel.setVerticalAlignment(JLabel.TOP);
        bannerPanel.add(bannerLabel, BorderLayout.CENTER);

        JLabel headlineLabel = new JLabel("<html>Your PC ran into a problem and couldn’t recover.<br><br> This PC will not be fixed. </html>", JLabel.CENTER);
        headlineLabel.setFont(branding.windowsFont2Large);
        headlineLabel.setForeground(branding.white);
        headlineLabel.setHorizontalAlignment(JLabel.LEFT);
        headlineLabel.setVerticalAlignment(JLabel.TOP);
        headlinePanel.add(headlineLabel, BorderLayout.CENTER);


        JLabel messageLabel = new JLabel("<html>Reason: <br><br> A stickman was detected interfering with your hardware.<br><br><br>Error code: STICKMAN_HARDWARE_MALFUNCTION<br><br><br>You can restart, but the stickman probably rebuilt itself. <br><br>Press any key to restart.</html>", JLabel.CENTER);
        messageLabel.setFont(branding.windowsFont2Medium);
        messageLabel.setForeground(branding.white);
        messageLabel.setHorizontalAlignment(JLabel.LEFT);
        messageLabel.setVerticalAlignment(JLabel.TOP);
        messagePanel.add(messageLabel, BorderLayout.CENTER);

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridBagLayout());
        displayPanel.setOpaque(false);
        displayPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH; // fill horizontally and vertically
        gbc.weightx = 1.0; // allow horizontal stretching
        gbc.ipadx = 500;
        gbc.gridy = 0;
        gbc.ipady = 50;
        gbc.weighty = 0.1; // optional: proportional vertical space
        displayPanel.add(headerPanel, gbc);

        gbc.gridy++;
        gbc.ipady = 0;
        gbc.weighty = 0.1;
        displayPanel.add(bannerPanel, gbc);

        gbc.gridy++;
        gbc.ipady = 70;
        gbc.weighty = 0.1;
        displayPanel.add(headlinePanel, gbc);

        gbc.gridy++;
        gbc.ipady = 250;
        gbc.weighty = 0.1;
        displayPanel.add(messagePanel, gbc);


        add(displayPanel);
    }
}
