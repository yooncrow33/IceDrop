package base.gameModel.ice;

import model.effects.IInfo;
import model.ice.Ice;
import model.ice.IceBasic;
import model.ice.IceLegendary;
import model.ice.IceRare;
import view.IMouse;

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

    double iceAutoCollectChance[] = {0,0.0001,0.0005,0.001,0.005,0.007};

    IIceManager im;
    IInfo in;
    IMouse iMouse;

    ArrayList<Ice> ices = new ArrayList<>();
    private final double iceBasicCurrentSpawnChanceList[] = {1,0.03,0.04,0.05,0.06};
    private final double iceRareCurrentSpawnChanceList[] = {1,0.008,0.015,0.025,0.035};
    private final double iceLegendaryCurrentSpawnChanceList[] = {1,0.001,0.002,0.005,0.01};

    public IceManager(IIceManager im, IInfo in,IMouse iMouse) {
        this.in = in;
        this.im = im;
        this.iMouse = iMouse;
    }

    public void addIceBasic() { ices.add(new IceBasic()); }
    public void addIceRare() { ices.add(new IceRare()); }
    public void addIceLegendary() { ices.add(new IceLegendary()); }

    public void update(double dt) {
        if (!im.isIceVacuuming()) {
            if (im.isIceRush(1)) {
                addIceBasic();
            } else {
                if (Math.random() < iceBasicCurrentSpawnChanceList[im.getIceSpawnChanceLevel(1)]) {
                    addIceBasic();
                }
            }
            if (im.isIceRush(2)) {
                addIceRare();
            } else {
                if (Math.random() < iceRareCurrentSpawnChanceList[im.getIceSpawnChanceLevel(2)]) {
                    addIceRare();
                }
            }
            if (im.isIceRush(3)) {
                addIceLegendary();
            } else {
                if (Math.random() < iceLegendaryCurrentSpawnChanceList[im.getIceSpawnChanceLevel(3)]) {
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
                if (Math.random() > iceAutoCollectChance[im.getIceAutoCollectLevel()]) {
                    continue;
                } else {
                    in.addInfo("Auto Collected!", "collected Ice : Basic", "");
                    collectIce(1);
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
            if (im.isClicked()) {
                if (ice.shouldBeCollected(iMouse.getVirtualMouseX(), iMouse.getVirtualMouseY(), im.getClickOffsetLevel())) {
                    ices.remove(i);
                    switch (ice.getTier()) {
                        case 1 : im.addIntEffect(ice.getValue());
                            collectIce(1);
                            break;
                        case 2 : im.addIntEffect(ice.getValue());
                            collectIce(2);
                            break;
                        case 3 : im.addIntEffect(ice.getValue());
                            collectIce(3);
                            break;
                        default: im.addIntEffect(ice.getValue());
                            collectIce(1);
                            break;
                    }
                }
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
        ices.clear();

        im.addCoin(collectedIceBasics * ICE_BASIC_VALUE);
        im.addCoin(collectedIceRares * ICE_RARE_VALUE);
        im.addCoin(collectedIceLegendaryes * ICE_LEGENDARY_VALUE);
        im.addXp(xpGained);


        im.addInfo("Ice Vacuum Activated!","You get coin : " + (collectedIceBasics * ICE_BASIC_VALUE + collectedIceRares * ICE_RARE_VALUE + collectedIceLegendaryes * ICE_LEGENDARY_VALUE) + "!", "You get xp : " + xpGained);

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
    public int getIceBasicTotalCollectCount() {return iceBasicCollectedCount + im.getLastIceBasicCollectCount(); }

    public void collectIce(int tier) {
        if (tier == 1) {
            iceBasicCollectedCount++;
            im.addXp(ICE_BASIC_VALUE);
            if (im.isThirdQuestComplete()) {
                im.addCoin(ICE_BASIC_VALUE);
                im.addCoin(ICE_BASIC_VALUE + im.getQuestManager().getBonus());
            } else {
                im.addCoin(ICE_BASIC_VALUE);
            }
        } else if (tier == 2) {
            iceRareCollectedCount++;
            im.addXp(ICE_RARE_VALUE);
            if (im.isThirdQuestComplete()) {
                im.addCoin(ICE_RARE_VALUE);
                im.addCoin(ICE_RARE_VALUE + im.getQuestManager().getBonus());
            } else {
                im.addCoin(ICE_RARE_VALUE);
            }
        } else if (tier == 3) {
            iceLegendaryCollectedCount++;
            im.addXp(ICE_LEGENDARY_VALUE);
            if (im.isThirdQuestComplete()) {
                im.addCoin(ICE_LEGENDARY_VALUE);
                im.addCoin(ICE_LEGENDARY_VALUE + im.getQuestManager().getBonus());
            } else {
                im.addCoin(ICE_LEGENDARY_VALUE);
            }
        }
    }
}
