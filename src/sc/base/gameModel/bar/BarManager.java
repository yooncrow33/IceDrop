package sc.base.gameModel.bar;

import sc.view.IExit;
import sc.view.IGameModel;
import sc.view.IMouse;
import sc.view.IPause;

import java.awt.*;
import java.util.Random;

import static sc.base.RenderUtils.drawStringCenter;

public class BarManager {
    final IGameModel iGameModel;
    final IMouse iMouse;
    final IExit iExit;
    final IPause iPause;

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
    private int moveYTime = 20;
    private int moveYDistance = 40;

    public BarManager(IGameModel iGameModel, IMouse iMouse, IExit iExit, IPause iPause) {
        this.iGameModel = iGameModel;
        this.iMouse = iMouse;
        this.iExit = iExit;
        this.iPause = iPause;
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
                if (iPause.isPause()) {
                    iPause.setPause(false);
                    putSystemMessage("Game Run!");
                } else { iPause.setPause(true); putSystemMessage("Game Pause!");}
                break;
            case 4 :

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

            if (iMouse.getVirtualMouseX() >= xPos && iMouse.getVirtualMouseX() < xPos + width &&
                    iMouse.getVirtualMouseY() >= 0 && iMouse.getVirtualMouseY() <= height) {
                execute(i);
                highLightCool = highLightTime;
            }
        }
    }

    public void update() {
        if (highLightCool >= 0) {highLightCool--;} else highLightCool = 0;
        if (messageChangeCool >= 0) {messageChangeCool--;} else { messageChangeCool = messageChangeTime; currentMessageIndex = random.nextInt(randomMessages.length);}
        if (messageCool >= 0) {messageCool--;} else { messageCool = 0;}


        open = iMouse.getVirtualMouseY() <= 30 + 10;
        if (iPause.isPause()) {open = true;}

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

            if (iMouse.getVirtualMouseX() >= xPos && iMouse.getVirtualMouseX() < xPos + width &&
                    iMouse.getVirtualMouseY() >= 0 && iMouse.getVirtualMouseY() <= height) {

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

        g.setFont(new Font("Arial", Font.BOLD, 24));
        if (messageCool <= 0) {
            g.setColor(Color.black);
            drawStringCenter(g,randomMessages[currentMessageIndex], 960-2,y+24);
            g.setColor(Color.white);
            drawStringCenter(g,randomMessages[currentMessageIndex], 960,y+24);
        } else {
            g.setColor(Color.black);
            drawStringCenter(g,currentMessage, 960-2,y+24);
            g.setColor(Color.green);
            drawStringCenter(g,currentMessage, 960,y+24);
        }

        g.setColor(Color.black);
        g.fillOval(1889,y + 1,28,28);

        if (iPause.isPause()) {
            g.setColor(Color.red);
        } else g.setColor(Color.green);
        g.fillOval(1890,y + 2,26,26);
    }
}
