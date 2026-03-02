package sc.base.input;

import sc.base.gameModel.GameModel;
import sc.base.ViewMetrics;
import sc.view.IExit;

import java.awt.*;
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
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_TYPED && gameModel.getConsole().isOpen()) {
                char c = e.getKeyChar();
                if (c != '\n' && c != '\b' && c != 27) {
                    gameModel.getConsole().inputKey(c, 0);
                }
            }
            return false;
        });

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F12) {
            gameModel.getConsole().toggle();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (gameModel.getConsole().isOpen()) gameModel.getConsole().inputKey('\n', 10);
        }
        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if (gameModel.getConsole().isOpen()) gameModel.getConsole().inputKey('\b', 8);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            gameModel.getTabManager().tabMoveRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            gameModel.getTabManager().tabMoveLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (gameModel.getConsole().isOpen()) {gameModel.getConsole().inputKey('\b', 8); return;}
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
}
