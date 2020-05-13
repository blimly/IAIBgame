package inc.heterological.iaibgame.desktop.characters;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.desktop.Assets;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

public class EnemyTest {

    private static Application application;

    @BeforeClass
    public static void init() {
        application = new HeadlessApplication(new ApplicationListener() {
            @Override public void create() {}
            @Override public void resize(int width, int height) {}
            @Override public void render() {}
            @Override public void pause() {}
            @Override public void resume() {}
            @Override public void dispose() {}
        });
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
    public void getCurrentFrame() {
        Enemy enemy = new Enemy(new Vector2(0,0));
        assertThat(enemy.getCurrentFrame(1, Enemy.ENEMY_TYPE.ZOMBIE), instanceOf(TextureRegion.class));
        assertThat(enemy.getCurrentFrame(1, Enemy.ENEMY_TYPE.BOB_RUNNING), instanceOf(TextureRegion.class));
        assertEquals(Assets.bob_flee.getKeyFrame(1, true), enemy.getCurrentFrame(1, Enemy.ENEMY_TYPE.BOB_FLEEING));
        assertEquals(Assets.healer_walking.getKeyFrame(1, true), enemy.getCurrentFrame(1, Enemy.ENEMY_TYPE.HEALER_WALKING));
        assertEquals(Assets.healer_healing.getKeyFrame(1, true), enemy.getCurrentFrame(1, Enemy.ENEMY_TYPE.HEALER_HEALING));
    }

    @Test
    public void getHealingParticles() {
        Enemy enemy = new Enemy(new Vector2(0,0));
        assertEquals(Assets.healing.getKeyFrame(0), enemy.getHealingParticles(0));
    }
}