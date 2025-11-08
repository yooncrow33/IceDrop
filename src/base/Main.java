package base;

import view.IFrameSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends JPanel implements IFrameSize {
    JFrame frame = new JFrame("alpha 1.9.1");

    private long lastTime;

    private boolean isResizing = false;

    public final int currentProfileId;

    private final ViewMetrics viewMetrics;
    private final SystemMonitor systemMonitor;
    private final GameModel gameModel;

    Font titleFont = new Font("SansSerif", Font.BOLD, 56);

    GraphicsManager graphicsManager = new GraphicsManager();

    public Main(int profileId) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        frame.setPreferredSize((new Dimension(1280,720)));
        setFocusable(true);

        viewMetrics = new ViewMetrics(this);
        systemMonitor = new SystemMonitor();
        currentProfileId = profileId;
        gameModel = new GameModel(profileId, viewMetrics);

        frame.add(this);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocus();
        frame.pack();
        InputHandler inputHandler = new InputHandler(viewMetrics, gameModel);

        gameModel.load(currentProfileId);

        setBackground(Color.BLACK);

        viewMetrics.calculateViewMetrics();

        frame.addKeyListener(inputHandler);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gameModel.clicked(true);
            }
        });

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
    }

    private void startGameLoop() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        lastTime = System.nanoTime();

        executor.scheduleAtFixedRate(() -> {
            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1_000_000_000.0; // 초 단위
            lastTime = now;

            update(deltaTime);

            SwingUtilities.invokeLater(this::repaint);

        }, 0, 16, TimeUnit.MILLISECONDS);
    }

    private void update(double deltaTime) {
        systemMonitor.updateMetrics();
        gameModel.update();
    }

    @Override
    public int getComponentWidth() {
        return this.getWidth();
    }

    @Override
    public int getComponentHeight() {
        return this.getHeight();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D d2 = (Graphics2D) g;

        d2.translate(viewMetrics.getCurrentXOffset(), viewMetrics.getCurrentYOffset());
        d2.scale(viewMetrics.getCurrentScale(), viewMetrics.getCurrentScale());

        g.setColor(Color.white);
        g.setFont(titleFont);
        g.drawString("Coin : " + gameModel.getCoin() + "/ Level : null ", 980, 90);

        graphicsManager.renderTapFrame(g);

        if (gameModel.getTap() == 1) {
            graphicsManager.renderInfoTap(g);
        } else if (gameModel.getTap() == 2) {
            graphicsManager.renderShopTap(g);
        } else if (gameModel.getTap() == 3) {
            graphicsManager.renderSkillPointTap(g);
        } else if (gameModel.getTap() == 4) {
            graphicsManager.renderQuestsTap(g);
        } else if (gameModel.getTap() == 5) {
            graphicsManager.renderSettingTap(g);
        } else if (gameModel.getTap() == 6) {
            graphicsManager.renderDebugTap(g, viewMetrics, systemMonitor, gameModel, viewMetrics);
        }
        graphicsManager.renderTapBar(g, gameModel.getTap(), gameModel.getTapBarPosition());

        gameModel.renderIces(g);

        graphicsManager.renderBaseFrame(d2);
    }

    public static void main(String[] args) {
        new Main(1);
    }

}
