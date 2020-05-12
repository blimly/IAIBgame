package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArenaButtonTest {

    @Test
    public void draw() {
    }

    @Test
    public void playerOnButton() {
        ArenaButton arenaButton = new ArenaButton(0,0, ArenaButton.ARENA_BUTTON_STATE.UP);
        assertTrue(arenaButton.playerOnButton(new Vector2(10,10)));
    }
}