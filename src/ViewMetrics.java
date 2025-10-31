import javax.swing.*;

public class ViewMetrics implements IViewMetrics {
    private JComponent targetComponent;
    int windowWidth;
    private int windowHeight;
    private double currentScale;
    private int currentXOffset;
    private int currentYOffset;
    private double scaleX;
    private double scaleY;
    private double scale;
    private int xOffset, yOffset;
    final int VIRTUAL_WIDTH = 1920;
    final int VIRTUAL_HEIGHT = 1080;
    public ViewMetrics(JComponent target) {
        this.targetComponent = target;
    }
        public void calculateViewMetrics() {
            windowWidth = targetComponent.getWidth();
            windowHeight = targetComponent.getHeight();

            // 기준 해상도와 실제 창 크기 비율
            scaleX = windowWidth / (double) VIRTUAL_WIDTH;
            scaleY = windowHeight / (double) VIRTUAL_HEIGHT;

            // 두 비율 중 작은 걸 선택 (aspect ratio 유지ㄴ
            scale = Math.min(scaleX, scaleY);

            // 중앙 정렬을 위해 여백 계산
            xOffset = (int) ((windowWidth - VIRTUAL_WIDTH * scale) / 2);
            yOffset = (int) ((windowHeight - VIRTUAL_HEIGHT * scale) / 2);

            currentScale = scale; // Math.min(scaleX, scaleY) 값
            currentXOffset = xOffset;
            currentYOffset = yOffset;
        }

    public int getVirtualX(int mouseX) {
        // 1. 여백(오프셋)을 먼저 뺍니다.
        int adjustedX = mouseX - getCurrentXOffset();
        // 2. 전체 스케일(currentScale)로 나눕니다.
        // currentScale은 Math.min(scaleX, scaleY)로 계산된 값입니다.
        double scale = getCurrentScale();
        if (scale <= 0) return mouseX; // 0으로 나누는 것과 오류 방지
        return (int) (adjustedX / scale);
    }

    public int getVirtualY(int mouseY) {
        // 1. 여백(오프셋)을 뺍니다.
        int adjustedY = mouseY - getCurrentYOffset();
        // 2. 전체 스케일(currentScale)로 나눕니다.
        // currentScale은 Math.min(scaleX, scaleY)로 계산된 값입니다.
        double scale = getCurrentScale();
        if (scale <= 0) return mouseY;
        return (int) (adjustedY / scale);
    }
    @Override public int getWindowWidth() { return windowWidth; };
    @Override public int getWindowHeight() { return windowHeight; };
    @Override public double getScaleX() { return scaleX;}
    @Override public double getScaleY() { return scaleY;}
    @Override public double getCurrentScale() { return currentScale; }
    @Override public int getCurrentXOffset() { return currentXOffset; }
    @Override public int getCurrentYOffset() { return currentYOffset; }
}

