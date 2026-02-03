package graphics;

import engine.MainEngine;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ScreenStart extends JPanel {
    Branding branding;
    MainEngine mainEngine;

    public ScreenStart(MainEngine mainEngine, Branding branding) {
        this.branding = branding;
        this.mainEngine = mainEngine;
        setBackground(branding.backgroundColor);
        setLayout(new GridBagLayout());
        JButton startButton = new JButton("Start");
        startButton.setIcon(branding.icoWindows);
        startButton.setPreferredSize(new java.awt.Dimension(100, 32));
        startButton.addActionListener(e -> {mainEngine.guiChangeScreen("ScreenDesktop");});
        branding.designButtonFlat(startButton);
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
