package sc.base.gameModel.setting;

import sc.base.RenderUtils;
import sc.base.gameModel.setting.object.Knob;
import sc.view.IGameModel;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class SettingManager {
    private final IGameModel iGameModel;

    public boolean isOpen() {
        return open;
    }

    private final Map<String, Knob> knobs = new LinkedHashMap<>();
    private final String[] settings = {"BGM", "SFX", "TAB SPEED", "FIXED BAR","NONSENSE", "GRAPHICS", "SHAKE", "HELP OVERLAY"};
    private final double[] knobsInitValue = {0.5, 1.0, 0.5, 0.0,1.0, 1.0,0.0,0,0};
    private final boolean[] knobsToggle = {false,true,false,true,true,false,true,true};

    private final GraphicSetting graphicSetting;
    private final UiSetting uiSetting;
    private final SoundSetting soundSetting;

    private boolean open = false;

    Color black = new Color(10, 15, 25);
    Color white = new Color(200,215,235);

    public GraphicSetting getGraphicSetting() {
        return graphicSetting;
    }

    public UiSetting getUiSetting() {
        return uiSetting;
    }

    public SettingManager(IGameModel iGameModel) {
        this.iGameModel = iGameModel;
        iGameModel.getSoundManager().loopBgm("698690__dantethehater__mmo-theme-bgm-music-synth-retro.wav");
        initKnobs();

        graphicSetting = new GraphicSetting(knobs.get("GRAPHICS"), knobs.get("SHAKE"));
        uiSetting = new UiSetting(iGameModel,knobs.get("FIXED BAR"),knobs.get("TAB SPEED"),knobs.get("NONSENSE"), knobs.get("HELP OVERLAY"));
        soundSetting = new SoundSetting(iGameModel,knobs.get("BGM"),knobs.get("SFX"));

        open = true;
        update();
        open = false;
    }

    private void initKnobs() {
        int centerX = 960;
        int centerY = 540;

        final int clos = 4;
        final int columnsGap = 220;
        final int rowGap = 200;


        int startX = centerX - ((clos - 1) * columnsGap) / 2;
        int startY = centerY - rowGap / 2;

        for (int i = 0; i < 8; i++) {
            int row = i / clos; // 0, 0, 0, 0, 1, 1, 1, 1
            int col = i % clos; // 0, 1, 2, 3, 0, 1, 2, 3

            int x = startX + (col * columnsGap);
            int y = startY + (row * rowGap);

            knobs.put(settings[i],new Knob(iGameModel.getiMouse(),iGameModel,settings[i],knobsInitValue[i], knobsToggle[i], x, y));
        }

    }

    public void handleMouseWheel(int wheelRotation) {
        if (!open) return;
        for (Map.Entry<String, Knob> entry : knobs.entrySet()) {
            Knob knob = entry.getValue();

            knob.handleMouseWheel(wheelRotation);
        }
    }

    public void toggle() {
        open = !open;
    }

    public void update() {
        if (!open) return;
        graphicSetting.update();
        uiSetting.update();
        soundSetting.update();
        for (Map.Entry<String, Knob> entry : knobs.entrySet()) {
            Knob knob = entry.getValue();

            knob.update();
        }
    }

    public void render(Graphics g) {
        if (!open) return;
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4f));
        g.setColor(new Color(10, 10, 10, 200));
        g.fillRect(-2000, -2000, 60000, 6000);
        g.setColor(black);
        g.fillRect(400,200,1120,680);
        g.setColor(white);
        g.drawRect(400,200,1120,680);
        g.setFont(new Font("Arial", Font.BOLD, 64));
        RenderUtils.drawStringCenter(g,"SETTINGS",960,300);
        for (Map.Entry<String, Knob> entry : knobs.entrySet()) {
            Knob knob = entry.getValue();

            knob.render(g);
        }
    }
}