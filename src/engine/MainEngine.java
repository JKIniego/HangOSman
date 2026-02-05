package engine;

import data.Data;
import graphics.MainGUI;

// SystemOut starting in engine starts with "@"

public class MainEngine {
    private MainGUI gui;
    private Data data;
    public String randomWord;

    public MainEngine(){
        this.data = new Data();
        start();
    }

    public void start(){
        System.out.println("@ Engine Started");
        getRandomRedactedWord();
    }

    public void decoderButtonPressed(char key){
        System.out.println("@ Decoder Button Pressed: " + key);
        gui.revealLetterInDecoder(randomWord, key);
    }

    public void desktopIconsPressed(String iconName){
        System.out.println("@ Desktop Icon Pressed: " + iconName);
        if (iconName.equals("Play")){
            // For now, just refreshes the redacted word, but it shall open the window
            // gui.OpenPlay(); Future method to be implemented
            System.out.println("@ Refreshing Redacted Word...");
            getRandomRedactedWord();
            gui.newRedactedWord(randomWord);
        }
    }

    public void startButtonPressed(String screenName){
        if (screenName.equals("ScreenDesktop")){
            gui.changeScreen(screenName);
        } else if (screenName.equals("ScreenStart")){
            gui.changeScreen(screenName);
        } else if (screenName.equals("ScreenGameOver")){
            gui.changeScreen(screenName);
        }

        // Future functionality for real start menu can be added here, 
        // for now it just changes to the desktop/end screen
        System.out.println("@ Start Button Pressed");
    }

    public void getRandomRedactedWord(){
        int randomIndex = (int)(Math.random() * data.tempWords.length);
        randomWord = data.tempWords[randomIndex];
        System.out.println("@ Random Redacted Word: " + randomWord);
    }

    public void setGUI(MainGUI gui) {
        this.gui = gui;
    }
}