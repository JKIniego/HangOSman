package engine;

import data.Data;
import graphics.MainGUI;
import java.util.ArrayList;

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
        playSound("correctWord.wav");
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
        gui.updateCategoryLabel(data.getCurrentCategory());
        gui.renderNewRedactedWord(wordToGuess);
        gui.openWindows("Clear Leak");
        gui.updateStickmanStatus(data.getLives());
    }

    public void startButtonPressed(String screenName){
        if (screenName.equals("ScreenDesktop")){
            gui.changeScreen(screenName);
            playMusic("mainBgMusic.wav");
        } else if (screenName.equals("ScreenStart")){
            gui.changeScreen(screenName);
            playSound("startup.wav");
        } else if (screenName.equals("Close Window")){
            gui.openWindows(screenName);
        }
        System.out.println("@ Start Button Pressed");
    }

    public void getRandomRedactedWord(){
        ArrayList<String> categories = data.getCategories();
        if (categories.isEmpty()) {
            wordToGuess = "";
            data.setCurrentCategory("Unknown");
            return;
        }

        String selectedCategory = categories.get((int)(Math.random() * categories.size()));
        ArrayList<String> wordsForCategory = data.getWordsForCategory(selectedCategory);

        if (wordsForCategory.isEmpty()) {
            wordToGuess = "";
            data.setCurrentCategory(selectedCategory);
            return;
        }

        int randomIndex = (int)(Math.random() * wordsForCategory.size());
        wordToGuess = wordsForCategory.get(randomIndex);
        data.setCurrentCategory(selectedCategory);

        if (gui != null) {
            gui.updateCategoryLabel(selectedCategory);
        }

        System.out.println("@ Random Redacted Word: " + wordToGuess + " [" + selectedCategory + "]");
    }

    public void setGUI(MainGUI gui) {
        this.gui = gui;
    }
    public MainGUI getGUI() {
        return gui;
    }

    public Data getData(){
        return data;
    }

    public AudioManager getAudioManager(){
        return audioManager;
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