package com.mycompany.a3;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

import java.io.*;

public class BGSound implements Runnable {
    private Media media;
    private boolean playing;
    private boolean isMuted;

    public BGSound(String fileName) {
        try {
            InputStream stream = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
            media = MediaManager.createMedia(stream, "audio/wav", this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (media != null && !playing && !isMuted) {
            media.setVolume(50);
            media.play();
            playing = true;
        }
    }

    public void stop() {
        if (media != null && playing) {
            ((BGSound) media).stop();
            playing = false;
        }
    }

    public void pause() {
        if (media != null && playing) {
            media.pause();
            playing = false;
        }
    }

    public void resume() {
        if (media != null && !playing && !isMuted) {
            media.play();
            playing = true;
        }
    }

    public void run() {
        if (media != null && !media.isPlaying() && !isMuted) {
            media.setTime(0);
            media.play();
        }
    }

    public void setMute(boolean b) {
        isMuted = b;
        if (media != null) {
            media.setVolume(isMuted ? 0 : 50);
            if (isMuted && playing) {
                media.pause();
                playing = false;
            } else if (!isMuted && !playing) {
                media.play();
                playing = true;
            }
        }
    }
}
