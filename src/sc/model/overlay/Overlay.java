package sc.model.overlay;

import sc.base.RenderUtils;
import sc.view.IGameModel;
import sc.view.IMouse;

import java.awt.*;

public class Overlay {
    private int x,y = 0;
    private final int width = 500;
    private final int height = 130;
    private final double lerp = 0.08;
    private boolean visible = false;

    Color gray = new Color(50, 60, 75);
    Color black = new Color(20, 25, 35);
    Color green = new Color(80, 220, 160);
    Color blue = new Color(30, 110, 185);
    Color yellow = new Color(255, 200, 90);
    Color white = new Color(200,215,235);

    Rectangle quest1Rect = new Rectangle(980, 180, 915, 240);
    Rectangle quest2Rect = new Rectangle(980, 430, 915, 240);
    Rectangle quest3Rect = new Rectangle(980, 680, 915, 240);
    Rectangle questRefreshRect = new Rectangle(1340, 130, 550, 45);

    Rectangle iceBasicRush = new Rectangle(985, 185, 298 - 10, 240 - 10);
    Rectangle iceRareRush = new Rectangle(1293, 185, 298 - 10, 240 - 10);
    Rectangle iceLegendaryRush = new Rectangle(1601, 185, 298 - 10, 240 - 10);

    Rectangle iceAutoCollect = new Rectangle(980,430,915,240);
    Rectangle iceVacuum = new Rectangle(980,680,915,240);

    final IMouse iMouse;
    final IGameModel iGameModel;

    MessageConfig currentMessageConfig = null;

    public Overlay(IMouse iMouse, IGameModel iGameModel) {
        this.iMouse = iMouse;
        this.iGameModel = iGameModel;
        Messages messages = new Messages();
    }

    public void update() {
        follow(
                Math.max(0, Math.min(iMouse.getVirtualMouseX(), 1920 - width)),
                Math.max(0, Math.min(iMouse.getVirtualMouseY(), 1080 - height))
        );
        /*iGameModel.getBarManager().click();
        iGameModel.getExitPopup().click();
        iGameModel.setClicked;
         */
        final int width = 110;
        final int height = 30;
        final String strs[] = {"⚠ Quit", "▼ Save", "▲ Load", "⏸ Pause", "⚙ Setting", ">_ Console"};
        currentMessageConfig = null;

        if (iMouse.getVirtualMouseY() <= 30) {
            currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.bar);
            if (iMouse.getVirtualMouseX() >= 940 && iMouse.getVirtualMouseX() <= 980) {
                currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.nonsense);
            }
            if (iMouse.getVirtualMouseX() >= 1880) {
                currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.pauseLight);
            }
            for (int i = 0; i < strs.length; i++) {
                int xPos = i * width;
                if (iGameModel.getiMouse().getVirtualMouseX() >= xPos && iGameModel.getiMouse().getVirtualMouseX() < xPos + width &&
                        iGameModel.getiMouse().getVirtualMouseY() >= 0 && iGameModel.getiMouse().getVirtualMouseY() <= height) {
                    switch (i) {
                        case 0 :
                            currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.quit);
                            break;
                        case 1 :
                            currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.save);
                            break;
                        case 2 :
                            currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.load);
                            break;
                        case 3 :
                            currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.pause);
                            break;
                        case 4 :
                            currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.setting);
                            break;
                        case 5 :
                            currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.console);
                            break;
                    }
                }
            }
        }


        if (iGameModel.getTabManager().getTab() == 4) {
            if (quest1Rect.contains(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY())) {
                currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.Quest);
            }
            if (quest2Rect.contains(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY())) {
                currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.Quest);
            }
            if (quest3Rect.contains(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY())) {
                currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.LongTimeQuest);
            }
            if (questRefreshRect.contains(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY())) {
                currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.refresh);
            }

        }
        if (iGameModel.getTabManager().getTab() == 3) {
            if (iceBasicRush.contains(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY())) {
                currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.iceSpawnChance);
            }
            if (iceRareRush.contains(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY())) {
                currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.iceSpawnChance);
            }
            if (iceLegendaryRush.contains(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY())) {
                currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.iceSpawnChance);
            }
            if (iceAutoCollect.contains(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY())) {
                currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.clickOffset);
            }
            if (iceVacuum.contains(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY())) {
                currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.itemCoolTime);
            }
        }

        if (iGameModel.getTabManager().getTab() == 2) {
            if (iceBasicRush.contains(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY())) {
                currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.rushItem);
            }
            if (iceRareRush.contains(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY())) {
                currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.rushItem);
            }
            if (iceLegendaryRush.contains(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY())) {
                currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.rushItem);
            }
            if (iceAutoCollect.contains(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY())) {
                currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.autoCollect);
            }
            if (iceVacuum.contains(iMouse.getVirtualMouseX(),iMouse.getVirtualMouseY())) {
                currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.vacuum);
            }
        }

        if (iGameModel.getSettingManager().isOpen()) {
            currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.knob);
        }

        if (iMouse.getVirtualMouseX() >= 10 && iMouse.getVirtualMouseX() <= 945 && iMouse.getVirtualMouseY() >= 40 && iMouse.getVirtualMouseY() <= 1070) {
            currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.frozenFiled);
        }

        if (iMouse.getVirtualMouseX() >= 960 && iMouse.getVirtualMouseX() <= 1910 && iMouse.getVirtualMouseY() >= 40 && iMouse.getVirtualMouseY() <= 140) {
            currentMessageConfig = MessageManager.getOverlayMessage(MessageKey.key.economy);
        }
    }

    public void follow(int targetX, int targetY) {
        this.x += (int) ((targetX + 20 - this.x) * lerp);
        this.y += (int) ((targetY + 20 - this.y) * lerp);
    }

    public void toggle() {visible = !visible;}

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void render(Graphics g) {
        if (!visible) return;
        Graphics2D gd = (Graphics2D) g;

        g.setColor(black);
        g.fillRect(x,y,width,height);

        g.setColor(blue);
        g.fillRect(x,y,width,40);

        if (currentMessageConfig != null) {
            g.setColor(white);
            g.setFont(new Font("Arial", Font.BOLD, 28));
            RenderUtils.drawStringCenter(g, currentMessageConfig.title, x + width/2, y+31);

            int lineOffset = 20;
            g.setColor(yellow);
            g.setFont(new Font("Dialog", Font.PLAIN,16));
            String strs[] = {currentMessageConfig.l1,currentMessageConfig.l2, currentMessageConfig.l3, currentMessageConfig.l4};
            for (int i = 0; i < 4; i++) {
                RenderUtils.drawStringCenter(g,strs[i] , x + width / 2, y + 60 + (i * lineOffset));
            }
        } else {
            g.setColor(white);
            g.setFont(new Font("Arial", Font.BOLD, 28));
            RenderUtils.drawStringCenter(g, "Wait...", x + width/2, y+31);
        }

        gd.setStroke(new BasicStroke(3f));
        g.setColor(Color.white);
        g.drawRect(x,y,width,height);
    }

}
