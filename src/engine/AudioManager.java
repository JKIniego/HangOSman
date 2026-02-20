package engine;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class AudioManager {
    private static final String SOUNDS_PATH = "src/assets/sounds/";

    // Separate volume levels
    private float musicVolume = 0.5f;
    private float sfxVolume = 0.5f;

    // Preloaded SFX clips
    private Clip buttonClickClip;
    private Clip typingClip;
    private Clip errorClip;

    // Current music clip
    private Clip currentMusicClip;

    public AudioManager() {
        preloadSounds();
    }

    private void preloadSounds() {
        try {
            buttonClickClip = loadClip("button.wav");
            typingClip = loadClip("typing.wav");
            errorClip = loadClip("error4.wav");

            applyVolume(buttonClickClip, sfxVolume);
            applyVolume(typingClip, sfxVolume);
            applyVolume(errorClip, sfxVolume);
        } catch (Exception e) {
            System.err.println("@ Error preloading sounds: " + e.getMessage());
        }
    }

    // -------------------------
    // Volume Control
    // -------------------------

    public void setMusicVolume(float volume) {
        musicVolume = clamp(volume);
        applyVolume(currentMusicClip, musicVolume);
    }

    public void setSFXVolume(float volume) {
        sfxVolume = clamp(volume);
        applyVolume(buttonClickClip, sfxVolume);
        applyVolume(typingClip, sfxVolume);
        applyVolume(errorClip, sfxVolume);
    }

    public float getMusicVolume() { return musicVolume; }
    public float getSFXVolume()   { return sfxVolume; }

    // -------------------------
    // SFX Methods
    // -------------------------

    public void playButtonClick() { playClip(buttonClickClip); }
    public void playTyping()      { playClip(typingClip); }
    public void playErrorSound()  { playClip(errorClip); }

    public void stopTyping() { stopClip(typingClip); }
    public void stopErrorSound() { stopClip(errorClip); }

    // One-off sound (not preloaded)
    public void playSound(String filename) {
        try {
            Clip clip = loadClip(filename);
            applyVolume(clip, sfxVolume);
            playClip(clip);
        } catch (Exception e) {
            System.err.println("@ Error playing sound " + filename + ": " + e.getMessage());
        }
    }

    // -------------------------
    // Music Methods
    // -------------------------

    public void playMusic(String filename) {
        stopMusic();
        try {
            currentMusicClip = loadClip(filename);
            applyVolume(currentMusicClip, musicVolume);
            currentMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.err.println("@ Error playing music " + filename + ": " + e.getMessage());
        }
    }

    public void stopMusic() { stopClip(currentMusicClip); }

    public void pauseMusic() { stopClip(currentMusicClip); }

    public void resumeMusic() {
        if (currentMusicClip != null && !currentMusicClip.isRunning()) {
            currentMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    // -------------------------
    // Helpers
    // -------------------------

    private static Clip loadClip(String filename)
            throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File soundFile = new File(SOUNDS_PATH + filename);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        return clip;
    }

    private static void playClip(Clip clip) {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    private static void stopClip(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    private static void applyVolume(Clip clip, float volume) {
        if (clip == null) return;
        if (!clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) return;

        FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        // Convert linear scale → decibels: dB = 20 * log10(volume)
        // Guard against log(0) with a tiny floor value
        float dB = (volume == 0f) ? gain.getMinimum() : 20f * (float) Math.log10(volume);
        gain.setValue(Math.max(gain.getMinimum(), Math.min(gain.getMaximum(), dB)));
    }

    private static float clamp(float value) {
        return Math.max(0f, Math.min(1f, value));
    }
}