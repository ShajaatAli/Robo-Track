package com.mycompany.a3;

import java.io.*;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class Sound {
    private Media media;

    public Sound(String fileName) {
        try {
            InputStream stream = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
            media = MediaManager.createMedia(stream, "audio/wav");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (media != null) {
            media.play();
        }
    }

    public void stop() {
        if (media != null) {
            ((Sound) media).stop();
        }
    }
}
