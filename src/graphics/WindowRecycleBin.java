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

public class WindowRecycleBin extends JPanel{
    private MainEngine mainEngine;
    private Branding branding;
    private JPanel windowHeaderPanel, statsContainter;
    private Point mouseDownCompCoords;

    public WindowRecycleBin(MainEngine mainEngine, Branding branding){
        this.mainEngine = mainEngine;
        this.branding = branding;

        setBackground(branding.windowColor);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(false);
        setPreferredSize(new Dimension(350, 180));
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

        JLabel iconLabel = new JLabel(branding.resizeImageIcon(branding.icoRecycleBin, 0.25f));
        JLabel titleLabel = new JLabel("Recycle Bin");
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
        windowContentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel statsLabel = new JLabel("Session Statistics");
        statsLabel.setFont(branding.windowsFontMedium);
        statsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        statsContainter = new JPanel();
        statsContainter.setBackground(branding.gray1);
        statsContainter.setLayout(new BoxLayout(statsContainter, BoxLayout.Y_AXIS));
        branding.designPanelBorderIndent(statsContainter);

        refreshStatDisplay();

        windowContentPanel.add(statsLabel, BorderLayout.NORTH);
        windowContentPanel.add(statsContainter, BorderLayout.CENTER);
        
        // Window Dragging Logic
        windowHeaderPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
                Container parent = getParent();
                if(parent != null){
                    parent.setComponentZOrder(WindowRecycleBin.this, 0); // 0 = topmost layer
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

    public void refreshStatDisplay() {
        statsContainter.removeAll();

        String stringLabels[] = {"Words Leaked: ", "Escaped Limbs: ", "Leak Efficiency: "};
        int stats[] = {
            mainEngine.getData().getCorrect(), 
            mainEngine.getData().getWrong(), 
            mainEngine.getData().getPercentage()
        };

        for (int i = 0; i < stringLabels.length; i++) {
            JPanel row = new JPanel();
            row.setLayout(new BorderLayout());
            row.setOpaque(false);
            row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            JLabel label = new JLabel(stringLabels[i]);
            label.setFont(branding.windowsFontSmall);
            
            String valueText = String.valueOf(stats[i]);
            if (stringLabels[i].contains("Efficiency")) {
                valueText += "%";
            }
            
            JLabel statLabel = new JLabel(valueText);
            statLabel.setFont(branding.windowsFontSmall);

            row.add(label, BorderLayout.WEST);
            row.add(statLabel, BorderLayout.EAST);

            statsContainter.add(row);
        }
        statsContainter.revalidate();
        statsContainter.repaint();
    }

    public void initializeListenerConsumer(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Container parent = getParent();
                if(parent != null){
                    parent.setComponentZOrder(WindowRecycleBin.this, 0);
                    parent.repaint();
                }
                e.consume();
            }
            @Override
            public void mousePressed(MouseEvent e) {
                Container parent = getParent();
                if(parent != null){
                    parent.setComponentZOrder(WindowRecycleBin.this, 0);
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
