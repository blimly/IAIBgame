package inc.heterological.iaibgame.desktop;

import inc.heterological.iaibgame.desktop.screens.Bye;
import inc.heterological.iaibgame.desktop.screens.IAIBGame;
import inc.heterological.iaibgame.desktop.screens.Loading;
import inc.heterological.iaibgame.desktop.screens.MainMenu;
import inc.heterological.iaibgame.desktop.screens.Test;

public class Main extends com.badlogic.gdx.Game {

    public IAIBGame gameScreen;
    public Loading loading;
    public MainMenu mainMenu;
    public Test test;
    public Bye bye;

    @Override
    public void create() {
        Assets.load();
        mainMenu = new MainMenu(this);
        loading = new Loading(this);
        gameScreen = new IAIBGame(this);
        test = new Test(this);
        bye = new Bye(this);
        setScreen(mainMenu);
    }

    public void render() {
        super.render();
    }

    public void dispose() {}
}
