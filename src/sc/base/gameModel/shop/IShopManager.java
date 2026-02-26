package sc.base.gameModel.shop;

import sc.base.gameModel.effects.EffectManager;
import sc.base.gameModel.ice.IceManager;
import sc.base.gameModel.skill.SkillManager;
import sc.base.gameModel.sound.SoundManager;

public interface IShopManager {
    EffectManager getEffectManager();
    SkillManager getSkillManager();
    IceManager getIceManager();
    SoundManager getSoundManager();
}
