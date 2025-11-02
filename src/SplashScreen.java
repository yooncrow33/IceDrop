import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SplashScreen {
    // static 변수는 이 로직에서 크게 중요하지 않아 그대로 두었습니다.
    static int startTime = 4;
    static int cool = 2; // cool 변수는 setOpacity 방식에서는 불필요하지만 일단 유지

    public static void showSplashThenLaunchGame(int profileId) {
        JWindow splash = new JWindow();

        // 1. JLabel 설정
        JLabel logo = new JLabel("IceDrop Lite Edition", SwingConstants.CENTER);
        logo.setFont(new Font("맑은 고딕", Font.BOLD, 72));
        logo.setForeground(Color.WHITE);

        JLabel powered = new JLabel("Developed by yooncrow33", SwingConstants.CENTER);
        powered.setFont(new Font("맑은 고딕", Font.ITALIC, 36));
        powered.setForeground(Color.WHITE);

        // 2. 레이아웃 설정 (BoxLayout 사용)
        splash.getContentPane().setBackground(Color.BLACK);
        splash.getContentPane().setLayout(new BoxLayout(splash.getContentPane(), BoxLayout.Y_AXIS));

        // 각 라벨이 가운데 정렬되도록
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        powered.setAlignmentX(Component.CENTER_ALIGNMENT);

        // BoxLayout으로 컴포넌트 추가
        splash.getContentPane().add(Box.createVerticalGlue()); // 위쪽 여백
        splash.getContentPane().add(logo);
        splash.getContentPane().add(Box.createRigidArea(new Dimension(0, 20))); // 라벨 사이 간격
        splash.getContentPane().add(powered);
        splash.getContentPane().add(Box.createVerticalGlue()); // 아래쪽 여백

        // 3. 창 설정 및 표시
        splash.setBounds(300, 200, 800, 400);
        splash.setLocationRelativeTo(null);
        splash.setVisible(true); // JWindow 표시

        // 4. 페이드 아웃 타이머 (setOpacity 사용)
        Timer fadeTimer = new Timer(50, null);
        fadeTimer.addActionListener(new ActionListener() {
            float alpha = 1.0f; // 초기 투명도 (불투명)

            @Override
            public void actionPerformed(ActionEvent e) {
                alpha -= 0.008f; // 점차 투명하게

                if (alpha <= 0.0f) {
                    alpha = 0.0f;
                    fadeTimer.stop();
                    splash.dispose(); // 완전히 투명해지면 창 닫기
                    new Main(profileId);

                } else {
                    // JWindow 자체의 투명도를 조절
                    splash.setOpacity(alpha);
                }
            }
        });
        fadeTimer.start();
    }

    // 예시 실행을 위한 main과 Main 클래스 더미
    public static void main(String[] args) {
        showSplashThenLaunchGame(1);
    }
}