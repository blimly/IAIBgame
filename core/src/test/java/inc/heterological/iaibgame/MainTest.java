package inc.heterological.iaibgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inc.heterological.iaibgame.desktop.managers.GameStateManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void create() {
        Main main = new Main();
        assertNull(Main.batch);
        assertNull(Main.camera);
        assertNull(Main.gameStateManager);
        main.create();
        assertEquals(new SpriteBatch(), Main.batch);
        assertEquals(new OrthographicCamera(640, 480), Main.camera);
        assertEquals(new GameStateManager(), Main.gameStateManager);
    }

    @Test
    public void render() {
    }

    @Test
    public void dispose() {
        Main main = new Main();
        main.create();
        assertEquals(new SpriteBatch(), Main.batch);
        main.dispose();
        assertNull(Main.batch);
    }
}