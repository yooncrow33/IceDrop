package sc.model.ice;

import sc.model.effects.IceAfterImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class IceRare implements Ice {
    private final int WIDTH = 30;
    private final int HEIGHT = 30;
    private int x;
    private int y;
    private  final double FALL_SPEED = 10.0;
    private boolean afterImageLimit = false;
    ArrayList<IceAfterImage> iceAfterImages = new ArrayList<>();
    int offsetX[] = {0,2,4,6,8,10,12,14,16,18,20};
    int offsetY[] = {0,3,6,9,12,15,18,21,24,27,30};

    final String name = "Rare";

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
            iceAfterImages.add(new IceAfterImage(x,y,2));
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
        return y > 1140;
    }

    public boolean shouldBeCollected(int mouseX, int mouseY, int offsetIndex) {
        return mouseX + offsetX[offsetIndex] >= x && mouseX - offsetX[offsetIndex] <= x + WIDTH && mouseY + offsetY[offsetIndex] >= y && mouseY - offsetY[offsetIndex] <= y + HEIGHT;
    }

    public String name() {
        return name;
    }

    public int getValue() {
        return 5;
    }

    public int getTier() {
        return 2;
    }

    public void setVacuumActive(boolean active) {
        this.vacuumActive = active;
    }

    public void draw(Graphics g) {
        for (IceAfterImage ai : iceAfterImages) {
            ai.draw(g);
        }

        Graphics2D g2 = (Graphics2D) g;

        // 발광 레이어 (아우라)
        g2.setStroke(new BasicStroke(6f));
        g2.setColor(new Color(120, 200, 255, 60));
        g2.drawRect(x - 2, y - 2, WIDTH + 4, HEIGHT + 4);

        // 본체
        g2.setStroke(new BasicStroke(1f));
        g2.setColor(new Color(200, 240, 255));
        g2.fillRect(x, y, WIDTH, HEIGHT);

        // 메인 테두리
        g2.setStroke(new BasicStroke(3f));
        g2.setColor(new Color(40, 90, 160));
        g2.drawRect(x, y, WIDTH, HEIGHT);

        // 내부 하이라이트
        g2.setStroke(new BasicStroke(1f));
        g2.setColor(new Color(255, 255, 255, 140));
        g2.drawRect(x + 3, y + 3, WIDTH - 6, HEIGHT - 6);
    }

}
