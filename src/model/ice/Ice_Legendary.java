package model.ice;

import model.effects.IceAfterImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Ice_Legendary {
    private final int WIDTH = 30;
    private final int HEIGHT = 30;
    private int x;
    private int y;
    private  final double FALL_SPEED = 20.0;
    private boolean afterImageLimit = false;
    ArrayList<IceAfterImage> iceAfterImages = new ArrayList<>();

    Random random = new Random();

    public Ice_Legendary() {
        x = random.nextInt(915) + 10;
        y = 10;
    }

    public void update(double dt) {
        y += FALL_SPEED * (dt / (16.0 / 1000.0));
        if (!afterImageLimit) {
            iceAfterImages.add(new IceAfterImage(x,y,3));
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

    public boolean shouldBeRemoved() {
        return y > 1040;
    }

    public boolean shouldBeCollected(int mouseX, int mouseY) {
        return mouseX + 20 >= x && mouseX - 20 <= x + WIDTH && mouseY + 60 >= y && mouseY - 60 <= y + HEIGHT;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return  y;
    }

    public void draw(Graphics g) {
        for (IceAfterImage ai : iceAfterImages) {
            ai.draw(g);
        }
        g.setColor(Color.RED);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }
}
