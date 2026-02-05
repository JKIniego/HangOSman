package data;

public class Data {
    public char[] alphabet;
    public String[] tempWords;

    // Temporary only for GUI testing, will be removed when actual backend is implemented
    public Data(){
        // Alphabet not used anywhere yet
        alphabet = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        // Temporary words for testing GUI
        tempWords = new String[]{"BRAINROT", "TUNG SAHUR", "SIX SEVEN", "HELLO", "JHUNCOCK" };
    }
}
