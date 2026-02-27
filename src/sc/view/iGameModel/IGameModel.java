package sc.view.iGameModel;

import sc.base.gameModel.effects.EffectManager;
import sc.base.gameModel.ice.IceManager;
import sc.base.gameModel.quest.QuestManager;
import sc.base.gameModel.setting.SettingManager;
import sc.base.gameModel.shop.ShopManager;
import sc.base.gameModel.skill.SkillManager;
import sc.base.gameModel.sound.SoundManager;
import sc.base.gameModel.tab.TabManager;
import sc.base.gameModel.tick.TickManager;
import sc.model.ice.Ice;

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
    boolean isClicked();
    int getCurrentProfileId();
}
