package sc.base.gameModel.tick;

public class TickManager {
    int playTick;
    int lastPlayTime;
    int totalPlayTime = 0;
    int sessionPlayTime = 0;

    public void loadPlayTick(int playTick) {
        this.playTick = playTick;
    }

    public void loadLastPlayTime(int lastPlayTime) {
        this.lastPlayTime = lastPlayTime;
    }

    public void loadTotalPlayTime(int totalPlayTime) {
        this.totalPlayTime = totalPlayTime;
    }

    public void loadSessionPlayTime(int sessionPlayTime) {
        this.sessionPlayTime = sessionPlayTime;
    }

    public int getPlayTick() {
        return playTick;
    }

    public int getLastPlayTime() {
        return lastPlayTime;
    }

    public int getTotalPlayTime() {
        return totalPlayTime;
    }

    public int getSessionPlayTime() {
        return sessionPlayTime;
    }

    public void update() {
        playTick++;
        sessionPlayTime = (int) Math.ceil(playTick / 3600f);
        totalPlayTime = lastPlayTime + sessionPlayTime;
    }
}
