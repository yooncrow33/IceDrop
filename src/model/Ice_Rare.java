package model;

import view.IMouse;

import java.awt.*;
import java.util.Random;

public class Ice_Rare {
    private final int WIDTH = 30;
    private final int HEIGHT = 30;
    private int x;
    private int y;
    private  final int FALL_SPEED = 10;

    Random random = new Random();

    public Ice_Rare() {
        x = random.nextInt(915) + 10;
        y = 10;
    }

    public void update() {
        y += FALL_SPEED;
    }

    public boolean shouldBeRemoved() {
        return y > 1040;
    }

    public boolean shouldBeCollected(int mouseX, int mouseY) {
        return mouseX + 20 >= x && mouseX - 20 <= x + WIDTH && mouseY + 50 >= y && mouseY - 50 <= y + HEIGHT;
    }

    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }
}
