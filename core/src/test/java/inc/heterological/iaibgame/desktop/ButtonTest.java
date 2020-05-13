package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

import static org.junit.Assert.*;

public class ButtonTest {

    @Test
    public void clicked() {
        Button button = new Button(10,10, 0,0, Assets.exit);
        assertTrue(button.clicked(new Vector2(1,1)));
    }
}