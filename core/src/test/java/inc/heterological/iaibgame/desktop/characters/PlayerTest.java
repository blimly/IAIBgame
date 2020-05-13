package inc.heterological.iaibgame.desktop.characters;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inc.heterological.iaibgame.desktop.Assets;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

public class PlayerTest {

    private static Application application;

    @BeforeClass
    public static void init() {
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
    public void moveLeft() {
        Player player = new Player();
        player.moveLeft(1);
        assertEquals(880, player.position.x, 0);
        assertFalse(player.facingRight);
    }

    @Test
    public void moveRight() {
        Player player = new Player();
        player.moveRight(1);
        assertEquals(880, player.position.x, 0);
        assertTrue(player.facingRight);
    }

    @Test
    public void moveUp() {
        Player player = new Player();
        player.moveUp(1);
        assertEquals(600, player.position.y, 0);
    }

    @Test
    public void moveDown() {
        Player player = new Player();
        player.moveDown(1);
        assertEquals(600, player.position.y, 0);

    }

    @Test
    public void jab() {
        Player player = new Player();
        player.jab();
        assertEquals(Player.Condition.JAB, player.currentState);
    }

    @Test
    public void kick() {
        Player player = new Player();
        player.kick();
        assertEquals(Player.Condition.KICK, player.currentState);
    }

    @Test
    public void stand() {
        Player player = new Player();
        player.stand();
        assertEquals(Player.Condition.IDLE, player.currentState);
    }

    @Test
    public void getCurrentFrame() {
        Player player = new Player();
        player.stand();
        assertEquals(Assets.playerIdle.getKeyFrame(1, true), player.getCurrentFrame(1, 1));
        player.moveUp(1);
        assertEquals(Assets.playerMove.getKeyFrame(1, true), player.getCurrentFrame(1, 1));
        player.jab();
        assertThat(player.getCurrentFrame(1, 1), instanceOf(TextureRegion.class));
        player.kick();
        assertThat(player.getCurrentFrame(1, 1), instanceOf(TextureRegion.class));

    }
}