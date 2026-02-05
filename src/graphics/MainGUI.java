package graphics;

import engine.MainEngine;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainGUI {
    private MainEngine engine;
    private JFrame mainFrame;
    private JPanel mainPanel;
    private Branding branding;
    private OverlayEffect overlay;
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
        
        scrnStartScreen = new ScreenStart(engine, branding);
        scrnDesktop = new ScreenDesktop(engine, branding);
        scrnGameOver = new ScreenGameOver(engine, branding);

        mainPanel.add(scrnStartScreen, "ScreenStart");
        mainPanel.add(scrnDesktop, "ScreenDesktop");
        mainPanel.add(scrnGameOver, "ScreenGameOver");
        mainFrame.add(mainPanel);
    }

    private void initializeOverlay() {
        overlay = new OverlayEffect("overlay4.png");
        mainFrame.setGlassPane(overlay);
        overlay.setVisible(true); 
    }


    // Updating Methods

    public void setOverlayVisible(boolean visible) {
        overlay.setVisible(visible);
    }

    public void newRedactedWord(String redactedWord) {
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

    public void changeScreen(String screenName){
        CardLayout cl = (CardLayout)(mainPanel.getLayout());
        cl.show(mainPanel, screenName);
        for (java.awt.Component comp : mainPanel.getComponents()) {
            if (comp.isVisible()) {
                comp.requestFocusInWindow();
                break;
            }
        }
    }
}

