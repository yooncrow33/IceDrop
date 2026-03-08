package sc.base.gameModel;

import sc.base.Console;
import sc.base.gameModel.quest.QuestManager;
import sc.base.gameModel.setting.SettingManager;
import sc.lang.Lang;
import sc.model.ExitPopup;
import sc.model.overlay.Overlay;
import sc.view.*;

public final class GameModel implements IGameModel {
    private boolean clicked = false;
    private boolean shift = false;

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
    private final ExitPopup exitPopup;
    private final Overlay overlay;
    private final IPause iPause;
    private final IMouse iMouse;

    public GameModel(int profileId, IMouse iMouse, Lang l, IPause iPause, IExit iExit) {
        this.currentProfileId = profileId;
        this.iPause = iPause;
        this.iMouse = iMouse;

        fileManager = new FileManager(this);
        barManager = new BarManager(this,iExit);
        tickManager = new TickManager(this);
        effectManager = new EffectManager(this);
        iceManager = new IceManager(this,l);
        tabManager = new TabManager(this);
        skillManager = new SkillManager(this, l);
        shopManager = new ShopManager(this,l);
        questManager = new QuestManager(this, l);
        soundManager = new SoundManager();
        overlay = new Overlay(iMouse,this);
        settingManager = new SettingManager(this);
        console = new Console(this);
        exitPopup = new ExitPopup(iExit,this);

        fileManager.load(currentProfileId);
    }

    public void update(double dt) {
        tickManager.update();
        barManager.update();
        exitPopup.update();
        settingManager.update();
        overlay.update();
        if (getiPause().isPause()) return;
        iceManager.update(dt);
        questManager.update();
        shopManager.update(dt);
        tabManager.tabUpdate();
        skillManager.updateLevelStatus();
        effectManager.update(dt);
        clicked = false;
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
    public ExitPopup getExitPopup() {return exitPopup; }
    public IPause getiPause() {return iPause;}
    public IMouse getiMouse() {return iMouse;}
    public Console getConsole() {return console;}
    public Overlay getOverlay() {return overlay;}

    public int getCurrentProfileId() {return currentProfileId;}
    public boolean isClicked() {return clicked;}
    public void setClicked() {clicked = true;}
    public boolean isShift() {return shift;}
    public void setShift(boolean b) {shift = b;}
}
