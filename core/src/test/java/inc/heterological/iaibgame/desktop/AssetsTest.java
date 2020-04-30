package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.junit.Test;

import static org.junit.Assert.*;

public class AssetsTest {

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