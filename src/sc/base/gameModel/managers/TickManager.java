package sc.base.gameModel.managers;

import sc.view.IGameModel;

public class TickManager {
    final IGameModel iGameModel;

    public TickManager(IGameModel iGameModel) {
        this.iGameModel = iGameModel;
    }

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

    public void init() {
        sessionPlayTick = 0;
        tick = 0;
    }

    public void update() {
        sessionPlayTick++;
        sessionPlayTime = (int) Math.ceil(sessionPlayTick / 62.5f*62.5f);
        totalPlayTime = lastPlayTime + sessionPlayTime;
        if (iGameModel.getiPause().isPause()) return;
        tick++;
    }
}
