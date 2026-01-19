package model.effects;

import java.awt.*;

public class IceAfterImage {
    int x, y;
    int tier;
    int alpha = 150;

    public IceAfterImage(int x, int y, int tier) {
        this.x = x;
        this.y = y;
        this.tier = tier;
    }

    public void update() {
        alpha -= 12;    // 점점 투명해지고
    }

    public boolean isExpired() {
        return alpha <= 0;
    }

    public void draw(Graphics g) {
        if (tier == 1) {
            g.setColor(new Color(200, 220, 255, alpha));   // Basic - 연한 아이스
        } else if (tier == 2) {
            g.setColor(new Color(120, 200, 255, alpha));   // Rare - 블루
        } else if (tier == 3) {
            g.setColor(new Color(255, 120, 120, alpha));   // Legendary - 핫 레드
        }
        g.setFont(new Font("굴림", Font.BOLD, 36));
        g.fillRect(x, y, 30 ,30);
    }
}
