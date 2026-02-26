package sc.base.gameModel.quest;

import sc.base.gameModel.effects.EffectManager;
import sc.base.gameModel.ice.IceManager;
import sc.base.gameModel.shop.ShopManager;
import sc.base.gameModel.skill.SkillManager;
import sc.base.gameModel.sound.SoundManager;

public interface IQuestManager {
    EffectManager getEffectManager();
    SkillManager getSkillManager();
    ShopManager getShopManager();
    IceManager getIceManager();
    int getSessionPlayTime();
    SoundManager getSoundManager();
}
