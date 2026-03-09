package sc.base.gameModel.managers;

import sc.view.IGameModel;

public class TabManager {
    final int tabWidth = 945;
    public int tab1X = 0;
    public int tab2X = tabWidth;
    public int tab3X = tabWidth * 2;
    public int tab4X = tabWidth * 3;
    public int tab5X = tabWidth * 4;
    boolean tabMoving = false;
    boolean tabMoveRight = true;
    final int tapSpeedMax = 6;
    final int tapSpeedMin = 36;
    int tabMoveDistance = 945;
    int tabMoveEndTick = 0;
    int recentTap = 0;
    public boolean tab1enabled = true;
    public boolean tab2enabled = true;
    public boolean tab3enabled = true;
    public boolean tab4enabled = true;
    public boolean tab5enabled = true;

    private int tab = 1;
    int tabBarPosition[] = {0,965,1154,1343,1532,1721,1721}; //1910

    IGameModel iGameModel;

    public TabManager(IGameModel iGameModel) {
        this.iGameModel = iGameModel;
    }

    public void tabMoveRight() {
        if (tabMoving) return;
        iGameModel.getSoundManager().play("select.wav");
        recentTap = tab;

        if (tab >= 5) {
            tab = 1;
            tab1X = tab5X + tabWidth;
        } else {
            tab++;
        }

        tabMoveEndTick = iGameModel.getTickManager().getTick() + iGameModel.getSettingManager().getUiSetting().getTapSpeed();
        tabMoving = true;
        tabMoveRight = true;
        tab1enabled = tab2enabled = tab3enabled = tab4enabled = tab5enabled = true;
    }

    public void tabMoveLeft() {
        if (tabMoving) return;
        iGameModel.getSoundManager().play("select.wav");
        recentTap = tab;

        if (tab <= 1) {
            tab = 5;
            tab5X = tab1X - tabWidth;
        } else {
            tab--;
        }

        tabMoveEndTick = iGameModel.getTickManager().getTick() + iGameModel.getSettingManager().getUiSetting().getTapSpeed();
        tabMoving = true;
        tabMoveRight = false;
        tab1enabled = tab2enabled = tab3enabled = tab4enabled = tab5enabled = true;
    }

    public void tabUpdate() {
        if (!tabMoving) return;
        int moveStep = tabMoveDistance / iGameModel.getSettingManager().getUiSetting().getTapSpeed();

        if (tabMoveRight) {
            moveAllTaps(-moveStep);
        } else {
            moveAllTaps(moveStep);
        }

        // 이동 완료 시점
        if (iGameModel.getTickManager().getTick() >= tabMoveEndTick) {
            tabMoving = false;
            setTap(this.tab);
            updateEnabledTaps();
        }
    }

    // 4. 좌표 일괄 이동 (노가다 방지)
    private void moveAllTaps(int step) {
        tab1X += step;
        tab2X += step;
        tab3X += step;
        tab4X += step;
        tab5X += step;
    }

    public void setTap(int tab) {
        this.tab = tab;
        tab1X = (1 - tab) * tabWidth;
        tab2X = (2 - tab) * tabWidth;
        tab3X = (3 - tab) * tabWidth;
        tab4X = (4 - tab) * tabWidth;
        tab5X = (5 - tab) * tabWidth;
    }

    private void updateEnabledTaps() {
        setTapDisable();
        if (tab == 1) tab1enabled = true;
        else if (tab == 2) tab2enabled = true;
        else if (tab == 3) tab3enabled = true;
        else if (tab == 4) tab4enabled = true;
        else if (tab == 5) tab5enabled = true;
    }

    public void setTapDisable() {
        tab1enabled = false;
        tab2enabled = false;
        tab3enabled = false;
        tab4enabled = false;
        tab5enabled = false;
    }

    public int getTab() { return tab; }
    public boolean isTab1enabled() { return tab1enabled; }
    public boolean isTab2enabled() { return tab2enabled; }
    public boolean isTab3enabled() { return tab3enabled; }
    public boolean isTab4enabled() { return tab4enabled; }
    public boolean isTab5enabled() { return tab5enabled; }
    public int getTab1X() { return tab1X; }
    public int getTab2X() { return tab2X; }
    public int getTab3X() { return tab3X; }
    public int getTab4X() { return tab4X; }
    public int getTab5X() { return  tab5X; }
    public int getTabBarPosition() {
        return  tabBarPosition[getTab()];
    }
    public int getTabSpeedMax() {return tapSpeedMax;}
    public int getTabSpeedMin() {return tapSpeedMin;}
}
