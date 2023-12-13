package dev.jille.barrowsnothingsound;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sound.sampled.*;
import java.io.*;
import java.util.concurrent.ScheduledExecutorService;

@Singleton
@Slf4j
public class SoundManager {
    @Inject
    private ScheduledExecutorService executor;
    private Clip clip = null;
    private static final long CLIP_MTIME_UNLOADED = -2;
    private long lastClipMTime = CLIP_MTIME_UNLOADED;
    public void playSound()
    {
        executor.execute(this::playClip);
    }
    private void playClip()
    {
        long currentMTime = System.currentTimeMillis();
        if (clip == null || currentMTime != lastClipMTime || !clip.isOpen()) {
            if (clip != null && clip.isOpen()) {
                clip.close();
            }

            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                lastClipMTime = CLIP_MTIME_UNLOADED;
                log.warn("Failed to get clip barrows nothing sound", e);
                return;
            }

            lastClipMTime = currentMTime;
            if (!loadClip()) {
                return;
            }
        }

        clip.loop(0);
    }

    private boolean loadClip() {

        try (InputStream stream = new BufferedInputStream(getSoundStream("nothing.wav"));
             AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(stream)) {
            clip.open(audioInputStream);
            return true;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            log.warn("Failed to load Barrows Nothing Sound", e);
        }
        return false;
    }

    public static InputStream getSoundStream(String resourceName) throws FileNotFoundException {
        return SoundManager.class.getClassLoader().getResourceAsStream(resourceName);
    }
}
