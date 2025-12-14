package view.iGameModel;

public interface IGameModelQuest {
    String getFirstQuestExplanation();
    String getSecondQuestExplanation();
    int getFirstQuestReward();
    int getSecondQuestReward();
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
}
