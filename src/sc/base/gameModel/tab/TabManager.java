package sc.base.gameModel.tab;

import sc.view.iGameModel.IGameModel;

public class TabManager {
    final int tabWidth = 945;
    public int tab1X = 0;
    public int tab2X = tabWidth;
    public int tab3X = tabWidth * 2;
    public int tab4X = tabWidth * 3;
    public int tab5X = tabWidth * 4;
    boolean tabMoving = false;
    boolean tabMoveRight = true;
    int tabMoveTime = 21;
    int tabMoveDistance = 945;
    int tabMoveEndTick = 0;
    int recentTap = 0;
    public boolean tab1enabled = true;
    public boolean tab2enabled = true;
    public boolean tab3enabled = true;
    public boolean tab4enabled = true;
    public boolean tab5enabled = true;

    private int tab = 1;
    int tabBarPosition[] = {0,965,1154,1343,1532,1721,1721};

    IGameModel iGameModel;

    public TabManager(IGameModel iGameModel) {
        this.iGameModel = iGameModel;
    }

    public void tabMoveRight() {
        if (tabMoving) return;
        recentTap = tab;

        if (tab >= 5) {
            tab = 1;
            // 5에서 1로 갈 때: 1번 탭을 5번 탭 바로 오른쪽(tabWidth)으로 순간이동 시킴
            tab1X = tab5X + tabWidth;
        } else {
            tab++;
        }

        tabMoveEndTick = iGameModel.getTickManager().getPlayTick() + tabMoveTime;
        tabMoving = true;
        tabMoveRight = true;
        tab1enabled = tab2enabled = tab3enabled = tab4enabled = tab5enabled = true;
    }

    // 2. 이동 시작 로직 (왼쪽 이동: 1 -> 5 -> 4 -> ...)
    public void tabMoveLeft() {
        if (tabMoving) return;
        recentTap = tab;

        if (tab <= 1) {
            tab = 5;
            // 1에서 5로 갈 때: 5번 탭을 1번 탭 바로 왼쪽(-tabWidth)으로 순간이동 시킴
            tab5X = tab1X - tabWidth;
        } else {
            tab--;
        }

        tabMoveEndTick = iGameModel.getTickManager().getPlayTick() + tabMoveTime;
        tabMoving = true;
        tabMoveRight = false;
        tab1enabled = tab2enabled = tab3enabled = tab4enabled = tab5enabled = true;
    }

    // 3. 핵심 업데이트 로직
    public void tabUpdate() {
        if (!tabMoving) return;

        // 매 프레임당 이동할 거리 계산
        int moveStep = tabMoveDistance / tabMoveTime;

        // 오른쪽 이동: 화면 전체가 왼쪽으로 밀려야 함 (-)
        // 왼쪽 이동: 화면 전체가 오른쪽으로 밀려야 함 (+)
        if (tabMoveRight) {
            moveAllTaps(-moveStep);
        } else {
            moveAllTaps(moveStep);
        }

        // 이동 완료 시점
        if (iGameModel.getTickManager().getPlayTick() >= tabMoveEndTick) {
            tabMoving = false;
            setTap(this.tab); // 정확한 위치로 좌표 강제 고정 (Snap)
            //updateEnabledTaps(); // 현재 탭만 빼고 다 끄기
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

    // 5. 좌표 정렬 및 초기화 (Snap)
    public void setTap(int tab) {
        this.tab = tab;
        // 현재 탭(tab)을 기준으로 0, 945, 1890... 순서로 정렬
        tab1X = (1 - tab) * tabWidth;
        tab2X = (2 - tab) * tabWidth;
        tab3X = (3 - tab) * tabWidth;
        tab4X = (4 - tab) * tabWidth;
        tab5X = (5 - tab) * tabWidth;
    }

    // 6. 현재 탭만 켜두는 로직
    private void updateEnabledTaps() {
        setTapDisable(); // 일단 다 끔
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

    public int getTap() {
        return tab; }
    public boolean isTap1enabled() {
        return tab1enabled;
    }
    public boolean isTap2enabled() {
        return tab2enabled;
    }
    public boolean isTap3enabled() {
        return tab3enabled;
    }
    public boolean isTap4enabled() {
        return tab4enabled;
    }
    public boolean isTap5enabled() {
        return tab5enabled;
    }
    public int getTap1X() {
        return tab1X;
    }
    public int getTap2X() {
        return tab2X;
    }
    public int getTap3X() {
        return tab3X;
    }
    public int getTap4X() {
        return tab4X;
    }
    public int getTap5X() {
        return  tab5X;
    }
    public int getTapBarPosition() {
        return  tabBarPosition[getTap()];
    }
}
