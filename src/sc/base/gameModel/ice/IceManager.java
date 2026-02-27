package sc.base.gameModel.ice;

import sc.lang.Lang;
import sc.model.ice.Ice;
import sc.model.ice.IceBasic;
import sc.model.ice.IceLegendary;
import sc.model.ice.IceRare;
import sc.view.IMouse;
import sc.view.iGameModel.IGameModel;

import java.awt.*;
import java.util.ArrayList;

public class IceManager {

    final int ICE_BASIC_VALUE = 1;
    final int ICE_RARE_VALUE = 5;
    final int ICE_LEGENDARY_VALUE = 10;

    final int ICE_BASIC_XP = 10;
    final int ICE_RARE_XP = 20;
    final int ICE_LEGENDARY_XP = 40;

    int iceBasicCollectedCount = 0;
    int iceRareCollectedCount = 0;
    int iceLegendaryCollectedCount = 0;

    int lastIceBasicCollectCount;
    int lastIceRareCollectCount;
    int lastIceLegendaryCollectCount;

    public void loadLastIceBasicCollectCount(int lastIceBasicCollectCount) {
        this.lastIceBasicCollectCount = lastIceBasicCollectCount;
    }

    public int getLastIceBasicCollectCount() {
        return lastIceBasicCollectCount;
    }

    public int getLastIceRareCollectCount() {
        return lastIceRareCollectCount;
    }

    public int getLastIceLegendaryCollectCount() {
        return lastIceLegendaryCollectCount;
    }

    public void loadLastIceRareCollectCount(int lastIceRareCollectCount) {
        this.lastIceRareCollectCount = lastIceRareCollectCount;
    }

    public void loadLastIceLegendaryCollectCount(int lastIceLegendaryCollectCount) {
        this.lastIceLegendaryCollectCount = lastIceLegendaryCollectCount;
    }

    double iceAutoCollectChance[] = {0,0.001,0.005,0.01,0.05,0.07};

    IGameModel iGameModel;
    IMouse iMouse;
    Lang l;

    ArrayList<Ice> ices = new ArrayList<>();
    private final double iceBasicCurrentSpawnChanceList[] = {1,0.03,0.04,0.05,0.06};
    private final double iceRareCurrentSpawnChanceList[] = {1,0.008,0.015,0.025,0.035};
    private final double iceLegendaryCurrentSpawnChanceList[] = {1,0.001,0.002,0.005,0.01};

    private int faTick = -1;
    private int fa1tick = -1;
    private int fa2tick = -1;

    public IceManager(IGameModel iGameModel, IMouse iMouse, Lang l) {
        this.iGameModel = iGameModel;
        this.iMouse = iMouse;
        this.l = l;
    }

    private void addIceBasic() { ices.add(new IceBasic()); }
    private void addIceRare() { ices.add(new IceRare()); }
    private void addIceLegendary() { ices.add(new IceLegendary()); }

    public void update(double dt) {
        if (!iGameModel.getShopManager().isIceVacuuming()) {
            if (iGameModel.getShopManager().isIceBasicRush()) {
                addIceBasic();
            } else {
                if (Math.random() < iceBasicCurrentSpawnChanceList[iGameModel.getSkillManager().getIceBasicSpawnChanceLevel()]) {
                    addIceBasic();
                }
            }
            if (iGameModel.getShopManager().isIceRareRush()) {
                addIceRare();
            } else {
                if (Math.random() < iceRareCurrentSpawnChanceList[iGameModel.getSkillManager().getIceRareSpawnChanceLevel()]) {
                    addIceRare();
                }
            }
            if (iGameModel.getShopManager().isIceLegendaryRush()) {
                addIceLegendary();
            } else {
                if (Math.random() < iceLegendaryCurrentSpawnChanceList[iGameModel.getSkillManager().getIceLegendarySpawnChanceLevel()]) {
                    addIceLegendary();
                }
            }
        }
        //ice
        for (int i = ices.size() - 1; i >= 0; i--) {
            Ice ice = ices.get(i);
            ice.update(dt);
            if (ice.shouldBeRemoved()) {
                ices.remove(i);
                if (Math.random() > iceAutoCollectChance[iGameModel.getShopManager().getIceAutoCollectLevel()]) {
                    continue;
                } else {
                    iGameModel.getEffectManager().addInfo(l.getInfoAutoCollectedMsg(), l.getInfoCollectedIceMsg()+ ice.name(), "");
                    switch (ice.getTier()) {
                        case 1 : collectIce(1);
                            break;
                        case 2 : collectIce(2);
                            break;
                        case 3 : collectIce(3);
                            break;
                        default: collectIce(1);
                            System.out.println("Error");
                            break;
                    }
                }
                continue;
            }
            if (iGameModel.isClicked()) {
                if (ice.shouldBeCollected(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY(), iGameModel.getSkillManager().getClickOffsetLevel())) {
                    iGameModel.getEffectManager().addFa(ice.getX() + (ice.getSize() / 2), ice.getY() + (ice.getSize() / 2));
                    ices.remove(i);
                    switch (ice.getTier()) {
                        case 1:
                            iGameModel.getEffectManager().addIntEffect(ice.getValue());
                            collectIce(1);
                            break;
                        case 2:
                            iGameModel.getEffectManager().addIntEffect(ice.getValue());
                            collectIce(2);
                            break;
                        case 3:
                            iGameModel.getEffectManager().addIntEffect(ice.getValue());
                            collectIce(3);
                            break;
                        default:
                            iGameModel.getEffectManager().addIntEffect(ice.getValue());
                            collectIce(1);
                            break;
                    }
                }
            }
            if (fa1tick == iGameModel.getTickManager().getPlayTick()) {
                iGameModel.getEffectManager().addFaUltra();
            }
            if (fa2tick == iGameModel.getTickManager().getPlayTick()) {
                iGameModel.getEffectManager().addFaUltra();
            }
        }
    }

    public void renderIces(Graphics g) {
        for (Ice ice : ices) {
            ice.draw(g);
        }
    }
    public void iceVacuumActive() {
        for (Ice ice : ices) {
            ice.setVacuumActive(true);
        }
    }

    public void iceVacuumClear() {
        int collectedIceBasics = 0;
        int collectedIceRares = 0;
        int collectedIceLegendaryes = 0;
        int xpGained = 0;


        for (Ice ice : ices) {
            switch (ice.getTier()) {
                case 1: // Basic
                    collectedIceBasics++;
                    addIceBasicCollectCount();
                    xpGained += ICE_BASIC_XP;
                    break;
                case 2: // Rare
                    collectedIceRares++;
                    addIceRareCollectCount();
                    xpGained += ICE_RARE_XP;
                    break;
                case 3: // Legendary
                    collectedIceLegendaryes++;
                    addIceLegendaryCollectCount();
                    xpGained += ICE_LEGENDARY_XP;
                    break;
            }
        }
        iGameModel.getEffectManager().addFaUltra();
        faTick = iGameModel.getTickManager().getPlayTick();
        fa1tick = faTick + 8;
        fa2tick = fa1tick + 10;
        ices.clear();

        iGameModel.getShopManager().addCoin(collectedIceBasics * ICE_BASIC_VALUE);
        iGameModel.getShopManager().addCoin(collectedIceRares * ICE_RARE_VALUE);
        iGameModel.getShopManager().addCoin(collectedIceLegendaryes * ICE_LEGENDARY_VALUE);
        iGameModel.getSkillManager().addXp(xpGained);


        iGameModel.getEffectManager().addInfo(l.getInfoVacuumActivatedMsg(),l.getInfoGetCoinMsg() + (collectedIceBasics * ICE_BASIC_VALUE + collectedIceRares * ICE_RARE_VALUE + collectedIceLegendaryes * ICE_LEGENDARY_VALUE) + "!",l.getInfoGetXpMsg() + xpGained);

    }

    private void addIceBasicCollectCount() {
        iceBasicCollectedCount++;
    }
    private void addIceRareCollectCount() {
        iceRareCollectedCount++;
    }
    private void addIceLegendaryCollectCount() {
        iceLegendaryCollectedCount++;
    }
    public int getIceBasicCollectedCount() {
        return iceBasicCollectedCount;
    }
    public int getIceRareCollectedCount() {
        return  iceRareCollectedCount;
    }
    public int getIceLegendaryCollectedCount() {
        return iceLegendaryCollectedCount;
    }
    public int getIceBasicTotalCollectCount() {return iceBasicCollectedCount + lastIceBasicCollectCount; }

    public void collectIce(int tier) {
        //ㅠㅠㅎ
        if (tier == 1) {
            iceBasicCollectedCount++;
            if (iGameModel.getQuestManager().isThirdQuestComplete()) {
                iGameModel.getShopManager().addCoin(ICE_BASIC_VALUE);
                iGameModel.getShopManager().addCoin(ICE_BASIC_VALUE + iGameModel.getQuestManager().getBonus());
            } else {
                iGameModel.getShopManager().addCoin(ICE_BASIC_VALUE);
            }
        } else if (tier == 2) {
            iceRareCollectedCount++;
            iGameModel.getSkillManager().addXp(ICE_RARE_VALUE/2);
            if (iGameModel.getQuestManager().isThirdQuestComplete()) {
                iGameModel.getShopManager().addCoin(ICE_RARE_VALUE);
                iGameModel.getShopManager().addCoin(ICE_RARE_VALUE + iGameModel.getQuestManager().getBonus());
            } else {
                iGameModel.getShopManager().addCoin(ICE_RARE_VALUE);
            }
        } else if (tier == 3) {
            iceLegendaryCollectedCount++;
            iGameModel.getSkillManager().addXp(ICE_LEGENDARY_VALUE/2);
            if (iGameModel.getQuestManager().isThirdQuestComplete()) {
                iGameModel.getShopManager().addCoin(ICE_LEGENDARY_VALUE);
                iGameModel.getShopManager().addCoin(ICE_LEGENDARY_VALUE + iGameModel.getQuestManager().getBonus());
            } else {
                iGameModel.getShopManager().addCoin(ICE_LEGENDARY_VALUE);
            }
        }
    }
}
