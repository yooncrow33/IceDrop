package sc.base;

import sc.base.gameModel.GameModel;
import sc.base.input.InputHandler;
import sc.base.input.MouseListener;
import sc.base.splashScreen.EndSplashScreen;
import sc.lang.Lang;
import sc.view.IExit;
import sc.view.IFrameSize;
import sc.view.IPause;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class Main extends JPanel implements IFrameSize, IExit, IPause {
    JFrame frame = new JFrame("beta 1.0.0");

    private long lastTime;

    private boolean isResizing = false;

    private boolean pause = false;

    public final int currentProfileId;
    public final int language;

    private final ViewMetrics viewMetrics;
    private final SystemMonitor systemMonitor;
    private final GameModel gameModel;
    private final GraphicsManager graphicsManager;

    public Main(int profileId, int language) {
        this.language = language;
        Lang l = new Lang(language);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        frame.setPreferredSize((new Dimension(1280,720)));
        setFocusable(true);

        viewMetrics = new ViewMetrics(this);
        systemMonitor = new SystemMonitor();
        currentProfileId = profileId;
        gameModel = new GameModel(profileId, viewMetrics, l,this,this);
        MouseListener mouseListener = new MouseListener(gameModel, viewMetrics);

        frame.add(this);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocus();
        frame.pack();
        InputHandler inputHandler = new InputHandler(viewMetrics, gameModel, this);
        graphicsManager = new GraphicsManager(l);

        setBackground(Color.BLACK);

        viewMetrics.calculateViewMetrics();

        this.addMouseListener(mouseListener);
        this.addMouseWheelListener(mouseListener);

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                viewMetrics.updateVirtualMouse(e.getX(),e.getY());
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (isResizing) return;

                int currentW = frame.getWidth();
                int currentH = frame.getHeight();

                double ratio = (double) currentW / currentH;
                double targetRatio = 16.0 / 9.0;

                if (Math.abs(ratio - targetRatio) > 0.05) {

                    isResizing = true;

                    int newH = (int) (currentW / targetRatio);
                    int newW = (int) (currentH * targetRatio);

                    if (Math.abs(currentW - newW) > Math.abs(currentH - newH)) {
                        frame.setSize(newW, currentH);
                    } else {
                        frame.setSize(currentW, newH);
                    }

                    viewMetrics.calculateViewMetrics();
                    EventQueue.invokeLater(() -> isResizing = false);
                }
            }
        });



        startGameLoop();

        frame.addKeyListener(inputHandler);
    }

    private void startGameLoop() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        lastTime = System.nanoTime();

        executor.scheduleAtFixedRate(() -> {
            try {
                long now = System.nanoTime();
                double deltaTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                update(deltaTime);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            SwingUtilities.invokeLater(this::repaint);

        }, 0, 16, TimeUnit.MILLISECONDS);
    }

    private void update(double deltaTime) {
        systemMonitor.updateMetrics();
        gameModel.update(deltaTime);
    }

    @Override public int getComponentWidth() { return this.getWidth(); }
    @Override public int getComponentHeight() { return this.getHeight(); }
    @Override public void exitApplication() {
        gameModel.getFileManager().save(currentProfileId);
        frame.dispose();
        EndSplashScreen.showSplashThenLaunchGame();
        gameModel.getSoundManager().stopBgm();
        gameModel.getSoundManager().play("logo2.wav");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D d2 = (Graphics2D) g;

        if (gameModel.getSettingManager().getGraphicSetting().isScreenShake() && gameModel.getSettingManager().getGraphicSetting().isScreenShakeActive()){
            graphicsManager.startShake(g,gameModel);
        }

        if (gameModel.getSettingManager().getGraphicSetting().isAa()) RenderUtils.applyQualityHints(d2);

        Color black = new Color(20, 25, 35);

        d2.translate(viewMetrics.getCurrentXOffset(), viewMetrics.getCurrentYOffset());
        d2.scale(viewMetrics.getCurrentScale(), viewMetrics.getCurrentScale());

        g.setColor(black);
        g.fillRect(10,10,1900,1060);
        g.setColor(new Color(200, 215, 235));
        g.setFont(new Font("SansSerif", Font.BOLD, 56));
        g.drawString("Coin : " + gameModel.getShopManager().getCoin() + "/ Level : " + gameModel.getSkillManager().getLevel(), 980, 90);

        if (gameModel.getTabManager().isTab1enabled()) { graphicsManager.renderInfoTap(g, gameModel.getTabManager().getTab1X(), gameModel); }
        if (gameModel.getTabManager().isTab2enabled()) { graphicsManager.renderShopTap(g, gameModel.getTabManager().getTab2X(), gameModel); }
        if (gameModel.getTabManager().isTab3enabled()) { graphicsManager.renderSkillPointTap(g,gameModel, gameModel.getTabManager().getTab3X()); }
        if (gameModel.getTabManager().isTab4enabled()) { graphicsManager.renderQuestsTap(g, gameModel.getTabManager().getTab4X(), gameModel); }
        if (gameModel.getTabManager().isTab5enabled()) { graphicsManager.renderEmptyMan(g, gameModel.getTabManager().getTab5X()); }

        if (gameModel.getTabManager().getTab() == 1) {
            graphicsManager.renderInfoTap(g,gameModel.getTabManager().getTab1X(), gameModel);
        } else if (gameModel.getTabManager().getTab() == 2) {
            graphicsManager.renderShopTap(g,gameModel.getTabManager().getTab2X(), gameModel);
        } else if (gameModel.getTabManager().getTab() == 3) {
            graphicsManager.renderSkillPointTap(g,gameModel , gameModel.getTabManager().getTab3X());
        } else if (gameModel.getTabManager().getTab() == 4) {
            graphicsManager.renderQuestsTap(g,gameModel.getTabManager().getTab4X(), gameModel);
        } else if (gameModel.getTabManager().getTab() == 5) {
            graphicsManager.renderEmptyMan(g, gameModel.getTabManager().getTab5X());
        }

        graphicsManager.renderTabFrame(g, gameModel);
        graphicsManager.renderBackGround(g);
        graphicsManager.renderTapBar(g, gameModel.getTabManager().getTab(), gameModel.getTabManager().getTabBarPosition());
        gameModel.getIceManager().renderIces(g);
        graphicsManager.renderBaseFrame(g);
        gameModel.getEffectManager().renderEffects(g);

        gameModel.getSettingManager().render(g);

        gameModel.getConsole().render(g);

        gameModel.getBarManager().render(g);

        gameModel.getOverlay().render(g);

        gameModel.getExitPopup().render(g);

        if (gameModel.getSettingManager().getGraphicSetting().isScreenShake() && gameModel.getSettingManager().getGraphicSetting().isScreenShakeActive()){
            graphicsManager.endShake(g);
        }
    }

    public static void main(String[] args) {
        new Main(1,2);
    }

    @Override public boolean isPause() {return pause;}
    @Override public void setPause(boolean b) {pause = b;}
}
