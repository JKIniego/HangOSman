package graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class OverlayEffect extends JComponent {

    // --- Layer 1 ---
    private BufferedImage image1;
    private boolean layer1Visible = true;
    private float alpha1 = 0.2f;
    private float baseAlpha1 = 0.2f;
    private float baseRange1 = 0.1f;
    private float intensityAlpha1 = 0f;
    private float targetIntensityAlpha1 = 0f;
    private float targetBaseRange1 = 0.1f;
    private int previousLives = 6;

    // --- Layer 2 ---
    private BufferedImage image2;
    private boolean layer2Visible = true;
    private float alpha2 = 0.2f;
    private float baseAlpha2 = 0.1f;
    private float baseRange2 = 0.1f;

    // --- Layer 3 ---
    private BufferedImage image3;
    private boolean layer3Visible = true;
    private float alpha3 = 0f;
    private float baseAlpha3 = 0.1f;
    private float baseRange3 = 0f;
    private boolean layer3FlipH = false;
    private boolean layer3FlipV = false;

    public OverlayEffect() {
        setOpaque(false);
        setEnabled(false);

        image1 = loadImage("/assets/images/overlay1.png");
        image2 = loadImage("/assets/images/overlay2.png");
        image3 = loadImage("/assets/images/overlay3.png");

        startFlicker();
    }

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Overlay image not found: " + path);
            return null;
        }
    }

    // --- Lives intensity ---
    public void setLivesIntensity(int lives, int maxLives) {
        float danger = 1f - ((float)(lives - 1) / (maxLives - 1));
        danger = Math.max(0f, Math.min(1f, danger));

        float newTargetIntensity = danger * 0.35f;
        float newTargetRange = 0.1f + danger * 0.15f;
        targetIntensityAlpha1 = newTargetIntensity;
        targetBaseRange1 = newTargetRange;
        if (lives <= previousLives) {
            intensityAlpha1 = newTargetIntensity;
            baseRange1 = newTargetRange;
        }

        previousLives = lives;
    }

    // --- Per-layer visibility setters ---
    public void setLayer1Visible(boolean visible) { layer1Visible = visible; repaint(); }
    public void setLayer2Visible(boolean visible) { layer2Visible = visible; repaint(); }
    public void setLayer3Visible(boolean visible) { layer3Visible = visible; repaint(); }

    public boolean isLayer1Visible() { return layer1Visible; }
    public boolean isLayer2Visible() { return layer2Visible; }
    public boolean isLayer3Visible() { return layer3Visible; }

    // --- Per-layer base alpha setters (optional tuning from Settings) ---
    public void setLayer2BaseAlpha(float a) { baseAlpha2 = a; }
    public void setLayer3BaseAlpha(float a) { baseAlpha3 = a; }

    private void startFlicker() {
        // Layer 1 — lives-driven
        new Timer(70, e -> {
            intensityAlpha1 += (targetIntensityAlpha1 - intensityAlpha1) * 0.3f;
            baseRange1       += (targetBaseRange1      - baseRange1)      * 0.3f;
            alpha1 = baseAlpha1 + intensityAlpha1 + (float) Math.random() * baseRange1;
            repaint();
        }).start();

        // Layer 2 — simple flicker
        new Timer(70, e -> {
            alpha2 = baseAlpha2 + (float) Math.random() * baseRange2;
            repaint();
        }).start();

        // Layer 3 — fast noise
        new Timer(15, e -> {
            alpha3 = baseAlpha3 + (float) Math.random() * baseRange3;
            layer3FlipH = Math.random() < 0.5;
            layer3FlipV = Math.random() < 0.5;
            repaint();
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        if (image1 != null && layer1Visible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, clamp(alpha1)));
            g2.drawImage(image1, 0, 0, getWidth(), getHeight(), null);
        }

        if (image2 != null && layer2Visible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, clamp(alpha2)));
            g2.drawImage(image2, 0, 0, getWidth(), getHeight(), null);
        }

        if (image3 != null && layer3Visible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, clamp(alpha3)));

            int tx = 0, ty = 0, sx = 1, sy = 1;
            if (layer3FlipH) { sx = -1; tx = getWidth(); }
            if (layer3FlipV) { sy = -1; ty = getHeight(); }
            g2.translate(tx, ty);
            g2.scale(sx, sy);

            g2.drawImage(image3, 0, 0, getWidth(), getHeight(), null);

            g2.scale(sx, sy);
            g2.translate(-tx, -ty);
        }

        g2.dispose();
    }

    private float clamp(float v) {
        return Math.max(0f, Math.min(1f, v));
    }

    @Override
    public boolean contains(int x, int y) {
        return false;
    }
}