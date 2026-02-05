package graphics;

import engine.MainEngine;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ScreenDesktop extends JPanel {
    private Branding branding;
    private MainEngine mainEngine;
    private JButton selectedDesktopIcon = null;

    private WindowDecoder windowDecoder;
    private WindowStickman windowStickman;

    public ScreenDesktop(MainEngine mainEngine, Branding branding) {
        this.branding = branding;
        this.mainEngine = mainEngine;
        setBackground(branding.backgroundColor);
        setLayout(new BorderLayout());
        initializeTaskbar();
        initializeDesktopIcons();
        initializeDesktopWindows();
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (selectedDesktopIcon != null){
                    selectedDesktopIcon.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
                    selectedDesktopIcon = null;
                }
            }
        });
        setVisible(true);
    }

    public void initializeDesktopWindows() {
        JPanel desktopWindows = new JPanel();
        desktopWindows.setLayout(null);
        desktopWindows.setOpaque(false);

        windowDecoder = new WindowDecoder(mainEngine, branding);
        windowDecoder.setSize(windowDecoder.getPreferredSize());
        windowDecoder.setLocation(100, 200);

        windowStickman = new WindowStickman(mainEngine, branding);
        windowStickman.setSize(windowStickman.getPreferredSize());
        windowStickman.setLocation(550, 70);

        desktopWindows.add(windowDecoder, BorderLayout.CENTER);
        desktopWindows.add(windowStickman, BorderLayout.CENTER);
        this.add(desktopWindows, BorderLayout.CENTER);
    }

    public void initializeDesktopIcons() {
        JPanel desktopIconsPanel = new JPanel();
        desktopIconsPanel.setOpaque(false);
        desktopIconsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        desktopIconsPanel.setPreferredSize(new java.awt.Dimension(120, 0));
        
        JPanel iconsContainer = new JPanel();
        iconsContainer.setOpaque(false);
        iconsContainer.setLayout(new BoxLayout(iconsContainer, BoxLayout.Y_AXIS));

        iconsContainer.add(createDesktopIconButton("Recycle Bin", branding.icoRecycleBin));
        iconsContainer.add(Box.createVerticalStrut(20));
        iconsContainer.add(createDesktopIconButton("Play", branding.icoComputer));
        iconsContainer.add(Box.createVerticalStrut(20));
        iconsContainer.add(createDesktopIconButton("Directions", branding.icoDirections));
        iconsContainer.add(Box.createVerticalStrut(20));
        iconsContainer.add(createDesktopIconButton("Settings", branding.icoSettings));

        desktopIconsPanel.add(iconsContainer);
        this.add(desktopIconsPanel , BorderLayout.WEST);
    }

    private JButton createDesktopIconButton(String text, Icon icon) {
        JButton button = new JButton(text, icon);

        // Style like desktop icon
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.BOTTOM);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setIconTextGap(10);
        button.setPreferredSize(new Dimension(100, 100));
        button.setMaximumSize(new Dimension(100, 100));
        button.setMinimumSize(new Dimension(100, 100));
        button.setToolTipText(text);
        button.setFont(branding.windowsFontSmall);
        button.setForeground(branding.white);
        // Borders
        Border emptyBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
        Border selectionBorder = BorderFactory.createLineBorder(branding.white, 1, true);
        button.setBorder(emptyBorder);
        button.setBorderPainted(true);

        // Mouse listener for single/double click
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // double-click detected
                    mainEngine.desktopIconsPressed(text);
                }
            }
        });

        button.addActionListener(e -> {
            button.setBorder(selectionBorder);
            if (selectedDesktopIcon != null && selectedDesktopIcon != button) {
                selectedDesktopIcon.setBorder(emptyBorder);
            }
            button.setBorder(selectionBorder);
            selectedDesktopIcon = button;
        });
        return button;
    }


    public void initializeTaskbar() {
        JPanel taskbar = new JPanel();
        taskbar.setBackground(branding.windowColor);
        taskbar.setPreferredSize(new java.awt.Dimension(0, 45));
        taskbar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(4, 0, 0, 0, branding.white), BorderFactory.createEmptyBorder(0, 10, 0, 0)));
        taskbar.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 4));
        this.add(taskbar , BorderLayout.SOUTH);

        JButton startButton = new JButton("Start");
        startButton.setIcon(branding.icoWindows);
        startButton.setPreferredSize(new java.awt.Dimension(100, 32));
        branding.designButtonDefault(startButton);
        startButton.setFont(branding.windowsFont2Small);
        taskbar.add(startButton);

        startButton.addActionListener(e -> {
            mainEngine.startButtonPressed("ScreenGameOver");
            });
    }
    
    // Wallpaper Painting
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image img = branding.imgWindows67.getImage();
        int x = (getWidth() - img.getWidth(this)) / 2;
        int y = (getHeight() - img.getHeight(this)) / 2;
        g.drawImage(img, x, y, this);
    }

    public WindowDecoder getWindowDecoder() {
        return windowDecoder;
    }
}
