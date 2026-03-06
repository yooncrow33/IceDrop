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
}
