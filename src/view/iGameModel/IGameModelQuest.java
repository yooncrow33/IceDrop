package view.iGameModel;

public interface IGameModelQuest {
    String getFirstQuestExplanation();
    String getSecondQuestExplanation();
    String getThirdQuestExplanation();
    int getFirstQuestCoinReward();
    int getSecondQuestCoinReward();
    int getFirstQuestXpReward();
    int getSecondQuestXpReward();
    boolean firstQuestCompleted();
    boolean secondQuestCompleted();
    boolean thirdQuestCompleted();
    boolean firstQuestRewarded();
    boolean secondQuestRewarded();
    boolean thirdQuestRewarded();
    int getFirstQuestProgress();
    int getSecondQuestProgress();
    int getThirdQuestProgress();
    int getFirstQuestGoal();
    int getSecondQuestGoal();
    int getThirdQuestGoal();
    int getQuestRefreshCost();
}
