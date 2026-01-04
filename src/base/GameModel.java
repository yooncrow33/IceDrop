package base;

import model.effects.IntegerEffect;
import model.effects.StringEffect;
import model.ice.IceBasic;
import model.ice.IceLegendary;
import model.ice.IceRare;
import view.*;
import view.iGameModel.IGameModel;
import view.iGameModel.IGameModelDebug;
import view.iGameModel.IGameModelQuest;
import view.iGameModel.IGameModelShop;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class GameModel implements IGameModel, IGameModelDebug, IGameModelQuest, IGameModelShop {
    private int coin;
    private int level;

    private int tap = 1;
    public boolean shiftPressed = false;
    boolean clicked = false;

    long playTick;
    int lastPlayTime;
    int totalPlayTime = 0;
    int sessionPlayTime = 0;


    //quest
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
    int iceLegendaryCollectCount = 0;

    int lastIceBasicCollectCount;
    int lastIceRareCollectCount;
    int lastIceLegendaryCollectCount;

    //shop
    int iceBasicRushItemCount = 0;
    int iceRareRushItemCount = 0;
    int iceLegendaryRushItemCount = 0;

    boolean iceBasicRush = false;
    boolean iceRareRush = false;
    boolean iceLegendaryRush = false;

    int iceBasicRushEndTick;
    int iceRareRushEndTick;
    int iceLegendaryRushEndTick;
    int iceVacuumEndTick;

    int iceBasicRushCoolTime = 0;
    int iceRareRushCoolTime = 0;
    int iceLegendaryRushCoolTime = 0;
    int iceVacuumCoolTime = 0;

    int iceAutoCollectLevel = 0;
    double iceAutoCollectChance[] = {0.0001,0.001,0.005,0.01,0.1};
    int iceVacuumCount = 0;

    boolean iceVacuumActive = false;

    final int ICE_BASIC_RUSH_ITEM_COST = 100;
    final int ICE_RARE_RUSH_ITEM_COST = 400;
    final int ICE_LEGENDARY_RUSH_ITEM_COST = 1000;

    final int ICE_AUTO_COLLECT_UPGRADE_COST[] = {20,300,600,2000,6000};
    final int ICE_VACUUM_ITEM_COST = 700;

    final int ICE_BASIC_RUSH_ENABLE_TICK = 1800; // 30 seconds
    final int ICE_RARE_RUSH_ENABLE_TICK = 3600; // 1 minute
    final int ICE_LEGENDARY_RUSH_ENABLE_TICK = 3600;
    final int ICE_VACUUM_ENABLE_TICK = 90; // 1.5 second

    final int ICE_BASIC_RUSH_COOL_DOWN_TICK = 1800; // 2 minutes
    final int ICE_RARE_RUSH_COOL_DOWN_TICK = 10800; // 3 minutes
    final int ICE_LEGENDARY_RUSH_COOL_DOWN_TICK = 18000; // 5 minutes
    final int ICE_VACUUM_COOL_DOWN_TICK = 72000; // just a 10 minute 내것이 되는 시간

    //skill points level
    int iceBasicSpawnChanceLevel = 1;
    int iceRareSpawnChanceLevel = 1;
    int iceLegendarySpawnChanceLevel = 1;

    //ice
    private final double iceBasicCurrentSpawnChanceList[] = {1,0.03,0.04,0.05,0.06};
    ArrayList<IceBasic> iceBasics = new ArrayList<>();

    private final double iceRareCurrentSpawnChanceList[] = {1,0.008,0.015,0.025,0.035};
    ArrayList<IceRare> iceRares = new ArrayList<>();

    private final double iceLegendaryCurrentSpawnChanceList[] = {1,0.001,0.002,0.005,0.01};
    ArrayList<IceLegendary> iceLegendaryes = new ArrayList<>();

    ArrayList<IntegerEffect> integerEffects = new ArrayList<>();
    ArrayList<StringEffect> stringEffects = new ArrayList<>();

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

    public void update(double dt) {
        if (!iceVacuumActive) {
            //basic
            if (iceBasicRush) {
                iceBasics.add(new IceBasic());
            } else {
                if (Math.random() < iceBasicCurrentSpawnChanceList[iceBasicSpawnChanceLevel]) {
                    iceBasics.add(new IceBasic());
                }
            }
            //rare
            if (iceRareRush) {
                iceRares.add(new IceRare());
            } else {
                if (Math.random() < iceRareCurrentSpawnChanceList[iceRareSpawnChanceLevel]) {
                    iceRares.add(new IceRare());
                }
            }
            //legendary
            if (iceLegendaryRush) {
                iceLegendaryes.add(new IceLegendary());
            } else {
                if (Math.random() < iceLegendaryCurrentSpawnChanceList[iceLegendarySpawnChanceLevel]) {
                    iceLegendaryes.add(new IceLegendary());
                }
            }
        }
        //basic
        for (int i = iceBasics.size() - 1; i >= 0; i--) {
            IceBasic iceBasic = iceBasics.get(i);
            iceBasic.update(dt);
            if (iceBasic.shouldBeRemoved()) {
                iceBasics.remove(i);
                continue;
            }
            if (clicked) {
                if (iceBasic.shouldBeCollected(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY())) {
                    iceBasics.remove(i);
                    integerEffects.add(new IntegerEffect(iMouse.getVirtualMouseX() + random.nextInt(5) - 10, iMouse.getVirtualMouseY() + random.nextInt(5) - 10, ICE_BASIC_VALUE));
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
            IceRare iceRare = iceRares.get(i);
            iceRare.update(dt);
            if (iceRare.shouldBeRemoved()) {
                iceRares.remove(i);
                continue;
            }
            if (clicked) {
                if (iceRare.shouldBeCollected(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY())) {
                    iceRares.remove(i);
                    integerEffects.add(new IntegerEffect(iMouse.getVirtualMouseX() + random.nextInt(5) - 10, iMouse.getVirtualMouseY() + random.nextInt(5) - 10, ICE_RARE_VALUE));
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
            IceLegendary iceLegendary = iceLegendaryes.get(i);
            iceLegendary.update(dt);
            if (iceLegendary.shouldBeRemoved()) {
                iceLegendaryes.remove(i);
                continue;
            }
            if (clicked) {
                if (iceLegendary.shouldBeCollected(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY())) {
                    iceLegendaryes.remove(i);
                    integerEffects.add(new IntegerEffect(iMouse.getVirtualMouseX() + random.nextInt(5) - 10, iMouse.getVirtualMouseY() + random.nextInt(5) - 10, ICE_LEGENDARY_VALUE));
                    iceLegendaryCollectCount++;
                    if (thirdQuestCompleted) {
                        coin += ICE_LEGENDARY_VALUE + ICE_COLLECT_BONUS;
                    } else {
                        coin += ICE_LEGENDARY_VALUE;
                    }
                }
            }
        }

        for (int i = integerEffects.size() - 1; i >= 0; i--) {
            IntegerEffect integerEffect = integerEffects.get(i);
            integerEffect.update();
            if (integerEffect.isExpired()) {
                integerEffects.remove(i);
            }
        }


        for (int i = stringEffects.size() - 1; i >= 0; i--) {
            StringEffect stringEffect = stringEffects.get(i);
            stringEffect.update();
            if (stringEffect.isExpired()) {
                stringEffects.remove(i);
            }
        }

            //other updates

        // play time
        playTick++;
        sessionPlayTime = (int)Math.ceil(playTick /3600f);
        totalPlayTime = lastPlayTime + sessionPlayTime;

        // rush item status update
        updateIceRushStatus();

        // vacuum status update
        if (iceVacuumActive) {
            if (iceVacuumEndTick <= playTick) {
                iceVacuumClear();
            }
        }

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
        for (IceBasic iceBasic : iceBasics) {
            iceBasic.draw(g);
        }

        for (IceRare iceRare : iceRares) {
            iceRare.draw(g);
        }

        for (IceLegendary iceLegendary : iceLegendaryes) {
            iceLegendary.draw(g);
        }
    }

    public void renderIntegerEffects(Graphics g) {
        for (IntegerEffect integerEffect : integerEffects) {
            integerEffect.draw(g);
        }
    }

    public void renderStringEffects(Graphics g) {
        for (StringEffect stringEffect : stringEffects) {
            stringEffect.draw(g);
        }
    }

    public void activateIceRushItem(int tier) {
        if (tier == 1) {
            if (iceBasicRushItemCount > 0 && !iceBasicRush && iceBasicRushCoolTime <= playTick) {
                iceBasicRushItemCount--;
                iceBasicRush = true;
                iceBasicRushEndTick = (int) playTick + ICE_BASIC_RUSH_ENABLE_TICK;
                stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY(), "activated!"));
            }
        } else if (tier == 2) {
            if (iceRareRushItemCount > 0 && !iceRareRush && iceRareRushCoolTime <= playTick) {
                iceRareRushItemCount--;
                iceRareRush = true;
                iceRareRushEndTick = (int) playTick + ICE_RARE_RUSH_ENABLE_TICK;
                stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY(), "activated!"));
            }
        } else if (tier == 3) {
            if (iceLegendaryRushItemCount > 0 && !iceLegendaryRush && iceLegendaryRushCoolTime <= playTick) {
                iceLegendaryRushItemCount--;
                iceLegendaryRush = true;
                iceLegendaryRushEndTick = (int) playTick + ICE_LEGENDARY_RUSH_ENABLE_TICK;
                stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY(), "activated!"));
            }
        }
    }

    public void iceVacuumActive() {
        if (iceVacuumCount > 0 && !iceVacuumActive && iceVacuumCoolTime <= playTick) {
            iceVacuumActive = true;
            iceVacuumEndTick = (int) playTick + ICE_VACUUM_ENABLE_TICK;

            for (IceBasic iceBasic : iceBasics) {
                iceBasic.setVacuumActive(true);
            }
            for (IceRare iceRare : iceRares) {
                iceRare.setVacuumActive(true);
            }
            for (IceLegendary iceLegendary : iceLegendaryes) {
                iceLegendary.setVacuumActive(true);
            }
        }
    }

    public void iceVacuumClear() {
        int collectedIceBasics = 0;
        int collectedIceRares = 0;
        int collectedIceLegendaryes = 0;

        collectedIceBasics += iceBasics.size();
        iceBasics.clear();

        collectedIceRares += iceRares.size();
        iceRares.clear();

        collectedIceLegendaryes += iceLegendaryes.size();
        iceLegendaryes.clear();

        coin += collectedIceBasics * ICE_BASIC_VALUE;
        coin += collectedIceRares * ICE_RARE_VALUE;
        coin += collectedIceLegendaryes * ICE_LEGENDARY_VALUE;

        iceVacuumCoolTime = (int) playTick + ICE_VACUUM_COOL_DOWN_TICK;

        stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY(), "+" + (collectedIceBasics * ICE_BASIC_VALUE + collectedIceRares * ICE_RARE_VALUE + collectedIceLegendaryes * ICE_LEGENDARY_VALUE) + "!"));

        iceVacuumCount--;
        iceVacuumCoolTime = (int) playTick + ICE_VACUUM_COOL_DOWN_TICK;

        iceVacuumActive = false;
    }

    public void updateIceRushStatus() {
        if (iceBasicRush && playTick >= iceBasicRushEndTick) {
            iceBasicRush = false;
            iceBasicRushCoolTime = (int) playTick + ICE_BASIC_RUSH_COOL_DOWN_TICK;
            stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY(), "Rush ended!"));
        }
        if (iceRareRush && playTick >= iceRareRushEndTick) {
            iceRareRush = false;
            iceRareRushCoolTime = (int) playTick + ICE_RARE_RUSH_COOL_DOWN_TICK;
            stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY(), "Rush ended!"));
        }
        if (iceLegendaryRush && playTick >= iceLegendaryRushEndTick) {
            iceLegendaryRush = false;
            iceLegendaryRushCoolTime = (int) playTick + ICE_LEGENDARY_RUSH_COOL_DOWN_TICK;
            stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY(), "Rush ended!"));
        }
    }

    public void purchaseIceRushItem(int tier) {
        if (tier == 1) {
            if (coin >= ICE_BASIC_RUSH_ITEM_COST) {
                coin -= ICE_BASIC_RUSH_ITEM_COST;
                iceBasicRushItemCount++;
                stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY(), "purchased!"));
            } else {
                stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY(),"Not enough money!"));
            }
        } else if (tier == 2) {
            if (coin >= ICE_RARE_RUSH_ITEM_COST) {
                coin -= ICE_RARE_RUSH_ITEM_COST;
                iceRareRushItemCount++;
                stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY(), "purchased!"));
            } else {
                stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY(),"Not enough money!"));
            }
        } else if (tier == 3) {
            if (coin >= ICE_LEGENDARY_RUSH_ITEM_COST) {
                coin -= ICE_LEGENDARY_RUSH_ITEM_COST;
                iceLegendaryRushItemCount++;
                stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY(), "purchased!"));
            } else {
                stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY(),"Not enough money!"));
            }
        }
    }

    public void upgradeIceAutoCollect() {
        if (iceAutoCollectLevel == 4) {
            stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY(), "max level!"));
            return;
        }

        if (coin >= ICE_AUTO_COLLECT_UPGRADE_COST[iceAutoCollectLevel]) {
            coin -= ICE_AUTO_COLLECT_UPGRADE_COST[iceAutoCollectLevel];
            iceAutoCollectLevel++;
            stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY(), "purchased!"));
        } else {
            stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY(), "Not enough money!"));
        }
    }
    
    public void purchaseIceVacuum() {
        if (coin >= ICE_VACUUM_ITEM_COST) {
            coin -= ICE_BASIC_RUSH_ITEM_COST;
            iceVacuumCount++;
            stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY(), "purchased!"));
        } else {
            stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY(), "Not enough money"));
        }
    }

    public void clamRewardedQuest(int questNumber) {
        if (questNumber == 1) {
            if (firstQuestReward) return;

            if (firstQuestCompleted) {
                coin += getFirstQuestReward();
                integerEffects.add(new IntegerEffect(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY(), getFirstQuestReward()));
                firstQuestReward = true;
            }

        } else if (questNumber == 2) {
            if (secondQuestReward) return;

            if (secondQuestCompleted) {
                coin += getSecondQuestReward();
                integerEffects.add(new IntegerEffect(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY(), getSecondQuestReward()));
                secondQuestReward = true;
            }

        } else if (questNumber == 3) {
            if (thirdQuestReward) return;
            // 세 번째 퀘스트는 고정 보상이므로, 퀘스트 완료만 확인
            if (thirdQuestCompleted) {
                coin += 1000;
                integerEffects.add(new IntegerEffect(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY(), 1000));
                thirdQuestReward = true;
            }
        }
    }

    public void FUCKYOUDEBUGRENDER(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0,200,20);

        g.setColor(Color.red);
        g.drawString(playTick + "/" + iceBasicRushEndTick, 0,10);
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
        switch (questId) {
            case 1: // Collect 10 Ice_Basic
                return iceBasicCollectedCount;
            case 2: // Collect 5 Ice_Rare
                return iceRareCollectedCount;
            case 3: // Collect 1 Ice_Legendary
                return iceLegendaryCollectCount;
            case 4: // Play for 10 min (sessionPlayTime)
            case 5: // Play for 30 min (sessionPlayTime)
            case 6: // Play for 1 hour (sessionPlayTime)
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
        props.setProperty("lastIceLegendaryCollectCount", String.valueOf(lastIceLegendaryCollectCount + iceLegendaryCollectCount));
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

    // Quest Interface Methods
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

    // Shop Interface Methods
    @Override public int getIceBasicRushItemCount() { return iceBasicRushItemCount; }
    @Override public int getIceRareRushItemCount() { return iceRareRushItemCount; }
    @Override public int getIceLegendaryRushItemCount() { return iceLegendaryRushItemCount; }
    @Override public int getIceBasicRushCost() { return ICE_BASIC_RUSH_ITEM_COST; }
    @Override public int getIceRareRushCost() { return ICE_RARE_RUSH_ITEM_COST; }
    @Override public int getIceLegendaryRushCost() { return ICE_LEGENDARY_RUSH_ITEM_COST; }
    @Override public int getIceAutoCollectCost() { return ICE_AUTO_COLLECT_UPGRADE_COST[iceAutoCollectLevel]; }
    @Override public int getIceAutoCollectLevel() { return iceAutoCollectLevel;}
    @Override public int getIceVacuumCost() { return ICE_VACUUM_ITEM_COST; }
    @Override public int getIceVacuumCount() { return iceVacuumCount; }

    @Override public int getIceBasicRushCoolDownTick() { return ICE_BASIC_RUSH_COOL_DOWN_TICK; }
    @Override public int getIceRareRushCoolDownTick() { return ICE_RARE_RUSH_COOL_DOWN_TICK; }
    @Override public int getIceLegendaryRushCoolDownTick() { return ICE_LEGENDARY_RUSH_COOL_DOWN_TICK; }
    @Override public int getIceVacuumCoolDownTick() { return ICE_VACUUM_COOL_DOWN_TICK; }
    @Override public int getIceBasicRushCoolTime() { return iceBasicRushCoolTime; }
    @Override public int getIceRareRushCoolTime() { return iceRareRushCoolTime; }
    @Override public int getIceLegendaryRushCoolTime() { return iceLegendaryRushCoolTime; }
    @Override public int getIceVacuumCoolTime() { return iceVacuumCoolTime; }

    @Override public boolean iceBasicRush() { return iceBasicRush; }
    @Override public boolean iceRareRush() { return iceRareRush; }
    @Override public boolean iceLegendaryRush() { return iceLegendaryRush; }
    @Override public boolean iceVacuuming() { return iceVacuumActive; }

    @Override public int getTap() { return tap; }
    @Override public int getTapBarPosition() { return tapBarPosition[tap]; }
    @Override public int getIce_BasicCount() { return iceBasics.size(); }
    @Override public int getIce_RareCount() { return iceRares.size(); }
    @Override public int getIce_LegendaryCount() { return iceLegendaryes.size(); }
    @Override public long getPlayTick() { return playTick; }
}
