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

    public void renderBaseFrame(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0,10, VIRTUAL_WIDTH - 10, 10);
        g.fillRect(0, 1060, VIRTUAL_WIDTH - 10, 10);
        g.fillRect( 0,10,10, VIRTUAL_HEIGHT - 20);
        g.fillRect(1910,10,10, VIRTUAL_HEIGHT - 20);
        g.fillRect(955,10,10, VIRTUAL_HEIGHT - 20);
    }

    public void renderTapFrame(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(965,120,945,820);
        g.setColor(Color.black);
        g.fillRect(970,125,935,810);

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("   INFO              SHOP       SKILLPOINT     QUESTS        SETTING", 1000, 1010);
    }

    public void renderInfoTap(Graphics g, IGameModel iGameModel) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("INFO Tap", 980, 165);

        g.setFont(new Font("맑은 고딕", Font.BOLD, 50));
        g.setColor(Color.yellow);
        g.drawString("current profile : " + iGameModel.getProfile(), 980, 220);
        g.drawString("total played time : " + iGameModel.getLast() + " min", 980, 290);
        g.drawString("session played time : " + iGameModel.getSessionPlayTime() + " min", 980, 360);
    }
    public void renderShopTap(Graphics g, IGameModelShop iGameModelShop) {
        /*
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("SHOP Tap", 980, 165);

        g.setColor(Color.gray);
        g.fillRect(980,180,298,240);
        g.fillRect(980 + 298 + 10,180 ,298,240);
        g.fillRect(980 + 298 + 10 + 298 + 10,180 ,298,240);

        g.fillRect(980,430,915,240);
        g.fillRect(980,680,915,240);

        g.setColor(Color.black);
        g.fillRect(985, 185, 298 - 10, 240 - 10);
        g.fillRect(1293, 185, 298 - 10, 240 - 10);
        g.fillRect(1601, 185, 298 - 10, 240 - 10);

        g.fillRect( 985,435,905,230);
        g.fillRect( 985,685,905,230);

        g.setColor(Color.yellow);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("IceBasic spawn rush", 992, 210);
        g.drawString("IceRare spawn rush", 1300, 210);
        g.drawString("IceLegendary spawn rush", 1608, 210);

        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Auto collect", 1000, 480);
        g.drawString("Vacuum", 1000, 730);

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("33 coin", 992, 225);
        g.drawString("22 coin", 1300, 225);
        g.drawString("87 coin", 1608, 225);

         */

    }
    public void renderSkillPointTap(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("SKILL POINT Tap", 980, 165);
    }
    public void renderQuestsTap(Graphics g, IGameModelQuest iGameModelQuest) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("QUESTS Tap", 980, 165);

        g.setColor(Color.gray);
        g.fillRect(980,180,915,240);
        g.fillRect(980,430,915,240);
        g.fillRect(980,680,915,240);

        g.setColor(Color.black);
        g.fillRect( 985,185,905,230);
        g.fillRect( 985,435,905,230);
        g.fillRect( 985,685,905,230);

        g.setColor(Color.white);
        g.drawString("Session Quest", 1000, 230);
        g.drawString("Session Quest", 1000, 480);
        g.drawString("Long Time Quest", 1000, 730);

        g.setColor(Color.yellow);
        g.drawString(iGameModelQuest.getFirstQuestExplanation(), 1000, 280);
        g.drawString(iGameModelQuest.getSecondQuestExplanation(), 1000, 530);
        g.drawString("Collect 5000 Ice_Basic", 1000, 780);

        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.CYAN);
        g.drawString("Reward : " + iGameModelQuest.getFirstQuestReward() + " Coins", 1000, 320);
        g.drawString("Reward : " + iGameModelQuest.getSecondQuestReward() + " Coins", 1000, 570);
        g.drawString("Reward : " + "Permanently grants +5 Gold per Ice collected. and 1000 coins.", 1000, 820);

        if (iGameModelQuest.firstQuestCompleted()) {
            if (iGameModelQuest.firstQuestRewarded()) {
                g.setColor(Color.green);
                g.drawString("Rewarded", 1000, 350);
            } else {
                g.setColor(Color.yellow);
                g.drawString("Not Rewarded", 1000, 350);
            }
        } else if (!iGameModelQuest.firstQuestCompleted()) {
            g.setColor(Color.red);
            g.drawString("Not Completed", 1000, 350);
        }

        if (iGameModelQuest.secondQuestCompleted()) {
            if (iGameModelQuest.secondQuestRewarded()) {
                g.setColor(Color.green);
                g.drawString("Rewarded", 1000, 600);
            } else {
                g.setColor(Color.yellow);
                g.drawString("Not Rewarded", 1000, 600);
            }
        } else if (!iGameModelQuest.secondQuestCompleted()) {
            g.setColor(Color.red);
            g.drawString("Not Completed", 1000, 600);
        }

        if (iGameModelQuest.thirdQuestCompleted()) {
            if (iGameModelQuest.thirdQuestRewarded()) {
                g.setColor(Color.green);
                g.drawString("Rewarded", 1000, 850);
            } else {
                g.setColor(Color.yellow);
                g.drawString("Not Rewarded", 1000, 850);
            }
        } else if (!iGameModelQuest.thirdQuestCompleted()) {
            g.setColor(Color.red);
            g.drawString("Not Completed", 1000, 850);
        }

        int firstQuestProgress = (int) ((iGameModelQuest.getFirstQuestProgress() / (float) iGameModelQuest.getFirstQuestGoal()) * 905);
        int secondQuestProgress = (int) ((iGameModelQuest.getSecondQuestProgress() / (float) iGameModelQuest.getSecondQuestGoal()) * 905);
        int thirdQuestProgress = (int) ((iGameModelQuest.getThirdQuestProgress() / (float) iGameModelQuest.getThirdQuestGoal()) * 905);
        if (firstQuestProgress >= 905) firstQuestProgress = 905;
        if (secondQuestProgress >= 905) secondQuestProgress = 905;
        if (thirdQuestProgress >= 905) thirdQuestProgress = 905;

        g.setColor(Color.green);
        g.fillRect(985,395,firstQuestProgress, 20);
        g.fillRect(985, 645,secondQuestProgress, 20);
        g.fillRect(985, 895,thirdQuestProgress, 20);

        //

    }
    public void renderSettingTap(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("SETTING Tap", 980, 165);
    }
    public void renderDebugTap(Graphics g, IViewMetrics viewMetrics, ISystemMonitor systemMonitor, IGameModelDebug gameModelDebug, IMouse mouse) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("DEBUG Tap", 980, 165);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("windowWidth: " + viewMetrics.getWindowWidth(), 980, 200);
        g.drawString("windowHeight: " + viewMetrics.getWindowHeight(), 980, 230);
        g.drawString("scaleX: " + viewMetrics.getScaleX(), 980, 260);
        g.drawString("scaleY: " + viewMetrics.getScaleY(), 980, 290);
        g.drawString("currentScale: " + viewMetrics.getCurrentScale(), 980, 320);
        g.drawString("currentXOffset: " + viewMetrics.getCurrentXOffset(), 980, 350);
        g.drawString("currentYOffset: " + viewMetrics.getCurrentYOffset(), 980, 380);
        g.drawString("used memory: " + systemMonitor.getUsedMemory() + "MB", 980, 410);
        g.drawString("total memory: " + systemMonitor.getTotalMemory() + "MB", 980, 440);
        g.drawString("free memory: " + systemMonitor.getFreeMemory() + "MB", 980, 470);
        g.drawString("cpu usage: " + systemMonitor.getCpuPercentage() + "%", 980, 500);
        g.drawString("moveX: " + mouse.getVirtualMouseX(), 980, 530);
        g.drawString("moveY: " + mouse.getVirtualMouseY(), 980, 560);
        g.drawString("model.ice.Ice_Basic Count: " + gameModelDebug.getIce_BasicCount(), 980, 590);
        g.drawString("model.ice.Ice_Rare Count: " + gameModelDebug.getIce_RareCount(), 980, 620);
        g.drawString("model.ice.Ice_Legendary Count: " + gameModelDebug.getIce_LegendaryCount(), 980, 650);
        g.drawString("playTicks: " + gameModelDebug.getPlayTick(), 980, 680);
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
