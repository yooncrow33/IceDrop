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

import com.sun.jdi.connect.Connector;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

public class Main extends JPanel implements TapData {
    Runtime run = Runtime.getRuntime();
    OperatingSystemMXBean mxbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    JFrame frame = new JFrame("alpha 1.0");

    private ScheduledExecutorService executor;
    private long lastTime;

    int mouseX, mouseY;
    private int  virtualMouseX,virtualMouseY;

    private boolean isResizing = false;
    private int currentProfileId;

    static final int VIRTUAL_WIDTH = 1920;
    static final int VIRTUAL_HEIGHT = 1080;
    private int windowWidth;
    private int windowHeight;
    private double currentScale;
    private int currentXOffset;
    private int currentYOffset;
    private double scaleX;
    private double scaleY;
    private long totalMemory;
    private long freeMemory;
    private long usedMemory;
    private double jvmCpuLoad;
    private int cpuPercentage;

    Font titleFont = new Font("SansSerif", Font.BOLD, 64);

    int test = 0;

    int tap = 1;
    boolean shiftPressed = false;

    //String alphabet[] = {"empty","A","B","C","D","E","F","G","H","I","K","K","L","M","N","O","P","Q","R","S","T","U","V","W",",X","Y","Z"};
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

        setBackground(Color.BLACK);

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

        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

                virtualMouseX = getVirtualX(mouseX);
                virtualMouseY = getVirtualY(mouseY);

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
        /*
        if (moveUp) playerY -= 300 * deltaTime;
        if (moveDown) playerY += 300 * deltaTime;
        if (moveLeft) playerX -= 300 * deltaTime;
        if (moveRight) playerX += 300 * deltaTime;
        checkBounds();

         */
        totalMemory = run.totalMemory();
        freeMemory = run.freeMemory();
        usedMemory = totalMemory - freeMemory;
        totalMemory = totalMemory/1048576;
        freeMemory = freeMemory/1048576;
        usedMemory = usedMemory/1048576;
        jvmCpuLoad = mxbean.getCpuLoad();
        cpuPercentage = (int) (jvmCpuLoad * 100);
    }

    public int getVirtualX(int mouseX) {
        if (currentScale == 0) return mouseX;
        return (int) ((mouseX - currentXOffset) / currentScale);
    }

    public int getVirtualY(int mouseY) {
        if (currentScale == 0) return mouseY; // 0으로 나누는 것 방지
        return (int) ((mouseY - currentYOffset) / currentScale);
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
    public int getVirtualMouseX() {
        return virtualMouseX;
    }

    @Override
    public int getVirtualMouseY() {
        return virtualMouseY;
    }

    @Override
    public int getWindowWidth() {
        return windowWidth;
    }

    @Override
    public int getWindowHeight() {
        return windowHeight;
    }

    @Override
    public double getCurrentScale() {
        return currentScale;
    }

    @Override
    public int getCurrentXOffset() {
        return currentXOffset;
    }

    @Override
    public int getCurrentYOffset() {
        return currentYOffset;
    }

    @Override
    public double getScaleX() {
        return scaleX;
    }

    @Override
    public double getScaleY() {
        return scaleY;
    }

    @Override
    public long getTotalMemory() {
        return totalMemory;
    }

    @Override
    public long getFreeMemory() {
        return freeMemory;
    }

    @Override
    public long getUsedMemory() {
        return usedMemory;
    }

    @Override
    public double jvmCpuLoad() {
        return jvmCpuLoad;
    }

    @Override
    public long getCpuPercentage() {
        return cpuPercentage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D d2 = (Graphics2D) g;

        windowWidth = getWidth();
        windowHeight = getHeight();

        // 기준 해상도와 실제 창 크기 비율
        scaleX = windowWidth / (double) VIRTUAL_WIDTH;
        scaleY = windowHeight / (double) VIRTUAL_HEIGHT;

        // 두 비율 중 작은 걸 선택 (aspect ratio 유지)
        double scale = Math.min(scaleX, scaleY);

        // 중앙 정렬을 위해 여백 계산
        int xOffset = (int) ((windowWidth - VIRTUAL_WIDTH * scale) / 2);
        int yOffset = (int) ((windowHeight - VIRTUAL_HEIGHT * scale) / 2);

        // 스케일 + 이동 적용
        d2.translate(xOffset, yOffset);
        d2.scale(scale, scale);

        int virtualMouseX = (int) ((mouseX - xOffset) / scale);
        int virtualMouseY = (int) ((mouseY - yOffset) / scale);

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
            gm.renderDebugTap(g, this);
        }
        gm.renderTapBar(g, tap, tapBarXPosition[tap]);
        gm.renderBaseFrame(d2);

        currentScale = scale; // Math.min(scaleX, scaleY) 값
        currentXOffset = xOffset;
        currentYOffset = yOffset;
    }
}
