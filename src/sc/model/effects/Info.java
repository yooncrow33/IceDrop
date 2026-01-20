package sc.model.effects;

import java.awt.*;

public class Info {
    String firstLine;
    String secondLine;
    String thirdLine;
    int tick;
    int popupEndTick;
    int stayEndTick;
    int removeEndTick;
    final int STAY_TIME = 300; // ticks
    final int POPUP_TIME = 12; // ticks
    final int REMOVE_TIME = 12; // ticks
    int a = 156;
    int x = 10;
    int y = 920 + 150; // y = 920
    int width = 400;
    int height = 150;
    int rise = 0;

    public Info(String firstLine, String secondLine, String thirdLine,int resentTick) {
        this.firstLine = firstLine;
        this.secondLine = secondLine;
        this.thirdLine = thirdLine;
        this.tick = resentTick;
        this.popupEndTick = tick + POPUP_TIME;
        this.stayEndTick = popupEndTick + STAY_TIME;
        this.removeEndTick = stayEndTick + REMOVE_TIME;
    }

    public void update() {
        // Optional: You can add logic to update the info box if needed
        if (popupEndTick >= tick) {
            rise -= a/POPUP_TIME;
        } else if (stayEndTick >= tick) {
        } else if (removeEndTick >= tick) {
            rise += a/REMOVE_TIME;
        } else {
            rise = a;
        }

        tick ++;
    }

    public boolean isExpired() {
        return tick > removeEndTick;
    }

    public void draw(Graphics g) {
        Color gray = new Color(50, 60, 75);
        Color black = new Color(20, 25, 35);
        Color green = new Color(80, 220, 160);
        Color blue = new Color(0, 200, 255);
        Color yellow = new Color(255, 200, 90);
        Color white = new Color(200,215,235);

        g.setColor(white); // info background color
        g.fillRect(x,y + rise, 400,height);
        g.setColor(black); // info top bar color
        g.fillRect(x, y + rise, 400,20);
        g.setColor(gray); // info right square color
        g.fillRect(x + width - 20, y + rise, 20,20);

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(white);
        g.drawString("Info System", 20, y + 15 + rise);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(black); // info = 920
        g.drawString(firstLine, 20, y + 50 + rise);
        g.drawString(secondLine, 20, y + 80 + rise);
        g.drawString(thirdLine, 20, y + 110 + rise);

/*
        g.setColor(new Color(191,222,255)); // info background color
        g.fillRect(x,y + rise, 400,height);
        g.setColor(new Color(0,74,153)); // info top bar color
        g.fillRect(x, y + rise, 400,20);
        g.setColor(new Color(15,135,255)); // info right square color
        g.fillRect(x + width - 20, y + rise, 20,20);

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.white);
        g.drawString("Info System", 20, y + 15 + rise);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.black); // info = 920
        g.drawString(firstLine, 20, y + 50 + rise);
        g.drawString(secondLine, 20, y + 80 + rise);
        g.drawString(thirdLine, 20, y + 110 + rise);

 */
    }
}
