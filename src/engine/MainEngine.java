package engine;

import data.Data;
import graphics.MainGUI;

// SystemOut starting in engine starts with "@"

public class MainEngine {
    private MainGUI gui;
    private Data data;
    private AudioManager audioManager;
    public String wordToGuess;

    public MainEngine(){
        this.data = new Data();
        this.audioManager = new AudioManager();
        start();
    }

    public void start(){
        System.out.println("@ Engine Started");
        getRandomRedactedWord();
    }

    public void gameOver(){
        gui.changeScreen("ScreenGameOver");
        gui.openWindows("Clear All");
    }

    public void decoderButtonPressed(char key){
        System.out.println("@ Decoder Button Pressed: " + key);
        if (wordToGuess.toUpperCase().contains(String.valueOf(key).toUpperCase())) {
            gui.revealLetterInDecoder(wordToGuess, key);
        } else {
            data.setLives(data.getLives() - 1);
            gui.updateStickmanStatus(data.getLives());
            if (data.getLives() <= 0){
                gameOver();
            }
        }
    }

    public void desktopIconsPressed(String iconName){
        System.out.println("@ Desktop Icon Pressed: " + iconName);
        if (iconName.equals("Play")){
            // For now, just refreshes the redacted word, but it shall open the window
            System.out.println("@ Refreshing Redacted Word...");
            getRandomRedactedWord();
            data.setLives(6);
            gui.renderNewRedactedWord(wordToGuess);
            gui.updateStickmanStatus(data.getLives());
        }
        gui.openWindows(iconName);
    }

    public void startButtonPressed(String screenName){
        if (screenName.equals("ScreenDesktop")){
            gui.changeScreen(screenName);
        } else if (screenName.equals("ScreenStart")){
            gui.changeScreen(screenName);
        }

        // Future functionality for real start menu can be added here, 
        // for now it does nothing on desktop
        System.out.println("@ Start Button Pressed");
    }

    public void getRandomRedactedWord(){
        int randomIndex = (int)(Math.random() * data.getTempWords().size());
        wordToGuess = data.getTempWords().get(randomIndex);
        System.out.println("@ Random Redacted Word: " + wordToGuess);
    }

    public void setGUI(MainGUI gui) {
        this.gui = gui;
    }

    public void playSound(String soundName) {
        if (soundName.equals("click")) {
            audioManager.playButtonClick();
        } else {
            audioManager.playSound(soundName);
        }
    }

    public void playMusic(String filename) {
        audioManager.playMusic(filename);
    }

    public void stopMusic() {
        audioManager.stopMusic();
    }
}