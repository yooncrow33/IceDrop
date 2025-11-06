import java.awt.*;
import java.util.Random;

public class Ice_Basic {
    private final int WIDTH = 30;
    private final int HEIGHT = 30;
    private int x;
    private int y;
    private  final int FALL_SPEED = 6;

    Random random = new Random();

    public Ice_Basic() {
        x = random.nextInt(915) + 10;
        y = 10;
    }

    public void update() {
        y += FALL_SPEED;
    }

    public boolean shouldBeRemoved() {
        return y > 1040;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }
}
