package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
    private AudioInputStream captureSound;
    private Clip captureClip;
    private AudioInputStream moveSound;
    private Clip moveClip;

    public SoundPlayer() {
        try {
            captureSound = AudioSystem.getAudioInputStream(new File("res/audio/capture.wav"));
            captureClip = AudioSystem.getClip();
            if(!captureClip.isOpen()){
                captureClip.open(captureSound);
            }
            moveSound = AudioSystem.getAudioInputStream(new File("res/audio/move-self.wav"));
            moveClip = AudioSystem.getClip();
            if(!moveClip.isOpen()){
                moveClip.open(moveSound);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playCaptureSound() {
        captureClip.setFramePosition(0);
        captureClip.start();
    }

    public void playMoveSound() {
        moveClip.setFramePosition(0);
        moveClip.start();
    }
}
