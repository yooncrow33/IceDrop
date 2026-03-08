package sc.base.gameModel;

import sc.view.IExit;
import sc.view.IGameModel;
import sc.view.IMouse;
import sc.view.IPause;

import java.awt.*;
import java.util.Random;

import static sc.base.RenderUtils.drawStringCenter;

public class BarManager {
    final IGameModel iGameModel;
    final IExit iExit;

    final int width = 110;
    final int height = 30;
    final String strs[] = {"⚠ Quit", "▼ Save", "▲ Load", "⏸ Pause", "⚙ Setting", ">_ Console"};

    /*final String[] randomMessages = {
            "Have a nice day :)",
            "IceDrop is early beta!",
            "I'm so Pretty",
            "Console.kt is written by Kotlin.",
            "Don't forget to blink."
    };

     */

    final String[] randomMessages = {
            // --- [친절 & 기본] ---
            "Have a nice day :)", "Welcome back!", "Stay hydrated.", "Don't forget to blink.",
            "System stable. (Maybe)", "Take a deep breath.", "Happy developing!", "You're doing great.",

            // --- [개발자 감성 & Scope 프레임워크] ---
            "Console.kt is written by Kotlin.", "Java is pain, but I'm okay.","Honesty is such a lonely word",
            "Made with 100% Caffeine.", "MacBook is getting hot.", "Compiling... please wait.",
            "It's not a bug, it's a feature.",
            "TODO: Fix everything tomorrow.", "Garbage Collector is busy.", "NullPointerException avoided.",
            "Git commit -m 'Fixed some bugs'.", "Stack Overflow is my best friend.", "One more line...",

            // --- [Ice & Elixir (게임 관련)] ---
            "IceDrop is early beta!", "Absolute Zero reached.", "Stay frosty.",
            "Ice & Elixir: Coming soon?", "Watch out for the frostbite!", "Gathering Ice...",
            "Why is the ice so slippery?", "Testing physics... wheee!",

            // --- [F1 & 자동차 덕후 (네 취향)] ---
            "MAX MAX MAX.", "Super Max!",
            "Full Send!", "Keep pushing.", "DRS Enabled.", "Purple Sector!", "Check the tire pressure.",
            "Manual transmission only.",
            "Tachometer goes BRRRRR.", "Downshift! 4-3-2-1.", "VTEC kicked in yo!",

            // --- [킹받는 문구 & 뻔뻔함] ---
            "I'm so Pretty.", "Why are you still here?", "Are you winning, son?","Stay with me",
            "Error: Success.", "Connecting to NASA...","And so, Sally can wait...","She knows it's too late as we're walking on by",
            "Mining Bitcoin... (Just kidding)", "Your mouse is moving.", "Loading more bugs...",
            "Don't click this. Oh wait, you can't.", "I'm watching you.", "Is it 2026 yet? Oh, it is.",

            // --- [생활 & 드립] ---
            "Boxing is good for your health.", "Gangneung is a cold city.",
            "Cutting mat is not a mousepad! (But it works)","Shot through the heart And you're to blame.",
            "Chimchakman is watching.",
            "Check your posture.",
            "Go is fast, but Kotlin is cool.",

            // --- [M-Series Mac & Dev Sarcasm] ---
            "So Rosetta 2 is trash of the world.",
            "M1, M2, M3... My wallet is empty.",
            "Kernel Panic: See you soon.",
            "Xcode is the heaviest game in the world.",

            // --- [Real World Pain] ---
            "House prices go UP, My soul goes DOWN.",
            "The stock market is a roller coaster with no seatbelts.",
            "Monday is inevitable. Run while you can.",
            "Is your ramen delicious? That's your dinner.",

            // --- [Pure Nonsense] ---
            "Wombats have square poop. Think about it.",
            "A shrimp's heart is in its head.",
            "I'm actually a human trapped in this code. Help!",
            "Error 418: I'm a teapot.", // 고전적인 HTTP 드립

            "How to open .hwp file on Mac?",
            "I will finish this game by tomorrow. (Lie)",
            "The 8GB RAM is enough for everyone. (Apple said that)",
            "System.out.println('I am tired');",
            "I drink 5 cups of coffee a day. (Not a lie)",
            "Is the earth flat? No, but this UI is.",
            "Don't look at the source code. It's a mess.",

            "99% Bug Free. (The 1% is this message)",
            "How to open .hwp on Mac? Just buy a Windows PC. (Joke)",
            "Wait, did I just see a NullPointerException?",
            "The developer is actually a cat. Meow.",
            "This message exists because you turned on 'Nonsense'.",

            // --- [게임 밸런스 & 시스템 폭로] ---
            "Honestly, even I think the 'Ice Rush' is too expensive.",
            "Actually, you can duplicate money using the console. (Shh!)",
            "Beta 1.0.0 alert: All save files will be encrypted!",
            "This was NOT made for Windows. Mac is the only way.",

            // --- [불안] ---
            "Is that a bug or a new gameplay mechanic?",
            "Your MacBook fan is preparing for takeoff.",
            "Warning: System rebooting... (Just kidding)",
            "Error 404: My motivation not found.",

            "Well Speak I Engligh.",
            "Android runtime is trash of my phone all file.",
            "I am is Developer.",
            "Computer is very hot like my heart.",
            "Delete your game for you happy.",
            "No Bug, Only Special Event.",
            "Well Sleep I Night.",

            // --- [개발자 빡침 & 시스템 폭로 시리즈] ---
            "Memory Leak is my lifestyle.",
            "Why this code work? I don't know too.",
            "Wait for 100 years for loading.",
            "Your RAM is screaming, but I don't care.",
            "Android Studio is a RAM vacuum cleaner.",
            "Rosetta 2 is trash of the world.",
            "Xcode is the heaviest game in the world.",

            // --- [유저 킹받게 하는 시스템 메시지] ---
            "Error: Success.",
            "Your mouse is moving. I see it.",
            "Don't click this. Oh wait, you can't.",
            "Is your ramen delicious? That's your dinner.",

            // --- [현실 고증 & 개학 드립] ---
            "Monday is inevitable. Run while you can.",

            // --- [You Know... 기술적 고백 시리즈] ---
            "You know... this game is Java.",
            "You know... actually no Game Framework here.",
            "You know... I am mixed Java and Kotlin. Why? I don't know.",
            "You know... this UI is only AWT JPanel. Believe it or not.",
            "You know... single thread is power. (Maybe)",
            "You know... this game almost became FXGL. But I failed.",
            "You know... Swing is old but gold. Like my MacBook.",
            "You know... Rendering is 100% manual labor.",

            // --- [뇌 뺀 매치업 & 근본 없는 문장 시리즈] ---
            "A330 vs F22 dogfight! Who win?",
            "My doll is '돌'. It is very hard.",
            "I am speak English very well. Trust me.",
            "Your GPU is sleeping? Wake up!",
            "Is this a game or a terminal? Yes.",
            "Keyboard is 2015 HHKB. Old is better.",
            "Don't touch my knob. It is very sensitive.",
            "CPU Fan: I can fly! I can fly!",

            // --- [F1 & 드라이버 밈] ---
            "Box Box! Stay out! Stay out!",
            "Checkers or wreckers.",
            "My tires are gone! (Fastest lap incoming)",
            "Leave me alone, I know what I'm doing.",
            "Slow button on. Slow button on.",

            // --- [브로큰 잉글리시 2탄] ---
            "Your mouse click make me money. Thanks.",
            "Error is my best friend. We meet everyday.",
            "Windows is trash of my Mac life.",
            "I finish this code in 3:00 AM. I am ghost.",
            "Blue screen is not here. Only Black screen.",

            // --- [실시간 킹받게 하기] ---
            "Are you still clicking? Very diligent.",
            "I know your password. It is 'password'.",
            "Don't look at the console. It's my secret diary.",
            "This message is 103rd. You find it!",

            // --- [개발자 허세 & 미래 기술 시리즈] ---
            "Developer : handsome guy. Trust me.",
            "Yeah this game use the Unreal v8.0.",
            "I am coding with 16-core my brain.",
            "Ray-tracing enabled... inside my imagination.",
            "This game is 8K Resolution. If you close your eyes.",
            "My code is art. You are the museum visitor.",
            "Developed by AI. (No, it is just my hard work)",

            // --- [리눅스 & OS 공포 시리즈] ---
            "So Linux is good OS. but scared of Install.",
            "Ubuntu is my friend. until terminal open.",
            "Windows Update is more scared than Ghost.",
            "Mac OS is just expensive Linux. (Don't tell Apple)",
            "Kernel Panic! Please run to your mom.",
            "sudo rm -rf / ... Don't do it. Just kidding.",

            // --- [뇌 뺀 매치업 & 아무말 2탄] ---
            "Boeing 747 vs AE86 on downhill. Who win?",
            "Water is wet. My code is dry. (Liar)",
            "Can I eat this MacBook? It looks like Aluminum foil.",
            "Keyboard sound is ASMR for my lonely soul.",
            "1 + 1 = 3. My math logic is unique.",
            "If you hungry, eat the 'Cookie' in browser.",

            // --- [You know... 시리즈 2탄] ---
            "You know... I am never use LWJGL yet.",
            "You know... IceDrop is better than Cyberpunk 2077. (Maybe)",
            "You know... this game have 0% bug. It is all features.",
            "You know... I'm writing this message at 2 AM. I'm very tired.",

            // --- [F1 & 킹받는 드립] ---
            "Copy that. We are checking.",
            "Blue Flag! Blue Flag!",
            "Is that Glock?! Yes, it is.",
            "Max Verstappen is 33. This game is SC 33.",
            "Stop the car. Engine is toasted.",

            "My Develop better than 'ASOBO'. Look my Knob.",
            "MS is Terrible compony of the world. Trust Mac.",
            "Google is watching you. But I am watching Google.",
            "Adobe is too expensive. I use my Hand and AWT.",
            "Intel CPU is good Heater for my room.",

            // --- [뇌 뺀 매치업 & 브로큰 잉글리시 3탄] ---
            "Boeing 747 vs My MacBook. Who is louder?",
            "I am very Handsome. Mirror is lie to me.",
            "Sleep is for weak. Code is for ghost.",
            "My English is very Perfect. You are wrong.",
            "Delicious is my dinner. Ramen is my friend.",
            "If you delete this game, I am cry in my bed.",

            // --- [You know... 기술적 허세 시리즈] ---
            "You know... actually I am use the Quantum Computer. (Liar)",
            "You know... this game have 1,000,000,000,000 FPS.",
            "You know... AWT is faster than NASA system. Maybe.",
            "You know... my code is clean like my shower feel.",
            "You know... this game is 100% Hand-made. No Machine.",

            // --- [현실 & 개학 고증 시리즈] ---
            "School is scard of me. Because I am Developer.",
            "Teacher: 'Stop the coding'. Me: 'Stop the teaching'.",
            "Homework is 404 Not Found in my bag.",
            "I want go home. But I am already home. Strange.",
            "Black coffee is fuel of my SC project.",

            // --- [F1 & 킹받는 무전] ---
            "Gentlemen, a short view back to the past...",
            "No Michael no! This is so not right!", // 토토 울프 빙의
            "I am moving up and down, side to side, like a roller coaster.",
            "Is the car okay? No, it is a pixel.",

            // --- [기타 짧은 것들] ---
            "404 Not Found.", "Hello World.", "SC!", "Wait...", "Look behind you.",
            "Click Save!", "Don't alt-f4.", "Processing...", "Beep boop.", "Loading awesomeness."
    };
    Random random = new Random();
    int currentMessageIndex = random.nextInt(randomMessages.length);
    final int messageChangeTime = 480;
    final int messageTime = 600;
    int messageChangeCool = messageChangeTime;
    String currentMessage = "";
    int messageCool = 0;

    private float targetY = -40f;
    private float lerpSpeed = 0.35f;  // magic number

    final int highLightTime = 9;
    int highLightCool = 0;
    private boolean open = false;
    private boolean render = false;
    private float y = -40;

    public BarManager(IGameModel iGameModel, IExit iExit) {
        this.iGameModel = iGameModel;
        this.iExit = iExit;
    }

    private void execute(int i) {
        iGameModel.getSoundManager().play("select.wav");
        switch (i) {
            case 0 :
                iGameModel.getExitPopup().setVisible();
                break;
            case 1 :
                putSystemMessage("Game Save!");
                iGameModel.getFileManager().save(iGameModel.getCurrentProfileId());
                break;
            case 2 :
                putSystemMessage("Game Load!");
                iGameModel.getFileManager().load(iGameModel.getCurrentProfileId());
                break;
            case 3 :
                if (iGameModel.getiPause().isPause()) {
                    iGameModel.getiPause().setPause(false);
                    putSystemMessage("Game Run!");
                } else { iGameModel.getiPause().setPause(true); putSystemMessage("Game Pause!");}
                break;
            case 4 :
                iGameModel.getSettingManager().toggle();
                break;
            case 5 :
                iGameModel.getConsole().toggle();
                break;
            default:

        }
    }

    public void putSystemMessage(String str) {
        currentMessage = str;
        messageCool = messageTime;
    }

    public void click() {
        if (!open) {return;}
        for (int i = 0; i < strs.length; i++) {
            int xPos = i * width;

            if (iGameModel.getiMouse().getVirtualMouseX() >= xPos && iGameModel.getiMouse().getVirtualMouseX() < xPos + width &&
                    iGameModel.getiMouse().getVirtualMouseY() >= 0 && iGameModel.getiMouse().getVirtualMouseY() <= height) {
                execute(i);
                highLightCool = highLightTime;
            }
        }
    }

    public void update() {
        if (highLightCool >= 0) {highLightCool--;} else highLightCool = 0;
        if (messageChangeCool >= 0) {messageChangeCool--;} else { messageChangeCool = messageChangeTime; currentMessageIndex = random.nextInt(randomMessages.length);}
        if (messageCool >= 0) {messageCool--;} else { messageCool = 0;}


        open = iGameModel.getiMouse().getVirtualMouseY() <= 30 + 10;

        if (iGameModel.getiPause().isPause()) { open = true; }
        if (iGameModel.getSettingManager().getUiSetting().isBarFixed()) {open = true;}

        targetY = open ? 0f : -40f;
        y += ((targetY - y) * lerpSpeed);
        if (targetY <= -40f && y < -38.4f) {
            y = -40;
        }
        if (y > -39.8f) {
            render = true;
        } else {
            render = false;
        }
    }

    public void render(Graphics g) {
        if (!render) {return;}
        Graphics2D g2 = (Graphics2D) g;
        int y = (int)this.y;
        g2.setStroke(new BasicStroke(6f));
        g.setColor(new Color(0,40,95));
        g.drawLine(0,y+height,1920,y+height);
        g2.setStroke(new BasicStroke(2f));
        g.setColor(new Color(0,140,215));
        g.fillRect(0,y,1920,height);
        for (int i = 0; i < strs.length; i++) {
            int xPos = i * width;

            if (iGameModel.getiMouse().getVirtualMouseX() >= xPos && iGameModel.getiMouse().getVirtualMouseX() < xPos + width &&
                    iGameModel.getiMouse().getVirtualMouseY() >= 0 && iGameModel.getiMouse().getVirtualMouseY() <= height) {

                if (highLightCool >= 1) {
                    g.setColor(new Color(255, 255, 255, 150));
                } else {
                    g.setColor(new Color(255, 255, 255, 100));
                }
                g.fillRect(xPos, y, width, height);
            }
        }
        g.setFont(new Font("Arial", Font.BOLD, 18));
        for (int i = 0; i < strs.length; i++) {
            g.setColor(new Color(0,40,95));
            g.drawRect(width*i,y,width,height);
            g.setColor(Color.black);
            drawStringCenter(g,strs[i], -2 + width/2 + (i * width),20 + y);
            g.setColor(Color.white);
            drawStringCenter(g,strs[i],width/2 + (i * width),20 + y);
        }

        g.setFont(new Font("Arial", Font.BOLD, 21));
        if (messageCool <= 0) {
            if (iGameModel.getSettingManager().getUiSetting().isNonsense()) {
                g.setColor(Color.black);
                drawStringCenter(g,randomMessages[currentMessageIndex], 960-2,y+24);
                g.setColor(Color.white);
                drawStringCenter(g,randomMessages[currentMessageIndex], 960,y+24);
            }
        } else {
            g.setColor(Color.black);
            drawStringCenter(g,currentMessage, 960-2,y+24);
            g.setColor(Color.green);
            drawStringCenter(g,currentMessage, 960,y+24);
        }

        g.setColor(Color.black);
        g.fillOval(1889,y + 1,28,28);

        if (iGameModel.getiPause().isPause()) {
            g.setColor(Color.red);
        } else g.setColor(Color.green);
        g.fillOval(1890,y + 2,26,26);
    }
}
