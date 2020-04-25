package inc.heterological.iaibgame.desktop.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.Assets;

public class Player {
    public final Rectangle bounds;
    public final Rectangle onlineBounds;
    public int width = 64;
    public int height = 64;
    public int health;

    public Vector2 position;
    public Vector2 velocity;
    public Vector2 friction;

    public String username;
    public boolean onButton;

    private static final int MOVE_SPEED = 200;

    public Player() {
        position = new Vector2(Main.GAME_WIDTH / 2f - width / 2f, Main.GAME_HEIGHT / 2f - height / 2f);
        bounds = new Rectangle(position.x, position.y, width, height);
        onlineBounds = new Rectangle(Main.GAME_WIDTH / 2f, Main.GAME_HEIGHT / 2f, width, height);
        health = 100;
        velocity = new Vector2(0, 0);
        friction = new Vector2(0, 0);
    }

    public void moveLeft(double dt) {
        position.add((float) dt * -MOVE_SPEED, 0);
    }
    public void moveRight(double dt) {
        position.add((float) dt * MOVE_SPEED, 0);
    }
    public void moveUp(double dt) {
        position.add(0, (float) dt * -MOVE_SPEED);
    }
    public void moveDown(double dt) {
        position.add(0, (float) dt * MOVE_SPEED);
    }

    public TextureRegion getCurrentFrame(float statetime) {
        return (TextureRegion) Assets.player.getKeyFrame(statetime, true);
    }
}