package graphics;

import engine.MainEngine;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class WindowClose extends JPanel {
    private MainEngine mainEngine;
    private Branding branding;
    private JPanel windowHeaderPanel;
    private Point mouseDownCompCoords;

    public WindowClose(MainEngine mainEngine, Branding branding) {
        this.mainEngine = mainEngine;
        this.branding = branding;

        setBackground(branding.windowColor);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(false);
        setPreferredSize(new Dimension(350, 180));
        Border white = BorderFactory.createLineBorder(branding.white, 3);
        Border shadow = BorderFactory.createMatteBorder(0, 0, 5, 5, branding.shadow);
        setBorder(BorderFactory.createCompoundBorder(shadow, white));
        initializeListenerConsumer();
        initializeUI();
    }

    public void initializeUI() {
        // Header
        windowHeaderPanel = new JPanel(new BorderLayout());
        windowHeaderPanel.setBackground(branding.blue1);
        windowHeaderPanel.setBorder(BorderFactory.createLineBorder(branding.windowColor, 4));
        windowHeaderPanel.setPreferredSize(new Dimension(0, 32));
        windowHeaderPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        windowHeaderPanel.setMinimumSize(new Dimension(0, 32));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(3, 2, 2, 2));
        titlePanel.setOpaque(false);
        JLabel iconLabel = new JLabel(branding.resizeImageIcon(branding.icoLeak, 0.25f));
        JLabel titleLabel = new JLabel("Quit Game");
        titleLabel.setFont(branding.windowsFontSmall);
        titleLabel.setForeground(branding.white);
        titlePanel.add(iconLabel);
        titlePanel.add(titleLabel);

        JPanel windowControlsPanel = new JPanel();
        windowControlsPanel.setOpaque(false);
        windowControlsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 3));
        windowControlsPanel.setBorder(BorderFactory.createEmptyBorder(0,0,2,2));
        
        JButton closeButton = new JButton(branding.icoWinClose);
        closeButton.setForeground(branding.black);
        closeButton.setPreferredSize(new Dimension(20, 20));
        closeButton.setFocusPainted(false);
        JButton maximizeButton = new JButton(branding.icoWinMax);
        maximizeButton.setForeground(branding.black);
        maximizeButton.setPreferredSize(new Dimension(20, 20));
        maximizeButton.setFocusPainted(false);
        JButton minimizeButton = new JButton(branding.icoWinMin);
        minimizeButton.setForeground(branding.black);
        minimizeButton.setPreferredSize(new Dimension(20, 20));
        minimizeButton.setFocusPainted(false);

        JButton[] windowButtons = {closeButton, maximizeButton, minimizeButton};
        for (JButton btn : windowButtons) {
            Color originalColor = btn.getBackground();
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    if (!btn.getModel().isPressed()) {
                        btn.setBackground(branding.gray1);
                    }
                }
                public void mouseExited(MouseEvent e) {
                    if (!btn.getModel().isPressed()) {
                        btn.setBackground(originalColor);
                    }
                }
            });
            btn.addActionListener(e -> {
                    mainEngine.playSound("click");
                    if (btn == minimizeButton || btn == closeButton){
                        this.setVisible(false);
                    } else if (btn == maximizeButton){
                        // maximize window implementation
                    }
                });
            btn.getModel().addChangeListener(e -> {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isPressed()) {
                    branding.designButtonDefaultPressed(btn);
                } else {
                    branding.designButtonDefault(btn);
                }
            });
        }    
        
        branding.designButtonDefault(closeButton);
        branding.designButtonDefault(maximizeButton);
        branding.designButtonDefault(minimizeButton);
        closeButton.setFont(branding.windowsFontExtraSmall);
        maximizeButton.setFont(branding.windowsFontExtraSmall);
        minimizeButton.setFont(branding.windowsFontExtraSmall);
        
        windowControlsPanel.add(minimizeButton);
        windowControlsPanel.add(maximizeButton);
        windowControlsPanel.add(closeButton);
        
        windowHeaderPanel.add(titlePanel, BorderLayout.WEST);
        windowHeaderPanel.add(windowControlsPanel, BorderLayout.EAST);

        // Content
        JPanel windowContentPanel = new JPanel(new BorderLayout());
        windowContentPanel.setBackground(branding.windowColor);
        windowContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel messagePanel = new JPanel(new BorderLayout(10, 10));
        messagePanel.setBackground(branding.windowColor);
        messagePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));

        JLabel warningIcon = new JLabel(" ");
        warningIcon.setVerticalAlignment(SwingConstants.TOP);
        messagePanel.add(warningIcon, BorderLayout.WEST);

        JPanel textPanel = new JPanel();
        textPanel.setBackground(branding.windowColor);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        JLabel titleText = new JLabel("Quit Game?");
        titleText.setFont(branding.windowsFontLarge);
        titleText.setForeground(branding.black);
        JLabel subText = new JLabel("This will exit the game.");
        subText.setFont(branding.windowsFontSmall);
        subText.setForeground(branding.black);
        textPanel.add(titleText);
        textPanel.add(subText);
        messagePanel.add(textPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(branding.windowColor);

        JButton cancelButton = new JButton("Cancel");
        JButton quitButton = new JButton("Quit");

        for (JButton button : new JButton[]{cancelButton, quitButton}) {
            button.setPreferredSize(new Dimension(100, 25));
            branding.designButtonStart(button);
            button.setFont(branding.windowsFontSmall);
            button.getModel().addChangeListener(e -> {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isPressed()) branding.designButtonDefaultPressed(button);
                else branding.designButtonDefault(button);
            });
        }

        cancelButton.addActionListener(e -> {
            mainEngine.playSound("click");
            this.setVisible(false);
        });

        quitButton.addActionListener(e -> {
            mainEngine.playSound("click");
            System.exit(0);
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(quitButton);

        windowContentPanel.add(messagePanel, BorderLayout.CENTER);
        windowContentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Dragging
        windowHeaderPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
                Container parent = getParent();
                if (parent != null) { parent.setComponentZOrder(WindowClose.this, 0); parent.repaint(); }
            }
        });
        windowHeaderPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point curr = e.getLocationOnScreen();
                Point parentLoc = getParent().getLocationOnScreen();
                int newX = curr.x - parentLoc.x - mouseDownCompCoords.x;
                int newY = curr.y - parentLoc.y - mouseDownCompCoords.y;
                Container parent = getParent();
                if (parent != null) {
                    newX = Math.max(0, Math.min(parent.getWidth() - 50, newX));
                    newY = Math.max(0, Math.min(parent.getHeight() - 30, newY));
                }
                setLocation(newX, newY);
            }
        });

        add(windowHeaderPanel);
        add(windowContentPanel);
    }

    public void initializeListenerConsumer() {
        this.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { bringToFront(); e.consume(); }
            @Override public void mousePressed(MouseEvent e) { bringToFront(); e.consume(); }
            @Override public void mouseReleased(MouseEvent e) { e.consume(); }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override public void mouseDragged(MouseEvent e) { e.consume(); }
            @Override public void mouseMoved(MouseEvent e) { e.consume(); }
        });
    }

    private void bringToFront() {
        Container parent = getParent();
        if (parent != null) { parent.setComponentZOrder(WindowClose.this, 0); parent.repaint(); }
    }
}