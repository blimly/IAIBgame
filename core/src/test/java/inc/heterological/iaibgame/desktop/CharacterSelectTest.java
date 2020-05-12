package inc.heterological.iaibgame.desktop;

import inc.heterological.iaibgame.desktop.managers.GameKeys;
import org.junit.Test;

import static org.junit.Assert.*;

public class CharacterSelectTest {

    @Test
    public void lock() {
        CharacterSelect cSel = new CharacterSelect(0,0);
        cSel.ChangeChar(GameKeys.ENTER);
        assertTrue(cSel.locked);
    }

    @Test
    public void changeChar() {
        CharacterSelect cSel = new CharacterSelect(0,0);
        cSel.ChangeChar(GameKeys.DOWN);
        assertEquals(1, cSel.currentIndex);
        cSel.ChangeChar(GameKeys.UP);
        assertEquals(0, cSel.currentIndex);
    }

    @Test
    public void changeCharMaxUp() {
        CharacterSelect cSel = new CharacterSelect(0,0);
        cSel.ChangeChar(GameKeys.UP);
        assertEquals(0, cSel.currentIndex);
    }

    @Test
    public void changeCharMaxDown() {
        CharacterSelect cSel = new CharacterSelect(0,0);
        cSel.ChangeChar(GameKeys.DOWN);
        cSel.ChangeChar(GameKeys.DOWN);
        cSel.ChangeChar(GameKeys.DOWN);
        cSel.ChangeChar(GameKeys.DOWN);
        cSel.ChangeChar(GameKeys.DOWN);
        assertEquals(3, cSel.currentIndex);
    }
}