package sc.base.gameModel.skill;

import sc.base.gameModel.sound.SoundManager;

public interface ISkillPointManager {
    void addStrEffect(String str);
    void addInfo(String l1,String l2,String l3);
    void addCoin(int value);
    SoundManager getSoundManager();
}
