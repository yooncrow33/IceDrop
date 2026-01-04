package base;

import view.*;
import view.iGameModel.IGameModel;
import view.iGameModel.IGameModelDebug;
import view.iGameModel.IGameModelQuest;
import view.iGameModel.IGameModelShop;

import java.awt.*;

public class GraphicsManager {

    final int VIRTUAL_WIDTH = 1920;
    final int VIRTUAL_HEIGHT = 1080;

    int firstQuestProgress;
    int secondQuestProgress;
    int thirdQuestProgress;

    int iceBasicRushCoolTimeBarWidth;
    int iceRareRushCoolTimeBarWidth;
    int iceLegendaryRushCoolTimeBarWidth;
    int iceVacuumCoolTimeBarWidth;

    public void renderBaseFrame(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0,10, VIRTUAL_WIDTH - 10, 10);
        g.fillRect(0, 1060, VIRTUAL_WIDTH - 10, 10);
        g.fillRect( 0,10,10, VIRTUAL_HEIGHT - 20);
        g.fillRect(1910,10,10, VIRTUAL_HEIGHT - 20);
        g.fillRect(955,10,10, VIRTUAL_HEIGHT - 20);

        g.setColor(Color.black);
        g.fillRect(0,1070,1080,10);
        g.fillRect(0,0,1080,10);
        g.fillRect(- 320,0,320,1080);
        g.fillRect(1920,0,320,1080);
        //center x = 468
        //center y = 525
    }

    public void renderTapFrame(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(965,120,945,5);
        g.fillRect(965,120,5,820);
        g.fillRect(1905,120,5,820);
        g.fillRect(965,935,945,5);

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("   INFO              SHOP       SKILLPOINT     QUESTS        SETTING", 1000, 1010);
    }

    public void renderInfoTap(Graphics g,int x, IGameModel iGameModel) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("INFO Tap", 980 + x, 165);

        g.setFont(new Font("맑은 고딕", Font.BOLD, 50));
        g.setColor(Color.yellow);
        g.drawString("current profile : " + iGameModel.getProfile(), 980 + x, 220);
        g.drawString("total played time : " + iGameModel.getLast() + " min", 980 + x, 290);
        g.drawString("session played time : " + iGameModel.getSessionPlayTime() + " min", 980 + x, 360);
    }
    public void renderShopTap(Graphics g,int x, IGameModelShop iGameModelShop, IGameModelDebug iGameModelDebug) {

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("SHOP Tap", 980 + x, 165);

        g.setColor(Color.gray);
        g.fillRect(980 + x,180,298,240);
        g.fillRect(980 + x + 298 + 10,180 ,298,240);
        g.fillRect(980 + x + 298 + 10 + 298 + 10,180 ,298,240);

        g.fillRect(980 + x,430,915,240);
        g.fillRect(980 + x,680,915,240);

        g.setColor(Color.black);
        g.fillRect(985 + x, 185, 298 - 10, 240 - 10);
        g.fillRect(1293 + x, 185, 298 - 10, 240 - 10);
        g.fillRect(1601 + x, 185, 298 - 10, 240 - 10);

        g.fillRect( 985 + x,435,905,230);
        g.fillRect( 985 + x,685,905,230);

        if (!iGameModelShop.iceBasicRush()) {
            g.setColor(Color.cyan);
            g.drawString("Cooldown", 992 + x, 395 - 30);
        } else {
            g.setColor(Color.red);
            g.drawString("Rush!", 992 + x, 395 - 30);
        }
        if (!iGameModelShop.iceRareRush()) {
            g.setColor(Color.cyan);
            g.drawString("Cooldown", 1300 + x, 395 - 30);
        } else {
            g.setColor(Color.red);
            g.drawString("Rush!", 1300 + x, 395 - 30);
        }
        if (!iGameModelShop.iceLegendaryRush()) {
            g.setColor(Color.cyan);
            g.drawString("Cooldown", 1610 + x, 395 - 30);
        } else {
            g.setColor(Color.red);
            g.drawString("Rush!", 1610 + x, 395 - 30);
        }
        if (!iGameModelShop.iceVacuuming()) {
            g.setColor(Color.cyan);
            g.drawString("Cooldown", 994 + x, 645 + 230 - 20);
        } else {
            g.setColor(Color.red);
            g.drawString("Vacuum!", 994 + x, 645 + 230 - 20);
        }

        g.drawString("Upgrade",994 + x, 425 + 230 - 40);

        g.setColor(Color.gray);
        g.fillRect(990 + x, 415 - 30 - 10, 298 - 20, 30);
        g.fillRect(1298 + x, 415 - 30 - 10, 298 - 20, 30);
        g.fillRect(1606 + x, 415 - 30 - 10, 298 - 20, 30);

        g.fillRect(990 + x, 435 + 230 - 40, 895, 30);
        g.fillRect(990 + x, 685 + 230 - 40, 895, 30);

        g.setColor(Color.cyan);
        iceBasicRushCoolTimeBarWidth = (int) (((iGameModelShop.getIceBasicRushCoolTime() - iGameModelDebug.getPlayTick()) / (float) iGameModelShop.getIceBasicRushCoolDownTick()) * 268);
        iceRareRushCoolTimeBarWidth = (int) (((iGameModelShop.getIceRareRushCoolTime() - iGameModelDebug.getPlayTick()) / (float) iGameModelShop.getIceRareRushCoolDownTick()) * 268);
        iceLegendaryRushCoolTimeBarWidth = (int) (((iGameModelShop.getIceLegendaryRushCoolTime() - iGameModelDebug.getPlayTick()) / (float) iGameModelShop.getIceLegendaryRushCoolDownTick()) * 268);
        iceVacuumCoolTimeBarWidth = (int) (((iGameModelShop.getIceVacuumCoolTime() - iGameModelDebug.getPlayTick()) / (float) iGameModelShop.getIceVacuumCoolDownTick()) * 885);
        if (iceBasicRushCoolTimeBarWidth >= 268) iceBasicRushCoolTimeBarWidth = 268;
        if (iceRareRushCoolTimeBarWidth >= 268) iceRareRushCoolTimeBarWidth = 268;
        if (iceLegendaryRushCoolTimeBarWidth >= 268) iceLegendaryRushCoolTimeBarWidth = 268;
        if (iceVacuumCoolTimeBarWidth >= 885) iceVacuumCoolTimeBarWidth = 885;
        if (iceBasicRushCoolTimeBarWidth <= 0) iceBasicRushCoolTimeBarWidth = 0;
        if (iceRareRushCoolTimeBarWidth <= 0) iceRareRushCoolTimeBarWidth = 0;
        if (iceLegendaryRushCoolTimeBarWidth <= 0) iceLegendaryRushCoolTimeBarWidth = 0;
        if (iceVacuumCoolTimeBarWidth <= 0) iceVacuumCoolTimeBarWidth = 0;

        g.fillRect( 995 + x, 416 - 30 - 10 + 5, iceBasicRushCoolTimeBarWidth, 20);
        g.fillRect(1303 + x, 416 - 30 - 10 + 5, iceRareRushCoolTimeBarWidth, 20);
        g.fillRect(1611 + x, 416 - 30 - 10 + 5, iceLegendaryRushCoolTimeBarWidth, 20);

        g.fillRect( 995 + x, 435 + 230 - 35, 885, 20);
        g.fillRect( 995 + x, 685 + 230 - 35, iceVacuumCoolTimeBarWidth, 20);

        g.setColor(Color.yellow);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("IceBasic spawn rush (Q)", 992 + x, 210);
        g.drawString("IceRare spawn rush (W)", 1300 + x, 210);
        g.drawString("IceLegendary spawn rush (E)", 1608 + x, 210);

        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Auto collect", 1000 + x, 480);
        g.drawString("Vacuum (R)", 1000 + x, 730);

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(Integer.toString(iGameModelShop.getIceBasicRushCost()) + " coin", 992 + x, 240);
        g.drawString(Integer.toString(iGameModelShop.getIceRareRushCost()) + " coin", 1300 + x, 240);
        g.drawString(Integer.toString(iGameModelShop.getIceLegendaryRushCost()) + " coin", 1608 + x, 240);

        g.drawString(Integer.toString(iGameModelShop.getIceAutoCollectCost()) + " coin", 1000 + x,515);
        g.drawString(Integer.toString(iGameModelShop.getIceVacuumCost()) + " coin", 1000 + x, 765);

        g.setColor(Color.green);
        g.fillRect(980 + x,430,915,240);
        g.drawString("Owned : " + iGameModelShop.getIceBasicRushItemCount(), 992 + x, 270);
        g.drawString("Owned : " + iGameModelShop.getIceRareRushItemCount(), 1300 + x, 270);
        g.drawString("Owned : " + iGameModelShop.getIceLegendaryRushItemCount(), 1608 + x, 270);
        g.drawString("Level : " + iGameModelShop.getIceAutoCollectLevel(), 1000 + x, 545);
        g.drawString("Owned : " + iGameModelShop.getIceVacuumCount(), 1000 + x, 795);

        g.setColor(Color.black);
        g.fillRect(980 + x,430,915,240);
    }

    public void renderSkillPointTap(Graphics g, int x) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("SKILL POINT Tap", 980 + x, 165);
    }

    public void renderQuestsTap(Graphics g,int x, IGameModelQuest iGameModelQuest) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("QUESTS Tap", 980 + x, 165);

        g.setColor(Color.gray);
        g.fillRect(980 + x,180,915,240);
        g.fillRect(980 + x,430,915,240);
        g.fillRect(980 + x,680,915,240);

        g.setColor(Color.black);
        g.fillRect( 985 + x,185,905,230);
        g.fillRect( 985 + x,435,905,230);
        g.fillRect( 985 + x,685,905,230);

        g.setColor(Color.white);
        g.drawString("Session Quest", 1000 + x, 230);
        g.drawString("Session Quest", 1000 + x, 480);
        g.drawString("Long Time Quest", 1000 + x, 730);

        g.setColor(Color.yellow);
        g.drawString(iGameModelQuest.getFirstQuestExplanation(), 1000 + x, 280);
        g.drawString(iGameModelQuest.getSecondQuestExplanation(), 1000 + x, 530);
        g.drawString("Collect 5000 Ice_Basic", 1000 + x, 780);

        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.CYAN);
        g.drawString("Reward : " + iGameModelQuest.getFirstQuestReward() + " Coins", 1000 + x, 320);
        g.drawString("Reward : " + iGameModelQuest.getSecondQuestReward() + " Coins", 1000 + x, 570);
        g.drawString("Reward : " + "Permanently grants +5 Gold per Ice collected. and 1000 coins.", 1000 + x, 820);

        if (iGameModelQuest.firstQuestCompleted()) {
            if (iGameModelQuest.firstQuestRewarded()) {
                g.setColor(Color.green);
                g.drawString("Rewarded", 1000 + x, 350);
            } else {
                g.setColor(Color.yellow);
                g.drawString("Not Rewarded", 1000 + x, 350);
            }
        } else if (!iGameModelQuest.firstQuestCompleted()) {
            g.setColor(Color.red);
            g.drawString("Not Completed", 1000 + x, 350);
        }

        if (iGameModelQuest.secondQuestCompleted()) {
            if (iGameModelQuest.secondQuestRewarded()) {
                g.setColor(Color.green);
                g.drawString("Rewarded", 1000 + x, 600);
            } else {
                g.setColor(Color.yellow);
                g.drawString("Not Rewarded", 1000 + x, 600);
            }
        } else if (!iGameModelQuest.secondQuestCompleted()) {
            g.setColor(Color.red);
            g.drawString("Not Completed", 1000 + x, 600);
        }

        if (iGameModelQuest.thirdQuestCompleted()) {
            if (iGameModelQuest.thirdQuestRewarded()) {
                g.setColor(Color.green);
                g.drawString("Rewarded", 1000 + x, 850);
            } else {
                g.setColor(Color.yellow);
                g.drawString("Not Rewarded", 1000 + x, 850);
            }
        } else if (!iGameModelQuest.thirdQuestCompleted()) {
            g.setColor(Color.red);
            g.drawString("Not Completed", 1000 + x, 850);
        }

        firstQuestProgress = (int) ((iGameModelQuest.getFirstQuestProgress() / (float) iGameModelQuest.getFirstQuestGoal()) * 905);
        secondQuestProgress = (int) ((iGameModelQuest.getSecondQuestProgress() / (float) iGameModelQuest.getSecondQuestGoal()) * 905);
        thirdQuestProgress = (int) ((iGameModelQuest.getThirdQuestProgress() / (float) iGameModelQuest.getThirdQuestGoal()) * 905);
        if (firstQuestProgress >= 905) firstQuestProgress = 905;
        if (secondQuestProgress >= 905) secondQuestProgress = 905;
        if (thirdQuestProgress >= 905) thirdQuestProgress = 905;

        g.setColor(Color.green);
        g.fillRect(985 + x,395,firstQuestProgress, 20);
        g.fillRect(985 + x, 645,secondQuestProgress, 20);
        g.fillRect(985 + x, 895,thirdQuestProgress, 20);

        //

    }
    public void renderSettingTap(Graphics g, int x) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("SETTING Tap", 980 + x, 165);
    }
    public void renderDebugTap(Graphics g,int x, IViewMetrics viewMetrics, ISystemMonitor systemMonitor, IGameModelDebug gameModelDebug, IMouse mouse) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("DEBUG Tap", 980 + x, 165);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("windowWidth: " + viewMetrics.getWindowWidth(), 980 + x, 200);
        g.drawString("windowHeight: " + viewMetrics.getWindowHeight(), 980 + x, 230);
        g.drawString("scaleX: " + viewMetrics.getScaleX(), 980 + x, 260);
        g.drawString("scaleY: " + viewMetrics.getScaleY(), 980 + x, 290);
        g.drawString("currentScale: " + viewMetrics.getCurrentScale(), 980 + x, 320);
        g.drawString("currentXOffset: " + viewMetrics.getCurrentXOffset(), 980 + x, 350);
        g.drawString("currentYOffset: " + viewMetrics.getCurrentYOffset(), 980 + x, 380);
        g.drawString("used memory: " + systemMonitor.getUsedMemory() + "MB", 980 + x, 410);
        g.drawString("total memory: " + systemMonitor.getTotalMemory() + "MB", 980 + x, 440);
        g.drawString("free memory: " + systemMonitor.getFreeMemory() + "MB", 980 + x, 470);
        g.drawString("cpu usage: " + systemMonitor.getCpuPercentage() + "%", 980 + x, 500);
        g.drawString("moveX: " + mouse.getVirtualMouseX(), 980 + x, 530);
        g.drawString("moveY: " + mouse.getVirtualMouseY(), 980 + x, 560);
        g.drawString("model.ice.Ice_Basic Count: " + gameModelDebug.getIce_BasicCount(), 980 + x, 590);
        g.drawString("model.ice.Ice_Rare Count: " + gameModelDebug.getIce_RareCount(), 980 + x, 620);
        g.drawString("model.ice.Ice_Legendary Count: " + gameModelDebug.getIce_LegendaryCount(), 980 + x, 650);
        g.drawString("playTicks: " + gameModelDebug.getPlayTick(), 980 + x, 680);
    }
    public void renderTapBar(Graphics g, int tap, int tapBarX) {
        if (tap != 6) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.RED);
        }
        g.fillRect(tapBarX,940, 189,20);
    }
}
