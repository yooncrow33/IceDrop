package sc.base.gameModel.managers.stastics.object;

import sc.base.gameModel.managers.stastics.StatisticsManager;

public class Data {
    private final int clickCount;
    private final int iceClickCount;
    private final float hitRate;
    private final int currentCoin;
    private final int totalGetCoin;
    private final int currentXp;
    private final int totalGetSkillPoint;
    private final int currentLevel;
    private final int currentQuestRewardedCount;
    private final int iceBasicRushUsedCount;
    private final int iceRareRushUsedCount;
    private final int iceLegendaryRushUsedCount;
    private final int iceVacuumUsedCount;
    private final int iceAutoCollectedCount;
    private final int questsCompletedCount;
    private final int questRefreshedCount;
    private final int iceBasicCollectedCount;
    private final int iceRareCollectedCount;
    private final int iceLegendaryCollectedCount;

    public Data(StatisticsManager statisticsManager) {
        this.clickCount = statisticsManager.getClickCount();
        this.iceClickCount = statisticsManager.getIceClickCount();
        this.hitRate = statisticsManager.getHitRate();
        this.currentCoin = statisticsManager.getCurrentCoin();
        this.totalGetCoin = statisticsManager.getTotalGetCoin();
        this.currentXp = statisticsManager.getCurrentXp();
        this.totalGetSkillPoint = statisticsManager.getTotalGetSkillPoint();
        this.currentLevel = statisticsManager.getCurrentLevel();
        this.currentQuestRewardedCount = statisticsManager.getCurrentQuestRewardedCount();
        this.iceBasicRushUsedCount = statisticsManager.getIceBasicRushUsedCount();
        this.iceRareRushUsedCount = statisticsManager.getIceRareRushUsedCount();
        this.iceLegendaryRushUsedCount = statisticsManager.getIceLegendaryRushUsedCount();
        this.iceVacuumUsedCount = statisticsManager.getIceVacuumUsedCount();
        this.iceAutoCollectedCount = statisticsManager.getIceAutoCollectedCount();
        this.questsCompletedCount = statisticsManager.getQuestsCompletedCount();
        this.questRefreshedCount = statisticsManager.getQuestRefreshedCount();
        this.iceBasicCollectedCount = statisticsManager.getIceBasicCollectedCount();
        this.iceRareCollectedCount = statisticsManager.getIceRareCollectedCount();
        this.iceLegendaryCollectedCount = statisticsManager.getIceLegendaryCollectedCount();
    }

    public int getClickCount() { return clickCount; }
    public int getIceClickCount() { return iceClickCount; }
    public float getHitRate() { return hitRate; }
    public int getCurrentCoin() { return currentCoin; }
    public int getTotalGetCoin() { return totalGetCoin; }
    public int getCurrentXp() { return currentXp; }
    public int getTotalGetSkillPoint() { return totalGetSkillPoint; }
    public int getCurrentLevel() { return currentLevel; }
    public int getCurrentQuestRewardedCount() { return currentQuestRewardedCount; }
    public int getIceBasicRushUsedCount() { return iceBasicRushUsedCount; }
    public int getIceRareRushUsedCount() { return iceRareRushUsedCount; }
    public int getIceLegendaryRushUsedCount() { return iceLegendaryRushUsedCount; }
    public int getIceVacuumUsedCount() { return iceVacuumUsedCount; }
    public int getIceAutoCollectedCount() { return iceAutoCollectedCount; }
    public int getQuestsCompletedCount() { return questsCompletedCount; }
    public int getQuestRefreshedCount() { return questRefreshedCount; }
    public int getIceBasicCollectedCount() { return iceBasicCollectedCount; }
    public int getIceRareCollectedCount() { return iceRareCollectedCount; }
    public int getIceLegendaryCollectedCount() { return iceLegendaryCollectedCount; }
}