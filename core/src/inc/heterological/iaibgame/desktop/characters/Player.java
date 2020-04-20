package inc.heterological.iaibgame.desktop.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.managers.GameKeys;


public class Player {
    public enum Condition {IDLE_RIGHT, IDLE_LEFT, MOVE_LEFT, MOVE_RIGHT, JAB_RIGHT, JAB_LEFT, KICK_RIGHT, KICK_LEFT}
    public Condition previousState;
    public Condition currentState;
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
        //ANIMATION
        previousState = Condition.IDLE_RIGHT;
        currentState = Condition.IDLE_RIGHT;

    }

    public void moveLeft(double dt) {
        previousState = Player.Condition.IDLE_LEFT;
        currentState = Player.Condition.MOVE_LEFT;
        bounds.x -= MOVE_SPEED * dt;
        if (!GameKeys.isDown(GameKeys.LEFT)) {
            currentState = Condition.IDLE_LEFT;
        }
    }
    public void moveRight(double dt) {
        previousState = Player.Condition.IDLE_RIGHT;
        currentState = Player.Condition.MOVE_RIGHT;
        bounds.x += MOVE_SPEED * dt;
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
        bounds.y += MOVE_SPEED * dt;
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
        bounds.y -= MOVE_SPEED * dt;
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