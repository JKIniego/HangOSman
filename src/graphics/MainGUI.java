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
        mainFrame.setLocationRelativeTo(null);
    }

    public void initializeMainPanel(){
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLUE);
        mainPanel.setLayout(new CardLayout());
        
        ScreenStart scrnStartScreen = new ScreenStart(engine, branding);
        ScreenDesktop scrnDesktop = new ScreenDesktop(engine, branding);
        ScreenGameOver scrnGameOver = new ScreenGameOver(engine, branding);

        mainPanel.add(scrnStartScreen, "ScreenStart");
        mainPanel.add(scrnDesktop, "ScreenDesktop");
        mainPanel.add(scrnGameOver, "ScreenGameOver");
        mainFrame.add(mainPanel);
    }

     private void initializeOverlay() {
        overlay = new OverlayEffect("overlay4.png");
        mainFrame.setGlassPane(overlay);
        overlay.setVisible(true); // toggle if needed
    }

    public void setOverlayVisible(boolean visible) {
        overlay.setVisible(visible);
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

