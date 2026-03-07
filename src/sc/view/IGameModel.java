package sc.view;

import sc.base.Console;
import sc.base.gameModel.*;
import sc.base.gameModel.quest.QuestManager;
import sc.base.gameModel.setting.SettingManager;
import sc.model.ExitPopup;

public interface IGameModel {
    IceManager getIceManager();
    EffectManager getEffectManager();
    QuestManager getQuestManager();
    SettingManager getSettingManager();
    SkillManager getSkillManager();
    TabManager getTabManager();
    SoundManager getSoundManager();
    TickManager getTickManager();
    ShopManager getShopManager();
    FileManager getFileManager();
    Console getConsole();
    BarManager getBarManager();
    ExitPopup getExitPopup();
    IPause getiPause();
    IMouse getiMouse();
    boolean isClicked();
    boolean isShift();
    int getCurrentProfileId();
}
