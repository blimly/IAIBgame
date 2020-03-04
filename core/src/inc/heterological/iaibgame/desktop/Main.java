package inc.heterological.iaibgame.desktop;

import inc.heterological.iaibgame.desktop.screens.IAIBGame;
import inc.heterological.iaibgame.desktop.screens.Loading;
import inc.heterological.iaibgame.desktop.screens.MainMenu;

public class Main extends com.badlogic.gdx.Game {

    public IAIBGame gameScreen;
    public Loading loading;
    public MainMenu mainMenu;

    @Override
    public void create() {
        mainMenu = new MainMenu(this);
        loading = new Loading(this);
        gameScreen = new IAIBGame(this);
        Assets.load();
        setScreen(mainMenu);
    }

    public void render() {
        super.render();
    }

    public void dispose() {}
}
