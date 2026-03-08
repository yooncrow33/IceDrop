package sc.model.effects;

import java.awt.*;
import java.util.Random;

public class Fa {
    double x, y;
    final double angle;
    final double speed;
    int alpha = 255;


    public Fa(double X, double Y) {
        Random ran = new Random();
        this.x = X;
        this.y = Y;

        this.angle = ran.nextDouble() * Math.PI * 2;
        this.speed = 50 + ran.nextDouble() * 50;
    }

    public void update(double dt) {
        x += speed * Math.cos(angle) * dt;
        y += speed * Math.sin(angle) * dt;
        alpha -= 10;
    }

    public void renderFa(Graphics g) {

        g.setColor(new Color(120, 220, 255,alpha));

        int size = 18;
        double tailWidth = 2.5;

        int x1 = (int) (x + Math.cos(angle) * size);
        int y1 = (int) (y + Math.sin(angle) * size);

        int x2 = (int) (x + Math.cos(angle + tailWidth) * (size / 2.0));
        int y2 = (int) (y + Math.sin(angle + tailWidth) * (size / 2.0));

        int x3 = (int) (x + Math.cos(angle - tailWidth) * (size / 2.0));
        int y3 = (int) (y + Math.sin(angle - tailWidth) * (size / 2.0));

        int[] xPoints = {x1, x2, x3};
        int[] yPoints = {y1, y2, y3};

        g.fillPolygon(xPoints, yPoints, 3);
    }

    public boolean isFire() {
        return alpha <= 0;
    }
}