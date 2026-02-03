package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

public class Branding {
    public Font windowsFontExtraSmall, windowsFontSmall, windowsFontMedium, windowsFontLarge, windowsFontExtraLarge, windowsFontGiant;
    public Font windowsFont2ExtraSmall, windowsFont2Small, windowsFont2Medium, windowsFont2Large, windowsFont2ExtraLarge;
    public ImageIcon imgWindows67;
    public ImageIcon icoWindows, icoRecycleBin, icoComputer, icoDirections, icoSettings;

    public Branding(){
        initializeFonts();
        initializeImages();
    }

    // Component Colors
    public Color backgroundColor = Color.decode("#008080");
    public Color windowColor = Color.decode("#BDBEBD");
    public Color buttonColor = Color.decode("#D9D9D9");

    // Shading Colors
    public Color gray1 = Color.decode("#666666");
    public Color gray2 = Color.decode("#666666");
    public Color gray3 = Color.decode("#666666");

    // Basic Colors
    public Color black = Color.decode("#000000");
    public Color white = Color.decode("#FFFFFF");
    public Color blue = Color.decode("#0000FF");
    public Color red = Color.decode("#FF0000");
    public Color green = Color.decode("#00FF00");
    
    public void initializeFonts(){
        System.out.println("Loading Fonts...");
        try {
            Font windowsFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/graphics/assets/fonts/Windows-Regular.ttf"));
            windowsFontExtraSmall = windowsFont.deriveFont(Font.PLAIN, 10f);
            windowsFontSmall = windowsFont.deriveFont(Font.PLAIN, 14f);
            windowsFontMedium = windowsFont.deriveFont(Font.PLAIN, 16f);
            windowsFontLarge = windowsFont.deriveFont(Font.PLAIN, 24f);
            windowsFontExtraLarge = windowsFont.deriveFont(Font.PLAIN, 32f);
            windowsFontGiant = windowsFont.deriveFont(Font.PLAIN, 200f);

            Font windowsFont2 = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/graphics/assets/fonts/pixelmix_bold.ttf"));
            windowsFont2ExtraSmall = windowsFont2.deriveFont(Font.PLAIN, 10f);
            windowsFont2Small = windowsFont2.deriveFont(Font.PLAIN, 14f);
            windowsFont2Medium = windowsFont2.deriveFont(Font.PLAIN, 16f);
            windowsFont2Large = windowsFont2.deriveFont(Font.PLAIN, 24f);
            windowsFont2ExtraLarge = windowsFont2.deriveFont(Font.PLAIN, 32f);
        } catch (FontFormatException | IOException e){
            System.err.println("Font failed to load.");
        }
    }

    public void initializeImages(){
        System.out.println("Loading Images...");
        try {
            BufferedImage buff_imgWindows67 = ImageIO.read(getClass().getResourceAsStream("/graphics/assets/images/windows67.png"));
            
            BufferedImage buff_icoWindowsIcon = ImageIO.read(getClass().getResourceAsStream("/graphics/assets/icons/windows-0.png"));
            BufferedImage buff_icoRecycleBinIcon = ImageIO.read(getClass().getResourceAsStream("/graphics/assets/icons/recycle_bin.png"));
            BufferedImage buff_icoComputerIcon = ImageIO.read(getClass().getResourceAsStream("/graphics/assets/icons/computer.png"));
            BufferedImage buff_icoDirectionsIcon = ImageIO.read(getClass().getResourceAsStream("/graphics/assets/icons/directions.png"));
            BufferedImage buff_icoSettingsIcon = ImageIO.read(getClass().getResourceAsStream("/graphics/assets/icons/settings.png"));
            
            imgWindows67 = resizeImage(buff_imgWindows67, 0.5f);

            icoWindows = resizeImage(buff_icoWindowsIcon, 0.9f);
            icoRecycleBin = resizeImage(buff_icoRecycleBinIcon, 0.6f);
            icoComputer = resizeImage(buff_icoComputerIcon, 0.6f);
            icoDirections = resizeImage(buff_icoDirectionsIcon, 0.6f);
            icoSettings = resizeImage(buff_icoSettingsIcon, 0.6f);

            //sampleGIF = new ImageIcon(getClass().getResource("/Assets/Images/jeopardy-animation.gif"));
            //sampleGIF = resizeGIF(sampleGIF, 800, 150);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ImageIcon resizeGIF(ImageIcon icon, int width, int height) {
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(scaled);
    }

    public ImageIcon resizeImage(BufferedImage original, float scale) {
        int newWidth  = Math.round(original.getWidth()  * scale);
        int newHeight = Math.round(original.getHeight() * scale);

        Image tmp = original.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        
        return new ImageIcon(resized);
    }

    public JButton designButtonFlat(JButton button){
        button.setBackground(buttonColor);
        button.setFocusPainted(false);
        Border highlight = BorderFactory.createMatteBorder(2,2,0,0,white);
        Border grayShade = BorderFactory.createMatteBorder(0,0,2,2,gray2);
        Border shadow = BorderFactory.createMatteBorder(0,0,2,2,black);

        Border combined1 = BorderFactory.createCompoundBorder(grayShade, highlight);
        Border combined = BorderFactory.createCompoundBorder(shadow, combined1);
        button.setFont(windowsFont2Small);
        button.setBorder(combined);
        return button;
    }
    public JButton designButtonPop(JButton button){
        button.setBackground(buttonColor);
        button.setFocusPainted(false);
        Border highlight = BorderFactory.createMatteBorder(4,4,0,0,white);
        Border grayShade = BorderFactory.createMatteBorder(0,0,4,4,gray2);
        Border shadow = BorderFactory.createMatteBorder(0,0,3,3,black);

        Border combined1 = BorderFactory.createCompoundBorder(grayShade, highlight);
        Border combined = BorderFactory.createCompoundBorder(shadow, combined1);
        button.setFont(windowsFont2ExtraSmall);
        button.setBorder(combined);
        return button;
    }
}
