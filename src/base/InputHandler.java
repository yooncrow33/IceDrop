package base;

import view.IExit;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter{
    private final ViewMetrics viewMetrics;
    private final GameModel gameModel;
    private final IExit iExit;

    public InputHandler(ViewMetrics viewMetrics, GameModel gameModel, IExit iExit) {
        this.gameModel = gameModel;
        this.viewMetrics = viewMetrics;
        this.iExit = iExit;
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
            iExit.exitApplication();
        }
        if (e.getKeyCode() == KeyEvent.VK_M) {
            viewMetrics.calculateViewMetrics();
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            gameModel.clicked(true);
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
