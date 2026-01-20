package sc.model.effects;

import java.awt.*;

public class StringEffect {
    int x, y;
    String str;
    int alpha = 255;
    int rise = 0;

    public StringEffect(int x, int y, String str) {
        this.x = x;
        this.y = y;
        this.str = str;
    }

    public void update() {
        rise++;        // 위로 올라가고
        alpha -= 2;    // 점점 투명해지고
    }

    public boolean isExpired() {
        return alpha <= 0;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(120, 255, 200, alpha));
        g.setFont(new Font("굴림", Font.BOLD, 36));
        g.drawString(str, x, y - rise);
    }
}
