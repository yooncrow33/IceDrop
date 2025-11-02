import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class GameModel implements IGameModel{
    private int coin;
    private int level;
    private int tap = 1;
    boolean shiftPressed = false;
    final int currentProfileId;

    int a;
    int b;
    int c;

    int tapBarPosition[] = {0,965,1154,1343,1532,1721,1721};

    public GameModel(int profileId) {
        this.currentProfileId = profileId;
    }

    public void update() {
        a++;
        b++;
        c++;
    }

    public void tapMoveRight(boolean shiftPressed) {
        if (shiftPressed) {
            if (tap != 6) {
                tap = 6;
                return;
            }
        }
        if (tap >= 5) {
            tap = 1;
        } else {
            tap++;
        }
    }

    public void tapMoveLeft() {
        if (tap <= 1) {
            tap = 5;
        } else {
            tap--;
        }
    }

    public void setShiftPressed(boolean pressed) {
        this.shiftPressed = pressed;
    }

    public void save(int currentProfileId) {
        Properties props = new Properties();

        //props.setProperty("test", String.valueOf(test));

        String homeDir = System.getProperty("user.home");

        String fullPath1 = homeDir + File.separator + "IceDropSaveProfile1.properties";
        String fullPath2 = homeDir + File.separator + "IceDropSaveProfile2.properties";
        String fullPath3 = homeDir + File.separator + "IceDropSaveProfile3.properties";

        String paths[] = {"empty", fullPath1, fullPath2, fullPath3};

        // 4. 파일 저장 시도
        try (FileOutputStream out = new FileOutputStream(paths[currentProfileId])) {
            props.store(out, "User Save Data - ID is not included");

        } catch (IOException e) {
            // 저장 실패 시 사용자에게 알림
            JOptionPane.showMessageDialog(null, "저장 실패: " + e.getMessage() + "\n경로: " + paths[currentProfileId]);
        }

    }

    public void load(int currentProfileId) {
        Properties props = new Properties();
        String homeDir = System.getProperty("user.home");

        String fullPath1 = homeDir + File.separator + "IceDropSaveProfile1.properties";
        String fullPath2 = homeDir + File.separator + "IceDropSaveProfile2.properties";
        String fullPath3 = homeDir + File.separator + "IceDropSaveProfile3.properties";

        String paths[] = {"empty", fullPath1, fullPath2, fullPath3};


        try (FileInputStream in = new FileInputStream(paths[currentProfileId])) {
            props.load(in);

            //test = Integer.parseInt(props.getProperty("test", "1"));

        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "저장파일 인식 실패");
        }
    }

    @Override public int getLevel() { return coin; }
    @Override public int getCoin() { return level; }
    @Override public int getTap() { return tap; }
    @Override public int getTapBarPosition() { return tapBarPosition[tap]; }
}
