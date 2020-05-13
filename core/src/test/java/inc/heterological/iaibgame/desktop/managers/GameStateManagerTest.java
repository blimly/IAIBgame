package inc.heterological.iaibgame.desktop.managers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.screens.GameState;
import inc.heterological.iaibgame.desktop.screens.MainMenu;
import inc.heterological.iaibgame.desktop.screens.MultiplayerArena;
import inc.heterological.iaibgame.desktop.screens.MultiplayerLobby;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

public class GameStateManagerTest {

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
    public void setGetGameState() {
        Assets.load();
        Main.camera = new OrthographicCamera(640, 480);
        GameStateManager gsm = new GameStateManager();
        gsm.setGameState(GameStateManager.MENU);
        assertThat(gsm.getGameState(), instanceOf(MainMenu.class));
        gsm.setGameState(GameStateManager.LOBBY);
        assertThat(gsm.getGameState(), instanceOf(MultiplayerLobby.class));
        gsm.setGameState(GameStateManager.PLAY_MULTIPLAYER);
        assertThat(gsm.getGameState(), instanceOf(MultiplayerArena.class));
    }
}