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
import java.awt.Label;
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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class WindowSettings extends JPanel{
    private MainEngine mainEngine;
    private Branding branding;
    private JPanel windowHeaderPanel;
    private Point mouseDownCompCoords;
    public WindowSettings(MainEngine mainEngine, Branding branding){
        this.mainEngine = mainEngine;
        this.branding = branding;

        setBackground(branding.windowColor);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(false);
        setPreferredSize(new Dimension(250, 350));
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

        JLabel iconLabel = new JLabel(branding.resizeImageIcon(branding.icoSettings, 0.25f));
        JLabel titleLabel = new JLabel("Settings");
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
        windowContentPanel.setLayout(new BoxLayout(windowContentPanel, BoxLayout.Y_AXIS));
        windowContentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,20,10));

        // ===== VIDEO SECTION =====
        JLabel controlsVideoLabel = new JLabel("Video");
        controlsVideoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        controlsVideoLabel.setFont(branding.windowsFontMedium);
        controlsVideoLabel.setAlignmentX(LEFT_ALIGNMENT);
        controlsVideoLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
        branding.designLabelBorderPertrude(controlsVideoLabel);

        // Video checkboxes panel with GridBagLayout for 2 columns
        JPanel controlsVideoPanel = new JPanel();
        controlsVideoPanel.setOpaque(false);
        controlsVideoPanel.setLayout(new GridBagLayout());
        controlsVideoPanel.setAlignmentX(LEFT_ALIGNMENT);

        // === buttons ===
        JButton rayTracingBox = new JButton();
        JButton vfxOverlayBox = new JButton();
        JButton shadersBox = new JButton();
        JButton antiAliasingBox = new JButton();

        // === labels ===
        JLabel rayTracingLabel = new JLabel("Ray Tracing");
        JLabel vfxOverlayLabel = new JLabel("VFX Overlay");
        JLabel shadersLabel = new JLabel("Shaders"); 
        JLabel antiAliasingLabel = new JLabel("Anti-Aliasing"); 

        // === arrays ===
        JButton[] boxes = {
                rayTracingBox,
                vfxOverlayBox,
                shadersBox,
                antiAliasingBox
        };

        JLabel[] labels = {
                rayTracingLabel,
                vfxOverlayLabel,
                shadersLabel,
                antiAliasingLabel
        };

        // === state tracking ===
        boolean[] buttonStates = {false, false, false, false};

        // === shared border ===
        Border highlight1 = BorderFactory.createMatteBorder(1,1,0,0,branding.white);
        Border grayShade1 = BorderFactory.createMatteBorder(0,0,1,1,branding.gray2);
        Border shadow1 = BorderFactory.createMatteBorder(0,0,1,1,branding.black);
        Border combined1 = BorderFactory.createCompoundBorder(grayShade1, highlight1);
        Border combined = BorderFactory.createCompoundBorder(shadow1, combined1);

        Border highlight = BorderFactory.createMatteBorder(0, 0, 2, 2, branding.white);
        Border grayShade = BorderFactory.createMatteBorder(2, 2, 0, 0, branding.gray2);
        Border boxBorder = BorderFactory.createCompoundBorder(highlight, grayShade);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(2, 5, 2, 10);
        
        for (int i = 0; i < boxes.length; i++) {
            JButton box = boxes[i];
            JLabel label = labels[i];
            final int index = i;

            box.setPreferredSize(new Dimension(20,20));
            box.setMinimumSize(new Dimension(20,20));
            box.setMaximumSize(new Dimension(20,20));
            box.setBorderPainted(true);
            box.setContentAreaFilled(false);
            box.setFocusPainted(false);
            box.setOpaque(true);
            box.setBackground(branding.gray1);
            box.setBorder(combined);

            box.addActionListener(e -> {
                buttonStates[index] = !buttonStates[index];
                
                if (buttonStates[index]) {
                    box.setBorder(boxBorder);
                    box.setIcon(branding.icoWinClose);
                } else {
                    box.setBorder(combined);
                    box.setIcon(null);
                }
            });
            
            box.getModel().addChangeListener(e -> {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isPressed() && !buttonStates[index]) {
                    box.setBorder(boxBorder);
                } else if (!model.isPressed() && !buttonStates[index]) {
                    box.setBorder(combined);
                }
            });

            label.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
            label.setFont(branding.windowsFontSmall);
            
            // Create row panel
            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            row.setOpaque(false);
            row.add(box);
            row.add(label);

            gbc.gridx = i % 2;
            gbc.gridy = i / 2;
            gbc.weightx = 0.5;
            gbc.weighty = 0;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            controlsVideoPanel.add(row, gbc);
        }
        controlsVideoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, controlsVideoPanel.getPreferredSize().height));
        
        
        // ===== SOUND SECTION =====
        JLabel controlsSoundLabel = new JLabel("Sound");
        controlsSoundLabel.setHorizontalAlignment(SwingConstants.CENTER);
        controlsSoundLabel.setFont(branding.windowsFontMedium);
        controlsSoundLabel.setAlignmentX(LEFT_ALIGNMENT);
        controlsSoundLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        branding.designLabelBorderPertrude(controlsSoundLabel);

        JPanel controlsSoundPanel = new JPanel();
        controlsSoundPanel.setOpaque(false);
        controlsSoundPanel.setLayout(new BoxLayout(controlsSoundPanel, BoxLayout.Y_AXIS));
        controlsSoundPanel.setAlignmentX(LEFT_ALIGNMENT);

        // Volume slider row
        JPanel volumeRow = new JPanel();
        volumeRow.setLayout(new BoxLayout(volumeRow, BoxLayout.X_AXIS));
        volumeRow.setOpaque(false);
        volumeRow.setAlignmentX(LEFT_ALIGNMENT);
        
        JLabel volumeLabel = new JLabel("Volume");
        volumeLabel.setFont(branding.windowsFontSmall);
        volumeLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));
        
        javax.swing.JSlider volumeSlider = new javax.swing.JSlider(0, 100, 50);
        volumeSlider.setOpaque(false);
        volumeSlider.setPreferredSize(new Dimension(150, 20));
        
        volumeSlider.setUI(new javax.swing.plaf.basic.BasicSliderUI(volumeSlider) {
            @Override
            public void paintTrack(java.awt.Graphics g) {
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
                
                int trackHeight = 4;
                int trackY = (trackRect.height - trackHeight) / 2 + trackRect.y;
                
                g2d.setColor(branding.black);
                g2d.drawLine(trackRect.x, trackY, trackRect.x + trackRect.width, trackY);
                g2d.drawLine(trackRect.x, trackY, trackRect.x, trackY + trackHeight);
                
                g2d.setColor(branding.gray2);
                g2d.drawLine(trackRect.x + 1, trackY + 1, trackRect.x + trackRect.width - 1, trackY + 1);
                g2d.drawLine(trackRect.x + 1, trackY + 1, trackRect.x + 1, trackY + trackHeight - 1);
                
                g2d.setColor(branding.white);
                g2d.drawLine(trackRect.x, trackY + trackHeight, trackRect.x + trackRect.width, trackY + trackHeight);
                g2d.drawLine(trackRect.x + trackRect.width, trackY, trackRect.x + trackRect.width, trackY + trackHeight);
                
                // Fill track background
                g2d.setColor(branding.gray1);
                g2d.fillRect(trackRect.x + 2, trackY + 2, trackRect.width - 3, trackHeight - 3);
            }
            
            @Override
            public void paintThumb(java.awt.Graphics g) {
                java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
                
                int thumbWidth = 8;
                int thumbHeight = 18;
                int thumbX = thumbRect.x + (thumbRect.width - thumbWidth) / 2;
                int thumbY = thumbRect.y + (thumbRect.height - thumbHeight) / 2;
                
                g2d.setColor(branding.windowColor);
                g2d.fillRect(thumbX, thumbY, thumbWidth, thumbHeight);
                
                g2d.setColor(branding.white);
                g2d.drawLine(thumbX, thumbY, thumbX + thumbWidth - 1, thumbY); // top
                g2d.drawLine(thumbX, thumbY, thumbX, thumbY + thumbHeight - 1); // left
                
                g2d.setColor(branding.gray2);
                g2d.drawLine(thumbX + 1, thumbY + thumbHeight - 2, thumbX + thumbWidth - 2, thumbY + thumbHeight - 2); // inner bottom
                g2d.drawLine(thumbX + thumbWidth - 2, thumbY + 1, thumbX + thumbWidth - 2, thumbY + thumbHeight - 2); // inner right
                
                g2d.setColor(branding.black);
                g2d.drawLine(thumbX, thumbY + thumbHeight - 1, thumbX + thumbWidth - 1, thumbY + thumbHeight - 1); // bottom
                g2d.drawLine(thumbX + thumbWidth - 1, thumbY, thumbX + thumbWidth - 1, thumbY + thumbHeight - 1); // right
            }
        });

        volumeRow.add(volumeLabel);
        volumeRow.add(volumeSlider);
        volumeRow.add(Box.createHorizontalGlue());

        JButton soundEffectsBox = new JButton();
        JButton musicBox = new JButton();
        
        JLabel soundEffectsLabel = new JLabel("Sound Effects");
        JLabel musicLabel = new JLabel("Music");
        
        JButton[] soundBoxes = {soundEffectsBox, musicBox};
        JLabel[] soundLabels = {soundEffectsLabel, musicLabel};
        boolean[] soundStates = {false, false};
        
        for (int i = 0; i < soundBoxes.length; i++) {
            JButton box = soundBoxes[i];
            JLabel label = soundLabels[i];
            final int index = i;

            box.setPreferredSize(new Dimension(20,20));
            box.setMinimumSize(new Dimension(20,20));
            box.setMaximumSize(new Dimension(20,20));
            box.setBorderPainted(true);
            box.setContentAreaFilled(false);
            box.setFocusPainted(false);
            box.setOpaque(true);
            box.setBackground(branding.gray1);
            box.setBorder(combined);

            box.addActionListener(e -> {
                soundStates[index] = !soundStates[index];
                
                if (soundStates[index]) {
                    box.setBorder(boxBorder);
                    box.setIcon(branding.icoWinClose);
                } else {
                    box.setBorder(combined);
                    box.setIcon(null);
                }
            });
            
            box.getModel().addChangeListener(e -> {
                ButtonModel model = (ButtonModel) e.getSource();
                if (model.isPressed() && !soundStates[index]) {
                    box.setBorder(boxBorder);
                } else if (!model.isPressed() && !soundStates[index]) {
                    box.setBorder(combined);
                }
            });

            label.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
            label.setFont(branding.windowsFontSmall);
            
            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
            row.setOpaque(false);
            row.setAlignmentX(LEFT_ALIGNMENT);
            row.add(box);
            row.add(label);
            row.add(Box.createHorizontalGlue());

            controlsSoundPanel.add(row);
            if (i < soundBoxes.length - 1) {
                controlsSoundPanel.add(Box.createVerticalStrut(3));
            }
        }

        // Add all components to window content panel
        windowContentPanel.add(controlsVideoLabel);
        windowContentPanel.add(Box.createVerticalStrut(8));
        windowContentPanel.add(controlsVideoPanel);
        windowContentPanel.add(Box.createVerticalStrut(8));
        windowContentPanel.add(controlsSoundLabel);
        windowContentPanel.add(Box.createVerticalStrut(8));
        windowContentPanel.add(volumeRow);
        windowContentPanel.add(Box.createVerticalStrut(5));
        windowContentPanel.add(controlsSoundPanel);

        // Window Dragging Logic
        windowHeaderPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
                Container parent = getParent();
                if(parent != null){
                    parent.setComponentZOrder(WindowSettings.this, 0); // 0 = topmost layer
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

    public void initializeListenerConsumer(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Container parent = getParent();
                if(parent != null){
                    parent.setComponentZOrder(WindowSettings.this, 0);
                    parent.repaint();
                }
                e.consume();
            }
            @Override
            public void mousePressed(MouseEvent e) {
                Container parent = getParent();
                if(parent != null){
                    parent.setComponentZOrder(WindowSettings.this, 0);
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
