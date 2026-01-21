package sc.base.gameModel.skill;

import sc.lang.Lang;

public class SkillManager {
    int iceBasicSpawnChanceLevel = 1;
    int iceRareSpawnChanceLevel = 1;
    int iceLegendarySpawnChanceLevel = 1;
    final int MAX_SPAWN_CHANCE_LEVEL = 4;

    int clickOffsetLevel = 0;
    final int MAX_OFFSET_LEVEL = 10;
    int itemCoolTimeLevel = 0;
    final int MAX_ITEM_COOL_TIME_LEVEL = 10;
    float[] coolTimeMultiplier = {
            1.0f, 0.95f, 0.9f, 0.8f, 0.75f,
            0.65f, 0.5f, 0.45f, 0.4f, 0.35f
    };
    float[] iceVacuumCoolTimeMultiplier = {
            1.0f, 0.95f, 0.9f, 0.87f, 0.85f,
            0.82f, 0.78f, 0.75f, 0.72f, 0.7f
    };

    private int level;
    public int xp;
    public int skillPoint;
    public int skillPointUsed;
    final int MAX_LEVEL = 32;
    int xpTable[] = {
            0,100,300,600,1000,1500,2100,2800,3600,4500,
            5500,6600,7800,9100,10500,12000,13600,15300,17100,19000,
            21000,23100,25300,27600,30000,32500,35100,37800,40600,43500,
            46500,50000,0
    };
    ISkillPointManager is;
    Lang l;

    public SkillManager(ISkillPointManager is, Lang l) {
        this.l = l;
        this.is = is;
    }

    public void updateLevelStatus() {
        if (level >= MAX_LEVEL && xp >= xpTable[level]) {
            level = MAX_LEVEL;
            return;
        }

        while (xp >= xpTable[level]) {
            xp -= xpTable[level];
            level++;
            skillPoint += 1;
        }
    }

    public void upgradeItemCoolTime() {
        if (itemCoolTimeLevel == MAX_ITEM_COOL_TIME_LEVEL) {
            is.addStrEffect( l.getShopMaxLevelMsg());
            return;
        }

        if (skillPoint > 0) {
            skillPoint--;
            skillPointUsed ++;
            itemCoolTimeLevel++;
            is.addInfo(l.getSkillPointUpgrade(),l.getSkillCurrentLevel() + itemCoolTimeLevel, l.getSkillPresentXp() + xp);
        } else {
            is.addStrEffect(l.getSkillNotEnoughPointMsg());
        }
    }

    public void upgradeClickOffset() {
        if (clickOffsetLevel == MAX_OFFSET_LEVEL) {
            is.addStrEffect( l.getShopMaxLevelMsg());
            return;
        }

        if (skillPoint > 0) {
            skillPoint--;
            skillPointUsed ++;
            clickOffsetLevel++;
            is.addInfo(l.getSkillPointUpgrade(),l.getSkillCurrentLevel() + clickOffsetLevel, l.getSkillPresentXp() + xp);
        } else {
            is.addStrEffect(l.getSkillNotEnoughPointMsg());
        }
    }

    public void upgradeIceSpawnChance(int tier) {
        if (skillPoint <= 0) {
            is.addStrEffect(
                    l.getSkillNotEnoughPointMsg()
            );
            return;
        }

        int currentLevel;

        if (tier == 1) {
            currentLevel = iceBasicSpawnChanceLevel;
        } else if (tier == 2) {
            currentLevel = iceRareSpawnChanceLevel;
        } else if (tier == 3) {
            currentLevel = iceLegendarySpawnChanceLevel;
        } else {
            return; // 이상한 tier 방어
        }

        if (currentLevel >= MAX_SPAWN_CHANCE_LEVEL) {
            is.addStrEffect(
                    l.getShopMaxLevelMsg()
            );
            return;
        }

        // 성공 루트
        skillPoint--;
        skillPointUsed++;

        if (tier == 1) {
            iceBasicSpawnChanceLevel++;
            is.addInfo(
                    l.getSkillBasicUpMsg(),
                    l.getSkillCurrentLevel() + iceBasicSpawnChanceLevel,
                    "");
        } else if (tier == 2) {
            iceRareSpawnChanceLevel++;
            is.addInfo(
                    l.getSkillRareUpMsg(),
                    l.getSkillCurrentLevel() + iceRareSpawnChanceLevel,
                    "");
        } else {
            iceLegendarySpawnChanceLevel++;
            is.addInfo(
                    l.getSkillLegendaryUpMsg(),
                    l.getSkillCurrentLevel() + iceLegendarySpawnChanceLevel,
                    "");
        }
    }

    public int getLevel() {
        return level;
    }
    public int getSkillPoint() {
        return skillPoint;
    }
    public int getSkillPointUsed() {
        return skillPointUsed;
    }
    public int getXp() {
        return xp;
    }
    public int getIceBasicSpawnChanceLevel() {
        return iceBasicSpawnChanceLevel;
    }
    public int getIceRareSpawnChanceLevel() {
        return  iceRareSpawnChanceLevel;
    }
    public int getIceLegendarySpawnChanceLevel() {
        return iceLegendarySpawnChanceLevel;
    }
    public int getClickOffsetLevel() {
        return clickOffsetLevel;
    }
    public int getItemCoolTimeLevel() {
        return itemCoolTimeLevel;
    }
    public float getCoolTimeMultiplier() {
        return coolTimeMultiplier[itemCoolTimeLevel];
    }
    public float getIceVacuumCoolTimeMultiplier() {
        return iceVacuumCoolTimeMultiplier[itemCoolTimeLevel];
    }

    public void loadLevel(int value) {
        level = value;
    }
    public void loadSkillPoint(int value) {
        skillPoint = value;
    }
    public void loadSkillPointUsed(int value) {
        skillPointUsed = value;
    }
    public void loadXp(int value) {
        xp = value;
    }
    public void loadIceBasicSpawnChanceLevel(int value) {
        iceBasicSpawnChanceLevel = value;
    }
    public void loadIceRareSpawnChanceLevel(int value) {
         iceRareSpawnChanceLevel = value;
    }
    public void loadIceLegendarySpawnChanceLevel(int value) {
        iceLegendarySpawnChanceLevel = value;
    }
    public void loadClickOffsetLevel(int value) {
        clickOffsetLevel = value;
    }
    public void loadItemCoolTimeLevel(int value) {
        itemCoolTimeLevel = value;
    }
    public int getMAX_SPAWN_CHANCE_LEVEL() {
        return MAX_SPAWN_CHANCE_LEVEL;
    }
    public int getMAX_OFFSET_LEVEL() {
        return MAX_OFFSET_LEVEL;
    }
    public int getMAX_ITEM_COOL_TIME_LEVEL() {
        return  MAX_ITEM_COOL_TIME_LEVEL;
    }
    public int getMAX_LEVEL() {
        return MAX_LEVEL;
    }
    public int getXPForNextLevel() {
        return xpTable[level + 1];
    }
    public void addXp(int value) {
        xp += value;
    }
}
