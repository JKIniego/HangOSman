package engine;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioManager {
    private static final String SOUNDS_PATH = "src/assets/sounds/";
    
    // Preloaded clips
    private static Clip buttonClickClip;
    private static Clip currentMusicClip;
    private static Clip typingClip;
    private static Clip errorClip;
    
    public AudioManager() {
        preloadSounds();
    }
    
    // Preload frequently used sounds to reduce latency
    private void preloadSounds() {
        try {
            buttonClickClip = loadClip("button.wav");
            typingClip = loadClip("typing.wav");
            errorClip = loadClip("error4.wav");
        } catch (Exception e) {
            System.err.println("@ Error preloading sounds: " + e.getMessage());
        }
    }
    
    public void playButtonClick() {
        playClip(buttonClickClip);
    }
    
    public void playTyping() {
        playClip(typingClip);
    }

    public void playErrorSound() {
        playClip(errorClip);
    }

    public void stopErrorSound() {
        if (errorClip != null && errorClip.isRunning()) {
            errorClip.stop();
        }
    }

    // General method to play sound
    public void playSound(String filename) {
        try {
            Clip clip = loadClip(filename);
            playClip(clip);
        } catch (Exception e) {
            System.err.println("@ Error playing sound " + filename + ": " + e.getMessage());
        }
    }
    
    public void stopTyping() {
        if (typingClip != null && typingClip.isRunning()) {
            typingClip.stop();
        }
    }

    // Music control methods
    public void playMusic(String filename) {
        stopMusic();
        try {
            currentMusicClip = loadClip(filename);
            currentMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.err.println("@ Error playing music " + filename + ": " + e.getMessage());
        }
    }
    
    // Stop currently playing music
    public void stopMusic() {
        if (currentMusicClip != null && currentMusicClip.isRunning()) {
            currentMusicClip.stop();
        }
    }
    
    // Helper methods
    private static Clip loadClip(String filename) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File soundFile = new File(SOUNDS_PATH + filename);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        return clip;
    }
    
    // Play a clip from the beginning
    private static void playClip(Clip clip) {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
}
