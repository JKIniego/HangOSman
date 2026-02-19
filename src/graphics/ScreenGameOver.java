package graphics;

import engine.MainEngine;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ScreenGameOver extends JPanel {
    private Branding branding;
    private MainEngine mainEngine;

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
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_R) {
                    System.out.println("R Pressed - Restarting...");
                    mainEngine.stopErrorSound();
                    mainEngine.startButtonPressed("ScreenStart");
                } else if (e.getKeyCode() == java.awt.event.KeyEvent.VK_E) {
                    System.out.println("E Pressed - Exiting...");
                    mainEngine.stopErrorSound();
                    System.exit(0);
                }
            }
        });

        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel bannerPanel = new JPanel(new BorderLayout());
        JPanel headlinePanel = new JPanel(new BorderLayout());
        JPanel messagePanel = new JPanel(new BorderLayout());
        JPanel optionsPanel = new JPanel();

        //headerPanel.setBackground(branding.green); bannerPanel.setBackground(branding.white); headlinePanel.setBackground(branding.black); messagePanel.setBackground(branding.gray2);

        headerPanel.setOpaque(false);
        bannerPanel.setOpaque(false);
        headlinePanel.setOpaque(false);
        messagePanel.setOpaque(false);
        optionsPanel.setOpaque(false);


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


        JLabel messageLabel = new JLabel("<html>Reason: <br><br> A stickman was detected interfering with your hardware.<br><br><br>Error code: STICKMAN_HARDWARE_MALFUNCTION<br><br><br>RECOVERY OPTIONS: <br><br></html>", JLabel.CENTER);
        messageLabel.setFont(branding.windowsFont2Medium);
        messageLabel.setForeground(branding.white);
        messageLabel.setHorizontalAlignment(JLabel.LEFT);
        messageLabel.setVerticalAlignment(JLabel.TOP);
        messagePanel.add(messageLabel, BorderLayout.CENTER);

        // Restart button styled as text
        JLabel restartLabel = new JLabel("Press R to restart.");
        restartLabel.setFont(branding.windowsFont2Medium);
        restartLabel.setForeground(branding.white);
        restartLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        restartLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                mainEngine.stopErrorSound();
                mainEngine.startButtonPressed("ScreenStart");
            }
        });

        // Separator
        JLabel separatorLabel = new JLabel("  |  ");
        separatorLabel.setFont(branding.windowsFont2Medium);
        separatorLabel.setForeground(branding.white);

        // Exit button styled as text
        JLabel exitLabel = new JLabel("Press E to exit.");
        exitLabel.setFont(branding.windowsFont2Medium);
        exitLabel.setForeground(branding.white);
        exitLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                mainEngine.stopErrorSound();
                System.exit(0);
            }
        });
        
        optionsPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        optionsPanel.add(restartLabel);
        optionsPanel.add(separatorLabel);
        optionsPanel.add(exitLabel);
        startFlickerEffect(restartLabel, separatorLabel, exitLabel);

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new GridBagLayout());
        displayPanel.setOpaque(false);
        displayPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.ipadx = 500;
        gbc.gridy = 0;
        gbc.ipady = 50;
        gbc.weighty = 0.1;
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
        gbc.ipady = 50;
        gbc.weighty = 0.1;
        displayPanel.add(messagePanel, gbc);
        
        gbc.gridy++;
        gbc.ipady = 250;
        gbc.weighty = 0.1;
        displayPanel.add(optionsPanel, gbc);


        add(displayPanel);
    }

    private void startFlickerEffect(JLabel restartLabel, JLabel separatorLabel, JLabel exitLabel) {
        Timer timer = new Timer(500, null);
        final boolean[] visible = {true};

        timer.addActionListener(e -> {
            java.awt.Color color = visible[0] ? java.awt.Color.WHITE : new java.awt.Color(0x0078D7);
            restartLabel.setForeground(color);
            separatorLabel.setForeground(color);
            exitLabel.setForeground(color);
            visible[0] = !visible[0];
        });

        timer.start();
    }
}
