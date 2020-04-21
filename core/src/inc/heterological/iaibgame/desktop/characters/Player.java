package inc.heterological.iaibgame.desktop.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.managers.GameKeys;


public class Player {
    public enum Condition {IDLE_RIGHT, IDLE_LEFT, MOVE_LEFT, MOVE_RIGHT, JAB_RIGHT, JAB_LEFT, KICK_RIGHT, KICK_LEFT}
    public Condition previousState;
    public Condition currentState;
    public final Rectangle bounds;
    public final Rectangle onlineBounds;
    public int width = 64;
    public int height = 64;
    public int id;
    public int health;

    public Vector2 position;
    public Vector2 velocity;
    public Vector2 friction;

    public String username;
    public boolean onButton;

    private static final int MOVE_SPEED = 200;

    public Player() {
        id = this.hashCode();
        position = new Vector2(Main.GAME_WIDTH / 2 - width / 2, Main.GAME_HEIGHT / 2 - height / 2);
        bounds = new Rectangle(position.x, position.y, width, height);
        onlineBounds = new Rectangle(Main.GAME_WIDTH / 2 - width / 2, Main.GAME_HEIGHT / 2 - height / 2, width, height);
        health = 100;
        velocity = new Vector2(0, 0);
        friction = new Vector2(0, 0);
        //ANIMATION
        previousState = Condition.IDLE_RIGHT;
        currentState = Condition.IDLE_RIGHT;

    }

    public void moveLeft(double dt) {
        previousState = Player.Condition.IDLE_LEFT;
        currentState = Player.Condition.MOVE_LEFT;
        position.add((float) dt * -MOVE_SPEED, 0);

        if (!GameKeys.isDown(GameKeys.LEFT)) {
            currentState = Condition.IDLE_LEFT;
        }
    }
    public void moveRight(double dt) {
        previousState = Player.Condition.IDLE_RIGHT;
        currentState = Player.Condition.MOVE_RIGHT;
        position.add((float) dt * MOVE_SPEED, 0);
        if (!GameKeys.isDown(GameKeys.RIGHT)) {
            currentState = Condition.IDLE_RIGHT;
        }
    }
    public void moveUp(double dt) {
        if (previousState == Condition.IDLE_RIGHT) {
            currentState = Condition.MOVE_RIGHT;
            if (!GameKeys.isDown(GameKeys.UP)) {
                currentState = Condition.IDLE_RIGHT;
            }
        } else if (previousState == Condition.IDLE_LEFT) {
            currentState = Condition.MOVE_LEFT;
            if (!GameKeys.isDown(GameKeys.UP)) {
                currentState = Condition.IDLE_LEFT;
            }
        }
        position.add(0, (float) dt * -MOVE_SPEED);
    }
    public void moveDown(double dt) {
        if (previousState == Condition.IDLE_RIGHT) {
            currentState = Condition.MOVE_RIGHT;
            if (!GameKeys.isDown(GameKeys.DOWN)) {
                currentState = Condition.IDLE_RIGHT;
            }
        } else if (previousState == Condition.IDLE_LEFT) {
            currentState = Condition.MOVE_LEFT;
            if (!GameKeys.isDown(GameKeys.DOWN)) {
                currentState = Condition.IDLE_LEFT;
            }
        }
        position.add(0, (float) dt * MOVE_SPEED);
    }
    public void jab() {
        if (previousState == Condition.IDLE_LEFT) {
            currentState = Condition.JAB_LEFT;
        } else { currentState = Condition.JAB_RIGHT; }


    }
    public void kick() {
        if (previousState == Condition.IDLE_LEFT) {
            currentState = Condition.KICK_LEFT;
        } else { currentState = Condition.KICK_RIGHT; }
    }

    public void stand() {
        if (previousState == Player.Condition.IDLE_RIGHT) {
            currentState = Player.Condition.IDLE_RIGHT;
        } else {
            currentState = Player.Condition.IDLE_LEFT;
        }
    }

    public boolean hasJabbed(float stateTime) {
        return Assets.playerJab.isAnimationFinished(stateTime);
    }

    public TextureRegion getCurrentFrame(float delta) {

        if (currentState == Condition.MOVE_RIGHT || currentState == Condition.MOVE_LEFT) {
            return Assets.playerMove.getKeyFrame(delta, true);
        } else if (currentState == Condition.JAB_RIGHT || currentState == Condition.JAB_LEFT) {
            return Assets.playerJab.getKeyFrame(delta, false);
        } else if (currentState == Condition.KICK_RIGHT || currentState == Condition.KICK_LEFT) {
            return Assets.playerKick.getKeyFrame(delta, false);
        } else {
            return Assets.playerIdle.getKeyFrame(delta, true);
        }
    }
}