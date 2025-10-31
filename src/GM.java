import javax.swing.*;
import java.awt.*;

public class GM {
    public void renderBaseFrame(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0,10, Main.VIRTUAL_WIDTH - 10, 10);
        g.fillRect(0, 1060, Main.VIRTUAL_WIDTH - 10, 10);
        g.fillRect( 0,10,10, Main.VIRTUAL_HEIGHT - 20);
        g.fillRect(1910,10,10, Main.VIRTUAL_HEIGHT - 20);
        g.fillRect(955,10,10, Main.VIRTUAL_HEIGHT - 20);
    }

    public void renderTapFrame(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(965,120,945,820);
        g.setColor(Color.black);
        g.fillRect(970,125,935,810);
    }

    public void renderInfoTap(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("INFO Tap", 980, 165);
    }
    public void renderShopTap(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("SHOP Tap", 980, 165);
    }
    public void renderSkillPointTap(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("SKILL POINT Tap", 980, 165);
    }
    public void renderQuestsTap(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("QUESTS Tap", 980, 165);
    }
    public void renderSettingTap(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("SETTING Tap", 980, 165);
    }
    public void renderDebugTap(Graphics g, ViewMetrics info, TapData debugInfo) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("DEBUG Tap", 980, 165);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("windowWidth: " + info.getWindowWidth(), 980, 200);
        g.drawString("windowHeight: " + info.getWindowHeight(), 980, 230);
        g.drawString("scaleX: " + info.getScaleX(), 980, 260);
        g.drawString("scaleY: " + info.getScaleY(), 980, 290);
        g.drawString("currentScale: " + info.getCurrentScale(), 980, 320);
        g.drawString("currentXOffset: " + info.getCurrentXOffset(), 980, 350);
        g.drawString("currentYOffset: " + info.getCurrentYOffset(), 980, 380);
        g.drawString("used memory: " + debugInfo.getUsedMemory() + "MB", 980, 410);
        g.drawString("total memory: " + debugInfo.getTotalMemory() + "MB", 980, 440);
        g.drawString("free memory: " + debugInfo.getUsedMemory() + "MB", 980, 470);
        g.drawString("cpu usage: " + debugInfo.getCpuPercentage() + "%", 980, 500);
        g.drawString("moveX: " + debugInfo.getVirtualMouseX(), 980, 530);
        g.drawString("moveY: " + debugInfo.getVirtualMouseY(), 980, 560);
    }
    public void renderTapBar(Graphics g, int tap, int tapBarX) {
        if (tap != 6) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.RED);
        }
        g.fillRect(tapBarX,940, 189,20);
    }
}
