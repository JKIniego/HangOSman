package graphics;

import engine.MainEngine;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import javax.swing.border.Border;

public class WindowDirections extends JPanel{
    private MainEngine mainEngine;
    private Branding branding;
    private JPanel windowHeaderPanel;
    private Point mouseDownCompCoords;
    public WindowDirections(MainEngine mainEngine, Branding branding){
        this.mainEngine = mainEngine;
        this.branding = branding;

        setBackground(branding.windowColor);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(false);
        setPreferredSize(new Dimension(900, 500));
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

        JLabel iconLabel = new JLabel(branding.resizeImageIcon(branding.icoDirections, 0.25f));
        JLabel titleLabel = new JLabel("Directions");
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
        windowContentPanel.setLayout(new BorderLayout());
        windowContentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,20,10));

        JPanel contentHeaderPanel = new JPanel();
        JLabel contentHeaderLabel = new JLabel("How to Play");
        contentHeaderLabel.setFont(branding.windowsFontMedium);
        contentHeaderPanel.setOpaque(false);
        contentHeaderPanel.add(contentHeaderLabel);
        
        JPanel howToPlayPanel = new JPanel();
        howToPlayPanel.setOpaque(false);
        howToPlayPanel.setLayout(new GridLayout(1,3,20,0));

        JPanel col1Panel = new JPanel(new BorderLayout());
        JPanel col2Panel = new JPanel(new BorderLayout());
        JPanel col3Panel = new JPanel(new BorderLayout());
        col1Panel.setOpaque(false);
        col2Panel.setOpaque(false);
        col3Panel.setOpaque(false);

        JPanel step1APanelIndent = new JPanel();
        JPanel step1BPanelIndent = new JPanel();
        JPanel step2PanelIndent = new JPanel();
        JPanel step3APanelIndent = new JPanel();
        JPanel step3BPanelIndent = new JPanel();
        step1APanelIndent.setLayout(new BorderLayout());
        step1BPanelIndent.setLayout(new BorderLayout());
        step2PanelIndent.setLayout(new BorderLayout());
        step3APanelIndent.setLayout(new BorderLayout());
        step3BPanelIndent.setLayout(new BorderLayout());
        step1APanelIndent.setBackground(branding.gray0);
        step1BPanelIndent.setBackground(branding.gray0);
        step2PanelIndent.setBackground(branding.gray0);
        step3APanelIndent.setBackground(branding.gray0);
        step3BPanelIndent.setBackground(branding.gray0);
        branding.designPanelBorderIndent(step1APanelIndent);
        branding.designPanelBorderIndent(step1BPanelIndent);
        branding.designPanelBorderIndent(step2PanelIndent);        
        branding.designPanelBorderIndent(step3APanelIndent);
        branding.designPanelBorderIndent(step3BPanelIndent);

        JLabel step1aLabel = new JLabel("1. Guess the redacted word.");
        JLabel step1bLabel = new JLabel("<html>Every wrong guess deducts a part of<br>the illustration and the letter is removed</htm>");
        JLabel step2aLabel = new JLabel("2. Keep Mr. E intact.");
        JLabel step2bLabel = new JLabel("<html>Do not let the man escape or else<br>he will destroy your computer.</html>");
        JLabel step3aLabel = new JLabel("3. Win or Lose.");
        JLabel step3bLabel = new JLabel("<html>Reveal the word and save the day.<br>Or lose and get your pc corrupted.</html>");

        JLabel step1aPic = new JLabel(branding.imgStep1a); 
        JLabel step1a2Pic = new JLabel(branding.imgStep1a); 
        JLabel step1bPic = new JLabel(branding.imgStep1b);
        JLabel step2Pic = new JLabel(branding.imgStep2);
        JLabel step3aPic = new JLabel(branding.imgStep3a);
        JLabel step3bPic = new JLabel(branding.imgStep3b);
        step1aPic.setBorder(BorderFactory.createEmptyBorder(10,10,30,10));
        step1a2Pic.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        step1bPic.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        step2Pic.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));        
        step3aPic.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        step3bPic.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


        step1aLabel.setFont(branding.windowsFontMedium);
        step2aLabel.setFont(branding.windowsFontMedium);
        step3aLabel.setFont(branding.windowsFontMedium);
        step1aLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        step2aLabel.setBorder(BorderFactory.createEmptyBorder(10,10,5,10));
        step3aLabel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        step1bLabel.setFont(branding.windowsFontMedium);
        step2bLabel.setFont(branding.windowsFontMedium);
        step3bLabel.setFont(branding.windowsFontMedium);

        step1APanelIndent.setPreferredSize(new Dimension(0,90));
        step1APanelIndent.add(step1aLabel, BorderLayout.NORTH);
        step1APanelIndent.add(step1aPic, BorderLayout.CENTER);

        step1BPanelIndent.setPreferredSize(new Dimension(0,230));
        JPanel containter = new JPanel();
        JPanel containerIndent = new JPanel(new GridBagLayout());
        branding.designPanelBorderIndent(containerIndent);
        containerIndent.add(step1a2Pic);
        containerIndent.setBackground(branding.gray1);
        containerIndent.setPreferredSize(new Dimension(230,50));
        containter.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        containter.setOpaque(false);
        containter.add(containerIndent);
        step1BPanelIndent.add(containter, BorderLayout.NORTH);
        step1BPanelIndent.add(step1bPic, BorderLayout.CENTER);

        step2PanelIndent.add(step2aLabel, BorderLayout.NORTH);
        step2PanelIndent.add(step2Pic, BorderLayout.CENTER);

        JPanel containter2 = new JPanel();
        JPanel containerIndent2 = new JPanel(new GridBagLayout());
        branding.designPanelBorderIndent(containerIndent2);
        containerIndent2.add(step3aPic);
        containerIndent2.setBackground(branding.blue1);
        containerIndent2.setPreferredSize(new Dimension(230,65));
        containter2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        containter2.setOpaque(false);
        containter2.add(containerIndent2);
        step3APanelIndent.setPreferredSize(new Dimension(0,130));
        step3APanelIndent.add(step3aLabel, BorderLayout.NORTH);
        step3APanelIndent.add(containter2, BorderLayout.CENTER);
        step3BPanelIndent.add(step3bPic, BorderLayout.CENTER);


        col1Panel.add(step1APanelIndent, BorderLayout.NORTH);
        col1Panel.add(step1bLabel, BorderLayout.CENTER);
        col1Panel.add(step1BPanelIndent, BorderLayout.SOUTH);

        col2Panel.add(step2PanelIndent, BorderLayout.NORTH);
        col2Panel.add(step2bLabel, BorderLayout.CENTER);

        col3Panel.add(step3APanelIndent, BorderLayout.NORTH);
        col3Panel.add(step3bLabel, BorderLayout.CENTER);
        col3Panel.add(step3BPanelIndent, BorderLayout.SOUTH);
        

        howToPlayPanel.add(col1Panel);
        howToPlayPanel.add(col2Panel);
        howToPlayPanel.add(col3Panel);
        



        windowContentPanel.add(contentHeaderPanel, BorderLayout.NORTH);
        windowContentPanel.add(howToPlayPanel, BorderLayout.CENTER);

        // Window Dragging Logic
        windowHeaderPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
                Container parent = getParent();
                if(parent != null){
                    parent.setComponentZOrder(WindowDirections.this, 0); // 0 = topmost layer
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
                    parent.setComponentZOrder(WindowDirections.this, 0);
                    parent.repaint();
                }
                e.consume();
            }
            @Override
            public void mousePressed(MouseEvent e) {
                Container parent = getParent();
                if(parent != null){
                    parent.setComponentZOrder(WindowDirections.this, 0);
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
