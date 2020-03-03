package inc.heterological.iaibgame.desktop;

import inc.heterological.iaibgame.desktop.screens.IAIBGame;

public class Main extends com.badlogic.gdx.Game {

    public IAIBGame gameScreen;
    @Override
    public void create() {
        Assets.load();
        gameScreen = new IAIBGame(this);
        setScreen(gameScreen);
    }
}
