package base;

import view.IGameModel;
import view.IGameModelDebug;
import view.IMouse;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import model.*;

public class GameModel implements IGameModel, IGameModelDebug {
    private int coin;
    private int level;

    private int tap = 1;
    boolean shiftPressed = false;
    boolean clicked = false;

    long PlayTick;
    int lastPlayTime;
    int totalPlayTime = 0;
    int sessionPlayTime = 0;

    final int FIRST_QUEST;
    final int SECOND_QUEST;

    int questGoalList[] = {0,10,5,1,10,30,60};
    int questRewardList[] = {0,50,60,500,70,150,400};
    String questsExplanation[] = {"empty","Collect 10 Ice_Basic", "Collect 5 Ice_Rare", "Collect 1 Ice_Legendary",
            "Play for 10 min", "Play for 30 min", "Play for 1 hours"};

    final int FIRST_QUEST_GOAL;
    final int SECOND_QUEST_GOAL;
    final int THIRD_QUEST_GOAL = 5000;

    boolean firstQuestCompleted = false;
    boolean secondQuestCompleted = false;
    boolean thirdQuestCompleted = false;

    boolean firstQuestReward = false;
    boolean secondQuestReward = false;
    boolean thirdQuestReward = false;

    final int currentProfileId;

    final int ICE_BASIC_VALUE = 1;
    final int ICE_RARE_VALUE = 5;
    final int ICE_LEGENDARY_VALUE = 10;

    final int ICE_COLLECT_BONUS = 5;

    int iceBasicCollectedCount = 0;
    int iceRareCollectedCount = 0;
    int intLegendaryCollectCount = 0;

    int lastIceBasicCollectCount;
    int lastIceRareCollectCount;
    int lastIceLegendaryCollectCount;

    private double ice_BasicCurrentSpawnChance = 0.07;
    ArrayList<Ice_Basic> iceBasics = new ArrayList<>();

    private double ice_RareCurrentSpawnChance = 0.05;
    ArrayList<Ice_Rare> iceRares = new ArrayList<>();

    private double ice_LegendaryCurrentSpawnChance = 0.001;
    ArrayList<Ice_Legendary> iceLegendaryes = new ArrayList<>();

    ArrayList<CoinEffect> coinEffects = new ArrayList<>();

    int tapBarPosition[] = {0,965,1154,1343,1532,1721,1721};

    IMouse iMouse;
    Random random = new Random();

    public GameModel(int profileId, IMouse iMouse) {
        this.currentProfileId = profileId;
        this.iMouse = iMouse;
        FIRST_QUEST = random.nextInt(6) + 1;
        SECOND_QUEST = random.nextInt(6) + 1;
        FIRST_QUEST_GOAL = questGoalList[FIRST_QUEST];
        SECOND_QUEST_GOAL = questGoalList[SECOND_QUEST];
    }

    public void update() {
        //basic
        if (Math.random() < ice_BasicCurrentSpawnChance) {
            iceBasics.add(new Ice_Basic());
        }
        //rare
        if (Math.random() < ice_RareCurrentSpawnChance) {
            iceRares.add(new Ice_Rare());
        }
        //legendary
        if (Math.random() < ice_LegendaryCurrentSpawnChance) {
            iceLegendaryes.add(new Ice_Legendary());
        }

        //basic
        for (int i = iceBasics.size() - 1; i >= 0; i--) {
            Ice_Basic iceBasic = iceBasics.get(i);
            iceBasic.update();
            if (iceBasic.shouldBeRemoved()) {
                iceBasics.remove(i);
                continue;
            }
            if (clicked) {
                if (iceBasic.shouldBeCollected(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY())) {
                    iceBasics.remove(i);
                    coinEffects.add(new CoinEffect(iMouse.getVirtualMouseX() + random.nextInt(5) - 10, iMouse.getVirtualMouseY() + random.nextInt(5) - 10, ICE_BASIC_VALUE));
                    iceBasicCollectedCount++;
                    if (thirdQuestCompleted) {
                        coin += ICE_BASIC_VALUE + ICE_COLLECT_BONUS;
                    } else {
                        coin += ICE_BASIC_VALUE;
                    }
                }
            }
        }

        //rare
        for (int i = iceRares.size() - 1; i >= 0; i--) {
            Ice_Rare iceRare = iceRares.get(i);
            iceRare.update();
            if (iceRare.shouldBeRemoved()) {
                iceRares.remove(i);
                continue;
            }
            if (clicked) {
                if (iceRare.shouldBeCollected(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY())) {
                    iceRares.remove(i);
                    coinEffects.add(new CoinEffect(iMouse.getVirtualMouseX() + random.nextInt(5) - 10, iMouse.getVirtualMouseY() + random.nextInt(5) - 10, ICE_RARE_VALUE));
                    iceRareCollectedCount++;
                    if (thirdQuestCompleted) {
                        coin += ICE_RARE_VALUE + ICE_COLLECT_BONUS;
                    } else {
                        coin += ICE_RARE_VALUE;
                    }
                }
            }
        }

        //legendary
        for (int i = iceLegendaryes.size() - 1; i >= 0; i--) {
            Ice_Legendary iceLegendary = iceLegendaryes.get(i);
            iceLegendary.update();
            if (iceLegendary.shouldBeRemoved()) {
                iceLegendaryes.remove(i);
                continue;
            }
            if (clicked) {
                if (iceLegendary.shouldBeCollected(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY())) {
                    iceLegendaryes.remove(i);
                    coinEffects.add(new CoinEffect(iMouse.getVirtualMouseX() + random.nextInt(5) - 10, iMouse.getVirtualMouseY() + random.nextInt(5) - 10, ICE_LEGENDARY_VALUE));
                    lastIceLegendaryCollectCount++;
                    if (thirdQuestCompleted) {
                        coin += ICE_LEGENDARY_VALUE + ICE_COLLECT_BONUS;
                    } else {
                        coin += ICE_LEGENDARY_VALUE;
                    }
                }
            }
        }

        for (int i = coinEffects.size() - 1; i >= 0; i--) {
            CoinEffect coinEffect = coinEffects.get(i);
            coinEffect.update();
            if (coinEffect.isExpired()) {
                coinEffects.remove(i);
            }
        }

        //other updates

        // play time
        PlayTick++;
        sessionPlayTime = (int)Math.ceil(PlayTick/3600f);
        totalPlayTime = lastPlayTime + sessionPlayTime;

        // quest check
        if (!firstQuestCompleted) {
            if (getFirstQuestProgress() >= FIRST_QUEST_GOAL) {
                firstQuestCompleted = true;
            }
        }

        if (!secondQuestCompleted) {
            if (getSecondQuestProgress() >= SECOND_QUEST_GOAL) {
                secondQuestCompleted = true;
            }
        }

        if (!thirdQuestCompleted) {
            if (lastIceBasicCollectCount + iceBasicCollectedCount >= THIRD_QUEST_GOAL) {
                thirdQuestCompleted = true;
            }
        }



        clicked = false;
    }

    public void renderIces(Graphics g) {
        for (Ice_Basic iceBasic : iceBasics) {
            iceBasic.draw(g);
        }

        for (Ice_Rare iceRare : iceRares) {
            iceRare.draw(g);
        }

        for (Ice_Legendary iceLegendary : iceLegendaryes) {
            iceLegendary.draw(g);
        }
    }

    public void renderCoinEffects(Graphics g) {
        for (CoinEffect coinEffect : coinEffects) {
            coinEffect.draw(g);
        }
    }

    public void clamRewardedQuest(int questNumber) {
        if (questNumber == 1) {
            if (firstQuestReward) return;

            if (firstQuestCompleted) {
                coin += getFirstQuestReward();
                coinEffects.add(new CoinEffect(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY(), getFirstQuestReward()));
                firstQuestReward = true;
            }

        } else if (questNumber == 2) {
            if (secondQuestReward) return;

            if (secondQuestCompleted) {
                coin += getSecondQuestReward();
                coinEffects.add(new CoinEffect(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY(), getSecondQuestReward()));
                secondQuestReward = true;
            }

        } else if (questNumber == 3) {
            if (thirdQuestReward) return;
            // 세 번째 퀘스트는 고정 보상이므로, 퀘스트 완료만 확인
            if (thirdQuestCompleted) {
                coin += 1000;
                coinEffects.add(new CoinEffect(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY(), 1000));
                thirdQuestReward = true;
            }
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

    private int getCurrentQuestProgress(int questId) {
        //흐아아아아아 개거지 같은거 아니 미친 왜 배열은 한번만 초기화 되냐고 씨빨놈들아 진짜 시발 이거 땜에 개고생했잖아 씨발놈들아
        switch (questId) {
            case 1: // Collect 10 Ice_Basic
                return iceBasicCollectedCount;
            case 2: // Collect 5 Ice_Rare
                return iceRareCollectedCount;
            case 3: // Collect 1 Ice_Legendary
                return intLegendaryCollectCount;
            case 4: // Play for 10 min (sessionPlayTime)
            case 5: // Play for 30 min (sessionPlayTime)
            case 6: // Play for 1 hours (sessionPlayTime)
                // 퀘스트 4, 5, 6은 모두 세션 플레이 시간을 목표로 합니다.
                return sessionPlayTime;
            default:
                return 0;
        }
    }

    public void clicked(boolean clicked) {
        this.clicked = clicked;
    }

    public void setShiftPressed(boolean pressed) {
        this.shiftPressed = pressed;
    }

    public void save(int currentProfileId) {
        Properties props = new Properties();

        props.setProperty("coin", String.valueOf(coin));
        props.setProperty("last", String.valueOf(lastPlayTime + sessionPlayTime));
        props.setProperty("lastIceBasicCollectCount", String.valueOf(lastIceBasicCollectCount + iceBasicCollectedCount));
        props.setProperty("lastIceRareCollectCount", String.valueOf(lastIceRareCollectCount + iceRareCollectedCount));
        props.setProperty("lastIceLegendaryCollectCount", String.valueOf(lastIceLegendaryCollectCount + intLegendaryCollectCount));
        props.setProperty("thirdQuestCompleted", String.valueOf(thirdQuestCompleted));

        String homeDir = System.getProperty("user.home");

        String fullPath1 = homeDir + File.separator + "IceDropSaveProfile1.properties";
        String fullPath2 = homeDir + File.separator + "IceDropSaveProfile2.properties";
        String fullPath3 = homeDir + File.separator + "IceDropSaveProfile3.properties";
        String paths[] = {"empty", fullPath1, fullPath2, fullPath3};

        try (FileOutputStream out = new FileOutputStream(paths[currentProfileId])) {

            props.store(out, "User Save Data - ID is not included");

        } catch (IOException e) {
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

            coin = Integer.parseInt(props.getProperty("coin", "7"));
            lastPlayTime = Integer.parseInt(props.getProperty("last", "0"));
            lastIceBasicCollectCount = Integer.parseInt(props.getProperty("lastIceBasicCollectCount", "0"));
            lastIceRareCollectCount = Integer.parseInt(props.getProperty("lastIceRareCollectCount", "0"));
            lastIceLegendaryCollectCount = Integer.parseInt(props.getProperty("lastIceLegendaryCollectCount", "0"));
            thirdQuestCompleted = Boolean.parseBoolean(props.getProperty("thirdQuestCompleted", "false"));

        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "저장파일 인식 실패");
        }
    }

    @Override public int getLevel() { return level; }
    @Override public int getCoin() { return coin; }
    @Override public int getLast() { return totalPlayTime; }
    @Override public int getSessionPlayTime() { return sessionPlayTime; }
    @Override public int getProfile() { return currentProfileId; }
    @Override public String getFirstQuestExplanation() { return questsExplanation[FIRST_QUEST]; }
    @Override public String getSecondQuestExplanation() { return questsExplanation[SECOND_QUEST]; }
    @Override public int getFirstQuestReward() { return questRewardList[FIRST_QUEST]; }
    @Override public int getSecondQuestReward() { return questRewardList[SECOND_QUEST]; }
    @Override public boolean firstQuestCompleted() { return firstQuestCompleted; }
    @Override public boolean secondQuestCompleted() { return secondQuestCompleted; }
    @Override public boolean thirdQuestCompleted() { return thirdQuestCompleted; }
    @Override public boolean firstQuestRewarded() { return firstQuestReward; }
    @Override public boolean secondQuestRewarded() { return secondQuestReward; }
    @Override public boolean thirdQuestRewarded() { return thirdQuestReward; }
    @Override public int getFirstQuestProgress() { return getCurrentQuestProgress(FIRST_QUEST); }
    @Override public int getSecondQuestProgress() { return getCurrentQuestProgress(SECOND_QUEST); }
    @Override public int getThirdQuestProgress() { return lastIceBasicCollectCount + iceBasicCollectedCount; }
    @Override public int getFirstQuestGoal() { return FIRST_QUEST_GOAL; }
    @Override public int getSecondQuestGoal() { return SECOND_QUEST_GOAL; }
    @Override public int getThirdQuestGoal() { return THIRD_QUEST_GOAL; }

    @Override public int getTap() { return tap; }
    @Override public int getTapBarPosition() { return tapBarPosition[tap]; }
    @Override public int getIce_BasicCount() { return iceBasics.size(); }
    @Override public int getIce_RareCount() { return iceRares.size(); }
    @Override public int getIce_LegendaryCount() { return iceLegendaryes.size(); }
    @Override public long getPlayTick() { return PlayTick; }
}
