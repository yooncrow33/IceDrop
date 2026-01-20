package sc.base;

import sc.base.gameModel.GameModel;
import sc.base.handler.InputHandler;
import sc.base.handler.MouseListener;
import sc.base.splashScreen.EndSplashScreen;
import sc.lang.Lang;
import sc.view.IExit;
import sc.view.IFrameSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends JPanel implements IFrameSize, IExit {
    JFrame frame = new JFrame("alpha 1.13.4");

    private long lastTime;

    private boolean isResizing = false;

    public final int currentProfileId;
    public final int language;

    private final ViewMetrics viewMetrics;
    private final SystemMonitor systemMonitor;
    private final GameModel gameModel;
    private final MouseListener mouseListener;
    private final GraphicsManager graphicsManager;
    private final Lang l;

    public Main(int profileId, int language) {
        this.language = language;
        l = new Lang(language);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        frame.setPreferredSize((new Dimension(1280,720)));
        setFocusable(true);

        viewMetrics = new ViewMetrics(this);
        systemMonitor = new SystemMonitor();
        currentProfileId = profileId;
        gameModel = new GameModel(profileId, viewMetrics,l);
        mouseListener = new MouseListener(gameModel, viewMetrics);

        frame.add(this);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocus();
        frame.pack();
        InputHandler inputHandler = new InputHandler(viewMetrics, gameModel, this);
        graphicsManager = new GraphicsManager(l);

        gameModel.load(currentProfileId);

        setBackground(Color.BLACK);

        viewMetrics.calculateViewMetrics();

        this.addMouseListener(mouseListener);

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

                int currentW = frame.getWidth(); // 프레임의 크기를 사용
                int currentH = frame.getHeight(); // 프레임의 크기를 사용

                // 현재 비율 계산
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
                t.printStackTrace(); // 로그 남기고
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
    @Override public void exitApplication() { gameModel.save(currentProfileId); frame.dispose(); EndSplashScreen.showSplashThenLaunchGame();}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D d2 = (Graphics2D) g;

        Color black = new Color(20, 25, 35);

        d2.translate(viewMetrics.getCurrentXOffset(), viewMetrics.getCurrentYOffset());
        d2.scale(viewMetrics.getCurrentScale(), viewMetrics.getCurrentScale());

        g.setColor(black);
        g.fillRect(10,10,1900,1060);
        g.setColor(new Color(200, 215, 235));
        g.setFont(new Font("SansSerif", Font.BOLD, 56));
        g.drawString("Coin : " + gameModel.getCoin() + "/ Level : " + gameModel.getLevel(), 980, 90);

        if (gameModel.getTapManager().isTap1enabled()) { graphicsManager.renderInfoTap(g, gameModel.getTapManager().getTap1X(), gameModel); }
        if (gameModel.getTapManager().isTap2enabled()) { graphicsManager.renderShopTap(g, gameModel.getTapManager().getTap2X(), gameModel,gameModel); }
        if (gameModel.getTapManager().isTap3enabled()) { graphicsManager.renderSkillPointTap(g,gameModel, gameModel.getTapManager().getTap3X()); }
        if (gameModel.getTapManager().isTap4enabled()) { graphicsManager.renderQuestsTap(g, gameModel.getTapManager().getTap4X(), gameModel); }
        if (gameModel.getTapManager().isTap5enabled()) { graphicsManager.renderSettingTap(g, gameModel.getTapManager().getTap5X()); }

        if (gameModel.getTap() == 1) {
            graphicsManager.renderInfoTap(g,gameModel.getTapManager().getTap1X(), gameModel);
        } else if (gameModel.getTap() == 2) {
            graphicsManager.renderShopTap(g,gameModel.getTapManager().getTap2X(), gameModel,gameModel);
        } else if (gameModel.getTap() == 3) {
            graphicsManager.renderSkillPointTap(g,gameModel , gameModel.getTapManager().getTap3X());
        } else if (gameModel.getTap() == 4) {
            graphicsManager.renderQuestsTap(g,gameModel.getTapManager().getTap4X(), gameModel);
        } else if (gameModel.getTap() == 5) {
            graphicsManager.renderSettingTap(g, gameModel.getTapManager().getTap5X());
        }

        graphicsManager.renderTapFrame(g);
        graphicsManager.renderBackGround(g);
        graphicsManager.renderTapBar(g, gameModel.getTap(), gameModel.getTapBarPosition());
        gameModel.getIceManager().renderIces(g);
        graphicsManager.renderBaseFrame(g);
        gameModel.getEffectManager().renderEffects(g);
    }

    public static void main(String[] args) {
        new Main(1,2);
    }

}
