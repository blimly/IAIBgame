package inc.heterological.iaibgame.desktop.arena_objects;

import inc.heterological.iaibgame.desktop.characters.Player;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ArenaButtonTest {

    @Test
    public void playerOnButton() {
        ArenaButton arenaButton = new ArenaButton(0,0);
        assertTrue(arenaButton.playerOnButton(0,0));

    }

    @Test
    public void getButtonX() {
        ArenaButton arenaButton = new ArenaButton(0,0);
        assertEquals(0, arenaButton.getButtonX());
    }

    @Test
    public void getButtonY() {
        ArenaButton arenaButton = new ArenaButton(0,0);
        assertEquals(0, arenaButton.getButtonY());
    }
}