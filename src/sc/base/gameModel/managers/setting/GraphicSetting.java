package sc.base.gameModel.managers.setting;

import sc.base.gameModel.managers.setting.object.Knob;

import java.awt.*;

public class GraphicSetting {
    private final Knob knob;
    private final Knob screenShakeKnob;

    public GraphicSetting(Knob knob, Knob screenShakeKnob) {
        this.knob = knob;
        this.screenShakeKnob = screenShakeKnob;
        knob.setShowValueFont(new Font("Arial",Font.BOLD,20));
        knob.setShowValueOffset(9);
    }

    public boolean isScreenShake() {
        return screenShake;
    }

    public boolean isScreenShakeActive() {
        return screenShakeActive;
    }

    public void update() {
        screenShakeKnob.setShowValue(value(screenShakeKnob.getCurrentValue() >= 1.0));
        screenShakeActive = screenShakeKnob.getCurrentValue() >= 1.0;
        switch (mapToRange()) {
            case 0 :
                allOff();
                knob.setShowValue("MIN");
                break;
            case 1 :
                allOff();
                knob.setShowValue("LOW");
                strEffect = true;
                break;
            case 2 :
                allOff();
                knob.setShowValue("MEDIUM");
                strEffect = true;
                ai = true;
                break;
            case 3 :
                allOff();
                knob.setShowValue("HIGH");
                strEffect = true;
                ai = true;
                faEffect = true;
                break;
            case 4 :
                allOff();
                knob.setShowValue("ULTRA");
                strEffect = true;
                ai = true;
                faEffect = true;
                aa = true;
                screenShake = true;
                break;
            default:
                allOff();
                break;
        }
    }

    private boolean aa = false;
    private boolean screenShake = false;
    private boolean screenShakeActive = false;
    private boolean ai = false;
    private boolean strEffect = false;
    private boolean faEffect = false;

    public boolean isFullyCharged() {
        return knob.getCurrentValue() >= 1.0;
    }

    private int mapToRange() {
        // 1.0을 넘지 않게 보정 (안전장치)
        double clamped = Math.max(0.0, Math.min(1.0, knob.getCurrentValue()));

        // 소수점을 반올림(round)해서 정수로 변환
        // (int) (0.5 * 6 + 0.5) -> 3 이런 식으로 딱딱 끊어짐
        return (int) Math.round(clamped * 4);
    }

    public void allOff() {
        aa = false;
        ai = false;
        strEffect = false;
        faEffect = false;
    }

    private String value(boolean b) {
        return b ? "ON" : "OFF";
    }


    public boolean isAa() {
        return aa;
    }

    public boolean isAi() {
        return ai;
    }

    public boolean isStrEffect() {
        return strEffect;
    }

    public boolean isFaEffect() {
        return faEffect;
    }
}
