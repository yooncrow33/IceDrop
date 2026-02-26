package sc.base.input;

import sc.base.gameModel.GameModel;
import sc.base.ViewMetrics;
import sc.view.IExit;

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
            gameModel.getTapManager().tabMoveRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            gameModel.getTapManager().tabMoveLeft();
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
        if (e.getKeyCode() == KeyEvent.VK_Q) { gameModel.getShopManager().activateIceRushItem(1); }
        if (e.getKeyCode() == KeyEvent.VK_W) { gameModel.getShopManager().activateIceRushItem(2); }
        if (e.getKeyCode() == KeyEvent.VK_E) { gameModel.getShopManager().activateIceRushItem(3); }
        if (e.getKeyCode() == KeyEvent.VK_R) { gameModel.getShopManager().iceVacuumActive(); }
        if (e.getKeyCode() == KeyEvent.VK_P) { gameModel.getEffectManager().addInfo("I am", "so", "pretty"); }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SHIFT) {
            gameModel.setShiftPressed(false);
        }
    }
}
