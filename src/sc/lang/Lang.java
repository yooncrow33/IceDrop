package sc.lang;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.util.Properties;

public final class Lang {

    // ===== TITLE / VERSION =====
    private String TITLE_GAME_NAME;
    private String TITLE_SUB_TEXT;
    private String VERSION_TEXT;

    // ===== TAP =====
    private String TAP_INFO;
    private String TAP_SHOP;
    private String TAP_SKILLPOINT;
    private String TAP_QUESTS;
    private String TAP_SETTING;

    // ===== INFO =====
    private String INFO_TITLE;
    private String INFO_CURRENT_PROFILE;
    private String INFO_TOTAL_PLAY_TIME;
    private String INFO_SESSION_PLAY_TIME;

    // ===== SHOP =====
    private String SHOP_TITLE;
    private String SHOP_COOLDOWN;
    private String SHOP_RUSH;
    private String SHOP_VACUUM;
    private String SHOP_UPGRADE;
    private String SHOP_OWNED;
    private String SHOP_LEVEL;
    private String SHOP_COIN;

    private String SHOP_ICE_BASIC_RUSH;
    private String SHOP_ICE_RARE_RUSH;
    private String SHOP_ICE_LEGENDARY_RUSH;
    private String SHOP_AUTO_COLLECT;
    private String SHOP_VACUUM_TITLE;

    // ===== SKILL POINT =====
    private String SKILLPOINT_TITLE;
    private String SKILLPOINT_XP_REMAIN;
    private String SKILLPOINT_UPGRADE;

    private String SKILL_ICE_BASIC_SPAWN;
    private String SKILL_ICE_RARE_SPAWN;
    private String SKILL_ICE_LEGENDARY_SPAWN;
    private String SKILL_CLICK_OFFSET;
    private String SKILL_ITEM_COOLTIME;

    private String SKILL_DESC_SPAWN;
    private String SKILL_DESC_CLICK_OFFSET;
    private String SKILL_DESC_ITEM_COOLTIME;

    private String SKILL_AVAILABLE;
    private String SKILL_USED;

    // ===== QUEST =====
    private String QUEST_TITLE;
    private String QUEST_REFRESH;
    private String QUEST_SESSION;
    private String QUEST_LONGTIME;
    private String QUEST_REWARD;

    private String QUEST_REWARDED;
    private String QUEST_NOT_REWARDED;
    private String QUEST_COMPLETED;
    private String QUEST_NOT_COMPLETED;

    // ===== SETTING =====
    private String SETTING_TITLE;

    // ===== INFO / EFFECT =====
    private String INFO_AUTO_COLLECTED;
    private String INFO_COLLECTED_ICE;
    private String INFO_VACUUM_ACTIVATED;
    private String INFO_GET_COIN;
    private String INFO_GET_XP;

    // ===== QUEST =====
    private String QUEST_REFRESHED;
    private String QUEST_NOT_ENOUGH_MONEY;
    private String QUEST_THIRD_REWARD;

    // ===== QUEST DESCRIPTION =====
    private String QUEST_DESC_BASIC;
    private String QUEST_DESC_RARE;
    private String QUEST_DESC_LEGENDARY;
    private String QUEST_DESC_10MIN;
    private String QUEST_DESC_30MIN;
    private String QUEST_DESC_1HOUR;
    private String QUEST_DESC_LONGTIME;

    // ===== SHOP =====
    private String SHOP_RUSH_BASIC_ON;
    private String SHOP_RUSH_RARE_ON;
    private String SHOP_RUSH_LEGENDARY_ON;
    private String SHOP_RUSH_TIME_30;
    private String SHOP_RUSH_TIME_60;

    private String SHOP_RUSH_BASIC_OFF;
    private String SHOP_RUSH_RARE_OFF;
    private String SHOP_RUSH_LEGENDARY_OFF;

    private String SHOP_PURCHASED;
    private String SHOP_MAX_LEVEL;

    // ===== SKILL =====
    private String SKILL_UPGRADED;
    private String SKILL_NOT_ENOUGH_POINT;
    private String SKILL_BASIC_UP;
    private String SKILL_RARE_UP;
    private String SKILL_LEGENDARY_UP;
    private String SKILL_PRESENT_COIN;
    private String SKILL_PRESENT_XP;
    private String SKILL_CURRENT_LEVEL;

    public Lang(int language) {
        Properties p = new Properties();
        String homeDir = System.getProperty("user.home") + File.separator + "SC" + File.separator + "lang";

        String fullPath1 = homeDir + File.separator + "english.properties";
        String fullPath2 = homeDir + File.separator + "korean.properties";
        String fullPath3 = homeDir + File.separator + "custom.properties";

        String paths[] = {"empty", fullPath1, fullPath2, fullPath3};

        try (FileInputStream in = new FileInputStream(paths[language])) {
            p.load(in);

            TITLE_GAME_NAME = p.getProperty(LangKey.TITLE_GAME_NAME);
            TITLE_SUB_TEXT = p.getProperty(LangKey.TITLE_SUB_TEXT);
            VERSION_TEXT = p.getProperty(LangKey.VERSION_TEXT);

            TAP_INFO = p.getProperty(LangKey.TAP_INFO);
            TAP_SHOP = p.getProperty(LangKey.TAP_SHOP);
            TAP_SKILLPOINT = p.getProperty(LangKey.TAP_SKILLPOINT);
            TAP_QUESTS = p.getProperty(LangKey.TAP_QUESTS);
            TAP_SETTING = p.getProperty(LangKey.TAP_SETTING);

            INFO_TITLE = p.getProperty(LangKey.INFO_TITLE);
            INFO_CURRENT_PROFILE = p.getProperty(LangKey.INFO_CURRENT_PROFILE);
            INFO_TOTAL_PLAY_TIME = p.getProperty(LangKey.INFO_TOTAL_PLAY_TIME);
            INFO_SESSION_PLAY_TIME = p.getProperty(LangKey.INFO_SESSION_PLAY_TIME);

            SHOP_TITLE = p.getProperty(LangKey.SHOP_TITLE);
            SHOP_COOLDOWN = p.getProperty(LangKey.SHOP_COOLDOWN);
            SHOP_RUSH = p.getProperty(LangKey.SHOP_RUSH);
            SHOP_VACUUM = p.getProperty(LangKey.SHOP_VACUUM);
            SHOP_UPGRADE = p.getProperty(LangKey.SHOP_UPGRADE);
            SHOP_OWNED = p.getProperty(LangKey.SHOP_OWNED);
            SHOP_LEVEL = p.getProperty(LangKey.SHOP_LEVEL);
            SHOP_COIN = p.getProperty(LangKey.SHOP_COIN);

            SHOP_ICE_BASIC_RUSH = p.getProperty(LangKey.SHOP_ICE_BASIC_RUSH);
            SHOP_ICE_RARE_RUSH = p.getProperty(LangKey.SHOP_ICE_RARE_RUSH);
            SHOP_ICE_LEGENDARY_RUSH = p.getProperty(LangKey.SHOP_ICE_LEGENDARY_RUSH);
            SHOP_AUTO_COLLECT = p.getProperty(LangKey.SHOP_AUTO_COLLECT);
            SHOP_VACUUM_TITLE = p.getProperty(LangKey.SHOP_VACUUM_TITLE);

            SKILLPOINT_TITLE = p.getProperty(LangKey.SKILLPOINT_TITLE);
            SKILLPOINT_XP_REMAIN = p.getProperty(LangKey.SKILLPOINT_XP_REMAIN);
            SKILLPOINT_UPGRADE = p.getProperty(LangKey.SKILLPOINT_UPGRADE);

            SKILL_ICE_BASIC_SPAWN = p.getProperty(LangKey.SKILL_ICE_BASIC_SPAWN);
            SKILL_ICE_RARE_SPAWN = p.getProperty(LangKey.SKILL_ICE_RARE_SPAWN);
            SKILL_ICE_LEGENDARY_SPAWN = p.getProperty(LangKey.SKILL_ICE_LEGENDARY_SPAWN);
            SKILL_CLICK_OFFSET = p.getProperty(LangKey.SKILL_CLICK_OFFSET);
            SKILL_ITEM_COOLTIME = p.getProperty(LangKey.SKILL_ITEM_COOLTIME);

            SKILL_DESC_SPAWN = p.getProperty(LangKey.SKILL_DESC_SPAWN);
            SKILL_DESC_CLICK_OFFSET = p.getProperty(LangKey.SKILL_DESC_CLICK_OFFSET);
            SKILL_DESC_ITEM_COOLTIME = p.getProperty(LangKey.SKILL_DESC_ITEM_COOLTIME);

            SKILL_AVAILABLE = p.getProperty(LangKey.SKILL_AVAILABLE);
            SKILL_USED = p.getProperty(LangKey.SKILL_USED);

            QUEST_TITLE = p.getProperty(LangKey.QUEST_TITLE);
            QUEST_REFRESH = p.getProperty(LangKey.QUEST_REFRESH);
            QUEST_SESSION = p.getProperty(LangKey.QUEST_SESSION);
            QUEST_LONGTIME = p.getProperty(LangKey.QUEST_LONGTIME);
            QUEST_REWARD = p.getProperty(LangKey.QUEST_REWARD);

            QUEST_REWARDED = p.getProperty(LangKey.QUEST_REWARDED);
            QUEST_NOT_REWARDED = p.getProperty(LangKey.QUEST_NOT_REWARDED);
            QUEST_COMPLETED = p.getProperty(LangKey.QUEST_COMPLETED);
            QUEST_NOT_COMPLETED = p.getProperty(LangKey.QUEST_NOT_COMPLETED);

            SETTING_TITLE = p.getProperty(LangKey.SETTING_TITLE);

            // ===== INFO / EFFECT =====
            INFO_AUTO_COLLECTED = p.getProperty(LangKey.INFO_AUTO_COLLECTED);
            INFO_COLLECTED_ICE = p.getProperty(LangKey.INFO_COLLECTED_ICE);
            INFO_VACUUM_ACTIVATED = p.getProperty(LangKey.INFO_VACUUM_ACTIVATED);
            INFO_GET_COIN = p.getProperty(LangKey.INFO_GET_COIN);
            INFO_GET_XP = p.getProperty(LangKey.INFO_GET_XP);

            // ===== QUEST =====
            QUEST_REFRESHED = p.getProperty(LangKey.QUEST_REFRESHED);
            QUEST_NOT_ENOUGH_MONEY = p.getProperty(LangKey.QUEST_NOT_ENOUGH_MONEY);
            QUEST_THIRD_REWARD = p.getProperty(LangKey.QUEST_THIRD_REWARD);

            // ===== QUEST DESCRIPTION =====
            QUEST_DESC_BASIC = p.getProperty(LangKey.QUEST_DESC_BASIC);
            QUEST_DESC_RARE = p.getProperty(LangKey.QUEST_DESC_RARE);
            QUEST_DESC_LEGENDARY = p.getProperty(LangKey.QUEST_DESC_LEGENDARY);
            QUEST_DESC_10MIN = p.getProperty(LangKey.QUEST_DESC_10MIN);
            QUEST_DESC_30MIN = p.getProperty(LangKey.QUEST_DESC_30MIN);
            QUEST_DESC_1HOUR = p.getProperty(LangKey.QUEST_DESC_1HOUR);
            QUEST_DESC_LONGTIME = p.getProperty(LangKey.QUEST_DESC_LONGTIME);

            // ===== SHOP =====
            SHOP_RUSH_BASIC_ON = p.getProperty(LangKey.SHOP_RUSH_BASIC_ON);
            SHOP_RUSH_RARE_ON = p.getProperty(LangKey.SHOP_RUSH_RARE_ON);
            SHOP_RUSH_LEGENDARY_ON = p.getProperty(LangKey.SHOP_RUSH_LEGENDARY_ON);
            SHOP_RUSH_TIME_30 = p.getProperty(LangKey.SHOP_RUSH_TIME_30);
            SHOP_RUSH_TIME_60 = p.getProperty(LangKey.SHOP_RUSH_TIME_60);
            SHOP_RUSH_BASIC_OFF = p.getProperty(LangKey.SHOP_RUSH_BASIC_OFF);
            SHOP_RUSH_RARE_OFF = p.getProperty(LangKey.SHOP_RUSH_RARE_OFF);
            SHOP_RUSH_LEGENDARY_OFF = p.getProperty(LangKey.SHOP_RUSH_LEGENDARY_OFF);
            SHOP_PURCHASED = p.getProperty(LangKey.SHOP_PURCHASED);
            SHOP_MAX_LEVEL = p.getProperty(LangKey.SHOP_MAX_LEVEL);

            // ===== SKILL =====
            SKILL_UPGRADED = p.getProperty(LangKey.SKILL_UPGRADED);
            SKILL_NOT_ENOUGH_POINT = p.getProperty(LangKey.SKILL_NOT_ENOUGH_POINT);
            SKILL_BASIC_UP = p.getProperty(LangKey.SKILL_BASIC_UP);
            SKILL_RARE_UP = p.getProperty(LangKey.SKILL_RARE_UP);
            SKILL_LEGENDARY_UP = p.getProperty(LangKey.SKILL_LEGENDARY_UP);
            SKILL_PRESENT_COIN = p.getProperty(LangKey.SKILL_PRESENT_COIN);
            SKILL_PRESENT_XP = p.getProperty(LangKey.SKILL_PRESENT_XP);
            SKILL_CURRENT_LEVEL = p.getProperty(LangKey.SKILL_CURRENT_LEVEL);


        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "저장파일 인식 실패");
        }
    }

    // ===== TITLE / VERSION =====
    public String getGameTitle() { return TITLE_GAME_NAME; }
    public String getSubText() { return TITLE_SUB_TEXT; }
    public String getVersion() { return VERSION_TEXT; }

    // ===== TAB NAMES =====
    public String getTapInfo() { return TAP_INFO; }
    public String getTapShop() { return TAP_SHOP; }
    public String getTapSkillPoint() { return TAP_SKILLPOINT; }
    public String getTapQuests() { return TAP_QUESTS; }
    public String getTapSetting() { return TAP_SETTING; }

    // ===== INFO TAP =====
    public String getInfoTitle() { return INFO_TITLE; }
    public String getInfoProfile() { return INFO_CURRENT_PROFILE; }
    public String getInfoTotalPlayTime() { return INFO_TOTAL_PLAY_TIME; }
    public String getInfoSessionPlayTime() { return INFO_SESSION_PLAY_TIME; }

    // ===== SHOP TAP =====
    public String getShopTitle() { return SHOP_TITLE; }
    public String getShopCooldown() { return SHOP_COOLDOWN; }
    public String getShopRush() { return SHOP_RUSH; }
    public String getShopVacuum() { return SHOP_VACUUM; }
    public String getShopUpgrade() { return SHOP_UPGRADE; }
    public String getShopOwned() { return SHOP_OWNED; }
    public String getShopLevel() { return SHOP_LEVEL; }
    public String getShopCoinLabel() { return SHOP_COIN; }

    public String getShopIceBasicRush() { return SHOP_ICE_BASIC_RUSH; }
    public String getShopIceRareRush() { return SHOP_ICE_RARE_RUSH; }
    public String getShopIceLegendaryRush() { return SHOP_ICE_LEGENDARY_RUSH; }
    public String getShopAutoCollect() { return SHOP_AUTO_COLLECT; }
    public String getShopVacuumTitle() { return SHOP_VACUUM_TITLE; }

    // ===== SKILL POINT TAP =====
    public String getSkillPointTitle() { return SKILLPOINT_TITLE; }
    public String getSkillPointXpRemain() { return SKILLPOINT_XP_REMAIN; }
    public String getSkillPointUpgrade() { return SKILLPOINT_UPGRADE; }

    public String getSkillIceBasicSpawn() { return SKILL_ICE_BASIC_SPAWN; }
    public String getSkillIceRareSpawn() { return SKILL_ICE_RARE_SPAWN; }
    public String getSkillIceLegendarySpawn() { return SKILL_ICE_LEGENDARY_SPAWN; }
    public String getSkillClickOffset() { return SKILL_CLICK_OFFSET; }
    public String getSkillItemCooltime() { return SKILL_ITEM_COOLTIME; }

    public String getSkillDescSpawn() { return SKILL_DESC_SPAWN; }
    public String getSkillDescClickOffset() { return SKILL_DESC_CLICK_OFFSET; }
    public String getSkillDescItemCooltime() { return SKILL_DESC_ITEM_COOLTIME; }

    public String getSkillAvailable() { return SKILL_AVAILABLE; }
    public String getSkillUsed() { return SKILL_USED; }

    // ===== QUEST TAP =====
    public String getQuestTitle() { return QUEST_TITLE; }
    public String getQuestRefresh() { return QUEST_REFRESH; }
    public String getQuestSession() { return QUEST_SESSION; }
    public String getQuestLongtime() { return QUEST_LONGTIME; }
    public String getQuestRewardLabel() { return QUEST_REWARD; }

    public String getQuestRewarded() { return QUEST_REWARDED; }
    public String getQuestNotRewarded() { return QUEST_NOT_REWARDED; }
    public String getQuestCompleted() { return QUEST_COMPLETED; }
    public String getQuestNotCompleted() { return QUEST_NOT_COMPLETED; }

    // ===== SETTING =====
    public String getSettingTitle() { return SETTING_TITLE; }

    // ===== INFO / EFFECT (NOTIFICATIONS) =====
    public String getInfoAutoCollectedMsg() { return INFO_AUTO_COLLECTED; }
    public String getInfoCollectedIceMsg() { return INFO_COLLECTED_ICE; }
    public String getInfoVacuumActivatedMsg() { return INFO_VACUUM_ACTIVATED; }
    public String getInfoGetCoinMsg() { return INFO_GET_COIN; }
    public String getInfoGetXpMsg() { return INFO_GET_XP; }

    // ===== QUEST STATUS =====
    public String getQuestRefreshedMsg() { return QUEST_REFRESHED; }
    public String getQuestNotEnoughMoneyMsg() { return QUEST_NOT_ENOUGH_MONEY; }
    public String getQuestThirdRewardMsg() { return QUEST_THIRD_REWARD; }

    // ===== QUEST DESCRIPTIONS =====
    public String getQuestDescBasic() { return QUEST_DESC_BASIC; }
    public String getQuestDescRare() { return QUEST_DESC_RARE; }
    public String getQuestDescLegendary() { return QUEST_DESC_LEGENDARY; }
    public String getQuestDesc10Min() { return QUEST_DESC_10MIN; }
    public String getQuestDesc30Min() { return QUEST_DESC_30MIN; }
    public String getQuestDesc1Hour() { return QUEST_DESC_1HOUR; }
    public String getQuestDescLongtime() { return QUEST_DESC_LONGTIME; }

    // ===== SHOP STATUS & RUSH =====
    public String getShopRushBasicOn() { return SHOP_RUSH_BASIC_ON; }
    public String getShopRushRareOn() { return SHOP_RUSH_RARE_ON; }
    public String getShopRushLegendaryOn() { return SHOP_RUSH_LEGENDARY_ON; }
    public String getShopRushTime30() { return SHOP_RUSH_TIME_30; }
    public String getShopRushTime60() { return SHOP_RUSH_TIME_60; }
    public String getShopRushBasicOff() { return SHOP_RUSH_BASIC_OFF; }
    public String getShopRushRareOff() { return SHOP_RUSH_RARE_OFF; }
    public String getShopRushLegendaryOff() { return SHOP_RUSH_LEGENDARY_OFF; }
    public String getShopPurchasedMsg() { return SHOP_PURCHASED; }
    public String getShopMaxLevelMsg() { return SHOP_MAX_LEVEL; }

    // ===== SKILL UPGRADE STATUS =====
    public String getSkillUpgradedMsg() { return SKILL_UPGRADED; }
    public String getSkillNotEnoughPointMsg() { return SKILL_NOT_ENOUGH_POINT; }
    public String getSkillBasicUpMsg() { return SKILL_BASIC_UP; }
    public String getSkillRareUpMsg() { return SKILL_RARE_UP; }
    public String getSkillLegendaryUpMsg() { return SKILL_LEGENDARY_UP; }
    public String getSKillPresentCoin() {return SKILL_PRESENT_COIN;}
    public String getSkillPresentXp() {return  SKILL_PRESENT_XP; }
    public String getSkillCurrentLevel() {return SKILL_CURRENT_LEVEL;}

}
