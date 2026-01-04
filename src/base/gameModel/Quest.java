package base.gameModel;

import model.effects.Info;
import view.IInfo;

import java.util.Random;

public abstract class Quest {
    final int QUEST_INDEX;

    int questGoalList[] = {7,10,5,1,10,30,60};
    int questCoinRewardList[] = {7,50,60,500,70,150,400};
    int questXpReawardList[] = {7,60,120,400,200,500,1200};
    String questsExplanation[] = {"empty","Collect 10 Ice_Basic", "Collect 5 Ice_Rare", "Collect 1 Ice_Legendary",
            "Play for 10 min", "Play for 30 min", "Play for 1 hours"};

    final int QUEST_GOAL;
    boolean QuestCompleted = false;
    boolean QuestReward = false;

    Random random = new Random();
    IInfo iInfo;

    public Quest(IInfo iInfo) {
        this.QUEST_INDEX = random.nextInt(6) + 1;
        this.QUEST_GOAL = questGoalList[QUEST_INDEX];
    }

    public void update(int questProgress) {
        if (!QuestCompleted) {
            if (questProgress >= QUEST_GOAL) {
                QuestCompleted = true;
            }
        }
    }

    /*public void clamRewardedQuest() {
        if (QuestReward) return;

        if (QuestCompleted) {
            coin += getFirstQuestReward();
            iInfo.addInfo(new Info("second quest!", "+ " + getFirstQuestReward(), "present coin : " + coin, getPlayTick()));
            QuestReward = true;
        }
    }

     */
}
