package inc.heterological.iaibgame.desktop.managers;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameKeysTest {

    @Test
    public void isPressed() {
        GameKeys.setKeys(GameKeys.UP, true);
        assertTrue(GameKeys.isPressed(GameKeys.UP));
    }

    @Test
    public void noKeyPressed() {
        assertTrue(GameKeys.noKeyPressed());
        GameKeys.setKeys(GameKeys.UP, true);
        GameKeys.update();
        assertFalse(GameKeys.noKeyPressed());
    }
}