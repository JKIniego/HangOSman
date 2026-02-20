package graphics;

import engine.MainEngine;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

public class MainGUI {
    private MainEngine engine;
    private JFrame mainFrame;
    private JPanel mainPanel;
    private Branding branding;
    private OverlayEffect overlay;
    private ScreenStory scrnStory;
    private ScreenStart scrnStartScreen;
    private ScreenDesktop scrnDesktop;
    private ScreenGameOver scrnGameOver;

    public MainGUI(MainEngine engine) {
        this.engine = engine;
        this.branding = new Branding();

        initializeMainFrame();
        initializeMainPanel();
        initializeOverlay();

        mainFrame.setVisible(true);
    }

    public void initializeMainFrame(){
        mainFrame = new JFrame();
        mainFrame.setSize(new Dimension(1240,720));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Windows67");
        mainFrame.setIconImage(branding.icoWindows.getImage());
        mainFrame.setLocationRelativeTo(null);
    }

    public void initializeMainPanel(){
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLUE);
        mainPanel.setLayout(new CardLayout());
        
        scrnStory = new ScreenStory(engine, branding);
        scrnStartScreen = new ScreenStart(engine, branding);
        scrnDesktop = new ScreenDesktop(engine, branding);
        scrnGameOver = new ScreenGameOver(engine, branding);

        mainPanel.add(scrnStory, "ScreenStory");
        mainPanel.add(scrnStartScreen, "ScreenStart");
        mainPanel.add(scrnDesktop, "ScreenDesktop");
        mainPanel.add(scrnGameOver, "ScreenGameOver");
        mainFrame.add(mainPanel);
    }

    private void initializeOverlay() {
        overlay = new OverlayEffect(); 
        mainFrame.setGlassPane(overlay);
        overlay.setVisible(true);
    }


    /** Master toggle — hides/shows the entire glass pane. */
    public void setOverlayVisible(boolean visible) {
        overlay.setVisible(visible);
    }

    /** Toggle overlay layer 1 (the lives-driven scanline / red vignette). */
    public void setOverlay1Visible(boolean visible) {
        overlay.setLayer1Visible(visible);
    }

    /** Toggle overlay layer 2. */
    public void setOverlay2Visible(boolean visible) {
        overlay.setLayer2Visible(visible);
    }

    /** Toggle overlay layer 3. */
    public void setOverlay3Visible(boolean visible) {
        overlay.setLayer3Visible(visible);
    }

    /** Read current visibility of each layer (useful to init Settings checkboxes). */
    public boolean isOverlay1Visible() { return overlay.isLayer1Visible(); }
    public boolean isOverlay2Visible() { return overlay.isLayer2Visible(); }
    public boolean isOverlay3Visible() { return overlay.isLayer3Visible(); }


    public void animateTaskbar(){
        if (scrnDesktop != null) {
            JPanel taskbar = scrnDesktop.getTaskbar();
            JPanel desktop = scrnDesktop;
            Dimension originalSize = new Dimension(0, 45);
            
            taskbar.setPreferredSize(new Dimension(0, 0));
            taskbar.setVisible(true);
            desktop.revalidate();
            
            Timer timer = new Timer(16, null);
            final long[] startTime = {System.currentTimeMillis()};
            final int duration = 1000;
            
            timer.addActionListener(e -> {
                long elapsed = System.currentTimeMillis() - startTime[0];
                double progress = Math.min(1.0, (double)elapsed / duration);
            
                double eased = 1 - Math.pow(1 - progress, 3);
                
                int currentHeight = (int)(45 * eased);
                taskbar.setPreferredSize(new Dimension(0, currentHeight));
                desktop.revalidate();
                desktop.repaint();
                
                if(progress >= 1.0){
                    taskbar.setPreferredSize(originalSize);
                    desktop.revalidate();
                    ((Timer)e.getSource()).stop();
                }
            });
            timer.start();
        }
    }

    public void animateDesktopIcons(){
        if (scrnDesktop != null) {
            JPanel iconsPanel = scrnDesktop.getDesktopIconsPanel();
            
            if(iconsPanel != null){
                // Store original preferred size
                Dimension originalSize = new Dimension(120, 0);
                
                // Start with width 0 (collapsed)
                iconsPanel.setPreferredSize(new Dimension(0, 0));
                iconsPanel.setVisible(true);
                scrnDesktop.revalidate();
                
                Timer timer = new Timer(16, null);
                final long[] startTime = {System.currentTimeMillis()};
                final int duration = 1000;
                
                timer.addActionListener(e -> {
                    long elapsed = System.currentTimeMillis() - startTime[0];
                    double progress = Math.min(1.0, (double)elapsed / duration);
                    
                    double eased = 1 - Math.pow(1 - progress, 3);
                    
                    int currentWidth = (int)(120 * eased);
                    iconsPanel.setPreferredSize(new Dimension(currentWidth, 0));
                    scrnDesktop.revalidate();
                    scrnDesktop.repaint();
                    
                    if(progress >= 1.0){
                        iconsPanel.setPreferredSize(originalSize);
                        scrnDesktop.revalidate();
                        ((Timer)e.getSource()).stop();
                    }
                });
                timer.start();
            }
        }
    }

    public void animateScreenStart() {
        scrnStartScreen.resetFade();

        Timer timer = new Timer(20, null);
        final long[] startTime = {System.currentTimeMillis()};
        final int duration = 1000;

        timer.addActionListener(e -> {
            long elapsed = System.currentTimeMillis() - startTime[0];
            float alpha = Math.min(1f, (float) elapsed / duration);

            scrnStartScreen.setFadeAlpha(alpha);

            if (alpha >= 1f) {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }

    public void renderNewRedactedWord(String redactedWord) {
        if (scrnDesktop != null) {
            WindowDecoder decoder = scrnDesktop.getWindowDecoder();
            if (decoder != null) {
                decoder.newRedactedWord(redactedWord);
            }
        }
    }

    public void revealLetterInDecoder(String redactedWord, char letter) {
        if (scrnDesktop != null) {
            WindowDecoder decoder = scrnDesktop.getWindowDecoder();
            if (decoder != null) {
                decoder.revealLetter(redactedWord, letter);
            }
        }
    }

    public void updateStickmanStatus(int lives){
        if (scrnDesktop != null) {
            WindowStickman stickman = scrnDesktop.getWindowStickman();
            if (stickman != null) {
                stickman.updateStickmanStatus(lives);
            }
        }
        overlay.setLivesIntensity(lives, 6);
    }

    public void updateStatistics(){
        if (scrnDesktop != null) {
            WindowRecycleBin recycleBin = scrnDesktop.getWindowRecycleBin();
            if (recycleBin != null) {
                recycleBin.refreshStatDisplay();
            }
        }
    }

    public void changeScreen(String screenName){
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, screenName);
        for (java.awt.Component comp : mainPanel.getComponents()) {
            if (comp.isVisible()) {
                comp.requestFocusInWindow();
                if(screenName.equals("ScreenStart")){animateScreenStart(); overlay.setLivesIntensity(6, 6);}
                if(screenName.equals("ScreenDesktop")){animateTaskbar();animateDesktopIcons();}
                break;
            }
        }
    }

    public void openWindows(String iconName){
        if (scrnDesktop != null) {
            WindowDecoder decoder = scrnDesktop.getWindowDecoder();
            WindowStickman stickman = scrnDesktop.getWindowStickman();
            WindowDirections directions = scrnDesktop.getWindowDirections();
            WindowSettings settings = scrnDesktop.getWindowSettings();
            WindowRecycleBin recycleBin = scrnDesktop.getWindowRecycleBin();
            WindowLeak leak = scrnDesktop.getWindowLeak();
            WindowClose close = scrnDesktop.getWindowClose();
            
            if(iconName.equals("Play")){
                if(decoder.isVisible() && stickman.isVisible()){
                    flickerBorder(decoder);
                    flickerBorder(stickman);
                    shakeWindow(decoder);
                    shakeWindow(stickman);
                    engine.getAudioManager().playSound("errorButton.wav");
                }else{
                    popIn(decoder);
                    popIn(stickman);
                }
                bringToFront(decoder);
                bringToFront(stickman);
            } else if (iconName.equals("Directions")){
                if(directions.isVisible()){
                    flickerBorder(directions);
                    shakeWindow(directions);
                    engine.getAudioManager().playSound("errorButton.wav");
                }else{
                    popIn(directions);
                }
                bringToFront(directions);
            } else if (iconName.equals("Settings")){
                if(settings.isVisible()){
                    flickerBorder(settings);
                    shakeWindow(settings);
                    engine.getAudioManager().playSound("errorButton.wav");
                }else{
                    popIn(settings);
                }
                bringToFront(settings);
            } else if (iconName.equals("Recycle Bin")){
                if(recycleBin.isVisible()){
                    flickerBorder(recycleBin);
                    shakeWindow(recycleBin);
                    engine.getAudioManager().playSound("errorButton.wav");
                }else{
                    popIn(recycleBin);
                }
                bringToFront(recycleBin);
            } else if (iconName.equals("Close Window")){
                if(close.isVisible()){
                    flickerBorder(close);
                    shakeWindow(close);
                    engine.getAudioManager().playSound("errorButton.wav");
                }else{
                    popIn(close);
                }
                bringToFront(close);
            } else if (iconName.equals("Leak")){
                if(leak.isVisible()){
                    flickerBorder(leak);
                    shakeWindow(leak);
                    engine.getAudioManager().playSound("errorButton.wav");
                }else{
                    popIn(leak);
                    flickerBorder(leak);
                }
                bringToFront(leak);
            } else if (iconName.equals("Clear Leak")){
                if(leak.isVisible()){
                    leak.setVisible(false);
                }
            } else if (iconName.equals("Clear All")) {
                decoder.setVisible(false);
                stickman.setVisible(false);
                directions.setVisible(false);
                settings.setVisible(false);
                recycleBin.setVisible(false);
                leak.setVisible(false);
                close.setVisible(false);
            } 
        }
    }

    private void bringToFront(JPanel window){
        Container parent = window.getParent();
        if(parent != null){
            parent.setComponentZOrder(window, 0); // 0 = topmost layer
            parent.repaint();
        }
    }

    private void popIn(JPanel window) {
        window.setVisible(true);
        Dimension originalSize = window.getPreferredSize();
        Point originalLocation = window.getLocation();
        
        window.setSize(0, 0);
        window.setLocation(originalLocation.x + originalSize.width/2, 
                        originalLocation.y + originalSize.height/2);
        
        Timer timer = new Timer(2, null);
        final double[] scale = {0.0};
        
        timer.addActionListener(e -> {
            scale[0] += 0.3;
            if (scale[0] >= 1.0) {
                scale[0] = 1.0;
                window.setSize(originalSize);
                window.setLocation(originalLocation);
                ((Timer)e.getSource()).stop();
            } else {
                int w = (int)(originalSize.width * scale[0]);
                int h = (int)(originalSize.height * scale[0]);
                window.setSize(w, h);
                window.setLocation(originalLocation.x + (originalSize.width - w)/2,
                                originalLocation.y + (originalSize.height - h)/2);
            }
            window.revalidate();
        });
        timer.start();
    }

    private void flickerBorder(JComponent window){
        Border white1 = BorderFactory.createLineBorder(branding.white, 3);
        Border shadow1 = BorderFactory.createMatteBorder(0,0,5,5,branding.shadow);
        Border originalBorder = BorderFactory.createCompoundBorder(shadow1, white1);
        Border white2 = BorderFactory.createLineBorder(branding.gray2, 3);
        Border shadow2 = BorderFactory.createMatteBorder(0,0,5,5,branding.gray2);
        Border flickerBorder = BorderFactory.createCompoundBorder(shadow2, white2);
        
        Timer timer = new Timer(200, null);
        final int[] count = {0};
        
        timer.addActionListener(e -> {
            if(count[0] % 2 == 0){
                window.setBorder(flickerBorder);
            } else {
                window.setBorder(originalBorder);
            }
            count[0]++;
            
            if(count[0] >= 6){ // Flicker 3 times
                timer.stop();
                window.setBorder(originalBorder);
            }
        });
        
        timer.start();
    }

    private void shakeWindow(JComponent window) {
        Point originalLocation = window.getLocation();
        int[] offsets = {-8, 8, -6, 6, -4, 4, -2, 2, 0};

        Timer timer = new Timer(30, null);
        final int[] index = {0};

        timer.addActionListener(e -> {
            if (index[0] >= offsets.length) {
                window.setLocation(originalLocation);
                ((Timer) e.getSource()).stop();
            } else {
                window.setLocation(originalLocation.x + offsets[index[0]], originalLocation.y);
                index[0]++;
            }
        });
        timer.start();
    }
}

