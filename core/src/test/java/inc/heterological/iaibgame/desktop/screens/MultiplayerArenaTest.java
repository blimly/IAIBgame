package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.characters.Player;
import inc.heterological.iaibgame.desktop.managers.GameKeys;
import inc.heterological.iaibgame.desktop.managers.GameStateManager;
import inc.heterological.iaibgame.net.shared.packets.Play;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class MultiplayerArenaTest {

    private static Application application;

    @BeforeClass
    public static void setUP() {
        // Note that we don't need to implement any of the listener's methods
        application = new HeadlessApplication(new ApplicationListener() {
            @Override public void create() {}
            @Override public void resize(int width, int height) {}
            @Override public void render() {}
            @Override public void pause() {}
            @Override public void resume() {}
            @Override public void dispose() {}
        });

        // Use Mockito to mock the OpenGL methods since we are running headlessly
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
    }

    @AfterClass
    public static void cleanUp() {
        // Exit the application first
        application.exit();
        application = null;
    }

    @Test
    public void show() {
        Assets.load();
        Main.camera = new OrthographicCamera(640, 480);
        GameStateManager gameStateManager = new GameStateManager();
        MultiplayerArena multiplayerArena = new MultiplayerArena(gameStateManager);
        MultiplayerArena.player.position = new Vector2(0,0);
        multiplayerArena.show();
        assertTrue(MultiplayerArena.gameClient.client.isConnected());
    }

    @Test
    public void updateMove() {
        Assets.load();
        Main.camera = new OrthographicCamera(640, 480);
        GameStateManager gameStateManager = new GameStateManager();
        MultiplayerArena multiplayerArena = new MultiplayerArena(gameStateManager);
        MultiplayerArena.player.health = 100;
        GameKeys.setKeys(GameKeys.LEFT, true);
        GameKeys.setKeys(GameKeys.RIGHT, true);
        multiplayerArena.update();
        assertEquals(Player.Condition.MOVE, MultiplayerArena.player.currentState);
    }

    @Test
    public void draw() {
    }

    @Test
    public void handleInput() {
    }

    @Test
    public void dispose() {
    }
}