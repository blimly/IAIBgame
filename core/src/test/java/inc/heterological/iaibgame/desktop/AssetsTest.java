package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class AssetsTest {

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
    public void load() {
        assertNull(Assets.font);
        assertNull(Assets.lockTex);
        assertNull(Assets.no);
        assertNull(Assets.textureSheet);
        Assets.load();
        assertEquals(Assets.font, Assets.getFont());
        assertEquals(new Texture(Gdx.files.internal("images/lock.png")), Assets.lockTex);
        assertEquals(Gdx.audio.newSound(Gdx.files.internal("audio/no.wav")), Assets.no);
        assertEquals(new Texture(Gdx.files.internal("images/characters/spritesheet.png"))
                , Assets.textureSheet);
    }

    @Test
    public void dispose() {
    }


}