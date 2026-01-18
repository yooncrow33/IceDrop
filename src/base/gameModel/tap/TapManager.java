package base.gameModel.tap;

import view.iGameModel.IGameModelTick;

public class TapManager {
    final int tapWidth = 945;
    public int tap1X = 0;
    public int tap2X = tapWidth;
    public int tap3X = tapWidth * 2;
    public int tap4X = tapWidth * 3;
    public int tap5X = tapWidth * 4;
    boolean tapMoving = false;
    boolean tapMoveRight = true;
    int tapMoveTime = 21;
    int tapMoveDistance = 945;
    int tapMoveEndTick = 0;
    int recentTap = 0;
    public boolean tap1enabled = true;
    public boolean tap2enabled = true;
    public boolean tap3enabled = true;
    public boolean tap4enabled = true;
    public boolean tap5enabled = true;

    private int tap = 1;
    int tapBarPosition[] = {0,965,1154,1343,1532,1721,1721};

    IGameModelTick iTick;

    public TapManager(IGameModelTick iTick) {
        this.iTick = iTick;
    }

    public void tapMoveRight() {
        if (tapMoving) return;
        recentTap = tap;

        if (tap >= 5) {
            tap = 1;
            // 5에서 1로 갈 때: 1번 탭을 5번 탭 바로 오른쪽(tapWidth)으로 순간이동 시킴
            tap1X = tap5X + tapWidth;
        } else {
            tap++;
        }

        tapMoveEndTick = iTick.getPlayTick() + tapMoveTime;
        tapMoving = true;
        tapMoveRight = true;
        tap1enabled = tap2enabled = tap3enabled = tap4enabled = tap5enabled = true;
    }

    // 2. 이동 시작 로직 (왼쪽 이동: 1 -> 5 -> 4 -> ...)
    public void tapMoveLeft() {
        if (tapMoving) return;
        recentTap = tap;

        if (tap <= 1) {
            tap = 5;
            // 1에서 5로 갈 때: 5번 탭을 1번 탭 바로 왼쪽(-tapWidth)으로 순간이동 시킴
            tap5X = tap1X - tapWidth;
        } else {
            tap--;
        }

        tapMoveEndTick = iTick.getPlayTick() + tapMoveTime;
        tapMoving = true;
        tapMoveRight = false;
        tap1enabled = tap2enabled = tap3enabled = tap4enabled = tap5enabled = true;
    }

    // 3. 핵심 업데이트 로직
    public void tapUpdate() {
        if (!tapMoving) return;

        // 매 프레임당 이동할 거리 계산
        int moveStep = tapMoveDistance / tapMoveTime;

        // 오른쪽 이동: 화면 전체가 왼쪽으로 밀려야 함 (-)
        // 왼쪽 이동: 화면 전체가 오른쪽으로 밀려야 함 (+)
        if (tapMoveRight) {
            moveAllTaps(-moveStep);
        } else {
            moveAllTaps(moveStep);
        }

        // 이동 완료 시점
        if (iTick.getPlayTick() >= tapMoveEndTick) {
            tapMoving = false;
            setTap(this.tap); // 정확한 위치로 좌표 강제 고정 (Snap)
            //updateEnabledTaps(); // 현재 탭만 빼고 다 끄기
        }
    }

    // 4. 좌표 일괄 이동 (노가다 방지)
    private void moveAllTaps(int step) {
        tap1X += step;
        tap2X += step;
        tap3X += step;
        tap4X += step;
        tap5X += step;
    }

    // 5. 좌표 정렬 및 초기화 (Snap)
    public void setTap(int tap) {
        this.tap = tap;
        // 현재 탭(tap)을 기준으로 0, 945, 1890... 순서로 정렬
        tap1X = (1 - tap) * tapWidth;
        tap2X = (2 - tap) * tapWidth;
        tap3X = (3 - tap) * tapWidth;
        tap4X = (4 - tap) * tapWidth;
        tap5X = (5 - tap) * tapWidth;
    }

    // 6. 현재 탭만 켜두는 로직
    private void updateEnabledTaps() {
        setTapDisable(); // 일단 다 끔
        if (tap == 1) tap1enabled = true;
        else if (tap == 2) tap2enabled = true;
        else if (tap == 3) tap3enabled = true;
        else if (tap == 4) tap4enabled = true;
        else if (tap == 5) tap5enabled = true;
    }

    public void setTapDisable() {
        tap1enabled = false;
        tap2enabled = false;
        tap3enabled = false;
        tap4enabled = false;
        tap5enabled = false;
    }

    public int getTap() {
        return tap; }
    public boolean isTap1enabled() {
        return tap1enabled;
    }
    public boolean isTap2enabled() {
        return tap2enabled;
    }
    public boolean isTap3enabled() {
        return tap3enabled;
    }
    public boolean isTap4enabled() {
        return tap4enabled;
    }
    public boolean isTap5enabled() {
        return tap5enabled;
    }
    public int getTap1X() {
        return tap1X;
    }
    public int getTap2X() {
        return tap2X;
    }
    public int getTap3X() {
        return tap3X;
    }
    public int getTap4X() {
        return tap4X;
    }
    public int getTap5X() {
        return  tap5X;
    }
    public int getTapBarPosition() {
        return  tapBarPosition[getTap()];
    }
}
