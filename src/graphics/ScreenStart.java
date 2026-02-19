package graphics;

import engine.MainEngine;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    private float alpha = 0f;
    private JButton startButton;

    public ScreenStart(MainEngine mainEngine, Branding branding) {
        this.branding = branding;
        this.mainEngine = mainEngine;
        setBackground(branding.backgroundColor);
        setLayout(new GridBagLayout());

        startButton = new JButton("Start"); // field assignment, not local variable
        startButton.setIcon(branding.icoWindows);
        startButton.setPreferredSize(new java.awt.Dimension(100, 32));
        startButton.addActionListener(e -> { mainEngine.playSound("click"); mainEngine.startButtonPressed("ScreenDesktop"); });
        branding.designButtonStart(startButton);
        startButton.setVisible(false); // hidden until fade begins

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

    public void setFadeAlpha(float alpha) {
        this.alpha = alpha;
        startButton.setVisible(alpha >= 0.8f);
        repaint();
    }

    public void resetFade() {
        alpha = 0f;
        startButton.setVisible(false);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        // Fade wallpaper
        Image img = branding.imgWindows67.getImage();
        int x = (getWidth() - img.getWidth(this)) / 2;
        int y = (getHeight() - img.getHeight(this)) / 2;
        g2d.drawImage(img, x, y, this);

        // Fade button manually during animation
        if (alpha < 1f && startButton != null) {
            g2d.translate(startButton.getX(), startButton.getY());
            startButton.paint(g2d);
        }

        g2d.dispose();
    }
}