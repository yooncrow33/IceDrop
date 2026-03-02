package sc.base;

import sc.view.IFrameSize;
import sc.view.IMouse;
import sc.view.IViewMetrics;

public class ViewMetrics implements IViewMetrics, IMouse {
    int windowWidth;
    private int windowHeight;
    private double currentScale;
    private int currentXOffset;
    private int currentYOffset;
    private double scaleX;
    private double scaleY;
    private double scale;
    private int virtualMouseX,virtualMouseY = 0;
    private int xOffset, yOffset;
    final int VIRTUAL_WIDTH = 1920;
    final int VIRTUAL_HEIGHT = 1080;

    private IFrameSize size;

    public ViewMetrics(IFrameSize size) {
        this.size = size;
    }
        public void calculateViewMetrics() {
            windowWidth = size.getComponentWidth();
            windowHeight = size.getComponentHeight();

            scaleX = windowWidth / (double) VIRTUAL_WIDTH;
            scaleY = windowHeight / (double) VIRTUAL_HEIGHT;

            scale = Math.min(scaleX, scaleY);

            xOffset = (int) ((windowWidth - VIRTUAL_WIDTH * scale) / 2);
            yOffset = (int) ((windowHeight - VIRTUAL_HEIGHT * scale) / 2);

            currentScale = scale;
            currentXOffset = xOffset;
            currentYOffset = yOffset;
        }

    public int getVirtualX(int mouseX) {
        int adjustedX = mouseX - getCurrentXOffset();
        double scale = getCurrentScale();
        if (scale <= 0) return mouseX;
        return (int) (adjustedX / scale);
    }

    public int getVirtualY(int mouseY) {
        int adjustedY = mouseY - getCurrentYOffset();
        double scale = getCurrentScale();
        if (scale <= 0) return mouseY;
        return (int) (adjustedY / scale);
    }

    public void updateVirtualMouse(int mouseX, int mouseY) {
        virtualMouseX = getVirtualX(mouseX);
        virtualMouseY = getVirtualY(mouseY);
    }

    @Override public int getVirtualMouseX() { return virtualMouseX; }
    @Override public int getVirtualMouseY() { return virtualMouseY; }

    @Override public int getWindowWidth() { return windowWidth; };
    @Override public int getWindowHeight() { return windowHeight; };
    @Override public double getScaleX() { return scaleX;}
    @Override public double getScaleY() { return scaleY;}
    @Override public double getCurrentScale() { return currentScale; }
    @Override public int getCurrentXOffset() { return currentXOffset; }
    @Override public int getCurrentYOffset() { return currentYOffset; }
}

