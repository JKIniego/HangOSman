package data;

public class Data {
    private char[] alphabet;
    private String[] tempWords;
    private int lives;

    // Temporary only for GUI testing, will be removed when actual backend is implemented
    public Data(){
        // Alphabet not used anywhere yet
        alphabet = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        // Temporary words for testing GUI
        tempWords = new String[]{"BRAINROT", "TUNG SAHUR", "SIX SEVEN", "HELLO", "JHUNCOCK" };

        // Lives
        lives = 6;
    }

    // Setters
    public void setLives(int lives){this.lives = lives;}
    public void setTempWords(String[] tempWords){this.tempWords = tempWords;}

    // Getters
    public int getLives(){return lives;}
    public String[] getTempWords(){return tempWords;}
}
