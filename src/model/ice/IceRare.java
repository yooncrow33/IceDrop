package model.ice;

import model.effects.IceAfterImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class IceRare {
    private final int WIDTH = 30;
    private final int HEIGHT = 30;
    private int x;
    private int y;
    private  final double FALL_SPEED = 10.0;
    private boolean afterImageLimit = false;
    ArrayList<IceAfterImage> iceAfterImages = new ArrayList<>();

    Random random = new Random();

    boolean vacuumActive;

    public IceRare() {
        x = random.nextInt(915) + 10;
        y = -30;
    }

    public void update(double dt) {
        if (!vacuumActive) {
            y += FALL_SPEED * (dt / (16.0 / 1000.0));
        } else {
            double angle = getAngle();
            if (x >= 468 && x <= 498 && y >= 525 && y <= 555) {
                x = 483;
                y = 540;
                return;
                // 중앙에 도달하면 멈춤
            }
            x += FALL_SPEED * Math.cos(angle) * (dt / (16.0 / 1000.0));
            y += FALL_SPEED * Math.sin(angle) * (dt / (16.0 / 1000.0));
        }

        if (!afterImageLimit) {
            iceAfterImages.add(new IceAfterImage(x,y,1));
            afterImageLimit = true;
        } else {
            afterImageLimit = false;
        }
        for (int i = iceAfterImages.size() - 1; i >= 0; i--) {
            IceAfterImage ai = iceAfterImages.get(i);
            ai.update();
            if (ai.isExpired()) {
                iceAfterImages.remove(i);
            }
        }
    }

    public double getAngle() {
        // Y 변화량 / X 변화량
        return Math.atan2(525 - y,
                468 - x);
    }

    public boolean shouldBeRemoved() {
        return y > 1090;
    }

    public boolean shouldBeCollected(int mouseX, int mouseY) {
        return mouseX + 20 >= x && mouseX - 20 <= x + WIDTH && mouseY + 50 >= y && mouseY - 50 <= y + HEIGHT;
    }

    public void setVacuumActive(boolean active) {
        this.vacuumActive = active;
    }

    public void draw(Graphics g) {
        for (IceAfterImage ai : iceAfterImages) {
            ai.draw(g);
        }
        g.setColor(new Color(135, 206, 255));
        g.fillRect(x, y, WIDTH, HEIGHT);
    }
}
