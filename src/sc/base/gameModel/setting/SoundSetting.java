package sc.base.gameModel.setting;

import sc.base.gameModel.setting.object.Knob;
import sc.view.IGameModel;

public class SoundSetting {
    private boolean sfx = true;
    private final Knob bgmKnob;
    private final Knob sfxKnob;
    final IGameModel iGameModel;

    public SoundSetting(IGameModel iGameModel, Knob bgmKnob, Knob sfxKnob) {
        this.iGameModel = iGameModel;
        this.bgmKnob = bgmKnob;
        this.sfxKnob = sfxKnob;
    }

    public void update() {
        iGameModel.getSoundManager().setBgmVolume((float) bgmKnob.getCurrentValue());
        bgmKnob.setShowValue(Integer.toString((int) (bgmKnob.getCurrentValue()*100)));
        sfx = sfxKnob.getCurrentValue() >= 1.0;
        iGameModel.getSoundManager().setSfxVolume(sfx);
        sfxKnob.setShowValue(value(sfx));
    }

    public String value(boolean b) {
        return b ? "ON" : "OFF";
    }
}
