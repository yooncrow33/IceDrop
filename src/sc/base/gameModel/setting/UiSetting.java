package sc.base.gameModel.setting;

import sc.base.gameModel.setting.object.Knob;
import sc.view.IGameModel;

public class UiSetting {
    private final Knob barFixedKnob;
    private final Knob tapSpeedKnob;
    private final Knob nonsenseKnob;

    private boolean barFixed = false;
    private boolean nonsense = true;
    private final int tapSpeedMin;
    private final int tapTapSpeedMax;
    private int tapSpeed;



    public boolean isBarFixed() {
        return barFixed;
    }

    public boolean isNonsense() {
        return nonsense;
    }

    public int getTapSpeed() {
        return tapSpeed;
    }

    public UiSetting(IGameModel iGameModel, Knob barFixKnob, Knob tapSpeedKnob, Knob nonsenseKnob) {
        this.barFixedKnob = barFixKnob;
        this.tapSpeedKnob = tapSpeedKnob;
        this.nonsenseKnob = nonsenseKnob;
        tapSpeedMin = iGameModel.getTabManager().getTabSpeedMin();
        tapTapSpeedMax = iGameModel.getTabManager().getTabSpeedMax();

        barFixed = isTrue(barFixedKnob);
        barFixedKnob.setShowValue(value(barFixed));
        nonsense = isTrue(nonsenseKnob);
        nonsenseKnob.setShowValue(value(nonsense));
        tapSpeed =  mapToRange(tapSpeedMin,tapTapSpeedMax,tapSpeedKnob);
        tapSpeedKnob.setShowValue(Integer.toString(getTapSpeed()));

        tapSpeed = tapSpeedMin + (tapTapSpeedMax - tapSpeedMin/2);
    }

    public void update() {
        barFixed = isTrue(barFixedKnob);
        barFixedKnob.setShowValue(value(barFixed));
        nonsense = isTrue(nonsenseKnob);
        nonsenseKnob.setShowValue(value(nonsense));
        tapSpeed =  mapToRange(tapSpeedMin,tapTapSpeedMax,tapSpeedKnob);
        tapSpeedKnob.setShowValue(Integer.toString(945/getTapSpeed()));
    }

    public String value(boolean b) {
        return b ? "ON" : "OFF";
    }

    private int mapToRange(int min, int max, Knob knob) {
        // 1.0을 넘지 않게 보정 (안전장치)
        double clamped = Math.max(0.0, Math.min(1.0, knob.getCurrentValue()));

        // 소수점을 반올림(round)해서 정수로 변환
        // (int) (0.5 * 6 + 0.5) -> 3 이런 식으로 딱딱 끊어짐
        int r = max - min;
        return (int) Math.round(clamped * r) + min;
    }

    public boolean isTrue(Knob knob) {
        return knob.getCurrentValue() >= 1.0;
    }
}
