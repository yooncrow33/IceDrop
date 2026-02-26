package sc.base;

import sc.lang.Lang;
import sc.view.iGameModel.*;

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
    int iceAutoCollectLevelBarWidth;
    int iceVacuumCoolTimeBarWidth;

    int iceBasicSpawnChanceLevelBarWidth;
    int iceRareSpawnChanceLevelBarWidth;
    int iceLegendarySpawnChanceLevelBarWidth;
    int clickOffsetLevelBarWidth;
    int itemCoolTimeLevelBarWidth;

    Color color1 = new Color(191,222,255); // info background color
    Color color3 = new Color(0,74,153); // info top bar color
    Color color2 = new Color(15,135,255); // info right square color

    Color gray = new Color(50, 60, 75);
    Color black = new Color(20, 25, 35);
    Color green = new Color(80, 220, 160);
    Color blue = new Color(0, 200, 255);
    Color yellow = new Color(255, 200, 90);
    Color white = new Color(200,215,235);

    static final Color BG_MAIN = new Color(20, 30, 45);      // 거의 검푸른 배경
    static final Color BG_PANEL = new Color(40, 60, 90);    // 패널 배경
    static final Color BAR_TOP = new Color(0, 140, 255);    // 포인트 블루
    static final Color ACCENT = new Color(0, 220, 255);     // 강조
    static final Color TEXT_MAIN = Color.WHITE;
    static final Color TEXT_SUB = new Color(180, 200, 220);
    static final Color BORDER = new Color(0, 90, 160);

    int xpBarWidth;

    Lang l;

    public GraphicsManager(Lang l) {
        this.l = l;
    }

    public void renderBackGround(Graphics g) {
        g.setColor(black);
        Graphics2D g2 = (Graphics2D) g;

        LinearGradientPaint leftBG = new LinearGradientPaint(
                0, 0,
                0, VIRTUAL_HEIGHT,
                new float[]{0f, 1f},
                new Color[]{
                        new Color(15, 25, 40),   // 위쪽 어두움
                        new Color(30, 45, 70)    // 아래쪽 살짝 밝음
                }
        );

        g2.setPaint(leftBG);
        g2.fillRect(10, 10, 955, 1060);

// 복구
        g2.setPaint(Color.BLACK);

        g.setColor(new Color(180, 220, 255));
        g.setFont(new Font("Arial", Font.BOLD, 26));
        g.drawString(l.getGameTitle(), 40, 60);

        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.setColor(new Color(120, 160, 200));
        g.drawString(l.getSubText(), 40, 80);
// 버전 정보 (구석으로 이동 & 투명도 활용)
        g.setFont(new Font("Monospaced", Font.PLAIN, 12)); // 고정폭 글꼴이 버전 정보에 잘 어울림
        g.setColor(new Color(120, 160, 200, 150)); // 마지막 150은 투명도 (0~255)
        g.drawString("v1.13.5-alpha", 20, VIRTUAL_HEIGHT - 30);
        // 화면 왼쪽 아래 끝

    }

    public void renderBaseFrame(Graphics g) {
        g.setColor(white);
        g.fillRect(0,10, VIRTUAL_WIDTH - 10, 10);
        g.fillRect(0, 1060, VIRTUAL_WIDTH - 10, 10);
        g.fillRect( 0,10,10, VIRTUAL_HEIGHT - 20);
        g.fillRect(1910,10,10, VIRTUAL_HEIGHT - 20);
        g.fillRect(955,10,10, VIRTUAL_HEIGHT - 20);

        g.setColor(black);
        int i = 10000; // 너무 크면 에러 날 수 있으니 적당히 큰 값
        g.fillRect(-i, -i, i, i * 3);
        g.fillRect(1920, -i, i, i * 3);
        g.fillRect(-i, -i+10, i * 3, i);
        g.fillRect(-i, 1070, i * 3, i);
        //center x = 468
        //center y = 525
    }

    public void renderTapFrame(Graphics g) {
        g.setColor(gray);
        g.fillRect(965,120,945,5);
        g.fillRect(965,120,5,820);
        g.fillRect(1905,120,5,820);
        g.fillRect(965,935,945,5);

        g.setColor(white);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("   INFO              SHOP       SKILLPOINT     QUESTS        SETTING", 1000, 1010);

        g.setColor(black);
        g.fillRect(10,10,955,1060);
    }

    public void renderInfoTap(Graphics g,int x, IGameModel iGameModel) {
        g.setColor(white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(l.getInfoTitle(), 980 + x, 165);

        g.setFont(new Font("맑은 고딕", Font.BOLD, 50));
        g.setColor(yellow);
        g.drawString(l.getInfoProfile() + iGameModel.getProfile(), 980 + x, 220);
        g.drawString(l.getInfoTotalPlayTime() + iGameModel.getLast() + " min", 980 + x, 290);
        g.drawString(l.getInfoSessionPlayTime() + iGameModel.getSessionPlayTime() + " min", 980 + x, 360);
    }
    public void renderShopTap(Graphics g,int x, IGameModelShop iGameModelShop, IGameModelTick iGameModelTick) {
        g.setColor(white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(l.getShopTitle(), 980 + x, 165);

        g.setColor(gray);
        g.fillRect(980 + x,180,298,240);
        g.fillRect(980 + x + 298 + 10,180 ,298,240);
        g.fillRect(980 + x + 298 + 10 + 298 + 10,180 ,298,240);

        g.fillRect(980 + x,430,915,240);
        g.fillRect(980 + x,680,915,240);

        g.setColor(black);
        g.fillRect(985 + x, 185, 298 - 10, 240 - 10);
        g.fillRect(1293 + x, 185, 298 - 10, 240 - 10);
        g.fillRect(1601 + x, 185, 298 - 10, 240 - 10);

        g.fillRect( 985 + x,435,905,230);
        g.fillRect( 985 + x,685,905,230);

        if (!iGameModelShop.isIceBasicRush()) {
            g.setColor(blue);
            g.drawString(l.getShopCooldown(), 992 + x, 395 - 30);
        } else {
            g.setColor(Color.red);
            g.drawString(l.getShopRush(), 992 + x, 395 - 30);
        }
        if (!iGameModelShop.isIceRareRush()) {
            g.setColor(blue);
            g.drawString(l.getShopCooldown(), 1300 + x, 395 - 30);
        } else {
            g.setColor(Color.red);
            g.drawString(l.getShopRush(), 1300 + x, 395 - 30);
        }
        if (!iGameModelShop.isIceLegendaryRush()) {
            g.setColor(blue);
            g.drawString(l.getShopCooldown(), 1610 + x, 395 - 30);
        } else {
            g.setColor(Color.red);
            g.drawString(l.getShopRush(), 1610 + x, 395 - 30);
        }
        if (!iGameModelShop.isIceVacuuming()) {
            g.setColor(blue);
            g.drawString(l.getShopCooldown(), 994 + x, 645 + 230 - 20);
        } else {
            g.setColor(Color.red);
            g.drawString(l.getShopVacuum(), 994 + x, 645 + 230 - 20);
        }

        g.drawString(l.getShopUpgrade(),994 + x, 425 + 230 - 40);

        g.setColor(gray);
        g.fillRect(990 + x, 415 - 30 - 10, 298 - 20, 30);
        g.fillRect(1298 + x, 415 - 30 - 10, 298 - 20, 30);
        g.fillRect(1606 + x, 415 - 30 - 10, 298 - 20, 30);

        g.fillRect(990 + x, 435 + 230 - 40, 895, 30);
        g.fillRect(990 + x, 685 + 230 - 40, 895, 30);

        g.setColor(blue);
        iceBasicRushCoolTimeBarWidth = (int) ((1.0f -((iGameModelShop.getIceBasicRushCoolTime() - iGameModelTick.getPlayTick()) / (float) iGameModelShop.getIceBasicRushCoolDownTick())) * 268);
        iceRareRushCoolTimeBarWidth = (int) ((1.0f -((iGameModelShop.getIceRareRushCoolTime() - iGameModelTick.getPlayTick()) / (float) iGameModelShop.getIceRareRushCoolDownTick())) * 268);
        iceLegendaryRushCoolTimeBarWidth = (int) ((1.0f -((iGameModelShop.getIceLegendaryRushCoolTime() - iGameModelTick.getPlayTick()) / (float) iGameModelShop.getIceLegendaryRushCoolDownTick())) * 268);
        iceVacuumCoolTimeBarWidth = (int) ((1.0f -((iGameModelShop.getIceVacuumCoolTime() - iGameModelTick.getPlayTick()) / (float) iGameModelShop.getIceVacuumCoolDownTick())) * 885);
        iceAutoCollectLevelBarWidth = (int) (((iGameModelShop.getIceAutoCollectLevel() / (float) iGameModelShop.getIceAutoCollectMaxLevel())) * 885);
        if (iceBasicRushCoolTimeBarWidth >= 268) iceBasicRushCoolTimeBarWidth = 268;
        if (iceRareRushCoolTimeBarWidth >= 268) iceRareRushCoolTimeBarWidth = 268;
        if (iceLegendaryRushCoolTimeBarWidth >= 268) iceLegendaryRushCoolTimeBarWidth = 268;
        if (iceVacuumCoolTimeBarWidth >= 885) iceVacuumCoolTimeBarWidth = 885;
        if (iceAutoCollectLevelBarWidth >= 885) iceAutoCollectLevelBarWidth = 885;
        if (iceBasicRushCoolTimeBarWidth <= 0) iceBasicRushCoolTimeBarWidth = 0;
        if (iceRareRushCoolTimeBarWidth <= 0) iceRareRushCoolTimeBarWidth = 0;
        if (iceLegendaryRushCoolTimeBarWidth <= 0) iceLegendaryRushCoolTimeBarWidth = 0;
        if (iceVacuumCoolTimeBarWidth <= 0) iceVacuumCoolTimeBarWidth = 0;
        if (iceAutoCollectLevelBarWidth <= 0) iceAutoCollectLevelBarWidth = 0;

        g.fillRect( 995 + x, 416 - 30 - 10 + 5, iceBasicRushCoolTimeBarWidth, 20);
        g.fillRect(1303 + x, 416 - 30 - 10 + 5, iceRareRushCoolTimeBarWidth, 20);
        g.fillRect(1611 + x, 416 - 30 - 10 + 5, iceLegendaryRushCoolTimeBarWidth, 20);

        g.fillRect( 995 + x, 435 + 230 - 35, iceAutoCollectLevelBarWidth, 20);
        g.fillRect( 995 + x, 685 + 230 - 35, iceVacuumCoolTimeBarWidth, 20);

        g.setColor(yellow);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(l.getShopIceBasicRush() + "(Q)", 992 + x, 210);
        g.drawString(l.getShopIceRareRush() + "(W)", 1300 + x, 210);
        g.drawString(l.getShopIceLegendaryRush() + "(E)", 1608 + x, 210);

        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(l.getShopAutoCollect(), 1000 + x, 480);
        g.drawString(l.getShopVacuumTitle() + "(R)", 1000 + x, 730);

        g.setColor(white);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(iGameModelShop.getIceBasicRushCost() +l.getShopCoinLabel(), 992 + x, 240);
        g.drawString(iGameModelShop.getIceRareRushCost() +l.getShopCoinLabel(), 1300 + x, 240);
        g.drawString(iGameModelShop.getIceLegendaryRushCost() +l.getShopCoinLabel(), 1608 + x, 240);

        g.drawString(iGameModelShop.getIceAutoCollectCost() +l.getShopCoinLabel(), 1000 + x,515);
        g.drawString(iGameModelShop.getIceVacuumCost() + l.getShopCoinLabel(), 1000 + x, 765);

        g.setColor(green);
        g.drawString(l.getShopOwned() + iGameModelShop.getIceBasicRushItemCount(), 992 + x, 270);
        g.drawString(l.getShopOwned() + iGameModelShop.getIceRareRushItemCount(), 1300 + x, 270);
        g.drawString(l.getShopOwned() + iGameModelShop.getIceLegendaryRushItemCount(), 1608 + x, 270);
        g.drawString(l.getShopLevel() + iGameModelShop.getIceAutoCollectLevel(), 1000 + x, 545);
        g.drawString(l.getShopOwned() + iGameModelShop.getIceVacuumCount(), 1000 + x, 795);
    }

    public void renderSkillPointTap(Graphics g, IGameSkillPoint iGameSkillPoint, int x) {

        g.setColor(white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(l.getSkillPointTitle(), 980 + x, 165);

        g.setColor(gray);
        g.fillRect(980 + x,180,298,240);
        g.fillRect(980 + x + 298 + 10,180 ,298,240);
        g.fillRect(980 + x + 298 + 10 + 298 + 10,180 ,298,240);

        g.fillRect(980 + x,430,915,240);
        g.fillRect(980 + x,680,915,240);

        g.setColor(gray);
        g.fillRect(1340 + x, 130, 550, 45); // test
        g.setColor(green);
        xpBarWidth = (int) ((iGameSkillPoint.getXP() / (float) iGameSkillPoint.getXPForNextLevel()) * 540);
        g.fillRect( 1345 + x, 135, xpBarWidth, 35); // test : 540
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(l.getSkillPointXpRemain() + (iGameSkillPoint.getXPForNextLevel() - iGameSkillPoint.getXP()), 1350 + x, 162); // test

        g.setColor(black);
        g.fillRect(985 + x, 185, 298 - 10, 240 - 10);
        g.fillRect(1293 + x, 185, 298 - 10, 240 - 10);
        g.fillRect(1601 + x, 185, 298 - 10, 240 - 10);

        g.fillRect( 985 + x,435,905,230);
        g.fillRect( 985 + x,685,905,230);

        g.drawString(l.getSkillPointUpgrade(), 992 + x, 395 - 30);
        g.drawString(l.getSkillPointUpgrade(), 1300 + x, 395 - 30);
        g.drawString(l.getSkillPointUpgrade(), 1610 + x, 395 - 30);
        g.drawString(l.getSkillPointUpgrade(), 994 + x, 645 + 230 - 20);
        g.drawString(l.getSkillPointUpgrade(),994 + x, 425 + 230 - 40);

        g.setColor(gray);
        g.fillRect(990 + x, 415 - 30 - 10, 298 - 20, 30);
        g.fillRect(1298 + x, 415 - 30 - 10, 298 - 20, 30);
        g.fillRect(1606 + x, 415 - 30 - 10, 298 - 20, 30);

        g.fillRect(990 + x, 435 + 230 - 40, 895, 30);
        g.fillRect(990 + x, 685 + 230 - 40, 895, 30);

        g.setColor(green);
        iceBasicSpawnChanceLevelBarWidth = (int) ((iGameSkillPoint.getIceBasicSpawnChanceLevel() / (float) iGameSkillPoint.getIceSpawnChanceMaxLevel()) * 268);
        iceRareSpawnChanceLevelBarWidth = (int) ((iGameSkillPoint.getIceRareSpawnChanceLevel() / (float) iGameSkillPoint.getIceSpawnChanceMaxLevel()) * 268);
        iceLegendarySpawnChanceLevelBarWidth = (int) ((iGameSkillPoint.getIceLegendarySpawnChanceLevel() / (float) iGameSkillPoint.getIceSpawnChanceMaxLevel()) * 268);
        clickOffsetLevelBarWidth = (int) (iGameSkillPoint.getClickOffsetLevel() / (float) iGameSkillPoint.getClickOffsetMaxLevel() * 885);
        itemCoolTimeLevelBarWidth = (int) ((iGameSkillPoint.getItemCoolTimeLevel() / (float) iGameSkillPoint.getItemCoolTimeMaxLevel()) * 885);
        if (iceBasicSpawnChanceLevelBarWidth >= 268) iceBasicSpawnChanceLevelBarWidth = 268;
        if (iceRareSpawnChanceLevelBarWidth >= 268) iceRareSpawnChanceLevelBarWidth = 268;
        if (iceLegendarySpawnChanceLevelBarWidth >= 268) iceLegendarySpawnChanceLevelBarWidth = 268;
        if (clickOffsetLevelBarWidth >= 885) clickOffsetLevelBarWidth = 885;
        if (itemCoolTimeLevelBarWidth >= 885) itemCoolTimeLevelBarWidth = 885;
        if (iceBasicSpawnChanceLevelBarWidth <= 0) iceBasicSpawnChanceLevelBarWidth = 0;
        if (iceRareSpawnChanceLevelBarWidth <= 0) iceRareSpawnChanceLevelBarWidth = 0;
        if (iceLegendarySpawnChanceLevelBarWidth <= 0) iceLegendarySpawnChanceLevelBarWidth = 0;
        if (clickOffsetLevelBarWidth <= 0) clickOffsetLevelBarWidth = 0;
        if (itemCoolTimeLevelBarWidth <= 0) itemCoolTimeLevelBarWidth = 0;

        g.fillRect( 995 + x, 416 - 30 - 10 + 5, iceBasicSpawnChanceLevelBarWidth, 20);
        g.fillRect(1303 + x, 416 - 30 - 10 + 5, iceRareSpawnChanceLevelBarWidth, 20);
        g.fillRect(1611 + x, 416 - 30 - 10 + 5, iceLegendarySpawnChanceLevelBarWidth, 20);

        g.fillRect( 995 + x, 435 + 230 - 35, clickOffsetLevelBarWidth, 20);
        g.fillRect( 995 + x, 685 + 230 - 35, itemCoolTimeLevelBarWidth, 20);

        g.setColor(yellow);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(l.getSkillIceBasicSpawn(), 992 + x, 210);
        g.drawString(l.getSkillIceRareSpawn(), 1300 + x, 210);
        g.drawString(l.getSkillIceLegendarySpawn(), 1608 + x, 210);

        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(l.getSkillClickOffset(), 1000 + x, 480);
        g.drawString(l.getSkillItemCooltime(), 1000 + x, 730);

        g.setFont(new Font("Arial", Font.BOLD, 20));

        g.setColor(white);
        g.setFont(new Font("Arial", Font.BOLD, 17));
        g.drawString(l.getSkillDescSpawn(), 992 + x, 240);
        g.drawString(l.getSkillDescSpawn(), 1300 + x, 240);
        g.drawString(l.getSkillDescSpawn(), 1608 + x, 240);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(l.getSkillDescClickOffset(), 1000 + x,515);
        g.drawString(l.getSkillDescItemCooltime(), 1000 + x, 765);

        g.setColor(green);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(l.getShopLevel() + iGameSkillPoint.getIceBasicSpawnChanceLevel(), 992 + x, 270);
        g.drawString(l.getShopLevel() + iGameSkillPoint.getIceRareSpawnChanceLevel(), 1300 + x, 270);
        g.drawString(l.getShopLevel() + iGameSkillPoint.getIceLegendarySpawnChanceLevel(), 1608 + x, 270);
        g.drawString(l.getShopLevel() + iGameSkillPoint.getClickOffsetLevel(), 1000 + x, 545);
        g.drawString(l.getShopLevel() + iGameSkillPoint.getItemCoolTimeLevel(), 1000 + x, 795);

        g.setColor(blue);
        g.drawString(l.getSkillAvailable() + iGameSkillPoint.getSkillPoint(), 1000 + x, 575);
        g.drawString(l.getSkillUsed() + iGameSkillPoint.getSkillPointUsed(), 1000 + x, 825);
    }

    public void renderQuestsTap(Graphics g,int x, IGameModelQuest iGameModelQuest) {
        g.setColor(white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(l.getQuestTitle(), 980 + x, 165);

        g.setColor(gray);
        g.fillRect(980 + x,180,915,240);
        g.fillRect(980 + x,430,915,240);
        g.fillRect(980 + x,680,915,240);

        g.setColor(new Color(50, 60, 75));
        g.fillRect(1340 + x, 130, 550, 45); // test
        g.setColor(green);
        g.fillRect( 1345 + x, 135, 540, 35);
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(l.getQuestRefresh() + iGameModelQuest.getQuestRefreshCost() + l.getShopCoinLabel(), 1350 + x, 162); // test

        g.setColor(black);
        g.fillRect( 985 + x,185,905,230);
        g.fillRect( 985 + x,435,905,230);
        g.fillRect( 985 + x,685,905,230);

        g.setColor(white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(l.getQuestSession(), 1000 + x, 230);
        g.drawString(l.getQuestSession(), 1000 + x, 480);
        g.drawString(l.getQuestLongtime(), 1000 + x, 730);

        g.setColor(yellow);
        g.drawString(iGameModelQuest.getFirstQuestExplanation(), 1000 + x, 280);
        g.drawString(iGameModelQuest.getSecondQuestExplanation(), 1000 + x, 530);
        g.drawString(l.getQuestDescLongtime(), 1000 + x, 780);

        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(green);
        g.drawString(l.getQuestRewardLabel() + iGameModelQuest.getFirstQuestCoinReward() + l.getShopCoinLabel() + ", +" + iGameModelQuest.getFirstQuestXpReward() + l.getInfoGetXpMsg(), 1000 + x, 320);
        g.drawString(l.getQuestRewardLabel() + iGameModelQuest.getSecondQuestCoinReward() + l.getShopCoinLabel() + ", +" + iGameModelQuest.getSecondQuestXpReward() + l.getInfoGetXpMsg(), 1000 + x, 570);
        g.drawString(l.getQuestRewardLabel() + iGameModelQuest.getThirdQuestExplanation(), 1000 + x, 820);

        if (iGameModelQuest.firstQuestCompleted()) {
            if (iGameModelQuest.firstQuestRewarded()) {
                g.setColor(Color.green);
                g.drawString(l.getQuestRewarded(), 1000 + x, 350);
            } else {
                g.setColor(yellow);
                g.drawString(l.getQuestNotRewarded(), 1000 + x, 350);
            }
        } else if (!iGameModelQuest.firstQuestCompleted()) {
            g.setColor(Color.red);
            g.drawString(l.getQuestNotCompleted(), 1000 + x, 350);
        }

        if (iGameModelQuest.secondQuestCompleted()) {
            if (iGameModelQuest.secondQuestRewarded()) {
                g.setColor(Color.green);
                g.drawString(l.getQuestRewarded(), 1000 + x, 600);
            } else {
                g.setColor(yellow);
                g.drawString(l.getQuestNotRewarded(), 1000 + x, 600);
            }
        } else if (!iGameModelQuest.secondQuestCompleted()) {
            g.setColor(Color.red);
            g.drawString(l.getQuestNotCompleted(), 1000 + x, 600);
        }

        if (iGameModelQuest.thirdQuestCompleted()) {
            if (iGameModelQuest.thirdQuestRewarded()) {
                g.setColor(Color.green);
                g.drawString(l.getQuestRewarded(), 1000 + x, 850);
            } else {
                g.setColor(yellow);
                g.drawString(l.getQuestNotRewarded(), 1000 + x, 850);
            }
        } else if (!iGameModelQuest.thirdQuestCompleted()) {
            g.setColor(Color.red);
            g.drawString(l.getQuestNotCompleted(), 1000 + x, 850);
        }

        firstQuestProgress = (int) ((iGameModelQuest.getFirstQuestProgress() / (float) iGameModelQuest.getFirstQuestGoal()) * 905);
        secondQuestProgress = (int) ((iGameModelQuest.getSecondQuestProgress() / (float) iGameModelQuest.getSecondQuestGoal()) * 905);
        thirdQuestProgress = (int) ((iGameModelQuest.getThirdQuestProgress() / (float) iGameModelQuest.getThirdQuestGoal()) * 905);
        if (firstQuestProgress >= 905) firstQuestProgress = 905;
        if (secondQuestProgress >= 905) secondQuestProgress = 905;
        if (thirdQuestProgress >= 905) thirdQuestProgress = 905;

        g.setColor(green);
        g.fillRect(985 + x,395,firstQuestProgress, 20);
        g.fillRect(985 + x, 645,secondQuestProgress, 20);
        g.fillRect(985 + x, 895,thirdQuestProgress, 20);

    }
    public void renderSettingTap(Graphics g, int x) {
        g.setColor(white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(l.getSettingTitle(), 980 + x, 165);

        g.setFont(new Font("Arial", Font.BOLD ,70));
        g.drawString("Bgm : ", 1000 + x, 300);
        g.drawString("Bgm sfx : ", 1000 + x,400);
        g.drawString("Sound : ", 1000 + x, 500);
        g.drawString("Sound sfx : ", 1000 + x, 600);
        g.drawString("Effect : ", 1000 + x, 700);
        g.drawString("Help : ", 1000 + x,800);

        g.setColor(gray);
        g.fillRect(1400 + x,250,500,70);
        g.fillRect(1400 + x,350,500,70);
        g.fillRect(1400 + x,450,500,70);
        g.fillRect(1400 + x,550,500,70);
        g.fillRect(1400 + x,650,500,70);
        g.fillRect(1400 + x,750,500,70);
    }
    public void renderTapBar(Graphics g, int tap, int tapBarX) {
        if (tap != 6) {
            g.setColor(green);
        } else {
            g.setColor(Color.RED);
        }
        g.fillRect(tapBarX,940, 189,20);
    }
}
