import javax.swing.*;

public class GameModel implements IGameModel{
    private int coin;
    private int level;

    public void update() {

    }

    @Override
    public int getLevel() {
        return coin;
    }

    @Override
    public int getCoin() {
        return level;
    }
}
