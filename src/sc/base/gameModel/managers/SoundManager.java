package sc.base.gameModel.managers;

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
    private boolean sfxEnable = true;

    private File externalRoot = null;

    public SoundManager() {
        setBgmVolume(0.5f);
    }

    public void setExternalRoot(String dir) {
        externalRoot = new File(dir);
    }

    public void play(String path) {
        if (!sfxEnable) return;
        try {
            Clip clip = loadClip(path);
            setVolume(clip, 0.5F);

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

    public void setSfxVolume(boolean b) {
        sfxEnable = b;
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

    private void setVolume(Clip clip, float volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // 0.0 ~ 1.0 사이의 값을 데시벨로 변환
            // volume이 0일 때 음소거를 위해 -80.0f 정도로 처리
            float dB = (float) (Math.log(volume <= 0.0 ? 0.0001 : volume) / Math.log(10.0) * 20.0);

            // 제한 범위 체크 (보통 -80.0 ~ 6.0 사이)
            float min = gain.getMinimum();
            float max = gain.getMaximum();
            if (dB < min) dB = min;
            if (dB > max) dB = max;

            gain.setValue(dB);
        }
    }
}
