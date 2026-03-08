package sc.base.gameModel.setting.object;

import sc.base.RenderUtils;
import sc.model.overlay.MessageConfig;
import sc.model.overlay.MessageKey;
import sc.view.IGameModel;
import sc.view.IMouse;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Knob {
    final IMouse iMouse;
    final IGameModel iGameModel;
    private double currentValue = 0.5;
    private int x,y;
    private double angle = -Math.PI/2;
    private double rawAngle;
    private final int radius = 45;
    private boolean selected = true;
    private final boolean toggle;

    private String showValue = "SC";

    MessageConfig knobSystemConfig = new MessageConfig(
            "Analog Knob UI",
            "Rotate with Mouse Wheel.",
            "Hold SHIFT for precise control.",
            "Range: 270 degrees (7:30 to 4:30).",
            "Built with AffineTransform & Trigonometry.",
            MessageKey.key.knob
    );

    public void setShowValueOffset(int showValueOffset) {
        this.showValueOffset = showValueOffset;
    }

    private int showValueOffset = 13;
    private Font showValueFont = new Font("Arial", Font.BOLD, 32);

    private final String name;

    Color gray = new Color(50, 60, 75);
    Color green = new Color(80, 220, 160);
    Color blue = new Color(0, 200, 255);
    Color yellow = new Color(255, 200, 90);
    Color black = new Color(20, 25, 35);
    Color white = new Color(200,215,235);


    public Knob(IMouse iMouse, IGameModel iGameModel, String name, double initValue,boolean toggle, int x, int y) {
        this.iMouse = iMouse;
        this.iGameModel = iGameModel;
        this.name = name;
        this.x = x;
        this.y = y;
        this.toggle = toggle;

        this.currentValue = initValue;

        // 현재 노브의 가동 범위는 0.75π ~ 2.25π (270도)
        double startLimit = 0.75 * Math.PI;
        double endLimit = 2.25 * Math.PI;

        // 초기값에 따른 각도 계산
        this.angle = startLimit + (currentValue * (endLimit - startLimit));
        this.rawAngle = this.angle; // 초기 rawAngle도 맞춰줘야 휠 조작 시 튀지 않음
    }

    public void setShowValue(String str) {
        showValue = str;
    }

    public void setShowValueFont(Font font) {
        showValueFont = font;
    }

    public void handleMouseWheel(int wheelRotation) {
        if (!selected) return;

        if (toggle) {
            if (wheelRotation > 0) currentValue = 0.0;
            else if (wheelRotation < 0) currentValue = 1.0;

            double startLimit = 0.75 * Math.PI;
            double endLimit = 2.25 * Math.PI;
            this.angle = startLimit + (currentValue * (endLimit - startLimit));
            this.rawAngle = this.angle;
            return;
        }

        double step = iGameModel.isShift() ? (Math.PI / 600) : (Math.PI / 60);
        double targetAngle = angle - (wheelRotation * step);

        setRawAngle(targetAngle);
    }

    private void setRawAngle(double angle) {
        rawAngle = angle;
    }

    public void update() {
        double dx = x - iMouse.getVirtualMouseX();
        double dy = y - iMouse.getVirtualMouseY();
        double distSq = dx * dx + dy * dy;
        double radiusSum = radius + 60;

        // 제곱 비교로 먼저 충돌 여부 확인 (루트 연산 아끼기)
        selected = distSq < radiusSum * radiusSum;
        if (!selected) return;
        if (rawAngle < 0) rawAngle += 2 * Math.PI;

        double startLimit = 0.75 * Math.PI;
        double endLimit = 2.25 * Math.PI;

        double adjustedAngle = rawAngle;

        if (adjustedAngle < 0.5 * Math.PI) {
            adjustedAngle += 2 * Math.PI;
        }

        double normalized = (adjustedAngle - startLimit) / (endLimit - startLimit);

        if (adjustedAngle < startLimit || adjustedAngle > endLimit) {
            if (currentValue > 0.8) {
                normalized = 1.0;
            } else if (currentValue < 0.2) {
                normalized = 0.0;
            } else {
                normalized = (adjustedAngle > 1.5 * Math.PI) ? 1.0 : 0.0;
            }
        }

        currentValue = Math.max(0.0, Math.min(1.0, normalized));

        angle = startLimit + (currentValue * (endLimit - startLimit));
    }

    public void render(Graphics g) {
        Graphics2D gd = (Graphics2D) g;

        // 1. 본체 (노브 원형)
        g.setColor(selected? Color.white : gray);
        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // 2. 현재 바늘 (이것만 rotate를 쓰는 게 편함)
        AffineTransform oldTransform = gd.getTransform();
        gd.translate(x, y);
        gd.rotate(angle);
        gd.setColor(black);
        gd.fillRect(32, -8, radius - 32, 16); // 반지름 내부에 바늘 표시
        gd.setTransform(oldTransform); // 즉시 복구

        // 270도 전체 범위를 표현하기 위한 수정
        // 시작 각도를 135도로 잡으면 6시 방향에서 왼쪽으로 45도 간 지점(7시~8시 사이)부터 시작합니다.
        g.setColor(selected? blue : gray);
        gd.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); // 끝을 둥글게 하면 더 예쁨

        // drawArc(x, y, width, height, startAngle, arcAngle)
        // startAngle: 225도 (Java 기준 3시 방향이 0도, 시계방향으로 225도 가면 7시 30분 방향임)
        // arcAngle: 현재 값에 따라 시계 방향으로 그려야 하므로 음수(-) 처리
        gd.drawArc(x - radius - 8, y - radius - 8, (radius + 8) * 2, (radius + 8) * 2, 225, -(int)(currentValue * 270));

        gd.setFont(showValueFont);
        g.setColor(selected? blue : black);
        RenderUtils.drawStringCenter(g,showValue,x,y+showValueOffset);


        // 3. 가이드 및 텍스트 설정
        gd.setFont(new Font("Arial", Font.BOLD, 16)); // 24는 너무 커서 겹칠 수 있음
        g.setColor(selected? Color.gray : Color.black);

        double minAngle = 0.75 * Math.PI; // 왼쪽 아래 135도
        double maxAngle = 0.25 * Math.PI; // 오른쪽 아래 45도

        // 4. Min 가이드 표시 (삼각함수로 좌표 직접 계산)
        int minX = (int) (x + Math.cos(minAngle) * (radius + 10));
        int minY = (int) (y + Math.sin(minAngle) * (radius + 10));
        RenderUtils.drawStringCenter(g,"min", minX, minY + 19); // 글자 위치 미세 조정
        gd.fillOval(minX - 2, minY - 2, 4, 4); // 점 하나 찍어서 위치 표시

        // 5. Max 가이드 표시
        int maxX = (int) (x + Math.cos(maxAngle) * (radius + 10));
        int maxY = (int) (y + Math.sin(maxAngle) * (radius + 10));
        RenderUtils.drawStringCenter(g,"max", maxX, maxY + 19);
        gd.fillOval(maxX - 2, maxY - 2, 4, 4);

        g.setFont(new Font("Arial", Font.BOLD,32));
        RenderUtils.drawStringCenter(g,name,x,y+95);
    }

    public double getCurrentValue() {
        return currentValue;
    }
}
