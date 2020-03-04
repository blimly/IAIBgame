package inc.heterological.iaibgame.desktop.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import inc.heterological.iaibgame.desktop.Assets;

public class Player {
    public final Rectangle bounds;

    public Player() {
        bounds = new Rectangle(0, 0, 128, 128);
    }

    public TextureRegion getCurrentFrame(float delta) {
        return (TextureRegion) Assets.player.getKeyFrame(delta, true);
    }
}