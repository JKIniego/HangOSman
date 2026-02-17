package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Data {
    private char[] alphabet;
    private String[] tempWords;
    private ArrayList<String> wordBank = new ArrayList<>();
    private Set<Character> guessedLetters = new HashSet<>();
    private int correct, wrong, percentage;
    private int lives;

    // Temporary only for GUI testing, will be removed when actual backend is implemented
    public Data(){
        // Alphabet not used anywhere yet
        alphabet = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        // Temporary words for testing GUI
        tempWords = new String[]{"BRAINROT", "TUNG SAHUR", "SIX SEVEN", "HELLO", "JHUNCOCK" };

        extractWords();

        // Lives
        lives = 6;

        //Stats counter
        correct = 0;
        wrong = 0;
        percentage = 0;
    }

    public void extractWords() {
        System.out.println("Importing from CSV...");
        InputStream is = Data.class.getResourceAsStream("Word Bank HangOSman.csv");

        if (is == null) {
            throw new RuntimeException("CSV file not found");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;

            while ((line = reader.readLine()) != null) {
                wordBank.add(line.toUpperCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Setters
    public void setLives(int lives){this.lives = lives;}
    public void setTempWords(String[] tempWords){this.tempWords = tempWords;}

    // Getters
    public int getLives(){return lives;}
    public ArrayList<String> getTempWords(){return wordBank;}

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage() {
        //this.percentage = ((correct * 6)-wrong)/correct*(correct*6);
    }

    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public void setGuessedLetters(Set<Character> guessedLetters) {
        this.guessedLetters = guessedLetters;
    }
}
