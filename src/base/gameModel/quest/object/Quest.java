package base.gameModel.quest.object;

import model.effects.IInfo;

import java.util.Random;

public class Quest {
    final int QUEST_INDEX;

    private int questGoalList[] = {7,10,5,1,10,30,60};
    private int questCoinRewardList[] = {7,50,60,500,70,150,400};
    private int questXpReawardList[] = {7,60,120,400,200,500,1200};
    private String questsExplanation[] = {"empty","Collect 10 Ice_Basic", "Collect 5 Ice_Rare", "Collect 1 Ice_Legendary",
            "Play for 10 min", "Play for 30 min", "Play for 1 hours"};

    private final int QUEST_GOAL;
    private boolean questCompleted = false;
    private boolean questReward = false;

    Random random = new Random();
    IInfo iInfo;
    IQuest iQuest;

    public Quest(IInfo iInfo, IQuest iQuest) {
        this.QUEST_INDEX = random.nextInt(questGoalList.length - 1) + 1;
        this.QUEST_GOAL = questGoalList[QUEST_INDEX];

        this.iQuest = iQuest;
        this.iInfo = iInfo;
    }

    public void update(int questProgress) {
        if (!questCompleted) {
            if (questProgress >= QUEST_GOAL) {
                questCompleted = true;
            }
        }
    }

    public void claimRewardedQuest() {
        if (questReward) return;
        System.out.println("[debug] claimFun");

        if (questCompleted) {
            System.out.println("[debug] completed");
            iQuest.addCoin(questCoinRewardList[QUEST_INDEX]);
            iQuest.addXp(questXpReawardList[QUEST_INDEX]);
            iInfo.addInfo("quest rewarded!", "+ " + questCoinRewardList[QUEST_INDEX], "present coin : " + iQuest.getCoin());
            questReward = true;
        }
    }

    public String getExplanation() {
        return questsExplanation[QUEST_INDEX];
    }

    public int getRewardCoin() { return questCoinRewardList[QUEST_INDEX]; }
    public int getRewardXp() { return questXpReawardList[QUEST_INDEX];}
    public int getQuestGoal() {return questGoalList[QUEST_INDEX];}
    public boolean getIsRewarded() { return questReward; }
    public boolean getIsCompleted() {return  questCompleted;}
    public int getQuestIndex() {return QUEST_INDEX; }
}
