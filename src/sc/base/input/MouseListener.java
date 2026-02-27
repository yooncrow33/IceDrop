package sc.base.input;

import sc.base.gameModel.GameModel;
import sc.base.ViewMetrics;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter {
    
    private GameModel gameModel;
    private ViewMetrics viewMetrics;

    Rectangle quest1Rect = new Rectangle(980, 180, 915, 240);
    Rectangle quest2Rect = new Rectangle(980, 430, 915, 240);
    Rectangle quest3Rect = new Rectangle(980, 680, 915, 240);
    Rectangle questRefreshRect = new Rectangle(1340, 130, 550, 45);

    Rectangle iceBasicRush = new Rectangle(985, 185, 298 - 10, 240 - 10);
    Rectangle iceRareRush = new Rectangle(1293, 185, 298 - 10, 240 - 10);
    Rectangle iceLegendaryRush = new Rectangle(1601, 185, 298 - 10, 240 - 10);

    Rectangle iceAutoCollect = new Rectangle(980,430,915,240);
    Rectangle iceVacuum = new Rectangle(980,680,915,240);
    
    public MouseListener(GameModel gameModel, ViewMetrics viewMetrics) {
        this.gameModel = gameModel;
        this.viewMetrics = viewMetrics;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        gameModel.setClicked();
        if (gameModel.getTabManager().getTap() == 4) {
            if (quest1Rect.contains(viewMetrics.getVirtualMouseX(),viewMetrics.getVirtualMouseY())) {
                gameModel.getQuestManager().clamRewardedQuest(1);
            }
            if (quest2Rect.contains(viewMetrics.getVirtualMouseX(),viewMetrics.getVirtualMouseY())) {
                gameModel.getQuestManager().clamRewardedQuest(2);
            }
            if (quest3Rect.contains(viewMetrics.getVirtualMouseX(),viewMetrics.getVirtualMouseY())) {
                gameModel.getQuestManager().clamRewardedQuest(3);
            }
            if (questRefreshRect.contains(viewMetrics.getVirtualMouseX(),viewMetrics.getVirtualMouseY())) {
                gameModel.getQuestManager().refreshQuests();
            }

        }
        if (gameModel.getTabManager().getTap() == 3) {
            if (iceBasicRush.contains(viewMetrics.getVirtualMouseX(),viewMetrics.getVirtualMouseY())) {
                gameModel.getSkillManager().upgradeIceSpawnChance(1);
            }
            if (iceRareRush.contains(viewMetrics.getVirtualMouseX(),viewMetrics.getVirtualMouseY())) {
                gameModel.getSkillManager().upgradeIceSpawnChance(2);
            }
            if (iceLegendaryRush.contains(viewMetrics.getVirtualMouseX(),viewMetrics.getVirtualMouseY())) {
                gameModel.getSkillManager().upgradeIceSpawnChance(3);
            }
            if (iceAutoCollect.contains(viewMetrics.getVirtualMouseX(),viewMetrics.getVirtualMouseY())) {
                gameModel.getSkillManager().upgradeClickOffset();
            }
            if (iceVacuum.contains(viewMetrics.getVirtualMouseX(),viewMetrics.getVirtualMouseY())) {
                gameModel.getSkillManager().upgradeItemCoolTime();
            }
        }

        if (gameModel.getTabManager().getTap() == 2) {
            if (iceBasicRush.contains(viewMetrics.getVirtualMouseX(),viewMetrics.getVirtualMouseY())) {
                gameModel.getShopManager().purchaseIceRushItem(1);
            }
            if (iceRareRush.contains(viewMetrics.getVirtualMouseX(),viewMetrics.getVirtualMouseY())) {
                gameModel.getShopManager().purchaseIceRushItem(2);
            }
            if (iceLegendaryRush.contains(viewMetrics.getVirtualMouseX(),viewMetrics.getVirtualMouseY())) {
                gameModel.getShopManager().purchaseIceRushItem(3);
            }
            if (iceAutoCollect.contains(viewMetrics.getVirtualMouseX(),viewMetrics.getVirtualMouseY())) {
                gameModel.getShopManager().upgradeIceAutoCollect();
            }
            if (iceVacuum.contains(viewMetrics.getVirtualMouseX(),viewMetrics.getVirtualMouseY())) {
                gameModel.getShopManager().purchaseIceVacuum();
            }
        }

    }
}
