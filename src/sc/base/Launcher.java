package sc.base;

import sc.base.splashScreen.StartSplashScreen;
import sc.lang.Lang;
import sc.lang.LangKey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Launcher {
    JFrame frame = new JFrame("IceDrop");
    JTextField main = new JTextField("Enter your password here");
    JButton Start = new JButton("Start >>");
    JButton fux = new JButton("Create password");
    JLabel lable = new JLabel("IceDrop Launcher");
    JLabel lable2 = new JLabel("I hate programming.");
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
    String SAVE_FILE = new File(System.getProperty("user.home") + "/SC", "ice_drop_password.txt").getAbsolutePath();
    final File file = new File(SAVE_FILE);

    String recent_news = "\n" +
            "이것을 쓴 시점은 런처만 만든 시점임.\n" +
            "       2025/10/22 -yooncrow33-\n" +
            "\n" +
            "alpha 1.3!\n" +
            "   - 내가 똥같이 싼 코드 치우는중...\n" +
            "       2025/10/28 -yooncrow33-\n" +
            "\n" +
            "alpha 1.8!\n" +
            "   - 정신 나갈거 같음.\n" +
            "       2025/11/02 -yooncrow33-\n" +
            "\n" +
            "alpha 1.10!\n" +
            "   - 정신 나갈거 같음...\n" +
            "       2025/11/10 -yooncrow33-\n" +
            "\n" +
            "alpha 1.13!\n" +
            "   - 정신 나갈거 같음...\n" +
            "       2026/1/9 -yooncrow33-\n" +
            "\n" +
            "alpha 1.13.3!\n" +
            "   - 끝이 보인다...!\n" +
            "       2026/1/19 -yooncrow33-\n" +
            "\n" +
            "alpha 1.13.5!\n" +
            "   - 뭐였지?\n" +
            "       2026/1/21 -yooncrow33-\n" +
            "\n";


    String patch_notes = "\n" +
            "alpha 1.0 : Foundation Setup\n" +
            "   [게임내용]\n" +
            "   - 창의 비율조절 고정\n" +
            "\n" +
            "alpha 1.1 : First Refactor & Profile System\n" +
            "   [최적화]\n" +
            "   - Gm클래스의 Main클래스 의존문제 해결..\n" +
            "   - static 변수 대거 삭제, 대량 캡슐화.\n" +
            "   - if else if 의 더러운 구조 정리...\n" +
            "   [게임내용]\n" +
            "   - 프로필 기능.\n" +
            "\n" +
            "alpha 1.2 : Decoupling Graphics Layer\n" +
            "   [최적화]\n" +
            "   - 인터페이스를 만들어 GM(그래픽 매니저)와 Main클래스의 결합도를 낮춤..\n" +
            "\n" +
            "alpha 1.3 : Input Accuracy & Render Optimization\n" +
            "   [버그 수정]\n" +
            "   - 마우스의 좌표가 프레임을 포함하여 계산하던 문제 해결..\n" +
            "   [최적화]\n" +
            "   - 매 프레임마다 스케일을 계산하던 방식에서 창을 조절하거나 M키를 누르면 계산하도록 최적화...\n" +
            "\n" +
            "alpha 1.4 : Breaking the God-Class (Phase 1)\n" +
            "   [최적화]\n" +
            "   - Main의 God-class 설계를 해결하기 위해 실험적으로 창크기 조절(a02.sc.base.ViewMetrics)클래스 분리및 메인 최적화 -> SRP(단일 책임 의무)\n" +
            "\n" +
            "alpha 1.5 : Visual Entry Point\n" +
            "   [게임내용]\n" +
            "   - 게임 실행시 스플래쉬 화면추가...\n" +
            "\n" +
            "alpha 1.6 : Breaking the God-Class (Phase 2)\n" +
            "   [최적화]\n" +
            "   - Main의 God-class 설계를 해결하기 위해 디버그 및 성능정보(a02.sc.base.SystemMonitor)클래스 분리및 메인 최적화 -> SRP(단일 책임 의무)\n" +
            "\n" +
            "alpha 1.7 : Game Model Separation\n" +
            "   [버그 수정]\n" +
            "   - 프로필 드롭다운 메뉴의 잘못된 크기 수정.\n" +
            "   [최적화]\n" +
            "   - Main의 God-class 설계를 해결하기 위해 게임내용(a02.sc.base.gameModel.GameModel)클래스 분리및 메인 최적화 -> SRP(단일 책임 의무)\n" +
            "\n" +
            "alpha 1.8 : Input & State Responsibility Cleanup\n" +
            "   [최적화]\n" +
            "   - Main에서 viewMetrics를 초기화할때 아예 this로 자신을 넘겨 결합도를 높이던 문제를 인터페이스(a01_model.ISize)를 넘기는 것으로 개선\n" +
            "   - Main에 있던 거대한 키어댑터를 자체 클래스(a02.sc.base.handler.InputHandler)로 분리\n" +
            "   - Main에서 부담 하던 저장/불러오기 기능을 GameModel클래스로 이전\n" +
            "   - 키어댑터에서 다 계산하던 tap값을 tapMoveRight(),tapMoveLeft() 생성하고 매서드를 GameModel클래스로 이동\n" +
            "   - debug창을 그릴떄 GM에서 Main의 static int를 참조하여 결합도를 높이는 문제를 viewMetrics로 이전해 결합도 해결\n" +
            "\n" +
            "alpha 1.9 : Core Gameplay Emergence\n" +
            "   [게임내용]\n" +
            "   - 드디어! Ice가 드롭됨..\n" +
            "\n" +
            "alpha 1.9.1 : Interactive Collection\n" +
            "   [게임내용]\n" +
            "   - Ice를 클릭으로 수집가능.\n" +
            "   - 게임의 클래스를 패키지로 묶음.\n" +
            "\n" +
            "alpha 1.10 : Quest & Session Expansion\n" +
            "   [사용자 편의]\n" +
            "   - TapBar 아래의 탭이름 추가..\n" +
            "   [게임내용]\n" +
            "   - ESC로 게임 종료시 슬플래쉬 화면추가..\n" +
            "   - 퀘스트 추가! \n" +
            "   - Ice를 수집했을때의 코인이 획득되는 효과 추가..\n" +
            "   - 플레이타임 추가! -> 세션 시간과 전체 실행시간을 둘다 properties파일에 저장.\n" +
            "   [최적화]\n" +
            "   - 굳이 없어도되는(사용위치 없음) 스플래쉬스크린 클래스의 static변수 삭제..\n" +
            "   - 쓸때 없는 systemMonitor의 생성자 삭제..\n" +
            "   - 메인의 쓸때 없는 SystemMonitor클래스의 잔재 삭제..\n" +
            "\n" +
            "alpha 1.10.1 : Frame-Independent Update\n" +
            "   [게임내용]\n" +
            "   - Ice의 잔상 추가! \n" +
            "   [최적화]\n" +
            "   - ice.update();에서 dt를 사용해 fps에 상관없이 게임이 업데이트되게 최적화..\n" +
            "\n" +
            "alpha 1.11 : Economy & Shop Introduction\n" +
            "   [게임내용]\n" +
            "   - shop 시스템 추가!\n" +
            "   - 아이템 추가!\n" +
            "   [최적화]\n" +
            "   - 더 나은 아이스 드롭.\n" +
            "   - Graphics Manager에서 QuestBar를 렌더링 할때 넓이를 매 프레임마다 지역변수로 선언하던 구조 개선 -> 클래스 변수\n" +
            "\n" +
            "alpha 1.12 : Feedback & Animation Systems\n" +
            "   [게임내용]\n" +
            "   - IceRush 아이템의 쿨타임 조정\n" +
            "   [최적화]\n" +
            "   - GameModel클래스안에서 playTick의 자료형을 long에서 int로 변경 -> 메모리 절약\n" +
            "   - Graphics Manager에서 QuestBar를 렌더링 할때 넓이를 매 프레임마다 지역변수로 선언하던 구조 개선 -> 클래스 변수\n" +
            "   - Info 객체의 라이프사이클(Tick 기반)을 관리하여 메모리 누수 방지 및 렌더링 최적화\n" +
            "   [사용자 편의/ UX]\n" +
            "   - Info 시스템 구축: 팝업 알림창에 POPUP-STAY-REMOVE 3단계 애니메이션 적용 (가시성 향상)\n" +
            "   - Tap 인터페이스 개선: 1번-5번 탭이 끊김 없이 연결되는 Carousel(회전목마) 무한 루프 애니메이션 구현\n" +
            "\n" +
            "alpha 1.13 : Progression Core Complete\n" +
            "   [게임내용]\n" +
            "   - Shop에 AutoCollect아이템 추가.\n" +
            "   - Level추가.\n" +
            "   - 스킬포인트 추가.\n" +
            "   - XP추가.\n" +
            "   - 기본적으로 들어가있던 마우스 클릭 오프셋을 스킬포인트로 업그레이드하도록 변경.\n" +
            "\n" +
            "alpha 1.13.1 : Quest System Refactor\n" +
            "   [게임내용]\n" +
            "   - Quest를 일정 코인을 내고 리프레쉬할수 있는 기능추가!\n" +
            "   [최적화]\n" +
            "   - GamModel클래스에서 모두 관리하던 Quest를 다른 클래스로 분리 -> god object해체.\n" +
            "   - 따로 관리하던 내용이 같은firstQuest와 secondQuest은 같은 클래스인 Quest로 관리.\n" +
            "   - GraphicManager에서 그리던 thirdQuest의 정보들을 getter매서드로 접근하게 변경 -> 의존성 줄임.\n" +
            "   [사용자 편의/ UX]\n" +
            "   - QuestTap에서 이제 퀘스트를 클리어 했을때 받는 XP도 표시.\n" +
            "   [버그 수정]\n" +
            "   - thirdQuest의 설명에서 \"Gold\" 라고 표시된것을 \"coin\"으로 반경..\n" +
            "\n" +
            "alpha 1.13.2 : Internal & Clean Code\n" +
            "   [게임내용]\n" +
            "   - Quest를 리프레쉬 했을때 진행도가 초기화 되자 않았던 것을 초기화되도록 수정.\n" +
            "   [최적화]\n" +
            "   - GamModel에서 관리하던 Tap애니메이션을 다른 클래스로 분리->god class해체.\n" +
            "   - GamModel에서 관리하던 Shop,Item을 다른 클래스로 분리->god class해체.\n" +
            "   - GamModel에서 관리하던 level,xp,skill point를 다른 클래스로 분리->god class해체.\n" +
            "   - GamModel에서 관리하던 ice들을 다른 클래스로 분리->god class해체.\n" +
            "   - GamModel에서 관리하던 effect들을 다른 클래스로 분리->god class해체.\n" +
            "   - GamModel에서 관리하던 Quest들을 묶어서 QuestManager 클래스로 분리->god class해체.\n" +
            "   - 이로 인하여 GamModel의 코드가 1008줄->284줄로 극적 회복.\n" +
            "   - IGameModelDebug를 폐기및 getPlayTick만 반환하는 IGameModelTick으로 변경.\n" +
            "   [버그 수정]\n" +
            "   - Quest를 리프레쉬 했을때 진행도가 초기화 되자 않았던 것을 초기화되도록 수정.\n" +
            "\n" +
            "alpha 1.13.3 : Balance & Theme Shift\n" +
            "   [게임내용]\n" +
            "   - IceLegendary의 드롭속도 너프.\n" +
            "   - 쿨타임 게이지를 반전시킴(쿨타임 온일때)0 -> 100.\n" +
            "   [버그 수정]\n" +
            "   - AutoCollect로 수집된 Ice는 무조건 Basic이라 나왔던 버그 수정.\n" +
            "   [최적화]\n" +
            "   - Ice의 삭제 범위 증가..\n" +
            "   - 비밀번호의 저장 디렉토리를 user.home이 아닌 home아래의 전용디렉토리 SC로 저장되게 변경.\n" +
            "   [사용자 편의/ UX]\n" +
            "   - 게임의 전체적인 테마 변경\n" +
            "\n" +
            "alpha 1.13.4 : Localization & System Stability\n" +
            "   [게임내용]\n" +
            "   - 언어 시스템 추가!.\n" +
            "       - 기본지원 언어 -> 영어/한국어.\n" +
            "   - user.home/SC/lang안에 custom/properties파일을 수정하면 어떤나라의 언어이든 사용가능!\n" +
            "   - 이제 모든 파일이 user.home이 아니라 home아래의 SC디렉토리에다가 저장됨.\n" +
            "   [버그 수정]\n" +
            "   - 화면밖을 막는 레이어 확장..\n" +
            "   [최적화]\n" +
            "   - 쓰레드 안정성 상향.\n" +
            "   - 비밀번호의 저장 디렉토리를 user.home이 아닌 home아래의 전용디렉토리 SC로 저장되게 변경.\n" +
            "\n" +
            "alpha 1.13.5 : Core Balance & Visual Feedback\n" +
            "   [게임내용]\n" +
            "   - XP의 획득량 밸런스 패치.\n" +
            "   - IceVacuum의 쿨타임감소 밸런스 패치..\n" +
            "   - AutoCollect확률 대폭상승.\n" +
            "   - Click offset범위 상향.\n" +
            "   [사용자 편의/ UX]\n" +
            "   - Ice수집시, IceVacuum발동시에 이펙트추가!\n" +
            "   [버그 수정]\n" +
            "   - IceVacuum시의 각 Ice들의 잔상이 사라지지 않는 버그수정.\n" +
            "   \n";

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


        String[] profile = {"profile1", "profile2", "profile3", "⚠ CustomMode is not implemented yet."};
        JComboBox<String> versionBox = new JComboBox<>(profile);
        String[] languages = {"English", "Korean","Custom"};
        JComboBox languageBox = new JComboBox(languages);
        String[] modEnable = {"Mode is not implemented yet.","Mod Enable", "Mod Disable"};
        JComboBox<String> modBox = new JComboBox<>(modEnable);
        frame.add(versionBox);
        frame.add(languageBox);
        frame.add(modBox);
        versionBox.setBounds(10, 430, 390, 40);
        languageBox.setBounds(10, 400, 390, 40);
        modBox.setBounds(10,370,390,40);
        modBox.enable(false);

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
        frame.add(TitleArea);

        main.setBounds(10, 270,390,70);
        main.setHorizontalAlignment(SwingConstants.CENTER);
        main.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        Start.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
        Start.setBounds(10, 470, 390, 70);
        fux.setBounds(10, 350, 390, 20);
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
                            String saveDirPath = System.getProperty("user.home") + "/SC";
                            File saveDir = new File(saveDirPath);
                            if (!saveDir.exists()) {
                                saveDir.mkdirs(); // SC 폴더가 없으면 생성
                            }
                            String saveDirPath1 = System.getProperty("user.home") + "/SC/lang";
                            File saveDir1 = new File(saveDirPath1);
                            if (!saveDir1.exists()) {
                                saveDir1.mkdirs();
                                saveCustom();
                            }
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
                if (!setPass) {
                    return;
                }
                login = main.getText().trim();
                if (login.equals(password)) {

                    /*JOptionPane.showMessageDialog(null, "Run the Speed Click sc.25.8.4.mon");
                    frame.dispose();
                    sc.base.SplashScreen.showSplashThenLaunchGame();*/
                    //new IceDash();

                    // 1. 콤보 박스에서 선택된 String 값을 가져옵니다.
                    String selectedProfileName = (String) versionBox.getSelectedItem();

                    // 2. String을 int 값으로 변환하는 함수 또는 로직을 사용합니다.
                    int profileId = getProfileIdFromName(selectedProfileName);

                    String str = (String) languageBox.getSelectedItem();

                    int language = getLangIdFormMane(str);
                    saveEnglish();
                    saveKorean();
                    //JOptionPane.showMessageDialog(null, "Run the IceDrop Lite Edition profile : " + selectedVersion  /*"을(를) 실행합니다."*/);
                    // 3. a02.sc.base.Main 클래스 생성자에 캡슐화된 int 값을 전달합니다.
                    frame.dispose();
                    StartSplashScreen.showSplashThenLaunchGame(profileId,language);



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

    private int getLangIdFormMane(String mane) {
        return switch (mane) {
            case "English" -> 1;
            case "Korean" -> 2;
            case "Custom" -> 3;
            default -> 1;
        };

    }

    public void loadEnglishFile() {

    }

    public static void main(String[] args) {
        new Launcher();
    }
    // ===== 영어 원문 저장 =====
    public void saveEnglish() {
        int language = 1;
        Properties p = new Properties();

        p.setProperty(LangKey.TITLE_GAME_NAME, "FROZEN FIELD");
        p.setProperty(LangKey.TITLE_SUB_TEXT, "Collect the ice. Control the flow.");
        p.setProperty(LangKey.VERSION_TEXT, "v1.13.5-alpha");

        p.setProperty(LangKey.TAP_INFO, "INFO");
        p.setProperty(LangKey.TAP_SHOP, "SHOP");
        p.setProperty(LangKey.TAP_SKILLPOINT, "SKILLPOINT");
        p.setProperty(LangKey.TAP_QUESTS, "QUESTS");
        p.setProperty(LangKey.TAP_SETTING, "SETTING");

        p.setProperty(LangKey.INFO_TITLE, "INFO Tap");
        p.setProperty(LangKey.INFO_CURRENT_PROFILE, "current profile : ");
        p.setProperty(LangKey.INFO_TOTAL_PLAY_TIME, "total played time : ");
        p.setProperty(LangKey.INFO_SESSION_PLAY_TIME, "session played time : ");

        p.setProperty(LangKey.SHOP_TITLE, "SHOP Tap");
        p.setProperty(LangKey.SHOP_COOLDOWN, "Cooldown");
        p.setProperty(LangKey.SHOP_RUSH, "Rush!");
        p.setProperty(LangKey.SHOP_VACUUM, "Vacuum!");
        p.setProperty(LangKey.SHOP_UPGRADE, "Upgrade");
        p.setProperty(LangKey.SHOP_OWNED, "Owned : ");
        p.setProperty(LangKey.SHOP_LEVEL, "Level : ");
        p.setProperty(LangKey.SHOP_COIN, " coin");

        p.setProperty(LangKey.SHOP_ICE_BASIC_RUSH, "IceBasic spawn rush ");
        p.setProperty(LangKey.SHOP_ICE_RARE_RUSH, "IceRare spawn rush ");
        p.setProperty(LangKey.SHOP_ICE_LEGENDARY_RUSH, "IceLegendary spawn rush ");
        p.setProperty(LangKey.SHOP_AUTO_COLLECT, "Auto collect");
        p.setProperty(LangKey.SHOP_VACUUM_TITLE, "Vacuum ");

        p.setProperty(LangKey.SKILLPOINT_TITLE, "SKILL POINT Tap");
        p.setProperty(LangKey.SKILLPOINT_XP_REMAIN, "Xp remaining until next level : ");
        p.setProperty(LangKey.SKILLPOINT_UPGRADE, "Upgrade");

        p.setProperty(LangKey.SKILL_ICE_BASIC_SPAWN, "IceBasic spawn chance");
        p.setProperty(LangKey.SKILL_ICE_RARE_SPAWN, "IceRare spawn chance");
        p.setProperty(LangKey.SKILL_ICE_LEGENDARY_SPAWN, "IceLegendary spawn chance");
        p.setProperty(LangKey.SKILL_CLICK_OFFSET, "Click Offset Level");
        p.setProperty(LangKey.SKILL_ITEM_COOLTIME, "Item Cool Time Decrease");

        p.setProperty(LangKey.SKILL_DESC_SPAWN, "The sponge rate of Ice goes up.");
        p.setProperty(LangKey.SKILL_DESC_CLICK_OFFSET, "The mouse's calibration value has increased.");
        p.setProperty(LangKey.SKILL_DESC_ITEM_COOLTIME, "Decreased cool time.");

        p.setProperty(LangKey.SKILL_AVAILABLE, "Available Skill Points : ");
        p.setProperty(LangKey.SKILL_USED, "Used Skill Points : ");

        p.setProperty(LangKey.QUEST_TITLE, "QUESTS Tap");
        p.setProperty(LangKey.QUEST_REFRESH, "Quest refresh : ");
        p.setProperty(LangKey.QUEST_SESSION, "Session Quest");
        p.setProperty(LangKey.QUEST_LONGTIME, "Long Time Quest");
        p.setProperty(LangKey.QUEST_REWARD, "Reward : ");

        p.setProperty(LangKey.QUEST_REWARDED, "Rewarded");
        p.setProperty(LangKey.QUEST_NOT_REWARDED, "Not Rewarded");
        p.setProperty(LangKey.QUEST_COMPLETED, "Completed");
        p.setProperty(LangKey.QUEST_NOT_COMPLETED, "Not Completed");

        p.setProperty(LangKey.SETTING_TITLE, "SETTING Tap");

        p.setProperty(LangKey.INFO_AUTO_COLLECTED, "Auto Collected!");
        p.setProperty(LangKey.INFO_COLLECTED_ICE, "Collected Ice : ");
        p.setProperty(LangKey.INFO_VACUUM_ACTIVATED, "Ice Vacuum Activated!");
        p.setProperty(LangKey.INFO_GET_COIN, "You get coin : ");
        p.setProperty(LangKey.INFO_GET_XP, "You get xp : ");

        p.setProperty(LangKey.QUEST_REFRESHED, "Quest refresh!");
        p.setProperty(LangKey.QUEST_NOT_ENOUGH_MONEY, "Not enough money!");
        p.setProperty(LangKey.QUEST_REWARDED, "Quest rewarded!");
        p.setProperty(LangKey.QUEST_THIRD_REWARD, "Ice Collect Bonus");

        p.setProperty(LangKey.QUEST_DESC_BASIC, "Collect 10 Ice Basic");
        p.setProperty(LangKey.QUEST_DESC_RARE, "Collect 5 Ice Rare");
        p.setProperty(LangKey.QUEST_DESC_LEGENDARY, "Collect 1 Ice Legendary");
        p.setProperty(LangKey.QUEST_DESC_10MIN, "Play for 10 min");
        p.setProperty(LangKey.QUEST_DESC_30MIN, "Play for 30 min");
        p.setProperty(LangKey.QUEST_DESC_1HOUR, "Play for 1 hour");
        p.setProperty(LangKey.QUEST_DESC_LONGTIME, "Permanently grants +5 coin per Ice collected.");

        p.setProperty(LangKey.SHOP_RUSH_BASIC_ON, "Ice Basic Rush Activated!");
        p.setProperty(LangKey.SHOP_RUSH_RARE_ON, "Ice Rare Rush Activated!");
        p.setProperty(LangKey.SHOP_RUSH_LEGENDARY_ON, "Ice Legendary Rush Activated!");
        p.setProperty(LangKey.SHOP_RUSH_TIME_30, "Rush time : 30 seconds");
        p.setProperty(LangKey.SHOP_RUSH_TIME_60, "Rush time : 1 min");

        p.setProperty(LangKey.SHOP_RUSH_BASIC_OFF, "Ice Basic Rush ended!");
        p.setProperty(LangKey.SHOP_RUSH_RARE_OFF, "Ice Rare Rush ended!");
        p.setProperty(LangKey.SHOP_RUSH_LEGENDARY_OFF, "Ice Legendary Rush ended!");

        p.setProperty(LangKey.SHOP_PURCHASED, "Purchased!");
        p.setProperty(LangKey.SHOP_MAX_LEVEL, "Max level!");

        p.setProperty(LangKey.SKILL_UPGRADED, "Upgraded!");
        p.setProperty(LangKey.SKILL_NOT_ENOUGH_POINT, "Not enough skill point!");
        p.setProperty(LangKey.SKILL_BASIC_UP, "Ice Basic Spawn Chance Upgraded!");
        p.setProperty(LangKey.SKILL_RARE_UP, "Ice Rare Spawn Chance Upgraded!");
        p.setProperty(LangKey.SKILL_LEGENDARY_UP, "Ice Legendary Spawn Chance Upgraded!");
        p.setProperty(LangKey.SKILL_PRESENT_COIN, "present coin : ");
        p.setProperty(LangKey.SKILL_PRESENT_XP, "present xp : ");
        p.setProperty(LangKey.SKILL_CURRENT_LEVEL, "Current Level : ");

        String homeDir = System.getProperty("user.home")+ File.separator + "SC" + File.separator + "lang";

        String fullPath1 = homeDir + File.separator + "english.properties";
        String fullPath2 = homeDir + File.separator + "korean.properties";
        String fullPath3 = homeDir + File.separator + "custom.properties";
        String paths[] = {"empty", fullPath1, fullPath2, fullPath3};

        try (FileOutputStream out = new FileOutputStream(paths[language])) {

            p.store(out, "User Save Data - ID is not included");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "저장 실패: " + e.getMessage() + "\n경로: " + paths[language]);
        }
    }
    public void saveKorean() {
        int language = 2;
        Properties p = new Properties();

        p.setProperty(LangKey.TITLE_GAME_NAME, "프로즌 필드");
        p.setProperty(LangKey.TITLE_SUB_TEXT, "얼음을 수집하라. 흐름을 지배하라.");
        p.setProperty(LangKey.VERSION_TEXT, "v1.13.5-알파");

        p.setProperty(LangKey.TAP_INFO, "정보");
        p.setProperty(LangKey.TAP_SHOP, "상점");
        p.setProperty(LangKey.TAP_SKILLPOINT, "스킬포인트");
        p.setProperty(LangKey.TAP_QUESTS, "퀘스트");
        p.setProperty(LangKey.TAP_SETTING, "설정");

        p.setProperty(LangKey.INFO_TITLE, "정보 탭");
        p.setProperty(LangKey.INFO_CURRENT_PROFILE, "현재 프로필 : ");
        p.setProperty(LangKey.INFO_TOTAL_PLAY_TIME, "총 플레이 시간 : ");
        p.setProperty(LangKey.INFO_SESSION_PLAY_TIME, "세션 플레이 시간 : ");

        p.setProperty(LangKey.SHOP_TITLE, "상점 탭");
        p.setProperty(LangKey.SHOP_COOLDOWN, "쿨타임");
        p.setProperty(LangKey.SHOP_RUSH, "러시!");
        p.setProperty(LangKey.SHOP_VACUUM, "흡수!");
        p.setProperty(LangKey.SHOP_UPGRADE, "업그레이드");
        p.setProperty(LangKey.SHOP_OWNED, "보유 : ");
        p.setProperty(LangKey.SHOP_LEVEL, "레벨 : ");
        p.setProperty(LangKey.SHOP_COIN, " 코인");

        p.setProperty(LangKey.SHOP_ICE_BASIC_RUSH, "기본 얼음 생성 러시");
        p.setProperty(LangKey.SHOP_ICE_RARE_RUSH, "희귀 얼음 생성 러시");
        p.setProperty(LangKey.SHOP_ICE_LEGENDARY_RUSH, "전설 얼음 생성 러시");
        p.setProperty(LangKey.SHOP_AUTO_COLLECT, "자동 수집");
        p.setProperty(LangKey.SHOP_VACUUM_TITLE, "흡수");

        p.setProperty(LangKey.SKILLPOINT_TITLE, "스킬 포인트 탭");
        p.setProperty(LangKey.SKILLPOINT_XP_REMAIN, "다음 레벨까지 남은 XP : ");
        p.setProperty(LangKey.SKILLPOINT_UPGRADE, "강화");

        p.setProperty(LangKey.SKILL_ICE_BASIC_SPAWN, "기본 얼음 생성 확률");
        p.setProperty(LangKey.SKILL_ICE_RARE_SPAWN, "희귀 얼음 생성 확률");
        p.setProperty(LangKey.SKILL_ICE_LEGENDARY_SPAWN, "전설 얼음 생성 확률");
        p.setProperty(LangKey.SKILL_CLICK_OFFSET, "클릭 보정 레벨");
        p.setProperty(LangKey.SKILL_ITEM_COOLTIME, "아이템 쿨타임 감소");

        p.setProperty(LangKey.SKILL_DESC_SPAWN, "얼음 생성 확률이 증가합니다.");
        p.setProperty(LangKey.SKILL_DESC_CLICK_OFFSET, "마우스 보정 수치가 증가합니다.");
        p.setProperty(LangKey.SKILL_DESC_ITEM_COOLTIME, "쿨타임이 감소합니다.");

        p.setProperty(LangKey.SKILL_AVAILABLE, "사용 가능 스킬 포인트 : ");
        p.setProperty(LangKey.SKILL_USED, "사용한 스킬 포인트 : ");

        p.setProperty(LangKey.QUEST_TITLE, "퀘스트 탭");
        p.setProperty(LangKey.QUEST_REFRESH, "퀘스트 갱신 : ");
        p.setProperty(LangKey.QUEST_SESSION, "세션 퀘스트");
        p.setProperty(LangKey.QUEST_LONGTIME, "장기 퀘스트");
        p.setProperty(LangKey.QUEST_REWARD, "보상 : ");

        p.setProperty(LangKey.QUEST_REWARDED, "보상 수령 완료");
        p.setProperty(LangKey.QUEST_NOT_REWARDED, "보상 미수령");
        p.setProperty(LangKey.QUEST_COMPLETED, "완료");
        p.setProperty(LangKey.QUEST_NOT_COMPLETED, "미완료");

        p.setProperty(LangKey.SETTING_TITLE, "설정 탭");

        p.setProperty(LangKey.INFO_AUTO_COLLECTED, "자동 수집!");
        p.setProperty(LangKey.INFO_COLLECTED_ICE, "수집한 얼음 : ");
        p.setProperty(LangKey.INFO_VACUUM_ACTIVATED, "얼음 흡수기 발동!");
        p.setProperty(LangKey.INFO_GET_COIN, "획득 코인 : ");
        p.setProperty(LangKey.INFO_GET_XP, "획득 경험치 : ");

        p.setProperty(LangKey.QUEST_REFRESHED, "퀘스트 갱신!");
        p.setProperty(LangKey.QUEST_NOT_ENOUGH_MONEY, "돈이 부족합니다!");
        p.setProperty(LangKey.QUEST_REWARDED, "퀘스트 보상 획득!");
        p.setProperty(LangKey.QUEST_THIRD_REWARD, "얼음 수집 보너스");

        p.setProperty(LangKey.QUEST_DESC_BASIC, "기본 얼음 10개 수집");
        p.setProperty(LangKey.QUEST_DESC_RARE, "희귀 얼음 5개 수집");
        p.setProperty(LangKey.QUEST_DESC_LEGENDARY, "전설 얼음 1개 수집");
        p.setProperty(LangKey.QUEST_DESC_10MIN, "10분 플레이");
        p.setProperty(LangKey.QUEST_DESC_30MIN, "30분 플레이");
        p.setProperty(LangKey.QUEST_DESC_1HOUR, "1시간 플레이");
        p.setProperty(LangKey.QUEST_DESC_LONGTIME,
                "얼음 수집 시 코인 +5 영구 증가 및 1000 코인 지급");

        p.setProperty(LangKey.SHOP_RUSH_BASIC_ON, "기본 얼음 러시 발동!");
        p.setProperty(LangKey.SHOP_RUSH_RARE_ON, "희귀 얼음 러시 발동!");
        p.setProperty(LangKey.SHOP_RUSH_LEGENDARY_ON, "전설 얼음 러시 발동!");
        p.setProperty(LangKey.SHOP_RUSH_TIME_30, "지속 시간 : 30초");
        p.setProperty(LangKey.SHOP_RUSH_TIME_60, "지속 시간 : 1분");

        p.setProperty(LangKey.SHOP_RUSH_BASIC_OFF, "기본 얼음 러시 종료!");
        p.setProperty(LangKey.SHOP_RUSH_RARE_OFF, "희귀 얼음 러시 종료!");
        p.setProperty(LangKey.SHOP_RUSH_LEGENDARY_OFF, "전설 얼음 러시 종료!");

        p.setProperty(LangKey.SHOP_PURCHASED, "구매 완료!");
        p.setProperty(LangKey.SHOP_MAX_LEVEL, "최대 레벨입니다!");

        p.setProperty(LangKey.SKILL_UPGRADED, "강화 완료!");
        p.setProperty(LangKey.SKILL_NOT_ENOUGH_POINT, "스킬 포인트가 부족합니다!");
        p.setProperty(LangKey.SKILL_BASIC_UP, "기본 얼음 생성 확률 증가!");
        p.setProperty(LangKey.SKILL_RARE_UP, "희귀 얼음 생성 확률 증가!");
        p.setProperty(LangKey.SKILL_LEGENDARY_UP, "전설 얼음 생성 확률 증가!");
        p.setProperty(LangKey.SKILL_PRESENT_COIN, "현재 코인 : ");
        p.setProperty(LangKey.SKILL_PRESENT_XP, "현재 XP : ");
        p.setProperty(LangKey.SKILL_CURRENT_LEVEL, "현재 레벨 : ");

        String homeDir = System.getProperty("user.home")+ File.separator + "SC" + File.separator + "lang";

        String fullPath1 = homeDir + File.separator + "english.properties";
        String fullPath2 = homeDir + File.separator + "korean.properties";
        String fullPath3 = homeDir + File.separator + "custom.properties";
        String paths[] = {"empty", fullPath1, fullPath2, fullPath3};

        try (FileOutputStream out = new FileOutputStream(paths[language])) {

            p.store(out, "User Save Data - ID is not included");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "저장 실패: " + e.getMessage() + "\n경로: " + paths[language]);
        }
    }
    public void saveCustom() {
        int language = 3;
        Properties p = new Properties();

        p.setProperty(LangKey.TITLE_GAME_NAME, "FROZEN FIELD");
        p.setProperty(LangKey.TITLE_SUB_TEXT, "Collect the ice. Control the flow.");
        p.setProperty(LangKey.VERSION_TEXT, "v1.13.5-alpha");

        p.setProperty(LangKey.TAP_INFO, "INFO");
        p.setProperty(LangKey.TAP_SHOP, "SHOP");
        p.setProperty(LangKey.TAP_SKILLPOINT, "SKILLPOINT");
        p.setProperty(LangKey.TAP_QUESTS, "QUESTS");
        p.setProperty(LangKey.TAP_SETTING, "SETTING");

        p.setProperty(LangKey.INFO_TITLE, "INFO Tap");
        p.setProperty(LangKey.INFO_CURRENT_PROFILE, "current profile : ");
        p.setProperty(LangKey.INFO_TOTAL_PLAY_TIME, "total played time : ");
        p.setProperty(LangKey.INFO_SESSION_PLAY_TIME, "session played time : ");

        p.setProperty(LangKey.SHOP_TITLE, "SHOP Tap");
        p.setProperty(LangKey.SHOP_COOLDOWN, "Cooldown");
        p.setProperty(LangKey.SHOP_RUSH, "Rush!");
        p.setProperty(LangKey.SHOP_VACUUM, "Vacuum!");
        p.setProperty(LangKey.SHOP_UPGRADE, "Upgrade");
        p.setProperty(LangKey.SHOP_OWNED, "Owned : ");
        p.setProperty(LangKey.SHOP_LEVEL, "Level : ");
        p.setProperty(LangKey.SHOP_COIN, " coin");

        p.setProperty(LangKey.SHOP_ICE_BASIC_RUSH, "IceBasic spawn rush");
        p.setProperty(LangKey.SHOP_ICE_RARE_RUSH, "IceRare spawn rush");
        p.setProperty(LangKey.SHOP_ICE_LEGENDARY_RUSH, "IceLegendary spawn rush");
        p.setProperty(LangKey.SHOP_AUTO_COLLECT, "Auto collect");
        p.setProperty(LangKey.SHOP_VACUUM_TITLE, "Vacuum");

        p.setProperty(LangKey.SKILLPOINT_TITLE, "SKILL POINT Tap");
        p.setProperty(LangKey.SKILLPOINT_XP_REMAIN, "Xp remaining until next level : ");
        p.setProperty(LangKey.SKILLPOINT_UPGRADE, "Upgrade");

        p.setProperty(LangKey.SKILL_ICE_BASIC_SPAWN, "IceBasic spawn chance");
        p.setProperty(LangKey.SKILL_ICE_RARE_SPAWN, "IceRare spawn chance");
        p.setProperty(LangKey.SKILL_ICE_LEGENDARY_SPAWN, "IceLegendary spawn chance");
        p.setProperty(LangKey.SKILL_CLICK_OFFSET, "Click Offset Level");
        p.setProperty(LangKey.SKILL_ITEM_COOLTIME, "Item Cool Time Decrease");

        p.setProperty(LangKey.SKILL_DESC_SPAWN, "The sponge rate of Ice goes up.");
        p.setProperty(LangKey.SKILL_DESC_CLICK_OFFSET, "The mouse's calibration value has increased.");
        p.setProperty(LangKey.SKILL_DESC_ITEM_COOLTIME, "Decreased cool time.");

        p.setProperty(LangKey.SKILL_AVAILABLE, "Available Skill Points : ");
        p.setProperty(LangKey.SKILL_USED, "Used Skill Points : ");

        p.setProperty(LangKey.QUEST_TITLE, "QUESTS Tap");
        p.setProperty(LangKey.QUEST_REFRESH, "Quest refresh : ");
        p.setProperty(LangKey.QUEST_SESSION, "Session Quest");
        p.setProperty(LangKey.QUEST_LONGTIME, "Long Time Quest");
        p.setProperty(LangKey.QUEST_REWARD, "Reward : ");

        p.setProperty(LangKey.QUEST_REWARDED, "Rewarded");
        p.setProperty(LangKey.QUEST_NOT_REWARDED, "Not Rewarded");
        p.setProperty(LangKey.QUEST_COMPLETED, "Completed");
        p.setProperty(LangKey.QUEST_NOT_COMPLETED, "Not Completed");

        p.setProperty(LangKey.SETTING_TITLE, "SETTING Tap");

        p.setProperty(LangKey.INFO_AUTO_COLLECTED, "Auto Collected!");
        p.setProperty(LangKey.INFO_COLLECTED_ICE, "Collected Ice : ");
        p.setProperty(LangKey.INFO_VACUUM_ACTIVATED, "Ice Vacuum Activated!");
        p.setProperty(LangKey.INFO_GET_COIN, "You get coin : ");
        p.setProperty(LangKey.INFO_GET_XP, "You get xp : ");

        p.setProperty(LangKey.QUEST_REFRESHED, "Quest refresh!");
        p.setProperty(LangKey.QUEST_NOT_ENOUGH_MONEY, "Not enough money!");
        p.setProperty(LangKey.QUEST_REWARDED, "Quest rewarded!");
        p.setProperty(LangKey.QUEST_THIRD_REWARD, "Ice Collect Bonus");

        p.setProperty(LangKey.QUEST_DESC_BASIC, "Collect 10 Ice Basic");
        p.setProperty(LangKey.QUEST_DESC_RARE, "Collect 5 Ice Rare");
        p.setProperty(LangKey.QUEST_DESC_LEGENDARY, "Collect 1 Ice Legendary");
        p.setProperty(LangKey.QUEST_DESC_10MIN, "Play for 10 min");
        p.setProperty(LangKey.QUEST_DESC_30MIN, "Play for 30 min");
        p.setProperty(LangKey.QUEST_DESC_1HOUR, "Play for 1 hour");
        p.setProperty(LangKey.QUEST_DESC_LONGTIME,
                "Permanently grants +5 coin per Ice collected and 1000 coins.");

        p.setProperty(LangKey.SHOP_RUSH_BASIC_ON, "Ice Basic Rush Activated!");
        p.setProperty(LangKey.SHOP_RUSH_RARE_ON, "Ice Rare Rush Activated!");
        p.setProperty(LangKey.SHOP_RUSH_LEGENDARY_ON, "Ice Legendary Rush Activated!");
        p.setProperty(LangKey.SHOP_RUSH_TIME_30, "Rush time : 30 seconds");
        p.setProperty(LangKey.SHOP_RUSH_TIME_60, "Rush time : 1 min");

        p.setProperty(LangKey.SHOP_RUSH_BASIC_OFF, "Ice Basic Rush ended!");
        p.setProperty(LangKey.SHOP_RUSH_RARE_OFF, "Ice Rare Rush ended!");
        p.setProperty(LangKey.SHOP_RUSH_LEGENDARY_OFF, "Ice Legendary Rush ended!");

        p.setProperty(LangKey.SHOP_PURCHASED, "Purchased!");
        p.setProperty(LangKey.SHOP_MAX_LEVEL, "Max level!");

        p.setProperty(LangKey.SKILL_UPGRADED, "Upgraded!");
        p.setProperty(LangKey.SKILL_NOT_ENOUGH_POINT, "Not enough skill point!");
        p.setProperty(LangKey.SKILL_BASIC_UP, "Ice Basic Spawn Chance Upgraded!");
        p.setProperty(LangKey.SKILL_RARE_UP, "Ice Rare Spawn Chance Upgraded!");
        p.setProperty(LangKey.SKILL_LEGENDARY_UP, "Ice Legendary Spawn Chance Upgraded!");
        p.setProperty(LangKey.SKILL_PRESENT_COIN, "present coin : ");
        p.setProperty(LangKey.SKILL_PRESENT_XP, "present xp : ");
        p.setProperty(LangKey.SKILL_CURRENT_LEVEL, "Current Level : ");

        String homeDir = System.getProperty("user.home")+ File.separator + "SC" + File.separator + "lang";

        String fullPath1 = homeDir + File.separator + "english.properties";
        String fullPath2 = homeDir + File.separator + "korean.properties";
        String fullPath3 = homeDir + File.separator + "custom.properties";
        String paths[] = {"empty", fullPath1, fullPath2, fullPath3};

        try (FileOutputStream out = new FileOutputStream(paths[language])) {

            p.store(out, "custom");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "저장 실패: " + e.getMessage() + "\n경로: " + paths[language]);
        }
    }
    // Thanks for Java!
    //Java Great Again!
}




