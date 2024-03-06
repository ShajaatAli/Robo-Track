package com.mycompany.a4;

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
            System.out.println("Error loading sound file: " + fileName);
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
