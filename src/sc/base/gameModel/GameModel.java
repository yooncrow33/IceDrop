package sc.base.gameModel;

import sc.base.Console;
import sc.base.gameModel.bar.BarManager;
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

import java.awt.*;

public class GameModel implements IGameModel {
    private boolean clicked = false;

    private final int currentProfileId;
    private final FileManager fileManager;
    private final IceManager iceManager;
    private final EffectManager effectManager;
    private final TabManager tabManager;
    private final SkillManager skillManager;
    private final ShopManager shopManager;
    private final QuestManager questManager;
    private final SoundManager soundManager;
    private final SettingManager settingManager;
    private final TickManager tickManager;
    private final BarManager barManager;
    private final Console console;
    private final IPause iPause;

    public GameModel(int profileId, IMouse iMouse, Lang l, IPause iPause, IExit iExit) {
        this.currentProfileId = profileId;
        this.iPause = iPause;

        fileManager = new FileManager(this);
        barManager = new BarManager(this,iMouse,iExit,iPause);
        tickManager = new TickManager();
        effectManager = new EffectManager(iMouse,this);
        iceManager = new IceManager(this,iMouse,l);
        tabManager = new TabManager(this);
        skillManager = new SkillManager(this, l);
        shopManager = new ShopManager(this,l);
        questManager = new QuestManager(this, l);
        soundManager = new SoundManager();
        settingManager = new SettingManager(this);
        console = new Console(this,iPause);

        fileManager.load(currentProfileId);
    }

    public void update(double dt, Boolean pause) {
        tickManager.update();
        clicked = false;
        if (pause) return;
        iceManager.update(dt);
        questManager.update();
        shopManager.update(dt);
        tabManager.tabUpdate();
        skillManager.updateLevelStatus();
        effectManager.update(dt);
    }

    public BarManager getBarManager() { return barManager; }
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
    public Console getConsole() {return console;}
    public boolean isClicked() {return clicked;}
    public int getCurrentProfileId() {return currentProfileId;}
    public void setClicked() {clicked = true;}
}
