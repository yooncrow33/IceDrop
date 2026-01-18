package base.gameModel.ice;

import base.gameModel.quest.QuestManager;
import base.gameModel.quest.object.Quest;

public interface IIceManager {
    void addCoin(int value);
    void addXp(int valur);
    boolean isThirdQuestComplete();
    boolean isIceVacuuming();
    boolean isIceRush(int tier);
    int getIceSpawnChanceLevel(int tier);
    boolean isClicked();
    int getIceAutoCollectLevel();
    int getClickOffsetLevel();
    void addIntEffect(int value);
    void addInfo(String l1,String l2,String l3);
    QuestManager getQuestManager();
    int getLastIceBasicCollectCount();
}
