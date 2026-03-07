package sc.base.gameModel;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    private final Map<String, Clip> bgmMap = new HashMap<>();
    private Clip currentBgm;

    private float bgmVolume = 0.0f;
    private float sfxVolume = 0.0f;

    private File externalRoot = null;

    public SoundManager() {
        loopBgm("698690__dantethehater__mmo-theme-bgm-music-synth-retro.wav");
    }

    public void setExternalRoot(String dir) {
        externalRoot = new File(dir);
    }

    public void play(String path) {
        try {
            Clip clip = loadClip(path);
            setVolume(clip, sfxVolume);

            clip.addLineListener(e -> {
                if (e.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

            clip.start();
        } catch (Exception e) {
            System.err.println("[Sound] play failed: " + path);
            e.printStackTrace();
        }
    }

    public void loopBgm(String path) {
        stopBgm();

        try {
            Clip clip = bgmMap.computeIfAbsent(path, p -> {
                try {
                    return loadClip(p);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            setVolume(clip, bgmVolume);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

            currentBgm = clip;
        } catch (Exception e) {
            System.err.println("[Sound] BGM failed: " + path);
            e.printStackTrace();
        }
    }

    public void stopBgm() {
        if (currentBgm != null) {
            currentBgm.stop();
            currentBgm.setFramePosition(0);
            currentBgm = null;
        }
    }

    public void setBgmVolume(float db) {
        bgmVolume = db;
        if (currentBgm != null) {
            setVolume(currentBgm, db);
        }
    }

    public void setSfxVolume(float db) {
        sfxVolume = db;
    }

    public void dispose() {
        stopBgm();
        for (Clip clip : bgmMap.values()) clip.close();
        bgmMap.clear();
    }

    private Clip loadClip(String path)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        AudioInputStream ais;

        if (externalRoot != null) {
            File file = new File(externalRoot, path);
            if (file.exists()) {
                ais = AudioSystem.getAudioInputStream(file);
            } else {
                throw new IOException("External sound not found: " + file);
            }
        }
        else {
            URL url = SoundManager.class.getResource("/" + path);
            if (url == null) {
                throw new IOException("Resource sound not found: " + path);
            }
            ais = AudioSystem.getAudioInputStream(url);
        }

        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        return clip;
    }

    private void setVolume(Clip clip, float db) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gain.setValue(db);
        }
    }
}
