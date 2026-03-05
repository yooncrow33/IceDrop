package sc.base.gameModel.tick;

public class TickManager {
    int sessionPlayTick = 0;
    int tick = 0;
    int lastPlayTime;
    int totalPlayTime = 0;
    int sessionPlayTime = 0;

    public void loadLastPlayTime(int lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

    public int getTick() {
        return tick;
    }

    public int getLastPlayTime() {
        return lastPlayTime;
    }

    public int getSessionPlayTime() {
        return sessionPlayTime;
    }

    public void update() {
        tick++;
        sessionPlayTick++;
        sessionPlayTime = (int) Math.ceil(sessionPlayTick / 3600f);
        totalPlayTime = lastPlayTime + sessionPlayTime;
    }
}
