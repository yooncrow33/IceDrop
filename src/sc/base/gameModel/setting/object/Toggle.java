package sc.base.gameModel.setting.object;

import sc.view.iGameModel.IGameModelTick;

import java.awt.*;

public class Toggle {
    boolean bool = true;
    final int CHANGE_TICK = 30;
    int changeEndTick = -1;
    boolean changing = false;
    int r = 0;
    int g = 255;
    final int b = 0;
    final int x;
    final int y;
    final int width = 490;
    final int height = 60;
    IGameModelTick iTick;
    public Toggle(IGameModelTick iTick,int x, int y) {
        this.iTick = iTick;
        this.x = x;
        this.y = y;
    }
    public void update() {
        if (iTick.getPlayTick() >= changeEndTick) {
            changing = false;
        }
    }
    public void click() {
        if (changing) {return;}

        bool = !bool;
        changeEndTick = iTick.getPlayTick() + CHANGE_TICK;
        changing = true;
    }
    public void drawToggle(Graphics graphics) {
        if (changing) {
            int remain = changeEndTick - iTick.getPlayTick();
            float progress = 1f - (remain / (float) CHANGE_TICK);
            progress = Math.max(0f, Math.min(1f, progress));

            if (bool) {
                r = (int)(255 * (1 - progress));
                g = (int)(255 * progress);
            } else {
                r = (int)(255 * progress);
                g = (int)(255 * (1 - progress));
            }
        } else {
            graphics.setColor(getColor());
        }
        graphics.fillRect(x,y,width,height);
    }

    public Color getColor() {
        if (bool) {
            return Color.green;
        } else {
            return Color.red;
        }
    }
}
