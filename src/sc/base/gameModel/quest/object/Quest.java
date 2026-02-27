package sc.base.gameModel.quest.object;

import sc.lang.Lang;
import sc.model.effects.IInfo;
import sc.view.iGameModel.IGameModel;

import java.util.Random;

public class Quest {
    final int QUEST_INDEX;

    private int questGoalList[] = {7,10,5,1,10,30,60};
    private int questCoinRewardList[] = {7,50,60,500,70,150,400};
    private int questXpReawardList[] = {7,60,120,400,200,500,1200};
    private String questsExplanation[];

    private final int QUEST_GOAL;
    private boolean questCompleted = false;
    private boolean questReward = false;

    Random random = new Random();
    IGameModel iGameModel;

    Lang l;

    public Quest(IGameModel iGameModel, Lang l) {
        this.QUEST_INDEX = random.nextInt(questGoalList.length - 1) + 1;
        this.QUEST_GOAL = questGoalList[QUEST_INDEX];

        this.iGameModel = iGameModel;
        this.l = l;

        questsExplanation = new String[]{"empty",l.getQuestDescBasic(), l.getQuestDescRare(), l.getQuestDescLegendary(),
                l.getQuestDesc10Min(), l.getQuestDesc30Min(), l.getQuestDesc1Hour()};
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

        if (questCompleted) {
            iGameModel.getShopManager().addCoin(questCoinRewardList[QUEST_INDEX]);
            iGameModel.getSkillManager().addXp(questXpReawardList[QUEST_INDEX]);
            iGameModel.getEffectManager().addInfo(l.getQuestRewarded(), "+ " + questCoinRewardList[QUEST_INDEX], l.getSKillPresentCoin() + iGameModel.getShopManager().getCoin());
            questReward = true;
        }
    }

    public String getExplanation() {
        return questsExplanation[QUEST_INDEX];
    }

    public int getCoinReward() { return questCoinRewardList[QUEST_INDEX]; }
    public int getXpReward() { return questXpReawardList[QUEST_INDEX];}
    public int getGoal() {return questGoalList[QUEST_INDEX];}
    public boolean getIsRewarded() { return questReward; }
    public boolean getIsCompleted() {return  questCompleted;}
    public int getQuestIndex() {return QUEST_INDEX; }
}
