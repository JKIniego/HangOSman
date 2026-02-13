package graphics;

import engine.MainEngine;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ScreenStory extends JPanel {
    private Branding branding;
    private MainEngine mainEngine;
    private JTextArea storyText;

    public ScreenStory(MainEngine mainEngine, Branding branding) {
        this.branding = branding;
        this.mainEngine = mainEngine;
        
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        
        initializeStoryText();
        
        // Click anywhere to continue
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainEngine.startButtonPressed("ScreenStart");
            }
        });
    }

    private void initializeStoryText() {
        storyText = new JTextArea();
        storyText.setBackground(Color.BLACK);
        storyText.setForeground(new Color(0, 255, 0)); // Green terminal text
        storyText.setFont(branding.windowsFont2Medium);
        storyText.setEditable(false);
        storyText.setFocusable(false);
        storyText.setLineWrap(true);
        storyText.setWrapStyleWord(true);
        storyText.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        // Placeholder story text
        String story = "C:\\> SYSTEM BOOT INITIATED...\n\n"
                     + "C:\\> LOADING HANGMAN PROTOCOL...\n\n"
                     + "C:\\> WELCOME TO HANGOSMAN\n\n"
                     + "C:\\> [STORY PLACEHOLDER]\n\n"
                     + "C:\\> The year is 1995...\n"
                     + "C:\\> Your mission: Decode the words or face the consequences.\n\n"
                     + "C:\\> Six lives. One chance.\n\n\n"
                     + "C:\\> Click anywhere to continue...";
        
        storyText.setText(story);
        
        // Make clicks pass through to the panel
        storyText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainEngine.startButtonPressed("ScreenStart");
            }
        });
        
        add(storyText, BorderLayout.CENTER);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
