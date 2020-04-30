package inc.heterological.iaibgame.desktop;

import inc.heterological.iaibgame.desktop.managers.GameKeys;
import org.junit.Test;

import static org.junit.Assert.*;

public class SelectArrowTest {

    @Test
    public void moveArrowDown() {
        SelectArrow arrow1 = new SelectArrow(180,120 ,210, 30, 90);
        arrow1.moveArrow(GameKeys.DOWN);
        assertEquals(30, arrow1.y);
    }

    @Test
    public void moveArrowUp() {
        SelectArrow arrow1 = new SelectArrow(180,120 ,210, 30, 90);
        arrow1.moveArrow(GameKeys.UP);
        assertEquals(210, arrow1.y);
    }

    @Test
    public void moveArrowOneButton() {
        SelectArrow arrow2 = new SelectArrow(180,120 ,120, 120, 10);
        arrow2.moveArrow(GameKeys.UP);
        assertEquals(120, arrow2.y);
        arrow2.moveArrow(GameKeys.DOWN);
        assertEquals(120, arrow2.y);
    }
}