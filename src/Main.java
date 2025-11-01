import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends JPanel{

    JFrame frame = new JFrame("alpha 1.6");

    private ScheduledExecutorService executor;
    private long lastTime;

    int mouseX, mouseY;
    static int  virtualMouseX,virtualMouseY;

    private boolean isResizing = false;
    private int currentProfileId;

    static final int VIRTUAL_WIDTH = 1920;
    static final int VIRTUAL_HEIGHT = 1080;

    private long totalMemory;
    private long freeMemory;
    private long usedMemory;
    private double jvmCpuLoad;
    private int cpuPercentage;

    private final ViewMetrics viewMetrics;
    private final SystemMonitor systemMonitor;

    Font titleFont = new Font("SansSerif", Font.BOLD, 64);

    int test = 0;

    int tap = 1;
    boolean shiftPressed = false;

    int tapBarXPosition[] = {0,965,1154,1343,1532,1721,1721};

    GM gm = new GM();

    Main(int profileId) {

        this.currentProfileId = profileId;
        load();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        frame.setPreferredSize((new Dimension(1280,720)));
        setFocusable(true);

        frame.add(this);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocus();
        frame.pack();

        viewMetrics = new ViewMetrics(this);
        systemMonitor = new SystemMonitor();

        setBackground(Color.BLACK);
        this.viewMetrics.calculateViewMetrics();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (shiftPressed) {
                        if (tap != 6) {
                            tap = 6;
                            return;
                        }
                    }
                    if (tap >= 5) {
                        tap = 1;
                    } else {
                        tap++;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (tap <= 1) {
                        tap = 5;
                    } else {
                        tap--;
                    }
                }
                if (key == KeyEvent.VK_SHIFT) {
                    shiftPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    save();
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
                    shiftPressed = false;
                }
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //if (e.getButton() == MouseEvent.BUTTON1);
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

                virtualMouseX = viewMetrics.getVirtualX(mouseX);
                virtualMouseY = viewMetrics.getVirtualY(mouseY);

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

                    // 가로 기준 새로운 높이 계산
                    int newH = (int) (currentW / targetRatio);

                    // 세로 기준 새로운 가로 계산 (어떤 것을 기준으로 할지 선택)
                    int newW = (int) (currentH * targetRatio);

                    // 현재 가로/세로 중 더 큰 편차를 기준으로 조정합니다.
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
        executor = Executors.newSingleThreadScheduledExecutor();
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
    }

    public void save() {
        Properties props = new Properties();
        props.setProperty("test", String.valueOf(test));
        String homeDir = System.getProperty("user.home");

        String fullPath1 = homeDir + File.separator + "IceDropSaveProfile1.properties";
        String fullPath2 = homeDir + File.separator + "IceDropSaveProfile2.properties";
        String fullPath3 = homeDir + File.separator + "IceDropSaveProfile3.properties";

        String paths[] = {"empty", fullPath1, fullPath2, fullPath3};

        // 4. 파일 저장 시도
        try (FileOutputStream out = new FileOutputStream(paths[currentProfileId])) {
            props.store(out, "User Save Data - ID is not included");

        } catch (IOException e) {
            // 저장 실패 시 사용자에게 알림
            JOptionPane.showMessageDialog(null, "저장 실패: " + e.getMessage() + "\n경로: " + paths[currentProfileId]);
        }

    }

    public void load() {
        Properties props = new Properties();
        String homeDir = System.getProperty("user.home");

        String fullPath1 = homeDir + File.separator + "IceDropSaveProfile1.properties";
        String fullPath2 = homeDir + File.separator + "IceDropSaveProfile2.properties";
        String fullPath3 = homeDir + File.separator + "IceDropSaveProfile3.properties";

        String paths[] = {"empty", fullPath1, fullPath2, fullPath3};


        try (FileInputStream in = new FileInputStream(paths[currentProfileId])) {
            props.load(in);
            test = Integer.parseInt(props.getProperty("test", "1"));
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "저장파일 인식 실패");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D d2 = (Graphics2D) g;

        // 스케일 + 이동 적용
        d2.translate(viewMetrics.getCurrentXOffset(), viewMetrics.getCurrentYOffset());
        d2.scale(viewMetrics.getCurrentScale(), viewMetrics.getCurrentScale());

        g.setColor(Color.white);
        g.setFont(titleFont);
        g.drawString("Coin : 60 / Level : 70 ", 980, 90);
        g.setColor(Color.red);
        g.fillRect(965,140,945,800);

        gm.renderTapFrame(g);

        if (tap == 1) {
            gm.renderInfoTap(g);
        } else if (tap == 2) {
            gm.renderShopTap(g);
        } else if (tap == 3) {
            gm.renderSkillPointTap(g);
        } else if (tap == 4) {
            gm.renderQuestsTap(g);
        } else if (tap == 5) {
            gm.renderSettingTap(g);
        } else if (tap == 6) {
            gm.renderDebugTap(g, viewMetrics, systemMonitor);
        }
        gm.renderTapBar(g, tap, tapBarXPosition[tap]);
        gm.renderBaseFrame(d2);
    }

    public static void main(String[] args) {
        new Main(1);
    }

}
