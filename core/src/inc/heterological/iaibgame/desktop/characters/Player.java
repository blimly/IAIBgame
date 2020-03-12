package inc.heterological.iaibgame.desktop.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import inc.heterological.iaibgame.desktop.Assets;

public class Player {
    public final Rectangle bounds;
    public int xWidth = 64;
    public int yHeight = 64;
    public int id;
    public String username;

    public Player(String username) {
        this.username = username;
        id = this.hashCode();
        bounds = new Rectangle(0, 0, xWidth, yHeight);
    }

    public Player() {
        bounds = null;
    }

    public TextureRegion getCurrentFrame(float delta) {
        return (TextureRegion) Assets.player.getKeyFrame(delta, true);
    }
}