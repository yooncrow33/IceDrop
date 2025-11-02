import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter{
    private final ViewMetrics viewMetrics;
    private final GameModel gameModel;
    public InputHandler(ViewMetrics viewMetrics,GameModel gameModel) {
        this.gameModel = gameModel;
        this.viewMetrics = viewMetrics;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            gameModel.tapMoveRight(gameModel.shiftPressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            gameModel.tapMoveLeft();
        }
        if (key == KeyEvent.VK_SHIFT) {
            gameModel.setShiftPressed(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameModel.save(gameModel.currentProfileId);
            System.exit(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_M) {
            viewMetrics.calculateViewMetrics();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SHIFT) {
            gameModel.setShiftPressed(false);
        }
    }
}
