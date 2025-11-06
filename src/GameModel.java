import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class GameModel implements IGameModel{
    private int coin;
    private int level;
    private int tap = 1;
    boolean shiftPressed = false;
    final int currentProfileId;


    private double ice_BasicCurrentSpawnChance = 0.07;
    ArrayList<Ice_Basic> iceBasics = new ArrayList<>();

    private double ice_RareCurrentSpawnChance = 0.05;
    ArrayList<Ice_Rare> iceRares = new ArrayList<>();

    private double ice_LegendaryCurrentSpawnChance = 0.001;
    ArrayList<Ice_Legendary> iceLegendaries = new ArrayList<>();

    int tapBarPosition[] = {0,965,1154,1343,1532,1721,1721};

    public GameModel(int profileId) {
        this.currentProfileId = profileId;
    }

    Random random = new Random();

    public void update() {

        //basic
        if (Math.random() < ice_BasicCurrentSpawnChance) {
            iceBasics.add(new Ice_Basic());
        }
        for (int i = 0; i < iceBasics.size(); i++) {
            Ice_Basic iceBasic = iceBasics.get(i);
            iceBasic.update();
            if (iceBasic.shouldBeRemoved()) {
                iceBasics.remove(i);
                i--;
            }
        }

        //rare
        if (Math.random() < ice_RareCurrentSpawnChance) {
            iceRares.add(new Ice_Rare());
        }
        for (int i = 0; i < iceRares.size(); i++) {
            Ice_Rare iceRare = iceRares.get(i);
            iceRare.update();
            if (iceRare.shouldBeRemoved()) {
                iceRares.remove(i);
                i--;
            }
        }

        //legendary
        if (Math.random() < ice_LegendaryCurrentSpawnChance) {
            iceLegendaries.add(new Ice_Legendary());
        }
        for (int i = 0; i < iceLegendaries.size(); i++) {
            Ice_Legendary iceLegendary = iceLegendaries.get(i);
            iceLegendary.update();
            if (iceLegendary.shouldBeRemoved()) {
                iceLegendaries.remove(i);
                i--;
            }
        }
    }

    public void renderIces(Graphics g) {
        for (Ice_Basic iceBasic : iceBasics) {
            iceBasic.draw(g);
        }

        for (Ice_Rare iceRare : iceRares) {
            iceRare.draw(g);
        }

        for (Ice_Legendary iceLegendary : iceLegendaries) {
            iceLegendary.draw(g);
        }
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
    @Override public int getIce_BasicCount() { return iceBasics.size(); }
    @Override public int getIce_RareCount() { return iceRares.size(); }
    @Override public int getIce_LegendaryCount() { return iceLegendaries.size(); }
}
