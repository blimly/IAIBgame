package inc.heterological.iaibgame.desktop.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.esotericsoftware.kryonet.Connection;
import inc.heterological.iaibgame.desktop.Assets;

public class Player {
    public final Rectangle bounds;
    public int xWidth = 64;
    public int yHeight = 64;
    public int id;
    public String username;
    public float x, y;
    public Connection c;


    public Player() {
        username = "player";
        id = this.hashCode();
        bounds = new Rectangle(0, 0, xWidth, yHeight);
    }

    public TextureRegion getCurrentFrame(float delta) {
        return (TextureRegion) Assets.player.getKeyFrame(delta, true);
    }

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {

        }
    }
}