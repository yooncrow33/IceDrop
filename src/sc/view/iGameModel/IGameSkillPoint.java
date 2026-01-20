package sc.view.iGameModel;

public interface IGameSkillPoint {
    int getIceBasicSpawnChanceLevel();
    int getIceRareSpawnChanceLevel();
    int getIceLegendarySpawnChanceLevel();
    int getIceSpawnChanceMaxLevel();

    int getClickOffsetLevel();
    int getClickOffsetMaxLevel();

    int getItemCoolTimeLevel();
    int getItemCoolTimeMaxLevel();

    int getSkillPoint();
    int getSkillPointUsed();

    int getXP();
    int getXPForNextLevel();
    int getLevel();
}
