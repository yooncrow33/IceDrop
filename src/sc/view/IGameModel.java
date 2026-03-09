package sc.view;

import sc.base.Console;
import sc.base.gameModel.managers.*;
import sc.base.gameModel.managers.quest.QuestManager;
import sc.base.gameModel.managers.setting.SettingManager;
import sc.base.gameModel.managers.stastics.StatisticsManager;
import sc.model.ExitPopup;
import sc.model.overlay.Overlay;

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
    Overlay getOverlay();
    StatisticsManager getStatisticsManager();
    boolean isClicked();
    boolean isShift();
    int getCurrentProfileId();
}
