package view;

public interface IGameModel {
    int getCoin();
    int getLevel();
    int getTap();
    int getTapBarPosition();
    int getLast();
    int getSessionPlayTime();
    int getProfile();
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
