package sc.view.iGameModel;

public interface IGameModelShop {
    int getIceBasicRushItemCount();
    int getIceRareRushItemCount();
    int getIceLegendaryRushItemCount();
    int getIceBasicRushCost();
    int getIceRareRushCost();
    int getIceLegendaryRushCost();

    int getIceAutoCollectCost();
    int getIceAutoCollectLevel();

    int getIceVacuumCost();
    int getIceVacuumCount();

    int getIceBasicRushCoolDownTick();
    int getIceRareRushCoolDownTick();
    int getIceLegendaryRushCoolDownTick();
    int getIceVacuumCoolDownTick();
    int getIceAutoCollectMaxLevel();

    int getIceBasicRushCoolTime();
    int getIceRareRushCoolTime();
    int getIceLegendaryRushCoolTime();
    int getIceVacuumCoolTime();

    boolean isIceBasicRush();
    boolean isIceRareRush();
    boolean isIceLegendaryRush();
    boolean isIceVacuuming();
}
