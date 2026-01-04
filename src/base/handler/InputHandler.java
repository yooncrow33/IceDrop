package base.handler;

import base.GameModel;
import base.ViewMetrics;
import model.effects.Info;
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
            gameModel.tapMoveRight();
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
        if (e.getKeyCode() == KeyEvent.VK_Q) { gameModel.activateIceRushItem(1); }
        if (e.getKeyCode() == KeyEvent.VK_W) { gameModel.activateIceRushItem(2); }
        if (e.getKeyCode() == KeyEvent.VK_E) { gameModel.activateIceRushItem(3); }
        if (e.getKeyCode() == KeyEvent.VK_R) { gameModel.iceVacuumActive(); }
        if (e.getKeyCode() == KeyEvent.VK_P) { gameModel.addInfo(new Info("I am", "so", "pretty",gameModel.getPlayTick())); }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SHIFT) {
            gameModel.setShiftPressed(false);
        }
    }
}
