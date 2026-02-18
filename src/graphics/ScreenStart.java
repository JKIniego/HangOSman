package graphics;

import engine.MainEngine;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ScreenStart extends JPanel {
    private Branding branding;
    private MainEngine mainEngine;

    public ScreenStart(MainEngine mainEngine, Branding branding) {
        this.branding = branding;
        this.mainEngine = mainEngine;
        setBackground(branding.backgroundColor);
        setLayout(new GridBagLayout());
        JButton startButton = new JButton("Start");
        startButton.setIcon(branding.icoWindows);
        startButton.setPreferredSize(new java.awt.Dimension(100, 32));
        startButton.addActionListener(e -> {mainEngine.playSound("click"); mainEngine.startButtonPressed("ScreenDesktop");});
        branding.designButtonStart(startButton);
        Color originalColor = startButton.getBackground();
        startButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!startButton.getModel().isPressed()) {
                    startButton.setBackground(branding.gray1);
                }
            }
            public void mouseExited(MouseEvent e) {
                if (!startButton.getModel().isPressed()) {
                    startButton.setBackground(originalColor);
                }
            }
        });
        startButton.getModel().addChangeListener(e -> {
            ButtonModel model = (ButtonModel) e.getSource();
            if (model.isPressed()) {
                branding.designButtonStartPressed(startButton);
            } else {
                branding.designButtonStart(startButton);
            }
        });
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(400, 0, 0, 0);
        add(startButton, gbc);
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
}
