package sc.base;

import java.awt.*;

public class RenderUtils {
    public static void drawStringCenter(Graphics g, String text, int x, int y) {
        FontMetrics metrics = g.getFontMetrics();

        int textWidth = metrics.stringWidth(text);

        int drawX = x - (textWidth / 2);

        //int textHeight = metrics.getAscent();
        //int drawY = y + (textHeight / 2);

        g.drawString(text, drawX, y);
    }

    public static void applyQualityHints(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }
}
