package base.gameModel.quest.object;

import model.effects.IInfo;

public class LongTimeQuest {
    private String questsExplanation = "Permanently grants +5 coin per Ice collected. and 1000 coins.";

    private final int QUEST_GOAL = 5000;
    public boolean questCompleted = false;
    private boolean questReward = false;
    IInfo iInfo;
    IQuest iQuest;
    final int ICE_COLLECT_BONUS = 5;

    public LongTimeQuest(IInfo iInfo, IQuest iQuest) {
        this.iQuest = iQuest;
        this.iInfo = iInfo;}

    public void update(int iceBasicCollectCount) {
        if (!questCompleted) {
            if (iceBasicCollectCount >= QUEST_GOAL) {
                questCompleted = true;
                iQuest.loadIsCompleted(true);
            }
        }
    }

    public void claimRewardedQuest() {
        if (questReward) return;

        if (questCompleted) {
            iQuest.addCoin(50000);
            iQuest.addXp(4000);
            iInfo.addInfo("third quest!","+ Ice Collect Bonus", "present coin : " + iQuest.getCoin());
            questReward = true;
            iQuest.loadIsRewarded(true);
        }
    }

    public void load(boolean completed, boolean rewarded) {
        this.questCompleted = completed;
        this.questReward = rewarded;
    }

    public String getExplanation() {
        return questsExplanation;
    }

    public int getRewardCoin() { return 50000; }
    public int getRewardXp() { return 4000;}
    public int getQuestGoal() {return 5000;}
    public int getICE_COLLECT_BONUS() { return ICE_COLLECT_BONUS; }
    public boolean getIsRewarded() { return questReward; }
    public boolean getIsCompleted() {return  questCompleted;}
}

