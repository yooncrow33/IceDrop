package base.gameModel.shop;

import model.effects.IInfo;
import view.iGameModel.IGameModelTick;

public class ShopManager {
    private int coin;

    int iceBasicRushItemCount = 0;
    int iceRareRushItemCount = 0;
    int iceLegendaryRushItemCount = 0;

    boolean iceBasicRush = false;
    boolean iceRareRush = false;
    boolean iceLegendaryRush = false;

    int iceBasicRushEndTick;
    int iceRareRushEndTick;
    int iceLegendaryRushEndTick;
    int iceVacuumEndTick;

    int iceBasicRushCoolTime = 0;
    int iceRareRushCoolTime = 0;
    int iceLegendaryRushCoolTime = 0;
    int iceVacuumCoolTime = 0;

    int iceAutoCollectLevel = 0;
    int iceVacuumCount = 0;

    boolean iceVacuumActive = false;

    final int ICE_BASIC_RUSH_ITEM_COST = 1000;
    final int ICE_RARE_RUSH_ITEM_COST = 4000;
    final int ICE_LEGENDARY_RUSH_ITEM_COST = 10000;

    final int ICE_AUTO_COLLECT_MAX_LEVEL = 4;
    final int ICE_AUTO_COLLECT_UPGRADE_COST[] = {3000,6000,20000,50000,100000};
    final int ICE_VACUUM_ITEM_COST = 700;

    final int ICE_BASIC_RUSH_ENABLE_TICK = 1800; // 30 seconds
    final int ICE_RARE_RUSH_ENABLE_TICK = 3600; // 1 minute
    final int ICE_LEGENDARY_RUSH_ENABLE_TICK = 3600;
    final int ICE_VACUUM_ENABLE_TICK = 90; // 1.5 second

    final int ICE_BASIC_RUSH_COOL_DOWN_TICK = 3600; // 2 minutes
    final int ICE_RARE_RUSH_COOL_DOWN_TICK = 7200; // 3 minutes
    final int ICE_LEGENDARY_RUSH_COOL_DOWN_TICK = 18000; // 5 minutes
    final int ICE_VACUUM_COOL_DOWN_TICK = 72000; // just a 10 minute 내것이 되는 시간

    IGameModelTick iTick;
    IInfo iInfo;
    IShopManager is;

    public ShopManager(IGameModelTick iTick, IInfo iInfo, IShopManager is) {
        this.iTick = iTick;
        this.iInfo = iInfo;
        this.is = is;
    }

    public void update(double dt) {
        if (iceVacuumActive) {
            if (iceVacuumEndTick <= iTick.getPlayTick()) {
                iceVacuumClear();
            }
        }
        updateIceRushStatus();
    }

    public void activateIceRushItem(int tier) {
        if (tier == 1) {
            if (iceBasicRushItemCount > 0 && !iceBasicRush && iceBasicRushCoolTime <= iTick.getPlayTick()) {
                iceBasicRushItemCount--;
                iceBasicRush = true;
                iceBasicRushEndTick = iTick.getPlayTick() + ICE_BASIC_RUSH_ENABLE_TICK;
                iInfo.addInfo("Ice Basic Rush Activated!","Rush time : 30 seconds", "");
            }
        } else if (tier == 2) {
            if (iceRareRushItemCount > 0 && !iceRareRush && iceRareRushCoolTime <= iTick.getPlayTick()) {
                iceRareRushItemCount--;
                iceRareRush = true;
                iceRareRushEndTick = iTick.getPlayTick() + ICE_RARE_RUSH_ENABLE_TICK;
                iInfo.addInfo("Ice Rare Rush Activated!","Rush time : 1 min", "");
            }
        } else if (tier == 3) {
            if (iceLegendaryRushItemCount > 0 && !iceLegendaryRush && iceLegendaryRushCoolTime <= iTick.getPlayTick()) {
                iceLegendaryRushItemCount--;
                iceLegendaryRush = true;
                iceLegendaryRushEndTick = iTick.getPlayTick() + ICE_LEGENDARY_RUSH_ENABLE_TICK;
                iInfo.addInfo("Ice Legendary Rush Activated!","Rush time : 1 min", "");
            }
        }
    }

    public void iceVacuumActive() {
        if (iceVacuumCount > 0 && !iceVacuumActive && iceVacuumCoolTime <= iTick.getPlayTick()) {
            iceVacuumActive = true;
            iceVacuumEndTick = iTick.getPlayTick() + ICE_VACUUM_ENABLE_TICK;
            is.getIceManager().iceVacuumActive();
        }
    }

    public void iceVacuumClear() {
        is.getIceManager().iceVacuumClear();
        iceVacuumCount--;
        iceVacuumCoolTime = iTick.getPlayTick() +  (int)(ICE_VACUUM_COOL_DOWN_TICK / is.getSkillManager().getCoolTimeMultiplier());

        iceVacuumActive = false;
    }

    public void updateIceRushStatus() {
        if (iceBasicRush && iTick.getPlayTick() >= iceBasicRushEndTick) {
            iceBasicRush = false;
            iceBasicRushCoolTime = iTick.getPlayTick() + (int) (ICE_BASIC_RUSH_COOL_DOWN_TICK / is.getSkillManager().getCoolTimeMultiplier());
            iInfo.addInfo("Ice Basic Rush ended!","", "");
        }
        if (iceRareRush && iTick.getPlayTick() >= iceRareRushEndTick) {
            iceRareRush = false;
            iceRareRushCoolTime = iTick.getPlayTick() + (int) (ICE_RARE_RUSH_COOL_DOWN_TICK / is.getSkillManager().getCoolTimeMultiplier());
            iInfo.addInfo("Ice Rare Rush ended!","", "");
        }
        if (iceLegendaryRush && iTick.getPlayTick() >= iceLegendaryRushEndTick) {
            iceLegendaryRush = false;
            iceLegendaryRushCoolTime = iTick.getPlayTick() + (int) (ICE_LEGENDARY_RUSH_COOL_DOWN_TICK / is.getSkillManager().getCoolTimeMultiplier());
            iInfo.addInfo("Ice Legendary Rush ended!","", "");
        }
    }

    public void purchaseIceRushItem(int tier) {
        if (tier == 1) {
            if (coin >= ICE_BASIC_RUSH_ITEM_COST) {
                coin -= ICE_BASIC_RUSH_ITEM_COST;
                iceBasicRushItemCount++;
                iInfo.addInfo("purchased!","- " + ICE_BASIC_RUSH_ITEM_COST, "present coin : " + coin);
            } else {
                is.getEffectManager().addStrEffect("Not enough money!");
            }
        } else if (tier == 2) {
            if (coin >= ICE_RARE_RUSH_ITEM_COST) {
                coin -= ICE_RARE_RUSH_ITEM_COST;
                iceRareRushItemCount++;
                iInfo.addInfo("purchased!","- " + ICE_RARE_RUSH_ITEM_COST, "present coin : " + coin);
            } else {
                is.getEffectManager().addStrEffect("Not enough money!");
            }
        } else if (tier == 3) {
            if (coin >= ICE_LEGENDARY_RUSH_ITEM_COST) {
                coin -= ICE_LEGENDARY_RUSH_ITEM_COST;
                iceLegendaryRushItemCount++;
                iInfo.addInfo("purchased!","- " + ICE_LEGENDARY_RUSH_ITEM_COST, "present coin : " + coin);
            } else {
                is.getEffectManager().addStrEffect("Not enough money!");
            }
        }
    }

    public void upgradeIceAutoCollect() {
        if (iceAutoCollectLevel == 4) {
            is.getEffectManager().addStrEffect( "max level!");
            return;
        }

        if (coin >= ICE_AUTO_COLLECT_UPGRADE_COST[iceAutoCollectLevel]) {
            coin -= ICE_AUTO_COLLECT_UPGRADE_COST[iceAutoCollectLevel];
            iceAutoCollectLevel++;
            iInfo.addInfo("purchased!","- " + ICE_AUTO_COLLECT_UPGRADE_COST[iceAutoCollectLevel], "present coin : " + coin);
        } else {
            is.getEffectManager().addStrEffect( "Not enough money!");
        }
    }

    public void purchaseIceVacuum() {
        if (coin >= ICE_VACUUM_ITEM_COST) {
            coin -= ICE_VACUUM_ITEM_COST;
            iceVacuumCount++;
            iInfo.addInfo("purchased!","- " + ICE_VACUUM_ITEM_COST, "present coin : " + coin);
        } else {
            is.getEffectManager().addStrEffect( "Not enough money");
        }
    }
    public int getCoin() { return coin; }
    public int getIceBasicRushItemCount() { return iceBasicRushItemCount; }
    public int getIceRareRushItemCount() { return iceRareRushItemCount; }
    public int getIceLegendaryRushItemCount() { return iceLegendaryRushItemCount; }
    public int getIceBasicRushCost() { return ICE_BASIC_RUSH_ITEM_COST; }
    public int getIceRareRushCost() { return ICE_RARE_RUSH_ITEM_COST; }
    public int getIceLegendaryRushCost() { return ICE_LEGENDARY_RUSH_ITEM_COST; }
    public int getIceAutoCollectCost() { return ICE_AUTO_COLLECT_UPGRADE_COST[iceAutoCollectLevel]; }
    public int getIceAutoCollectLevel() { return iceAutoCollectLevel;}
    public int getIceVacuumCost() { return ICE_VACUUM_ITEM_COST; }
    public int getIceVacuumCount() { return iceVacuumCount; }

    public int getIceBasicRushCoolDownTick() { return ICE_BASIC_RUSH_COOL_DOWN_TICK; }
    public int getIceRareRushCoolDownTick() { return ICE_RARE_RUSH_COOL_DOWN_TICK; }
    public int getIceLegendaryRushCoolDownTick() { return ICE_LEGENDARY_RUSH_COOL_DOWN_TICK; }
    public int getIceVacuumCoolDownTick() { return ICE_VACUUM_COOL_DOWN_TICK; }
    public int getIceBasicRushCoolTime() { return iceBasicRushCoolTime; }
    public int getIceRareRushCoolTime() { return iceRareRushCoolTime; }
    public int getIceLegendaryRushCoolTime() { return iceLegendaryRushCoolTime; }
    public int getIceVacuumCoolTime() { return iceVacuumCoolTime; }
    public int getIceAutoCollectMaxLevel() { return ICE_AUTO_COLLECT_MAX_LEVEL; }

    public boolean isIceBasicRush() { return iceBasicRush; }
    public boolean isIceRareRush() { return iceRareRush; }
    public boolean isIceLegendaryRush() { return iceLegendaryRush; }
    public boolean isIceVacuuming() { return iceVacuumActive; }

    public void addCoin(int value) {
        coin += value;
    }

    public void loadCoin(int value) { coin = value; }
    public void loadIceBasicRushItemCount(int value) { iceBasicRushItemCount = value; }
    public void loadIceRareRushItemCount(int value) { iceRareRushItemCount = value; }
    public void loadIceLegendaryRushItemCount(int value) { iceLegendaryRushItemCount = value; }
    public void loadIceAutoCollectLevel(int value) { iceAutoCollectLevel = value;}
    public void loadIceVacuumCount(int value) { iceVacuumCount = value; }
}
