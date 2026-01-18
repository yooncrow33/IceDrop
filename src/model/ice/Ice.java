package model.ice;

import java.awt.*;

public interface Ice {
    void update(double dt);
    void draw(Graphics g);
    double getAngle();
    boolean shouldBeRemoved();
    boolean shouldBeCollected(int mouseX, int mouseY, int offsetIndex);
    void setVacuumActive(boolean active);
    String name();
    int getValue();
    int getTier();
}
