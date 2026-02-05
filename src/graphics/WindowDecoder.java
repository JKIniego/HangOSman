package graphics;

import engine.MainEngine;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import javax.swing.border.Border;

public class WindowDecoder extends JPanel{
    private MainEngine mainEngine;
    private Branding branding;
    private JPanel windowHeaderPanel, windowContentPanel, redactedWordPanel;
    private Point mouseDownCompCoords;
    public WindowDecoder(MainEngine mainEngine, Branding branding){
        this.mainEngine = mainEngine;
        this.branding = branding;

        setBackground(branding.windowColor);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        setPreferredSize(new Dimension(400, 280));
        setBorder(BorderFactory.createLineBorder(branding.white, 3));

        initializeDecoderUI();
    }

    public void initializeDecoderUI(){
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
        JLabel titleLabel = new JLabel("Decoder");
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
        windowContentPanel = new JPanel();
        windowContentPanel.setBackground(branding.windowColor); 
        windowContentPanel.setPreferredSize(new Dimension(100,100));
        windowContentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        windowContentPanel.setLayout(new BorderLayout());

        // Words Display
        JPanel decoderDisplayPanel = new JPanel();
        decoderDisplayPanel.setBackground(branding.gray0);
        decoderDisplayPanel.setBorder(BorderFactory.createLineBorder(branding.gray2, 5));
        decoderDisplayPanel.setPreferredSize(new Dimension(0,50));
        
        Border highlight = BorderFactory.createMatteBorder(0,0,3,3,branding.white);
        Border grayShade = BorderFactory.createMatteBorder(3,3,0,0,branding.gray2);
        Border innerShading = BorderFactory.createCompoundBorder(grayShade, highlight);
        decoderDisplayPanel.setBorder(innerShading);

        redactedWordPanel = new JPanel();
        redactedWordPanel.setOpaque(false);
        decoderDisplayPanel.add(redactedWordPanel);

        windowContentPanel.add(decoderDisplayPanel, BorderLayout.NORTH);

        // Keys Panel
        JPanel decoderKeysPanel = new JPanel();
        decoderKeysPanel.setLayout(new GridBagLayout());
        decoderKeysPanel.setBackground(branding.windowColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // 2D array for keyboard layout
        String[][] keyboardLayout = {
            {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
            {"A", "S", "D", "F", "G", "H", "J", "K", "L"},
            {"Z", "X", "C", "V", "B", "N", "M"},
            {" "}
        };

        for (int row = 0; row < keyboardLayout.length; row++) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 1));
            rowPanel.setBackground(branding.windowColor);
            
            for (int col = 0; col < keyboardLayout[row].length; col++) {
                char key = keyboardLayout[row][col].charAt(0);
                JButton btn = new JButton(keyboardLayout[row][col]);
                btn.setPreferredSize(new Dimension(30, 40));
                branding.designButtonDefault(btn);

                if (keyboardLayout[row][col].equals(" ")) {
                    btn.setPreferredSize(new Dimension(170, 30));
                }
                
                btn.addActionListener(e -> {
                    mainEngine.decoderButtonPressed(key);
                });

                String keyString = keyboardLayout[row][col].equals(" ") ? " " : String.valueOf(key);
                int keyCode = keyboardLayout[row][col].equals(" ") ? KeyEvent.VK_SPACE : KeyEvent.getExtendedKeyCodeForChar(key);
                
                btn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(keyCode, 0), "pressed_" + keyString);
                btn.getActionMap().put("pressed_" + keyString, new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btn.doClick();
                    }
                });

                rowPanel.add(btn);
            }
            
            gbc.gridy = row;
            decoderKeysPanel.add(rowPanel, gbc);
        }

        windowContentPanel.add(decoderKeysPanel, BorderLayout.CENTER);


        // Window Dragging Logic
        windowHeaderPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
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

    public void newRedactedWord(String redactedWord){
        redactedWordPanel.removeAll();
        int numLetters = redactedWord.length();
        redactedWordPanel.setLayout(new GridLayout(1, numLetters, 5, 0));
        for (int i = 0; i < numLetters; i++) {
            JLabel letterLabel = new JLabel(" ", JLabel.CENTER);
            letterLabel.setPreferredSize(new Dimension(20, 30));
            letterLabel.setBackground(branding.black);
            letterLabel.setOpaque(true);
            redactedWordPanel.add(letterLabel);
        }
        redactedWordPanel.revalidate();
        redactedWordPanel.repaint();
    }

    public void revealLetter(String redactedWord, char letter){
        if (redactedWordPanel.getComponentCount() == 0) return;
        Component[] labels = redactedWordPanel.getComponents();
        for (int i = 0; i < redactedWord.length(); i++) {
            if (redactedWord.charAt(i) == letter) {
                JLabel letterLabel = (JLabel) labels[i];
                letterLabel.setOpaque(false);
                letterLabel.setFont(branding.windowsFont2Small);
                letterLabel.setForeground(branding.black);
                Border underline = BorderFactory.createMatteBorder(0, 0, 2, 0, branding.black);
                letterLabel.setBorder(underline);
                letterLabel.setText(String.valueOf(letter).toUpperCase());
            }
        }
    }
    
}
