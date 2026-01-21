package sc.model.effects;

import java.awt.*;
import java.util.Random;

public class Fa {
    double x, y;
    final double angle; // 생성 시 결정된 각도 고정
    final double speed; // 입자마다 속도를 다르게 하면 더 자연스러움
    int alpha = 255;


    final int VIRTUAL_X_SCREEN_CENTER = 960;
    final int VIRTUAL_Y_SCREEN_CENTER = 540;

    public Fa(double X, double Y) {
        Random ran = new Random();
        this.x = X;
        this.y = Y;

        // 0~360도 무작위 방향 설정
        this.angle = ran.nextDouble() * Math.PI * 2;
        // 비산 효과를 위해 속도도 랜덤하게 부여 (5~15 사이)
        this.speed = 50 + ran.nextDouble() * 50;
    }

    public void update(double dt) {
        // 고정된 각도로 직진
        x += speed * Math.cos(angle) * dt;
        y += speed * Math.sin(angle) * dt;
        alpha -= 10;
    }

    public void renderFa(Graphics g) {

        g.setColor(new Color(120, 220, 255,alpha));

        // 삼각형의 크기 (길이)
        int size = 18;
        // 삼각형의 폭 (뒷부분 벌어지는 정도)
        double tailWidth = 2.5;

        // 1. 머리 부분 (진행 방향 앞쪽)
        int x1 = (int) (x + Math.cos(angle) * size);
        int y1 = (int) (y + Math.sin(angle) * size);

        // 2. 꼬리 왼쪽 (진행 방향에서 약간 뒤쪽 + 왼쪽)
        int x2 = (int) (x + Math.cos(angle + tailWidth) * (size / 2.0));
        int y2 = (int) (y + Math.sin(angle + tailWidth) * (size / 2.0));

        // 3. 꼬리 오른쪽 (진행 방향에서 약간 뒤쪽 + 오른쪽)
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