package graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class OverlayEffect extends JComponent {

    private BufferedImage image;
    private float alpha = 0.4f;

    public OverlayEffect(String path) {
        setOpaque(false);
        setEnabled(false);

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/graphics/assets/images/overlay4.png"));
        } catch (IOException e) {
            System.out.println("Overlay image not found: " + path);
        }

        startFlicker();
    }

    private void startFlicker() {
        new Timer(70, e -> {
            alpha = 0.4f + (float) Math.random() * 0.3f;
            repaint();
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image == null) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)
        );
        g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g2.dispose();
    }

    // Click-through overlay
    @Override
    public boolean contains(int x, int y) {
        return false;
    }
}
