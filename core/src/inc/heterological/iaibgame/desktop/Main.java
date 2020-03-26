package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inc.heterological.iaibgame.desktop.screens.MainMenu;

public class Main extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    public static final int GAME_WIDTH = 640;
    public static final int GAME_HEIGHT = 489;

    public boolean hasPlayedOnce;

    @Override
    public void create() {
        hasPlayedOnce = false;
        batch = new SpriteBatch();
        Assets.load();
        font = Assets.getFont();

        this.setScreen(new MainMenu(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        Assets.dispose();
    }
}
