package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Data {
    private char[] alphabet;
    private String[] tempWords;
    private ArrayList<String> wordBank = new ArrayList<>();
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
}
