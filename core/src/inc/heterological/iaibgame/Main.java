package inc.heterological.iaibgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.managers.GameInputProcessor;
import inc.heterological.iaibgame.desktop.managers.GameKeys;
import inc.heterological.iaibgame.desktop.managers.GameStateManager;
import inc.heterological.iaibgame.net.client.GameClient;
import inc.heterological.iaibgame.net.server.GameServer;

public class Main extends ApplicationAdapter {
    public static SpriteBatch batch;
    public BitmapFont font;

    public static OrthographicCamera camera;
    public static GameStateManager gameStateManager;

    public static int GAME_WIDTH = 640;
    public static int GAME_HEIGHT = 480;


    public static GameServer server;
    public static GameClient client;

    @Override
    public void create() {
        Assets.load();
        GAME_WIDTH = Gdx.graphics.getWidth();
        GAME_HEIGHT = Gdx.graphics.getHeight();

        batch = new SpriteBatch();

        camera = new OrthographicCamera(GAME_WIDTH, GAME_HEIGHT);
        camera.setToOrtho(false, 640, 480);

        Gdx.input.setInputProcessor(new GameInputProcessor());

        //new game state manager for render
        gameStateManager = new GameStateManager();

    }

    public void render() {
        Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f); // clear color magenta
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameStateManager.update(Gdx.graphics.getDeltaTime());
        gameStateManager.draw();
        GameKeys.update();

    }

    public void dispose() {
        batch.dispose();
        Assets.dispose();
    }
}
