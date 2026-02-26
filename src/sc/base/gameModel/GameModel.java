package sc.base.gameModel;

import sc.base.gameModel.effects.EffectManager;
import sc.base.gameModel.ice.IIceManager;
import sc.base.gameModel.ice.IceManager;
import sc.base.gameModel.quest.IQuestManager;
import sc.base.gameModel.quest.QuestManager;
import sc.base.gameModel.shop.IShopManager;
import sc.base.gameModel.shop.ShopManager;
import sc.base.gameModel.skill.ISkillPointManager;
import sc.base.gameModel.skill.SkillManager;
import sc.base.gameModel.sound.SoundManager;
import sc.base.gameModel.tab.TabManager;
import sc.lang.Lang;
import sc.model.effects.IInfo;
import sc.view.*;
import sc.view.iGameModel.*;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class GameModel implements IGameModel, IGameModelTick, IGameModelQuest, IGameModelShop, IGameSkillPoint, IInfo, IIceManager, ISkillPointManager, IShopManager, IQuestManager {
    public boolean shiftPressed = false;
    boolean clicked = false;

    int playTick;
    int lastPlayTime;
    int totalPlayTime = 0;
    int sessionPlayTime = 0;

    final int currentProfileId;

    int lastIceBasicCollectCount;
    int lastIceRareCollectCount;
    int lastIceLegendaryCollectCount;
    IceManager iceManager;
    EffectManager effectManager;
    TabManager tabManager;
    SkillManager skillManager;
    ShopManager shopManager;
    QuestManager questManager;
    SoundManager soundManager;
    IMouse iMouse;
    Lang l;

    private static final String KEY = "iamsoprettyitset";
    private static final SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");

    public GameModel(int profileId, IMouse iMouse, Lang l) {
        this.currentProfileId = profileId;
        this.iMouse = iMouse;
        this.l = l;

        effectManager = new EffectManager(iMouse,this);
        iceManager = new IceManager(this,this,iMouse,l);
        tabManager = new TabManager(this);
        skillManager = new SkillManager(this, l);
        shopManager = new ShopManager(this,this,this, l);
        questManager = new QuestManager(this,this, l);
        soundManager = new SoundManager();
    }

    public void update(double dt) {
        iceManager.update(dt);
        playTick++;
        sessionPlayTime = (int) Math.ceil(playTick / 3600f);
        totalPlayTime = lastPlayTime + sessionPlayTime;

        questManager.update();
        shopManager.update(dt);
        tabManager.tabUpdate();
        skillManager.updateLevelStatus();
        effectManager.update(dt);
        clicked = false;
    }

    public void clicked(boolean clicked) {
        this.clicked = clicked;
    }

    public void setShiftPressed(boolean pressed) {
        this.shiftPressed = pressed;
    }

    @Override public void addInfo(String l1,String l2,String l3) {
        effectManager.addInfo(l1, l2, l3);
    }

    public TabManager getTapManager() {return tabManager;}
    public IceManager getIceManager() {return iceManager;}
    public EffectManager getEffectManager() {return effectManager;}
    public SkillManager getSkillManager() {return skillManager;}
    public ShopManager getShopManager() {return shopManager;}
    public QuestManager getQuestManager() { return questManager; }
    public SoundManager getSoundManager() { return soundManager; }

    public static String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes()));
        } catch (Exception e) { return null; }
    }

    public static String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) { return null; }
    }


    public void save(int currentProfileId) {
        Properties props = new Properties();

        props.setProperty("coin", String.valueOf(getCoin()));
        props.setProperty("last", String.valueOf(lastPlayTime + sessionPlayTime));
        props.setProperty("lastIceBasicCollectCount", String.valueOf(lastIceBasicCollectCount + iceManager.getIceBasicCollectedCount()));
        props.setProperty("lastIceRareCollectCount", String.valueOf(lastIceRareCollectCount + iceManager.getIceRareCollectedCount()));
        props.setProperty("lastIceLegendaryCollectCount", String.valueOf(lastIceLegendaryCollectCount + iceManager.getIceLegendaryCollectedCount()));
        props.setProperty("thirdQuestCompleted", String.valueOf(questManager.getThirdQuest().getIsCompleted()));
        props.setProperty("level", String.valueOf(skillManager.getLevel()));
        props.setProperty("xp", String.valueOf(skillManager.getXp()));
        props.setProperty("skillPoint", String.valueOf(skillManager.getSkillPoint()));
        props.setProperty("skillPointUsed", String.valueOf(skillManager.getSkillPointUsed()));
        props.setProperty("itemCoolTimeLevel", String.valueOf(skillManager.getItemCoolTimeLevel()));
        props.setProperty("clickOffsetLevel", String.valueOf(skillManager.getClickOffsetLevel()));
        props.setProperty("iceBasicSpawnChanceLevel", String.valueOf(skillManager.getIceBasicSpawnChanceLevel()));
        props.setProperty("iceRareSpawnChanceLevel", String.valueOf(skillManager.getIceRareSpawnChanceLevel()));
        props.setProperty("iceLegendarySpawnChanceLevel", String.valueOf(skillManager.getIceLegendarySpawnChanceLevel()));
        props.setProperty("iceBasicRushItemCount", String.valueOf(getIceBasicRushItemCount()));
        props.setProperty("iceRareRushItemCount", String.valueOf(getIceRareRushItemCount()));
        props.setProperty("iceLegendaryRushItemCount", String.valueOf(getIceLegendaryRushItemCount()));
        props.setProperty("iceAutoCollectLevel", String.valueOf(getIceAutoCollectLevel()));
        props.setProperty("iceVacuumCount", String.valueOf(getIceVacuumCount()));
        props.setProperty("thirdQuestReward", String.valueOf(questManager.getThirdQuest().getIsRewarded()));

        String homeDir = System.getProperty("user.home")+ File.separator + "SC" + File.separator + "save";

        String fullPath1 = homeDir + File.separator + "IceDropSaveProfile1.properties";
        String fullPath2 = homeDir + File.separator + "IceDropSaveProfile2.properties";
        String fullPath3 = homeDir + File.separator + "IceDropSaveProfile3.properties";
        String paths[] = {"empty", fullPath1, fullPath2, fullPath3};

        try {

            StringWriter writer = new StringWriter();
            props.store(writer, "User Save Data"); // 메모리상에 먼저 텍스트로 씀
            String encryptedData = encrypt(writer.toString()); // 통째로 암호화

            Files.writeString(Path.of(paths[currentProfileId]), encryptedData); // 파일에 저장

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "저장 실패: " + e.getMessage() + "\n경로: " + paths[currentProfileId]);
        }

    }

    public void load(int currentProfileId) {
        Properties props = new Properties();
        String homeDir = System.getProperty("user.home")+ File.separator + "SC" + File.separator + "save";

        String fullPath1 = homeDir + File.separator + "IceDropSaveProfile1.properties";
        String fullPath2 = homeDir + File.separator + "IceDropSaveProfile2.properties";
        String fullPath3 = homeDir + File.separator + "IceDropSaveProfile3.properties";

        String paths[] = {"empty", fullPath1, fullPath2, fullPath3};

        try (FileInputStream in = new FileInputStream(paths[currentProfileId])) {
            String encryptedData = Files.readString(Path.of(paths[currentProfileId]));

            // 2. 복호화 진행
            String decryptedData = decrypt(encryptedData);

            if (decryptedData == null) {
                throw new Exception("worng decryption");
            }

            // 3. 복호화된 문자열을 Properties 객체에 로드
            props.load(new StringReader(decryptedData));
            props.load(in);

            shopManager.loadCoin(Integer.parseInt(props.getProperty("coin", "7")));
            lastPlayTime = Integer.parseInt(props.getProperty("last", "0"));
            lastIceBasicCollectCount = Integer.parseInt(props.getProperty("lastIceBasicCollectCount", "0"));
            lastIceRareCollectCount = Integer.parseInt(props.getProperty("lastIceRareCollectCount", "0"));
            lastIceLegendaryCollectCount = Integer.parseInt(props.getProperty("lastIceLegendaryCollectCount", "0"));
            questManager.loadIsCompleted(Boolean.parseBoolean(props.getProperty("thirdQuestCompleted", "false")));
            skillManager.loadLevel(Integer.parseInt(props.getProperty("level", "1")));
            skillManager.loadXp(Integer.parseInt(props.getProperty("xp", "0")));
            skillManager.loadSkillPoint(Integer.parseInt(props.getProperty("skillPoint", "0")));
            skillManager.loadSkillPointUsed(Integer.parseInt(props.getProperty("skillPointUsed", "0")));
            skillManager.loadItemCoolTimeLevel(Integer.parseInt(props.getProperty("itemCoolTimeLevel", "0")));
            skillManager.loadClickOffsetLevel(Integer.parseInt(props.getProperty("clickOffsetLevel", "0")));
            skillManager.loadIceBasicSpawnChanceLevel(Integer.parseInt(props.getProperty("iceBasicSpawnChanceLevel", "1")));
            skillManager.loadIceRareSpawnChanceLevel(Integer.parseInt(props.getProperty("iceRareSpawnChanceLevel", "1")));
            skillManager.loadIceLegendarySpawnChanceLevel(Integer.parseInt(props.getProperty("iceLegendarySpawnChanceLevel", "1")));
            shopManager.loadIceBasicRushItemCount(Integer.parseInt(props.getProperty("iceBasicRushItemCount", "0")));
            shopManager.loadIceRareRushItemCount(Integer.parseInt(props.getProperty("iceRareRushItemCount", "0")));
            shopManager.loadIceLegendaryRushItemCount(Integer.parseInt(props.getProperty("iceLegendaryRushItemCount", "0")));
            shopManager.loadIceAutoCollectLevel(Integer.parseInt(props.getProperty("iceAutoCollectLevel", "0")));
            shopManager.loadIceVacuumCount(Integer.parseInt(props.getProperty("iceVacuumCount", "0")));
            questManager.loadIsRewarded(Boolean.parseBoolean(props.getProperty("thirdQuestReward", "false")));


        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "저장파일 인식 실패");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override public int getLevel() { return skillManager.getLevel(); }
    @Override public int getCoin() { return shopManager.getCoin(); }
    @Override public int getLast() { return totalPlayTime; }
    @Override public int getSessionPlayTime() { return sessionPlayTime; }
    @Override public int getProfile() { return currentProfileId; }

    @Override public boolean isIceRush(int tier) {
        switch (tier) {
            case 1 : return isIceBasicRush();
            case 2 : return isIceRareRush();
            case 3 : return isIceLegendaryRush();
            default : return false;
        }
    }

    @Override public int getIceSpawnChanceLevel(int tier) {
        switch (tier) {
            case 1 : return skillManager.getIceBasicSpawnChanceLevel();
            case 2 : return skillManager.getIceRareSpawnChanceLevel();
            case 3 : return skillManager.getIceLegendarySpawnChanceLevel();
            default : return skillManager.getIceBasicSpawnChanceLevel();
        }
    }
    @Override public boolean isClicked() {
        return clicked;
    }
    @Override public void addIntEffect(int value) {
        effectManager.addIntEffect(value);
    }
    @Override public void addStrEffect(String str) {
        effectManager.addStrEffect(str);
    }

    // Quest Interface Methods
    @Override public String getFirstQuestExplanation() { return questManager.getFirstQuest().getExplanation(); }
    @Override public String getSecondQuestExplanation() { return questManager.getSecondQuest().getExplanation(); }
    @Override public String getThirdQuestExplanation() { return questManager.getThirdQuest().getExplanation(); }
    @Override public int getFirstQuestCoinReward() { return questManager.getFirstQuest().getRewardCoin(); }
    @Override public int getSecondQuestCoinReward() { return questManager.getSecondQuest().getRewardCoin(); }
    @Override public int getFirstQuestXpReward() {return questManager.getFirstQuest().getRewardXp();}
    @Override public int getSecondQuestXpReward() {return questManager.getSecondQuest().getRewardXp();}
    @Override public boolean firstQuestCompleted() { return questManager.getFirstQuest().getIsCompleted(); }
    @Override public boolean secondQuestCompleted() { return questManager.getSecondQuest().getIsCompleted(); }
    @Override public boolean thirdQuestCompleted() { return questManager.getThirdQuest().getIsCompleted(); }
    @Override public boolean firstQuestRewarded() { return questManager.getFirstQuest().getIsRewarded(); }
    @Override public boolean secondQuestRewarded() { return questManager.getSecondQuest().getIsRewarded(); }
    @Override public boolean thirdQuestRewarded() { return questManager.getThirdQuest().getIsRewarded(); }
    @Override public int getFirstQuestProgress() { return questManager.getCurrentQuestProgress(questManager.getFirstQuest().getQuestIndex()); }
    @Override public int getSecondQuestProgress() { return questManager.getCurrentQuestProgress(questManager.getSecondQuest().getQuestIndex()); }
    @Override public int getThirdQuestProgress() { return iceManager.getIceBasicTotalCollectCount(); }
    @Override public int getFirstQuestGoal() { return questManager.getFirstQuest().getQuestGoal(); }
    @Override public int getSecondQuestGoal() { return questManager.getSecondQuest().getQuestGoal(); }
    @Override public int getThirdQuestGoal() { return questManager.getThirdQuest().getQuestGoal(); }
    @Override public int getQuestRefreshCost() {return 400;}

    // Shop Interface Methods
    @Override public boolean isThirdQuestComplete() { return questManager.isThirdQuestComplete(); }
    @Override public int getIceBasicRushItemCount() { return shopManager.getIceBasicRushItemCount(); }
    @Override public int getIceRareRushItemCount() { return shopManager.getIceRareRushItemCount(); }
    @Override public int getIceLegendaryRushItemCount() { return shopManager.getIceLegendaryRushItemCount(); }
    @Override public int getIceBasicRushCost() { return shopManager.getIceBasicRushCost(); }
    @Override public int getIceRareRushCost() { return shopManager.getIceRareRushCost(); }
    @Override public int getIceLegendaryRushCost() { return shopManager.getIceLegendaryRushCost(); }
    @Override public int getIceAutoCollectCost() { return shopManager.getIceAutoCollectCost(); }
    @Override public int getIceAutoCollectLevel() { return shopManager.getIceAutoCollectLevel();}
    @Override public int getIceVacuumCost() { return shopManager.getIceVacuumCost(); }
    @Override public int getIceVacuumCount() { return shopManager.getIceVacuumCount(); }

    @Override public int getIceBasicRushCoolDownTick() { return shopManager.getIceBasicRushCoolDownTick(); }
    @Override public int getIceRareRushCoolDownTick() { return shopManager.getIceRareRushCoolDownTick(); }
    @Override public int getIceLegendaryRushCoolDownTick() { return shopManager.getIceLegendaryRushCoolDownTick(); }
    @Override public int getIceVacuumCoolDownTick() { return shopManager.getIceVacuumCoolDownTick(); }
    @Override public int getIceBasicRushCoolTime() { return shopManager.getIceBasicRushCoolTime(); }
    @Override public int getIceRareRushCoolTime() { return shopManager.getIceRareRushCoolTime(); }
    @Override public int getIceLegendaryRushCoolTime() { return shopManager.getIceLegendaryRushCoolTime(); }
    @Override public int getIceVacuumCoolTime() { return shopManager.getIceVacuumCoolTime(); }
    @Override public int getIceAutoCollectMaxLevel() { return shopManager.getIceAutoCollectMaxLevel(); }

    @Override public boolean isIceBasicRush() { return shopManager.isIceBasicRush(); }
    @Override public boolean isIceRareRush() { return shopManager.isIceRareRush(); }
    @Override public boolean isIceLegendaryRush() { return shopManager.isIceLegendaryRush(); }
    @Override public boolean isIceVacuuming() { return shopManager.isIceVacuuming(); }

    // skillPoint Interface Methods
    @Override public int getIceBasicSpawnChanceLevel() { return skillManager.getIceBasicSpawnChanceLevel(); }
    @Override public int getIceRareSpawnChanceLevel() { return skillManager.getIceRareSpawnChanceLevel(); }
    @Override public int getIceLegendarySpawnChanceLevel() { return skillManager.getIceLegendarySpawnChanceLevel(); }
    @Override public int getIceSpawnChanceMaxLevel() { return skillManager.getMAX_SPAWN_CHANCE_LEVEL(); }
    @Override public int getClickOffsetLevel() { return skillManager.getClickOffsetLevel(); }
    @Override public int getClickOffsetMaxLevel() { return skillManager.getMAX_OFFSET_LEVEL(); }
    @Override public int getItemCoolTimeLevel() { return skillManager.getItemCoolTimeLevel(); }
    @Override public int getItemCoolTimeMaxLevel() { return skillManager.getMAX_ITEM_COOL_TIME_LEVEL(); }
    @Override public int getXPForNextLevel() { if(skillManager.getLevel() >= skillManager.getMAX_LEVEL()) return 0; return skillManager.getXPForNextLevel(); }
    @Override public int getXP() { return skillManager.getXp(); }
    @Override public int getSkillPointUsed() { return skillManager.getSkillPointUsed(); }
    @Override public int getSkillPoint() { return skillManager.getSkillPoint(); }

    @Override public int getTap() { return tabManager.getTap(); }
    @Override public int getTapBarPosition() { return tabManager.getTapBarPosition(); }
    @Override public int getPlayTick() { return playTick; }

    @Override public void addCoin(int addCoinValue) { shopManager.addCoin(addCoinValue);}
    @Override public void addXp(int addXpValue) { skillManager.addXp(addXpValue); }
    @Override public int getLastIceBasicCollectCount() { return lastIceBasicCollectCount; }
}
