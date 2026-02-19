package engine;

import data.Data;
import graphics.MainGUI;

// SystemOut starting in engine starts with "@"

public class MainEngine {
    private MainGUI gui;
    private Data data;
    private AudioManager audioManager;
    private boolean firstRound;
    public boolean isWordGuessed;
    public String wordToGuess;


    public MainEngine(){
        this.data = new Data();
        this.audioManager = new AudioManager();
        start();
    }

    public void start(){
        System.out.println("@ Engine Started");
        firstRound = true;
        isWordGuessed = false;
        getRandomRedactedWord();
    }

    public void gameOver(){
        data.setCorrect(0);
        data.setWrong(0);
        data.setPercentage();
        firstRound = true;
        playSound("error4.wav");
        gui.changeScreen("ScreenGameOver");
        gui.openWindows("Clear All");
    }

    public void wordGuessed(){
        data.setCorrect(data.getCorrect()+1);
        data.setPercentage();
        isWordGuessed = true;
        gui.openWindows("Leak");
        System.out.println("WORD GUESSED");
    }

    private boolean isWordFullyGuessed() {
        for (char c : wordToGuess.toUpperCase().toCharArray()) {
            if (!data.getGuessedLetters().contains(c)) {
                return false;
            }
        }
        return true;
    }

    public void decoderButtonPressed(char key){
        System.out.println("@ Decoder Button Pressed: " + key);
        key = Character.toUpperCase(key);
        data.getGuessedLetters().add(key);

        if (wordToGuess.toUpperCase().contains(String.valueOf(key))) {
            playSound("click");
            gui.revealLetterInDecoder(wordToGuess, key);

            if (isWordFullyGuessed()) {
                wordGuessed();
            }

        } else {
            playSound("errorButton.wav");
            data.setLives(data.getLives() - 1);
            data.setWrong(data.getWrong() + 1);
            gui.updateStickmanStatus(data.getLives());

            if (data.getLives() <= 0){
                gameOver();
            }
        }
        gui.updateStatistics();
    }

    public void desktopIconsPressed(String iconName){
        System.out.println("@ Desktop Icon Pressed: " + iconName);
        if (iconName.equals("Play")){
            if(firstRound){
                newRound();
                firstRound = false;
            }
        }
        gui.openWindows(iconName);
    }

    public void newRound(){
        System.out.println("@ Refreshing Redacted Word...");
        getRandomRedactedWord();
        isWordGuessed = false;
        data.setLives(6);
        data.getGuessedLetters().clear();
        gui.renderNewRedactedWord(wordToGuess);
        gui.openWindows("Clear Leak");
        gui.updateStickmanStatus(data.getLives());
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

    public Data getData(){
        return data;
    }

    public void playSound(String soundName) {
        if (soundName.equals("click")) {
            audioManager.playButtonClick();
        } 
        else if (soundName.equals("typing.wav")) {
            audioManager.playTyping();
        }
        else if (soundName.equals("error4.wav")) {
            audioManager.playErrorSound();
        }
        else {
            audioManager.playSound(soundName);
        }
    }

    public void stopTypingSound() {
        audioManager.stopTyping();
    }

    public void stopErrorSound() {
        audioManager.stopErrorSound();
    }

    public void playMusic(String filename) {
        audioManager.playMusic(filename);
    }

    public void stopMusic() {
        audioManager.stopMusic();
    }
}