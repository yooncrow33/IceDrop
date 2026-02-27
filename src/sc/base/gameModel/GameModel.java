package sc.base.gameModel;

import sc.base.gameModel.effects.EffectManager;
import sc.base.gameModel.file.FileManager;
import sc.base.gameModel.ice.IceManager;
import sc.base.gameModel.quest.QuestManager;
import sc.base.gameModel.setting.SettingManager;
import sc.base.gameModel.shop.ShopManager;
import sc.base.gameModel.skill.SkillManager;
import sc.base.gameModel.sound.SoundManager;
import sc.base.gameModel.tab.TabManager;
import sc.base.gameModel.tick.TickManager;
import sc.lang.Lang;
import sc.view.*;
import sc.view.iGameModel.*;

import javax.swing.*;
import java.io.*;

public class GameModel implements IGameModel {
    public boolean shiftPressed = false;
    boolean clicked = false;

    final int currentProfileId;
    FileManager fileManager;
    IceManager iceManager;
    EffectManager effectManager;
    TabManager tabManager;
    SkillManager skillManager;
    ShopManager shopManager;
    QuestManager questManager;
    SoundManager soundManager;
    SettingManager settingManager;
    TickManager tickManager;
    IMouse iMouse;
    Lang l;

    public GameModel(int profileId, IMouse iMouse, Lang l) {
        this.currentProfileId = profileId;
        this.iMouse = iMouse;
        this.l = l;

        fileManager = new FileManager(this);
        tickManager = new TickManager();
        effectManager = new EffectManager(iMouse,this);
        iceManager = new IceManager(this,iMouse,l);
        tabManager = new TabManager(this);
        skillManager = new SkillManager(this, l);
        shopManager = new ShopManager(this,l);
        questManager = new QuestManager(this, l);
        soundManager = new SoundManager();
        settingManager = new SettingManager();

        fileManager.load(currentProfileId);
    }

    public void update(double dt) {
        iceManager.update(dt);
        tickManager.update();
        questManager.update();
        shopManager.update(dt);
        tabManager.tabUpdate();
        skillManager.updateLevelStatus();
        effectManager.update(dt);
        clicked = false;
    }

    public void setShiftPressed(boolean pressed) {
        this.shiftPressed = pressed;
    }

    public IceManager getIceManager() {return iceManager;}
    public EffectManager getEffectManager() {return effectManager;}
    public SkillManager getSkillManager() {return skillManager;}
    public TabManager getTabManager() {return tabManager;}
    public ShopManager getShopManager() {return shopManager;}
    public QuestManager getQuestManager() { return questManager; }
    public SettingManager getSettingManager() {return settingManager;}
    public SoundManager getSoundManager() { return soundManager; }
    public TickManager getTickManager() {return tickManager;}
    public FileManager getFileManager() {return fileManager;}
    public boolean isClicked() {return clicked;}
    public int getCurrentProfileId() {return currentProfileId;}
    public void setClicked() {clicked = true;}

}
