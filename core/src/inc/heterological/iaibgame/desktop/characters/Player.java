package inc.heterological.iaibgame.desktop.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import inc.heterological.iaibgame.desktop.Assets;

public class Player {
    public final Rectangle bounds;
    public final Rectangle onlineBounds;
    public int xWidth = 64;
    public int yHeight = 64;
    public int id;
    public int health;
    public String username;
    public boolean onButton;

    private static final int MOVE_SPEED = 200;

    public Player() {
        id = this.hashCode();
        bounds = new Rectangle(0, 0, xWidth, yHeight);
        onlineBounds = new Rectangle(10, 10, xWidth, yHeight);
        health = 100;
    }

    public void moveLeft(double dt) {
        bounds.x -= MOVE_SPEED * dt;
    }
    public void moveRight(double dt) {
        bounds.x += MOVE_SPEED * dt;
    }
    public void moveUp(double dt) {
        bounds.y -= MOVE_SPEED * dt;
    }
    public void moveDown(double dt) {
        bounds.y += MOVE_SPEED * dt;
    }


    public TextureRegion getCurrentFrame(float delta) {
        return (TextureRegion) Assets.player.getKeyFrame(delta, true);
    }
}