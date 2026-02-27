package sc.base.gameModel.shop;

import sc.lang.Lang;
import sc.view.iGameModel.IGameModel;

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

    IGameModel iGameModel;
    Lang l;

    public ShopManager(IGameModel iGameModel, Lang l) {
        this.iGameModel = iGameModel;
        this.l = l;
    }

    public void update(double dt) {
        if (iceVacuumActive) {
            if (iceVacuumEndTick <= iGameModel.getTickManager().getPlayTick()) {
                iceVacuumClear();
            }
        }
        updateIceRushStatus();
    }

    public void activateIceRushItem(int tier) {
        if (tier == 1) {
            if (iceBasicRushItemCount > 0 && !iceBasicRush && iceBasicRushCoolTime <= iGameModel.getTickManager().getPlayTick()) {
                iceBasicRushItemCount--;
                iceBasicRush = true;
                iceBasicRushEndTick = iGameModel.getTickManager().getPlayTick() + ICE_BASIC_RUSH_ENABLE_TICK;
                iGameModel.getEffectManager().addInfo(l.getShopRushBasicOn(),l.getShopRushTime30(), "");
            }
        } else if (tier == 2) {
            if (iceRareRushItemCount > 0 && !iceRareRush && iceRareRushCoolTime <= iGameModel.getTickManager().getPlayTick()) {
                iceRareRushItemCount--;
                iceRareRush = true;
                iceRareRushEndTick = iGameModel.getTickManager().getPlayTick() + ICE_RARE_RUSH_ENABLE_TICK;
                iGameModel.getEffectManager().addInfo(l.getShopRushRareOn(),l.getShopRushTime60(), "");
            }
        } else if (tier == 3) {
            if (iceLegendaryRushItemCount > 0 && !iceLegendaryRush && iceLegendaryRushCoolTime <= iGameModel.getTickManager().getPlayTick()) {
                iceLegendaryRushItemCount--;
                iceLegendaryRush = true;
                iceLegendaryRushEndTick = iGameModel.getTickManager().getPlayTick() + ICE_LEGENDARY_RUSH_ENABLE_TICK;
                iGameModel.getEffectManager().addInfo(l.getShopRushLegendaryOn(),l.getShopRushTime60(), "");
            }
        }
    }

    public void iceVacuumActive() {
        if (iceVacuumCount > 0 && !iceVacuumActive && iceVacuumCoolTime <= iGameModel.getTickManager().getPlayTick()) {
            iceVacuumActive = true;
            iceVacuumEndTick = iGameModel.getTickManager().getPlayTick() + ICE_VACUUM_ENABLE_TICK;
            iGameModel.getIceManager().iceVacuumActive();
        }
    }

    public void iceVacuumClear() {
        iGameModel.getIceManager().iceVacuumClear();
        iceVacuumCount--;
        iceVacuumCoolTime = iGameModel.getTickManager().getPlayTick() +  (int)(ICE_VACUUM_COOL_DOWN_TICK / iGameModel.getSkillManager().getIceVacuumCoolTimeMultiplier());

        iceVacuumActive = false;
    }

    public void updateIceRushStatus() {
        if (iceBasicRush && iGameModel.getTickManager().getPlayTick() >= iceBasicRushEndTick) {
            iceBasicRush = false;
            iceBasicRushCoolTime = iGameModel.getTickManager().getPlayTick() + (int) (ICE_BASIC_RUSH_COOL_DOWN_TICK / iGameModel.getSkillManager().getCoolTimeMultiplier());
            iGameModel.getEffectManager().addInfo(l.getShopRushBasicOff(),"", "");
        }
        if (iceRareRush && iGameModel.getTickManager().getPlayTick() >= iceRareRushEndTick) {
            iceRareRush = false;
            iceRareRushCoolTime = iGameModel.getTickManager().getPlayTick() + (int) (ICE_RARE_RUSH_COOL_DOWN_TICK / iGameModel.getSkillManager().getCoolTimeMultiplier());
            iGameModel.getEffectManager().addInfo(l.getShopRushRareOff(),"", "");
        }
        if (iceLegendaryRush && iGameModel.getTickManager().getPlayTick() >= iceLegendaryRushEndTick) {
            iceLegendaryRush = false;
            iceLegendaryRushCoolTime = iGameModel.getTickManager().getPlayTick() + (int) (ICE_LEGENDARY_RUSH_COOL_DOWN_TICK / iGameModel.getSkillManager().getCoolTimeMultiplier());
            iGameModel.getEffectManager().addInfo(l.getShopRushLegendaryOff(),"", "");
        }
    }

    public void purchaseIceRushItem(int tier) {
        if (tier == 1) {
            if (coin >= ICE_BASIC_RUSH_ITEM_COST) {
                coin -= ICE_BASIC_RUSH_ITEM_COST;
                iceBasicRushItemCount++;
                iGameModel.getEffectManager().addInfo(l.getShopPurchasedMsg(),"- " + ICE_BASIC_RUSH_ITEM_COST, l.getSKillPresentCoin() + coin);
            } else {
                iGameModel.getEffectManager().addStrEffect(l.getQuestNotEnoughMoneyMsg());
            }
        } else if (tier == 2) {
            if (coin >= ICE_RARE_RUSH_ITEM_COST) {
                coin -= ICE_RARE_RUSH_ITEM_COST;
                iceRareRushItemCount++;
                iGameModel.getEffectManager().addInfo(l.getShopPurchasedMsg(),"- " + ICE_RARE_RUSH_ITEM_COST, l.getSKillPresentCoin() + coin);
            } else {
                iGameModel.getEffectManager().addStrEffect(l.getQuestNotEnoughMoneyMsg());
            }
        } else if (tier == 3) {
            if (coin >= ICE_LEGENDARY_RUSH_ITEM_COST) {
                coin -= ICE_LEGENDARY_RUSH_ITEM_COST;
                iceLegendaryRushItemCount++;
                iGameModel.getEffectManager().addInfo(l.getShopPurchasedMsg(),"- " + ICE_LEGENDARY_RUSH_ITEM_COST, l.getSKillPresentCoin() + coin);
            } else {
                iGameModel.getEffectManager().addStrEffect(l.getQuestNotEnoughMoneyMsg());
            }
        }
    }

    public void upgradeIceAutoCollect() {
        if (iceAutoCollectLevel == 4) {
            iGameModel.getEffectManager().addStrEffect(l.getShopMaxLevelMsg());
            return;
        }

        if (coin >= ICE_AUTO_COLLECT_UPGRADE_COST[iceAutoCollectLevel]) {
            coin -= ICE_AUTO_COLLECT_UPGRADE_COST[iceAutoCollectLevel];
            iceAutoCollectLevel++;
            iGameModel.getEffectManager().addInfo(l.getShopPurchasedMsg(),"- " + ICE_AUTO_COLLECT_UPGRADE_COST[iceAutoCollectLevel], l.getSKillPresentCoin() + coin);
        } else {
            iGameModel.getEffectManager().addStrEffect( l.getQuestNotEnoughMoneyMsg());
        }
    }

    public void purchaseIceVacuum() {
        if (coin >= ICE_VACUUM_ITEM_COST) {
            coin -= ICE_VACUUM_ITEM_COST;
            iceVacuumCount++;
            iGameModel.getEffectManager().addInfo(l.getShopPurchasedMsg(),"- " + ICE_VACUUM_ITEM_COST, l.getSKillPresentCoin() + coin);
        } else {
            iGameModel.getEffectManager().addStrEffect( l.getQuestNotEnoughMoneyMsg());
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
