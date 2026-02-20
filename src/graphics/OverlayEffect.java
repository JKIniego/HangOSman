package graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class OverlayEffect extends JComponent {

    private BufferedImage image;
    private float alpha = 0.2f;
    private float baseAlpha = 0.2f;
    private float baseRange = 0.1f;

    private float intensityAlpha = 0f;
    private float targetIntensityAlpha = 0f;
    private float targetBaseRange = 0.1f;
    private int previousLives = 6;

    public OverlayEffect(String path) {
        setOpaque(false);
        setEnabled(false);

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/assets/images/overlay4.png"));
        } catch (IOException e) {
            System.out.println("Overlay image not found: " + path);
        }

        startFlicker();
    }

    public void setLivesIntensity(int lives, int maxLives) {
        float danger = 1f - ((float)(lives - 1) / (maxLives - 1));
        danger = Math.max(0f, Math.min(1f, danger));

        float newTargetIntensity = danger * 0.45f;
        float newTargetRange = 0.1f + danger * 0.15f;

        if (lives > previousLives) {
            // Recovering lives — lerp smoothly back
            targetIntensityAlpha = newTargetIntensity;
            targetBaseRange = newTargetRange;
        } else {
            // Taking damage — snap immediately
            targetIntensityAlpha = newTargetIntensity;
            targetBaseRange = newTargetRange;
            intensityAlpha = newTargetIntensity;
            baseRange = newTargetRange;
        }

        previousLives = lives;
    }

    private void startFlicker() {
        new Timer(70, e -> {
            intensityAlpha += (targetIntensityAlpha - intensityAlpha) * 0.3f;
            baseRange      += (targetBaseRange      - baseRange)      * 0.3f;

            alpha = baseAlpha + intensityAlpha + (float) Math.random() * baseRange;
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

    @Override
    public boolean contains(int x, int y) {
        return false;
    }
}