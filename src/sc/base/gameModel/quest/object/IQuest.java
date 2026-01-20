package sc.base.gameModel.quest.object;

public interface IQuest {
    void addCoin(int addCoinValue);
    void addXp(int addXpValue);
    int getCoin();
    void loadIsRewarded(boolean b);
    void loadIsCompleted(boolean b);
}
