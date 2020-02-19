package inc.heterological.iaibgame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Player {

    public final Sprite image;
    public final Rectangle bounds;

    public Player() {
        image = Assets.player;
        bounds = new Rectangle(0, 0, 64, 64);
    }
}