package base.gameModel.shop;

import base.gameModel.effects.EffectManager;
import base.gameModel.ice.IceManager;
import base.gameModel.skill.SkillManager;

public interface IShopManager {
    EffectManager getEffectManager();
    SkillManager getSkillManager();
    IceManager getIceManager();
}
