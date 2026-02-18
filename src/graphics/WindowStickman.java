package graphics;

import engine.MainEngine;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class WindowStickman extends JPanel{
    private MainEngine mainEngine;
    private Branding branding;
    private JPanel windowHeaderPanel, stickmanCanvas;
    private Point mouseDownCompCoords;
    public WindowStickman(MainEngine mainEngine, Branding branding){
        this.mainEngine = mainEngine;
        this.branding = branding;

        setBackground(branding.windowColor);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(false);
        setPreferredSize(new Dimension(400, 500));
        Border white = BorderFactory.createLineBorder(branding.white, 3);
        Border shadow = BorderFactory.createMatteBorder(0,0,5,5,branding.shadow);
        Border border = BorderFactory.createCompoundBorder(shadow, white);
        setBorder(border);
        initializeListenerConsumer();

        initializeStickmanUI();
    }

    public void initializeStickmanUI(){
        // Header Panel
        windowHeaderPanel = new JPanel();
        windowHeaderPanel.setBackground(branding.blue1);
        windowHeaderPanel.setLayout(new BorderLayout());
        windowHeaderPanel.setBorder(BorderFactory.createLineBorder(branding.windowColor, 4));
        windowHeaderPanel.setPreferredSize(new Dimension(0, 32)); 
        windowHeaderPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32)); 
        windowHeaderPanel.setMinimumSize(new Dimension(0, 32)); 


        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(3,2,2,2));
        titlePanel.setOpaque(false);

        JLabel iconLabel = new JLabel(branding.resizeImageIcon(branding.icoComputer, 0.25f));
        JLabel titleLabel = new JLabel("Stickman");
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


        // Window Panel
        JPanel windowContentPanel = new JPanel();
        windowContentPanel.setBackground(branding.windowColor); 
        windowContentPanel.setPreferredSize(new Dimension(100,100));
        windowContentPanel.setLayout(new BorderLayout());
        windowContentPanel.setBorder(BorderFactory.createEmptyBorder(0,0,20,10));


        // Menu Panel (north)
        JPanel stickmanMenuPanel = new JPanel();
        stickmanMenuPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 3));
        stickmanMenuPanel.setPreferredSize(new Dimension(0,25));
        stickmanMenuPanel.setOpaque(false);

        JLabel labelFile = new JLabel("File");
        JLabel labelEdit = new JLabel("Edit");
        JLabel labelView = new JLabel("View");
        JLabel labelImages = new JLabel("Images");
        JLabel labelColors = new JLabel("Colors");
        JLabel labelHelp = new JLabel("Help");

        labelFile.setFont(branding.windowsFontSmall);
        labelEdit.setFont(branding.windowsFontSmall);
        labelView.setFont(branding.windowsFontSmall);
        labelImages.setFont(branding.windowsFontSmall);
        labelColors.setFont(branding.windowsFontSmall);
        labelHelp.setFont(branding.windowsFontSmall);
        labelFile.setForeground(branding.black);
        labelEdit.setForeground(branding.black);
        labelView.setForeground(branding.black);
        labelImages.setForeground(branding.black);
        labelColors.setForeground(branding.black);
        labelHelp.setForeground(branding.black);

        stickmanMenuPanel.add(labelFile);
        stickmanMenuPanel.add(labelEdit);
        stickmanMenuPanel.add(labelView);
        stickmanMenuPanel.add(labelImages);
        stickmanMenuPanel.add(labelColors);
        stickmanMenuPanel.add(labelHelp);

        windowContentPanel.add(stickmanMenuPanel, BorderLayout.NORTH);

        // Tools Panel (west)
        JPanel toolsPanel = new JPanel();
        toolsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        toolsPanel.setOpaque(false);
        toolsPanel.setLayout(new GridBagLayout());
        JPanel toolsContainer = new JPanel();
        toolsContainer.setLayout(new GridLayout(8,2,0,0));

        JButton[] toolButtons = new JButton[16];
        for (int i = 0; i < toolButtons.length; i++) {
            toolButtons[i] = new JButton(branding.icoTools[i]);
            toolButtons[i].setPreferredSize(new Dimension(25, 25));
            branding.designButtonFlat(toolButtons[i]);
            toolsContainer.add(toolButtons[i]);
            JButton btn = toolButtons[i];
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
            btn.getModel().addChangeListener(e -> {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isPressed()) {
                    branding.designButtonFlatPressed(btn);
                } else {
                    branding.designButtonFlat(btn);
                }
            });
        }
        
        JPanel toolSpacePanel = new JPanel();
        toolSpacePanel.setPreferredSize(new Dimension(45, 70));
        toolSpacePanel.setBackground(branding.windowColor);
        Border highlight = BorderFactory.createMatteBorder(0,0,2,2,branding.white);
        Border grayShade = BorderFactory.createMatteBorder(2,2,0,0,branding.gray2);
        Border innerShading = BorderFactory.createCompoundBorder(grayShade, highlight);
        toolSpacePanel.setBorder(innerShading);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 0, 0, 0);
        gbc.anchor = GridBagConstraints.NORTH;  // Anchor to top
        gbc.fill = GridBagConstraints.VERTICAL;
        toolsPanel.add(toolsContainer, gbc);

        gbc.gridy = 1;
        gbc.weightx = 0.5;
        toolsPanel.add(toolSpacePanel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 1.0; 
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        toolsPanel.add(Box.createVerticalGlue(), gbc);

        windowContentPanel.add(toolsPanel, BorderLayout.WEST);

        // Stickman Canvas (center)
        stickmanCanvas = new JPanel();
        stickmanCanvas.setForeground(branding.white);
        stickmanCanvas.setBorder(BorderFactory.createMatteBorder(7, 7, 3, 3, branding.gray3));
        stickmanCanvas.setLayout(new GridBagLayout());

        windowContentPanel.add(stickmanCanvas, BorderLayout.CENTER);

        // ScrollBar (east)
        Border highlight2 = BorderFactory.createMatteBorder(2,2,0,0,branding.white);
        Border grayShade2 = BorderFactory.createMatteBorder(0,0,2,2,branding.gray2);
        Border shading = BorderFactory.createCompoundBorder(grayShade2, highlight2);

        JPanel scrollbar = new JPanel();
        scrollbar.setLayout(new BorderLayout());
        scrollbar.setBackground(branding.gray0);
        scrollbar.setBorder(BorderFactory.createMatteBorder(3,0,3,3, branding.gray3));

        JButton scrollBarButtonUp = new JButton("▲");
        JButton scrollBarButtonDown = new JButton("▼");
        scrollBarButtonUp.setPreferredSize(new Dimension(20,20));
        scrollBarButtonDown.setPreferredSize(new Dimension(20,20));
        branding.designButtonDefault(scrollBarButtonUp);
        branding.designButtonDefault(scrollBarButtonDown);
        scrollBarButtonUp.setFont(null);
        scrollBarButtonDown.setFont(null);

        JPanel scrollableContainer = new JPanel();
        scrollableContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        scrollableContainer.setOpaque(false);

        JPanel scrollable = new JPanel();
        scrollable.setPreferredSize(new Dimension(20, 200));
        scrollable.setBackground(branding.windowColor);
        scrollable.setBorder(shading);

        scrollableContainer.add(scrollable);


        scrollbar.add(scrollBarButtonUp, BorderLayout.NORTH);
        scrollbar.add(scrollableContainer, BorderLayout.CENTER);
        scrollbar.add(scrollBarButtonDown, BorderLayout.SOUTH);
        
        windowContentPanel.add(scrollbar, BorderLayout.EAST);

        // Window Dragging Logic
        windowHeaderPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
                Container parent = getParent();
                if(parent != null){
                    parent.setComponentZOrder(WindowStickman.this, 0); // 0 = topmost layer
                    parent.repaint();
                }
            }
        });
        
        windowHeaderPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                Point parentLocation = getParent().getLocationOnScreen();
                
                int newX = currCoords.x - parentLocation.x - mouseDownCompCoords.x;
                int newY = currCoords.y - parentLocation.y - mouseDownCompCoords.y;
                
                Container parent = getParent();
                if (parent != null) {
                    int parentWidth = parent.getWidth();
                    int parentHeight = parent.getHeight();
                    
                    int minVisibleWidth = 50;
                    int leftPanelWidth = 0;
                    
                    newX = Math.max(leftPanelWidth, newX);                    // Stop at left panel edge
                    newX = Math.min(parentWidth - minVisibleWidth, newX);     // Right boundary

                    newY = Math.max(0, newY);                                 // Top boundary
                    newY = Math.min(parentHeight - 30, newY);                 // Bottom boundary
                }    
                setLocation(newX, newY);
            }
        });

        add(windowHeaderPanel);
        add(windowContentPanel);
    }

    public void updateStickmanStatus(int lives){
        stickmanCanvas.removeAll();
        ImageIcon newStickman;
        switch (lives){
            case 6 -> newStickman = branding.imgHangman6;
            case 5 -> newStickman = branding.imgHangman5;
            case 4 -> newStickman = branding.imgHangman4;
            case 3 -> newStickman = branding.imgHangman3;
            case 2 -> newStickman = branding.imgHangman2;
            case 1 -> newStickman = branding.imgHangman1;
            case 0 -> newStickman = branding.imgHangman0;
            default -> newStickman = branding.imgHangman0;
        }
        stickmanCanvas.add(new JLabel(newStickman));
        stickmanCanvas.revalidate();
        stickmanCanvas.repaint();
    }

    public void initializeListenerConsumer(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Container parent = getParent();
                if(parent != null){
                    parent.setComponentZOrder(WindowStickman.this, 0);
                    parent.repaint();
                }
                e.consume();
            }
            @Override
            public void mousePressed(MouseEvent e) {
                Container parent = getParent();
                if(parent != null){
                    parent.setComponentZOrder(WindowStickman.this, 0);
                    parent.repaint();
                }
                e.consume();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                e.consume();
            }
        });
        
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                e.consume();
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                e.consume();
            }
        });
    }
}
