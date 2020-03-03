package inc.heterological.iaibgame.desktop.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import inc.heterological.iaibgame.desktop.Assets;

public class Player {

    public final Sprite image;
    public final Rectangle bounds;

    public Player() {
        image = Assets.player;
        bounds = new Rectangle(0, 0, 64, 64);
    }
}