package sc.base.gameModel.bar;

import sc.base.GraphicsManager;
import sc.view.IExit;
import sc.view.IGameModel;
import sc.view.IMouse;
import sc.view.IPause;

import java.awt.*;

public class BarManager {
    final IGameModel iGameModel;
    final IMouse iMouse;
    final IExit iExit;
    final IPause iPause;

    final int width = 110;
    final int height = 30;
    final String strs[] = {"Quit", "Save", "Load", "Pause", "Setting", "Console"};

    public BarManager(IGameModel iGameModel, IMouse iMouse, IExit iExit, IPause iPause) {
        this.iGameModel = iGameModel;
        this.iMouse = iMouse;
        this.iExit = iExit;
        this.iPause = iPause;
    }

    public void execute(int i) {
        switch (i) {
            case 0 :
                iExit.exitApplication();
                break;
            case 1 :
                iGameModel.getFileManager().save(iGameModel.getCurrentProfileId());
                break;
            case 2 :
                iGameModel.getFileManager().load(iGameModel.getCurrentProfileId());
                break;
            case 3 :
                if (iPause.isPause()) {
                    iPause.setPause(false);
                } else  iPause.setPause(true);
                break;
            case 4 :

                break;
            case 5 :
                iGameModel.getConsole().toggle();
                break;
            default:


        }
    }

    public void click() {
        if (iMouse.getVirtualMouseY() >= 30) {return;}
        for (int i = 0; i < strs.length; i++) {
            int xPos = i * width;

            if (iMouse.getVirtualMouseX() >= xPos && iMouse.getVirtualMouseX() < xPos + width &&
                    iMouse.getVirtualMouseY() >= 0 && iMouse.getVirtualMouseY() <= height) {
                execute(i);
            }
        }
    }

    public void render(Graphics g) {
        if (iMouse.getVirtualMouseY() >= 30) {return;}
        g.setColor(new Color(0,140,215));
        g.fillRect(0,0,1920,height);
        for (int i = 0; i < strs.length; i++) {
            int xPos = i * width;

            if (iMouse.getVirtualMouseX() >= xPos && iMouse.getVirtualMouseX() < xPos + width &&
                    iMouse.getVirtualMouseY() >= 0 && iMouse.getVirtualMouseY() <= height) {

                g.setColor(new Color(255, 255, 255, 100));
                g.fillRect(xPos, 0, width, height);
            }
        }
        g.setFont(new Font("Arial", Font.ITALIC, 24));
        g.setColor(Color.black);
        for (int i = 0; i < strs.length; i++) {
            g.drawString(strs[i],2 + (i * width),24);
        }
        g.setColor(Color.white);
        for (int i = 0; i < strs.length; i++) {
            g.drawString(strs[i],4 + (i * width),24);
        }
    }
}
