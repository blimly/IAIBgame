package inc.heterological.iaibgame.desktop;

import inc.heterological.iaibgame.desktop.screens.IAIBGame;
import inc.heterological.iaibgame.desktop.screens.Loading;

public class Main extends com.badlogic.gdx.Game {

    public IAIBGame gameScreen;
    public Loading loading;

    @Override
    public void create() {
        loading = new Loading(this);
        gameScreen = new IAIBGame(this);
        Assets.load();
        setScreen(loading);
    }

    public void render() {
        super.render();
    }

    public void dispose() {}
}
