package inc.heterological.iaibgame;

public class Game extends com.badlogic.gdx.Game {

    public IAIBGame gameScreen;
    @Override
    public void create() {
        Assets.load();
        gameScreen = new IAIBGame(this);
        setScreen(gameScreen);
    }
}
