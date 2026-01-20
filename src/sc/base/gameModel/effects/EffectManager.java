package sc.base.gameModel.effects;

import sc.model.effects.Info;
import sc.model.effects.IntegerEffect;
import sc.model.effects.StringEffect;
import sc.view.IMouse;
import sc.view.iGameModel.IGameModelTick;

import java.awt.*;
import java.util.ArrayList;

public class EffectManager {
    ArrayList<IntegerEffect> integerEffects = new ArrayList<>();
    ArrayList<StringEffect> stringEffects = new ArrayList<>();
    ArrayList<Info> infos = new ArrayList<>();

    IMouse iMouse;
    IGameModelTick iTick;

    public EffectManager(IMouse iMouse, IGameModelTick iTick) {
        this.iMouse = iMouse;
        this.iTick  = iTick;
    }

    public void update() {
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
    }

    public void addStrEffect(String str) {
        stringEffects.add(new StringEffect(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY(),str));
    }
    public void addIntEffect(int value) {
        integerEffects.add(new IntegerEffect(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY(),value));
    }
    public void addInfo(String l1,String l2, String l3) {
        infos.add(new Info(l1,l2,l3,iTick.getPlayTick()));
    }
}
