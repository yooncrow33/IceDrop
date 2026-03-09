package sc.base.gameModel.managers;

import sc.model.effects.*;
import sc.view.IGameModel;

import java.awt.*;
import java.util.ArrayList;

public class EffectManager {
    ArrayList<IntegerEffect> integerEffects = new ArrayList<>();
    ArrayList<StringEffect> stringEffects = new ArrayList<>();
    ArrayList<Info> infos = new ArrayList<>();
    ArrayList<Fa> fas = new ArrayList<>();

    private boolean shake = false;
    private int shakeIntensity = 4;
    private int shakeTime = 0;

    public int getShakeIntensity() {
        return shakeIntensity;
    }

    public void setShake(int tick, int shakeIntensity) {
        shakeTime = tick;
        this.shakeIntensity = shakeIntensity;
    }

    public boolean isShake() {
        return shake;
    }

    ArrayList<FaUltra> faUltras = new ArrayList<>();

    IGameModel iGameModel;

    public EffectManager(IGameModel iGameModel) {
        this.iGameModel  = iGameModel;
    }

    public void update(double dt) {
        if (shakeTime <= 0) {
            shake = false;
            shakeTime = 0;
        } else {
            shakeTime --;
            shake = true;
        }


        for (int i = integerEffects.size() - 1; i >= 0; i--) {
            IntegerEffect integerEffect = integerEffects.get(i);
            integerEffect.update();
            if (integerEffect.isExpired()) {
                integerEffects.remove(i);
            }
        }


        for (int i = stringEffects.size() - 1; i >= 0; i--) {
            StringEffect stringEffect = stringEffects.get(i);
            stringEffect.update();
            if (stringEffect.isExpired()) {
                stringEffects.remove(i);
            }
        }

        for (int i = infos.size() - 1; i >= 0; i--) {
            Info info = infos.get(i);
            info.update();
            if (info.isExpired()) {
                infos.remove(i);
            }
        }

        for (int i = fas.size() - 1; i >= 0; i--) {
            Fa f = fas.get(i);
            f.update(dt);
            if (f.isFire()) {
                fas.remove(i);
            }
        }

        for (int i = faUltras.size() - 1; i >= 0; i--) {
            FaUltra f = faUltras.get(i);
            f.update(dt);
            if (f.isFire()) {
                faUltras.remove(i);
            }
        }
    }

    public void renderEffects(Graphics g) {
        for (IntegerEffect integerEffect : integerEffects) {
            integerEffect.draw(g);
        }
        for (StringEffect stringEffect : stringEffects) {
            stringEffect.draw(g);
        }
        for (Info info : infos) {
            info.draw(g);
        }
        for (Fa f : fas) {
            f.renderFa(g);
        }
        for(FaUltra faUltra : faUltras) {
            faUltra.renderFa(g);
        }
    }

    public void addStrEffect(String str) {
        if (!iGameModel.getSettingManager().getGraphicSetting().isStrEffect()) return;
        stringEffects.add(new StringEffect(iGameModel.getiMouse().getVirtualMouseX(),iGameModel.getiMouse().getVirtualMouseY(),str));
    }
    public void addIntEffect(int value) {
        if (!iGameModel.getSettingManager().getGraphicSetting().isStrEffect()) return;
        integerEffects.add(new IntegerEffect(iGameModel.getiMouse().getVirtualMouseX(),iGameModel.getiMouse().getVirtualMouseY(),value));
    }
    public void addInfo(String l1,String l2, String l3) {
        infos.add(new Info(l1,l2,l3,iGameModel.getTickManager().getTick()));
    }
    public void addFa(int x, int y) {
        if (!iGameModel.getSettingManager().getGraphicSetting().isFaEffect()) return;
        for (int i = 0; i <= 30; i++) {
            fas.add(new Fa(x, y));
        }
    }
    public void addFaUltra() {
        if (!iGameModel.getSettingManager().getGraphicSetting().isFaEffect()) return;
        for (int i = 0; i <= 70; i++) {
            faUltras.add(new FaUltra(498,555));
        }
    }
}
