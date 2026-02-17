package engine;

import data.Data;
import graphics.MainGUI;

// SystemOut starting in engine starts with "@"

public class MainEngine {
    private MainGUI gui;
    private Data data;
    private AudioManager audioManager;
    private boolean firstRound;
    public String wordToGuess;


    public MainEngine(){
        this.data = new Data();
        this.audioManager = new AudioManager();
        start();
    }

    public void start(){
        System.out.println("@ Engine Started");
        firstRound = true;
        getRandomRedactedWord();
    }

    public void gameOver(){
        data.setCorrect(0);
        data.setWrong(0);
        data.setPercentage();
        firstRound = true;
        gui.changeScreen("ScreenGameOver");
        gui.openWindows("Clear All");
    }

    public void wordGuessed(){
        data.setCorrect(data.getCorrect()+1);
        data.setPercentage();
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
            gui.revealLetterInDecoder(wordToGuess, key);

            if (isWordFullyGuessed()) {
                wordGuessed();
            }

        } else {
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
        data.setLives(6);
        data.getGuessedLetters().clear();
        gui.renderNewRedactedWord(wordToGuess);
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