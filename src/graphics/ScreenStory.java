package graphics;

import engine.MainEngine;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class ScreenStory extends JPanel {

    private Branding branding;
    private MainEngine mainEngine;
    private JTextArea storyText;
    private JButton skipButton;

    private ArrayList<String> lines = new ArrayList<>();
    private int currentLine = 0;
    private int currentChar = 0;
    private Timer typingTimer;
    private boolean isTyping = false;

    public ScreenStory(MainEngine mainEngine, Branding branding) {
        this.branding = branding;
        this.mainEngine = mainEngine;

        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        mainEngine.playMusic("splash.wav");

        initializeStoryLines();
        initializeStoryText();
        initializeSkipButton();
        initializeKeyListener();

        startTypingLine();
    }

    private void initializeStoryLines() {
        lines.add("C:\\> SYSTEM BOOT INITIATED...");
        lines.add("C:\\> LOADING HANGMAN PROTOCOL...");
        lines.add("C:\\> WELCOME TO HANGOSMAN");
        lines.add("C:\\> The year is 1995...");
        lines.add("C:\\> News headlines, there is a heinous criminal on the run");
        lines.add("C:\\> After some time, reports said that the criminal commited suicide by hanging");
        lines.add("C:\\> However, many believe he is still alive...");
        lines.add("C:\\> His files have been released but most are redacted.");
        lines.add("C:\\> You are an FBI agent uncovering the files for clues.");
        lines.add("C:\\> Your mission: Decode the words or face the consequences.");
        lines.add("C:\\> Six lives. One chance.");
        lines.add("");
        lines.add("C:\\> Press any key to continue...");
    }

    private void initializeStoryText() {
        storyText = new JTextArea();
        storyText.setBackground(Color.BLACK);
        storyText.setForeground(branding.green);
        storyText.setFont(branding.windowsFont2Medium);
        storyText.setEditable(false);
        storyText.setFocusable(false);
        storyText.setLineWrap(true);
        storyText.setWrapStyleWord(true);
        storyText.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        add(storyText, BorderLayout.CENTER);
    }

    private void initializeSkipButton() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.BLACK);

        skipButton = new JButton("Skip >>");
        skipButton.setFont(branding.windowsFontLarge);
        skipButton.setForeground(branding.green);
        skipButton.setOpaque(false);
        skipButton.setContentAreaFilled(false);
        skipButton.setBorderPainted(false);
        skipButton.setFocusPainted(false);
        skipButton.setBorder(BorderFactory.createEmptyBorder(0,0,40,70));

        

        skipButton.addActionListener(e -> {
            mainEngine.playSound("click");
            mainEngine.stopTypingSound();
            mainEngine.stopMusic();
            typingTimer.stop();
            mainEngine.startButtonPressed("ScreenStart");
        });

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.BLACK);
        rightPanel.add(skipButton);

        bottomPanel.add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initializeKeyListener() {
        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (isTyping) {
                    typingTimer.stop();
                    mainEngine.stopTypingSound();

                    String fullLine = lines.get(currentLine);

                    // Remove partially typed line
                    int textLength = storyText.getText().length();
                    storyText.setText(
                        storyText.getText().substring(0, textLength - currentChar)
                    );

                    // Add full correct line
                    storyText.append(fullLine + "\n\n");

                    isTyping = false;
                } else {
                    // Move to next line
                    currentLine++;
                    if (currentLine >= lines.size()) {
                        mainEngine.stopMusic();
                        mainEngine.startButtonPressed("ScreenStart");
                    } else {
                        startTypingLine();
                    }
                }
            }
        });
    }

    private void startTypingLine() {
        if (currentLine >= lines.size()) return;

        currentChar = 0;
        isTyping = true;

        typingTimer = new Timer(50, e -> {
            String line = lines.get(currentLine);

            if (currentChar < line.length()) {
                if (line.charAt(currentChar) != ' ') {
                    mainEngine.playSound("typing.wav");
                }

                storyText.append(String.valueOf(line.charAt(currentChar)));
                currentChar++;
            } else {
                storyText.append("\n\n");
                typingTimer.stop();
                isTyping = false;
                mainEngine.stopTypingSound();
            }
        });

        typingTimer.start();
    }
}
