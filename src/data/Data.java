package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Data {
    private char[] alphabet;
    private String[] tempWords;
    private ArrayList<String> wordBank = new ArrayList<>();
    private Map<String, ArrayList<String>> categories = new LinkedHashMap<>();
    private String currentCategory = "";
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
        wordBank.clear();
        categories.clear();

        InputStream is = null;
        File csvFile = new File("src/data/Word Bank HangOSman.csv");
        if (csvFile.exists()) {
            try {
                is = new FileInputStream(csvFile);
            } catch (IOException e) {
                throw new RuntimeException("Failed to open CSV from project folder", e);
            }
        } else {
            is = Data.class.getResourceAsStream("/data/Word Bank HangOSman.csv");
            if (is == null) {
                is = Data.class.getResourceAsStream("Word Bank HangOSman.csv");
            }
        }

        if (is == null) {
            throw new RuntimeException("CSV file not found");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                String[] row = trimmed.split("[,;]", 2);
                if (row.length < 2) {
                    continue;
                }

                String category = row[0].trim();
                String categoryKey = category.toUpperCase(Locale.ROOT);
                ArrayList<String> wordsForCategory = new ArrayList<>();
                String wordList = row[1].trim();

                for (String rawWord : wordList.split(";")) {
                    String cleanedWord = rawWord.trim();
                    if (!cleanedWord.isEmpty()) {
                        String word = cleanedWord.toUpperCase(Locale.ROOT);
                        wordsForCategory.add(word);
                        wordBank.add(word);
                    }
                }

                if (!categoryKey.isEmpty() && !wordsForCategory.isEmpty()) {
                    categories.put(categoryKey, wordsForCategory);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Setters
    public void setLives(int lives){this.lives = lives;}
    public void setTempWords(String[] tempWords){this.tempWords = tempWords;}
    public void setCurrentCategory(String currentCategory) { this.currentCategory = currentCategory; }

    // Getters
    public int getLives(){return lives;}
    public ArrayList<String> getTempWords(){return wordBank;}
    public ArrayList<String> getCategories() {
        ArrayList<String> categoryList = new ArrayList<>();
        for (String key : categories.keySet()) {
            categoryList.add(key);
        }
        return categoryList;
    }
    public int getCategoryCount() { return categories.size(); }
    public String getCurrentCategory() { return currentCategory; }
    public ArrayList<String> getWordsForCategory(String category) {
        if (category == null) {
            return new ArrayList<>();
        }
        ArrayList<String> words = categories.get(category.toUpperCase(Locale.ROOT));
        return words == null ? new ArrayList<>() : new ArrayList<>(words);
    }

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
        if (correct <= 0) {
            percentage = 0;
        }

        int totalPossibleLives = correct * 6;

        // Prevent negative or impossible values (optional safety clamp)
        if (wrong < 0) {
            wrong = 0;
        }
        if (wrong > totalPossibleLives) {
            wrong = totalPossibleLives;
        }

        double guessRate = ((double)(totalPossibleLives - wrong) 
                            / totalPossibleLives) * 100;

        percentage = (int) Math.round(guessRate);
    }

    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public void setGuessedLetters(Set<Character> guessedLetters) {
        this.guessedLetters = guessedLetters;
    }
}
