package sc.base.gameModel.setting;

import sc.view.IGameModel;

public class SettingManager {
    final IGameModel iGameModel;
    public SettingManager(IGameModel iGameModel) {
        this.iGameModel = iGameModel;
        iGameModel.getSoundManager().loopBgm("bgm2.wav");
    }
}
