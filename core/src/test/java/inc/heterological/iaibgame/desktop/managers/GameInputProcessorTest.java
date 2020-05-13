package inc.heterological.iaibgame.desktop.managers;

import com.badlogic.gdx.Input;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameInputProcessorTest {

    @Test
    public void keyDown() {
        GameInputProcessor processor = new GameInputProcessor();
        processor.keyDown(Input.Keys.W);
        processor.keyDown(Input.Keys.A);
        processor.keyDown(Input.Keys.S);
        processor.keyDown(Input.Keys.D);
        processor.keyDown(Input.Keys.J);
        processor.keyDown(Input.Keys.K);
        processor.keyDown(Input.Keys.ENTER);
        processor.keyDown(Input.Keys.BACKSPACE);
        assertTrue(GameKeys.isDown(GameKeys.UP));
        assertTrue(GameKeys.isDown(GameKeys.LEFT));
        assertTrue(GameKeys.isDown(GameKeys.DOWN));
        assertTrue(GameKeys.isDown(GameKeys.RIGHT));
        assertTrue(GameKeys.isDown(GameKeys.JAB));
        assertTrue(GameKeys.isDown(GameKeys.KICK));
        assertTrue(GameKeys.isDown(GameKeys.ENTER));
        assertTrue(GameKeys.isDown(GameKeys.BACKSPACE));
    }

    @Test
    public void keyUp() {
        GameInputProcessor processor = new GameInputProcessor();
        processor.keyUp(Input.Keys.W);
        processor.keyUp(Input.Keys.A);
        processor.keyUp(Input.Keys.S);
        processor.keyUp(Input.Keys.D);
        processor.keyUp(Input.Keys.J);
        processor.keyUp(Input.Keys.K);
        processor.keyUp(Input.Keys.ENTER);
        processor.keyUp(Input.Keys.BACKSPACE);
        assertFalse(GameKeys.isDown(GameKeys.UP));
        assertFalse(GameKeys.isDown(GameKeys.LEFT));
        assertFalse(GameKeys.isDown(GameKeys.DOWN));
        assertFalse(GameKeys.isDown(GameKeys.RIGHT));
        assertFalse(GameKeys.isDown(GameKeys.JAB));
        assertFalse(GameKeys.isDown(GameKeys.KICK));
        assertFalse(GameKeys.isDown(GameKeys.ENTER));
        assertFalse(GameKeys.isDown(GameKeys.BACKSPACE));
    }
}