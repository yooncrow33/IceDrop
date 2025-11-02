import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Launcher {
    JFrame frame = new JFrame("IceDrop Lite Edition Launcher");
    JTextField main = new JTextField("Enter your password here");
    JButton Start = new JButton("Start >>");
    JButton fux = new JButton("Create password");
    JLabel lable = new JLabel("IceDrop Launcher");
    JLabel lable2 = new JLabel("alpha is now!");
    JButton recentNews = new JButton("Recent News");
    JButton patchNotes = new JButton("Patch Notes");
    JButton manual = new JButton("Manual");
    JTextArea Area = new JTextArea();
    JScrollPane p = new JScrollPane(Area);
    JTextArea TitleArea = new JTextArea("Manual");
    //JLabel lable2 = new JLabel("Since it is in the development stage, only the latest version will be executed.");
    String password;
    String login;
    boolean setPass;
    String SAVE_FILE = new File(System.getProperty("user.home"), "ice_drop_password.txt").getAbsolutePath();
    final File file = new File(SAVE_FILE);



    String recent_news = "\n" +
            "이것을 쓴 시점은 런처만 만든 시점임.\n" +
            "       2025/10/22 -yooncrow33-\n" +
            "\n" +
            "alpha 1.3!\n" +
            "   - 내가 똥같이 싼 코드 치우는중...\n" +
            "       2025/10/28 -yooncrow33-\n"  +
            "alpha 1.8!\n" +
            "   - 정신 나갈거 같음.\n" +
            "       2025/11/02 -yooncrow33-\n" ;

    String patch_notes = "\n" +
            "alpha 1.0\n" +
            "   [버그 수정]\n" +
            "   - 없음.\n" +
            "   [사용자 편의]\n" +
            "   - 없음.\n" +
            "   [게임내용]\n" +
            "   - 없음.\n" +
            "   [최적화]\n" +
            "   - 없음.\n" +
            "   [신기능]\n" +
            "   - 창의 비율조절 고정\n"+
            "\n" +
            "alpha 1.1\n" +
            "   [버그 수정]\n" +
            "   - 없음.\n" +
            "   [사용자 편의]\n" +
            "   - 없음.\n" +
            "   [게임내용]\n" +
            "   - 없음.\n" +
            "   [최적화]\n" +
            "   - Gm클래스의 Main클래스 의존문제 해결..\n" +
            "   - static 변수 대거 삭제, 대량 캡슐화.\n" +
            "   - if else if 의 더러운 구조 정리...\n" +
            "   [신기능]\n" +
            "   - 프로필 기능.\n" +
            "\n" +
            "alpha 1.2\n" +
            "   [버그 수정]\n" +
            "   - 없음.\n" +
            "   [사용자 편의]\n" +
            "   - 없음.\n" +
            "   [게임내용]\n" +
            "   - 없음.\n" +
            "   [최적화]\n" +
            "   - 인터페이스를 만들어 GM(그래픽 매니저)와 Main클래스의 결합도를 낮춤..\n" +
            "   [신기능]\n" +
            "   - 없음.\n" +
            "\n" +
            "alpha 1.3\n" +
            "   [버그 수정]\n" +
            "   - 마우스의 좌표가 프레임을 포함하여 계산하던 문제 해결..\n" +
            "   [사용자 편의]\n" +
            "   - 없음.\n" +
            "   [게임내용]\n" +
            "   - 없음.\n" +
            "   [최적화]\n" +
            "   - 매 프레임마다 스케일을 계산하던 방식에서 창을 조절하거나 M키를 누르면 계산하도록 최적화...\n" +
            "   [신기능]\n" +
            "   - 없음.\n" +
            "\n" +
            "alpha 1.4\n" +
            "   [버그 수정]\n" +
            "   - 없음.\n" +
            "   [사용자 편의]\n" +
            "   - 없음.\n" +
            "   [게임내용]\n" +
            "   - 없음.\n" +
            "   [최적화]\n" +
            "   - Main의 God-class 설계를 해결하기 위해 실험적으로 창크기 조절(ViewMetrics)클래스 분리및 메인 최적화 -> SRP(단일 책임 의무)\n" +
            "   [신기능]\n" +
            "   - 없음.\n" +
            "\n" +
            "alpha 1.5\n" +
            "   [버그 수정]\n" +
            "   - 없음.\n" +
            "   [사용자 편의]\n" +
            "   - 없음.\n" +
            "   [게임내용]\n" +
            "   - 없음.\n" +
            "   [최적화]\n" +
            "   - 없음.\n" +
            "   [신기능]\n" +
            "   - 게임 실행시 스플래쉬 화면추가...\n" +
            "\n" +
            "alpha 1.6\n" +
            "   [버그 수정]\n" +
            "   - 없음.\n" +
            "   [사용자 편의]\n" +
            "   - 없음.\n" +
            "   [게임내용]\n" +
            "   - 없음.\n" +
            "   [최적화]\n" +
            "   - 없음.\n" +
            "   [신기능]\n" +
            "   - Main의 God-class 설계를 해결하기 위해 디버그 및 성능정보(SystemMonitor)클래스 분리및 메인 최적화 -> SRP(단일 책임 의무)\n" +
            "\n" +
            "alpha 1.7\n" +
            "   [버그 수정]\n" +
            "   - 프로필 드롭다운 메뉴의 잘못된 크기 수정.\n" +
            "   [사용자 편의]\n" +
            "   - 없음.\n" +
            "   [게임내용]\n" +
            "   - 없음.\n" +
            "   [최적화]\n" +
            "   - 없음.\n" +
            "   [신기능]\n" +
            "   - Main의 God-class 설계를 해결하기 위해 게임내용(GameModel)클래스 분리및 메인 최적화 -> SRP(단일 책임 의무)\n" +
            "alpha 1.8\n" +
            "   [버그 수정]\n" +
            "   - 없음.\n" +
            "   [사용자 편의]\n" +
            "   - 없음.\n" +
            "   [게임내용]\n" +
            "   - 없음.\n" +
            "   [최적화]\n" +
            "   - 없음.\n" +
            "   [신기능]\n" +
            "   - Main에서 viewMetrics를 초기화할때 아예 this로 자신을 넘겨 결합도를 높이던 문제를 인터페이스(ISize)를 넘기는 것으로 개선\n" +
            "   - Main에 있던 거대한 키어댑터를 자체 클래스(InputHandler)로 분리\n" +
            "   - Main에서 부담 하던 저장/불러오기 기능을 GameModel클래스로 이전\n" +
            "   - 키어댑터에서 다 계산하던 tap값을 tapMoveRight(),tapMoveLeft() 생성하고 매서드를 GameModel클래스로 이동\n" +
            "   - debug창을 그릴떄 GM에서 Main의 static int를 참조하여 결합도를 높이는 문제를 viewMetrics로 이전해 결합도 해결\n";

    String manual_text = "\n" +
            "IceDrop Launcher 사용법\n" +
            "\n" +
            "1. 패스워드 생성\n" +
            "   - 처음 실행 시,원하는 패스워드를 입력하고 'Create password' 버튼을 클릭하여 패스워드를 생성합니다.\n" +
            "   - 패스워드는 이후 게임 실행 시 필요합니다.\n" +
            "\n" +
            "2. 게임 실행\n" +
            "   - 생성한 패스워드를 입력하고 'Start >>' 버튼을 클릭하여 게임을 실행합니다.\n" +
            "   - 올바른 패스워드를 입력해야 게임이 실행됩니다.\n" +
            "\n" +
            "3. 프로필 선택\n" +
            "   - 드롭다운 메뉴에서 원하는 게임 프로필을 선택할 수 있습니다.\n" +
            "\n" +
            "4. 최근 소식 및 패치 노트\n" +
            "   - 'Recent News' 버튼을 클릭하여 최신 소식을 확인할 수 있습니다.\n" +
            "   - 'Patch Notes' 버튼을 클릭하여 최신 패치 내용을 확인할 수 있습니다.\n";
    /*

    패치노트의 기본 양식

                "alpha 0.0\n" +
            "   [버그 수정]\n" +
            "\n" +
            "   [사용자 편의]\n" +
            "\n" +
            "   [게임내용]\n" +
            "\n" +
            "   [최적화]\n" +
            "\n" +
            "   [신기능]\n" +
            "\n" +
     */

    public Launcher() {
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                password = reader.readLine();
                reader.close();
                setPass = true;
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(null, "Password error!");
                exception.printStackTrace();
            }
        }

        Area.setText(manual_text);


        String[] profile = {"profile1", "profile2", "profile3"};
        JComboBox<String> versionBox = new JComboBox<>(profile);
        frame.add(versionBox);
        versionBox.setBounds(10, 430, 390, 40);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(main);
        frame.add(Start);
        frame.add(fux);
        frame.add(lable);
        frame.add(lable2);
        frame.add(p);
        frame.add(recentNews);
        frame.add(patchNotes);
        frame.add(manual);
        frame.add(TitleArea)
;
        main.setBounds(10, 320,390,70);
        main.setHorizontalAlignment(SwingConstants.CENTER);
        main.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        Start.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
        Start.setBounds(10, 470, 390, 70);
        fux.setBounds(10, 400, 390, 20);
        lable.setBounds(0, 40, 410,90);
        lable.setFont(new Font("맑은 고딕", Font.BOLD, 36));
        lable.setHorizontalAlignment(SwingConstants.CENTER);
        lable2.setBounds(0, 110, 410,70);
        lable2.setFont(new Font("맑은 고딕", Font.PLAIN, 26));//원래 36
        lable2.setHorizontalAlignment(SwingConstants.CENTER);
        TitleArea.setBounds(410,10,380,60);
        TitleArea.setFont(new Font("맑은 고딕", Font.BOLD, 48));
        TitleArea.setEnabled(false);
        p.setBounds(410,70,380,470);
        Area.setEditable(false);
        Area.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        Area.setLineWrap(true);
        recentNews.setBounds(410,540,126,10);
        patchNotes.setBounds(536,540,127,10);
        manual.setBounds(663,540,126,10);

        fux.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!setPass) {
                    password = main.getText().trim();
                    if (password.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please enter the password you want to create!");
                    } else {
                        // 패스워드 생성 및 파일 저장
                        try {
                            setPass = true; // 상태 업데이트
                            fux.setEnabled(false); // 버튼 비활성화 (핵심 개선)
                            fux.setText("password has already been created."); // 문구 변경 (선택 사항)

                            PrintWriter writer = new PrintWriter(SAVE_FILE);
                            writer.println(password);
                            writer.close();

                            JOptionPane.showMessageDialog(null, "Your password has been created and the button is now disabled.");

                        } catch (FileNotFoundException ex) {
                            JOptionPane.showMessageDialog(null, "Error saving password file!");
                            throw new RuntimeException(ex);
                        }
                    }
                } else {
                    // 이 블록은 setPass=true일 때 실행되는데, 버튼이 비활성화되므로 실행될 일이 거의 없습니다.
                    // 하지만 혹시 모를 경우를 대비해 메시지는 유지합니다.
                    JOptionPane.showMessageDialog(null, "The password has already been created.");
                }
            }
        });

        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedVersion = (String) versionBox.getSelectedItem();
                if (!setPass) {
                    return;
                }
                login = main.getText().trim();
                if (login.equals(password)) {

                    /*JOptionPane.showMessageDialog(null, "Run the Speed Click sc.25.8.4.mon");
                    frame.dispose();
                    SplashScreen.showSplashThenLaunchGame();*/
                    //new IceDash();

                    // 1. 콤보 박스에서 선택된 String 값을 가져옵니다.
                    String selectedProfileName = (String) versionBox.getSelectedItem();

                    // 2. String을 int 값으로 변환하는 함수 또는 로직을 사용합니다.
                    int profileId = getProfileIdFromName(selectedProfileName);
                    //JOptionPane.showMessageDialog(null, "Run the IceDrop Lite Edition profile : " + selectedVersion  /*"을(를) 실행합니다."*/);
                    // 3. Main 클래스 생성자에 캡슐화된 int 값을 전달합니다.
                    frame.dispose();
                    SplashScreen.showSplashThenLaunchGame(profileId);



                } else {
                    JOptionPane.showMessageDialog(null, "The password is wrong.");
                }
            }
        });

        recentNews.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Area.setText(recent_news);
                TitleArea.setText("Recent News");
            }
        });

        patchNotes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Area.setText(patch_notes);
                TitleArea.setText("Patch Notes");
            }
        });

        manual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Area.setText(manual_text);
                TitleArea.setText("Manual");
            }
        });

        if (setPass) {
            fux.setEnabled(false);
            fux.setText("password has already been created."); // 문구 변경 (선택 사항)
        }

    }

    private int getProfileIdFromName(String name) {
        // 콤보박스 이름이 "profile1"이면 1, "profile2"이면 2를 반환
        return switch (name) { // Java 12+ switch expression 사용 시 간결
            case "profile1" -> 1;
            case "profile2" -> 2;
            case "profile3" -> 3;
            default -> -1; // 잘못된 값 처리
        };
    }

    public static void main(String[] args) {
        new Launcher();
    }
}




