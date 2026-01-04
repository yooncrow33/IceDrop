package model.effects;

import java.awt.*;

public class Info {
    String firstLine;
    String secondLine;
    String thirdLine;
    long createTick;
    int x = 10;
    int y = 920 - 150; // y = 920
    int width = 400;
    int height = 150;
    int rise = 0;

    public Info(String firstLine, String secondLine, String thirdLine,long resentTick) {
        this.firstLine = firstLine;
        this.secondLine = secondLine;
        this.thirdLine = thirdLine;
        this.createTick = resentTick;
    }

    public void update() {
        // Optional: You can add logic to update the info box if needed

    }

    public void draw(Graphics g) {
        g.setColor(new Color(191,222,255)); // info background color
        g.fillRect(x,y + 0, 400,height);
        g.setColor(new Color(0,74,153)); // info top bar color
        g.fillRect(x, y + 0, 400,20);
        g.setColor(new Color(15,135,255)); // info right square color
        g.fillRect(x + width - 20, y + 0, 20,20);

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.white);
        g.drawString("Info System", 10, 935);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.black);
        g.drawString("Profile : ", 10, 970 + 0);
        g.drawString("Total Played Time : ", 10, 1000 + 0);
        g.drawString("Session Played Time : ", 10, 1030 + 0);
    }
}
