package base.gameModel.quest;

import base.gameModel.effects.EffectManager;
import base.gameModel.ice.IceManager;
import base.gameModel.shop.ShopManager;
import base.gameModel.skill.SkillManager;

public interface IQuestManager {
    EffectManager getEffectManager();
    SkillManager getSkillManager();
    ShopManager getShopManager();
    IceManager getIceManager();
    int getSessionPlayTime();
}
