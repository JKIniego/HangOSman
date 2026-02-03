package engine;

import graphics.MainGUI;

public class MainEngine {
    private MainGUI gui;

    public MainEngine(){

    }

    public void guiChangeScreen(String screenName){
        gui.changeScreen(screenName);
    }

    public void setGUI(MainGUI gui) {
        this.gui = gui;
    }
}