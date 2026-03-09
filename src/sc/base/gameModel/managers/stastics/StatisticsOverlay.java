package sc.base.gameModel.managers.stastics;

import sc.base.gameModel.managers.stastics.object.Data;

import java.awt.*;
import java.util.ArrayList;

import static sc.base.gameModel.managers.stastics.StatisticsOverlay.ScreenState.*;

public class StatisticsOverlay {
    private boolean open = false;
    private ScreenState state = ScreenState.TIME_HIT_RATE; // 기본값
    private final StatisticsManager sm;

    public StatisticsOverlay(StatisticsManager sm) {
        this.sm = sm;
    }

    public enum ScreenState {
        // === 1. 시간에 따른 변화 그래프 (Timeline / Line Chart) ===
        // datas 리스트를 순회하며 각 세션별 추이를 개별적으로 보여주는 화면들
        TIME_CLICK_COUNT,                // 클릭 횟수 추이
        TIME_ICE_CLICK_COUNT,            // 아이스 적중 횟수 추이
        TIME_HIT_RATE,                   // 적중률(%) 변화 추이
        TIME_TOTAL_GET_COIN,             // 누적 코인 획득량 추이
        TIME_CURRENT_XP,                 // 경험치 획득 추이
        TIME_CURRENT_LEVEL,              // 레벨업 기록 추이
        TIME_TOTAL_GET_SKILL_POINT,      // 스킬 포인트 획득 추이
        TIME_QUEST_REWARDED_COUNT,       // 퀘스트 보상 수령 횟수 추이

        // === 2. 현재값 항목별 비교 그래프 (Category / Bar Chart) ===
        // 현재 시점의 수치들을 비슷한 성격끼리 묶어서 막대로 비교하는 화면들

        /**
         * 아이 수집 등급별 비교
         * 대상: iceBasicCollectedCount, iceRareCollectedCount, iceLegendaryCollectedCount
         */
        COMPARE_ICE_COLLECTION,

        /**
         * 스킬(Rush) 사용 빈도 비교
         * 대상: iceBasicRushUsedCount, iceRareRushUsedCount, iceLegendaryRushUsedCount
         */
        COMPARE_RUSH_USAGE,

        /**
         * 특수 기능 및 자동화 비교
         * 대상: iceVacuumUsedCount (진공), iceAutoCollectedCount (자동 수집)
         */
        COMPARE_SPECIAL_FEATURE,

        /**
         * 퀘스트 활동량 비교
         * 대상: questsCompletedCount (완료), questRefreshedCount (새로고침)
         */
        COMPARE_QUEST_ACTIVITY
    }

    public void render(Graphics g) {
        if (!open) return;

        // 배경 및 테두리
        g.setColor(new Color(20, 30, 43, 230)); // 약간의 투명도 추가
        g.fillRect(0, 0, 1920, 1080);
        g.setColor(new Color(240, 240, 240));
        g.drawRect(50, 50, 1820, 940); // 여백 확보

        // 현재 상태 타이틀 출력
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("STATISTICS: " + state.name(), 70, 90);

        if (state.name().startsWith("TIME_")) {
            renderTimeGraph(g);
        } else {
            renderCompareGraph(g);
        }
    }

    // 1. 시간에 따른 변화 그래프 (데이터 길이에 비례하여 넓이 조정)
    private void renderTimeGraph(Graphics g) {
        ArrayList<Data> datas = sm.datas;
        if (datas.isEmpty()) return;

        int size = datas.size();
        float barWidth = 1820f / size; // 전체 너비에서 데이터 개수만큼 나눔
        int maxHeight = 800;
        int baseY = 950;

        // 해당 스탯의 최댓값 찾기 (비율 조절용)
        float maxValue = 1;
        for (Data d : datas) {
            float val = getValueByState(d, state);
            if (val > maxValue) maxValue = val;
        }

        for (int i = 0; i < size; i++) {
            float val = getValueByState(datas.get(i), state);
            int h = (int) ((val / maxValue) * maxHeight);

            g.setColor(new Color(100, 150, 255, 150));
            g.fillRect(50 + (int)(i * barWidth), baseY - h, (int)barWidth, h);
            g.setColor(new Color(255, 255, 255, 100));
            g.drawRect(50 + (int)(i * barWidth), baseY - h, (int)barWidth, h);
        }
    }

    // 2. 항목별 비교 그래프 (최댓값 기준 비율 렌더링)
    private void renderCompareGraph(Graphics g) {
        int[] values;
        String[] labels;

        // 카테고리별 데이터 세팅
        switch (state) {
            case COMPARE_ICE_COLLECTION -> {
                values = new int[]{sm.getIceBasicCollectedCount(), sm.getIceRareCollectedCount(), sm.getIceLegendaryCollectedCount()};
                labels = new String[]{"Basic", "Rare", "Legendary"};
            }
            case COMPARE_RUSH_USAGE -> {
                values = new int[]{sm.getIceBasicRushUsedCount(), sm.getIceRareRushUsedCount(), sm.getIceLegendaryRushUsedCount()};
                labels = new String[]{"Basic Rush", "Rare Rush", "Legendary Rush"};
            }
            case COMPARE_SPECIAL_FEATURE -> {
                values = new int[]{sm.getIceVacuumUsedCount(), sm.getIceAutoCollectedCount()};
                labels = new String[]{"Vacuum", "Auto Collect"};
            }
            case COMPARE_QUEST_ACTIVITY -> {
                values = new int[]{sm.getQuestsCompletedCount(), sm.getQuestRefreshedCount()};
                labels = new String[]{"Completed", "Refreshed"};
            }
            default -> { return; }
        }

        int maxVal = 1;
        for (int v : values) if (v > maxVal) maxVal = v;

        int barFixedWidth = 200;
        int spacing = 100;
        for (int i = 0; i < values.length; i++) {
            int h = (int) ((float) values[i] / maxVal * 700);
            g.setColor(new Color(255, 100, 100));
            g.fillRect(200 + (i * (barFixedWidth + spacing)), 900 - h, barFixedWidth, h);
            g.setColor(Color.WHITE);
            g.drawString(labels[i] + ": " + values[i], 200 + (i * (barFixedWidth + spacing)), 930);
        }
    }

    // 상태에 따른 데이터 매핑 헬퍼
    private float getValueByState(Data d, ScreenState s) {
        return switch (s) {
            case TIME_CLICK_COUNT -> d.getClickCount();
            case TIME_ICE_CLICK_COUNT -> d.getIceClickCount();
            case TIME_HIT_RATE -> d.getHitRate();
            case TIME_TOTAL_GET_COIN -> d.getTotalGetCoin();
            case TIME_CURRENT_XP -> d.getCurrentXp();
            case TIME_CURRENT_LEVEL -> d.getCurrentLevel();
            case TIME_TOTAL_GET_SKILL_POINT -> d.getTotalGetSkillPoint();
            case TIME_QUEST_REWARDED_COUNT -> d.getCurrentQuestRewardedCount();
            default -> 0;
        };
    }

    public void setState(ScreenState state) { this.state = state; }
    public void setOpen(boolean open) { this.open = open; }
    public void toggle() { this.open = !open; }
    public boolean isOpen() { return open; }
}
