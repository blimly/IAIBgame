package inc.heterological.iaibgame.desktop.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.Assets;


public class Player {
    private static final int MOVE_SPEED = 200;
    public final Rectangle bounds;
    public final Rectangle onlineBounds;
    public Condition currentState;
    public int width = 64;
    public int height = 64;
    public int id;
    public int health;

    public Vector2 position;
    public Vector2 velocity;
    public Vector2 friction;

    public String username;
    public boolean onButton;

    public boolean facingRight;
    private float attackStateTime = 0;

    public Player() {
        position = new Vector2(Main.GAME_WIDTH / 2f - width / 2f, Main.GAME_HEIGHT / 2f - height / 2f);
        bounds = new Rectangle(position.x, position.y, width, height);
        onlineBounds = new Rectangle(Main.GAME_WIDTH / 2f, Main.GAME_HEIGHT / 2f, width, height);
        health = 100;
        velocity = new Vector2(0, 0);
        friction = new Vector2(0, 0);
        //ANIMATION
        currentState = Condition.IDLE;
        facingRight = true;

    }

    public void moveLeft(float dt) {
        facingRight = false;
        currentState = Condition.MOVE;
        collideWithWall();
        position.add(dt * -MOVE_SPEED, 0);
    }

    public void moveRight(float dt) {
        facingRight = true;
        currentState = Condition.MOVE;
        collideWithWall();
        position.add(dt * MOVE_SPEED, 0);
    }

    public void moveUp(float dt) {
        currentState = Condition.MOVE;
        collideWithWall();
        position.add(0, dt * MOVE_SPEED);
    }

    public void moveDown(float dt) {
        currentState = Condition.MOVE;
        collideWithWall();
        position.add(0, dt * -MOVE_SPEED);
    }

    private void collideWithWall() {
        if (position.dst(480, 512) > 512) {
            position.add(new Vector2(512, 512).sub(position).setLength(5));
        }
    }

    public void jab() {
        currentState = Condition.JAB;
    }

    public void kick() {
        currentState = Condition.KICK;
    }

    public void stand() {
        if (currentState != Condition.JAB && currentState != Condition.KICK) {
            currentState = Condition.IDLE;
        }
    }

    public TextureRegion getCurrentFrame(float stateTime, float delta) {

        switch (currentState) {
            case JAB:
                if (attackStateTime < Assets.playerJab.getAnimationDuration()) {
                    attackStateTime += delta;
                    return Assets.playerJab.getKeyFrame(attackStateTime, false);
                } else {
                    currentState = Condition.IDLE;
                    attackStateTime = 0;
                }
            case KICK:
                if (attackStateTime < Assets.playerKick.getAnimationDuration()) {
                    attackStateTime += delta;
                    return Assets.playerKick.getKeyFrame(attackStateTime, false);
                } else {
                    currentState = Condition.IDLE;
                    attackStateTime = 0;
                }
            case MOVE:
                return Assets.playerMove.getKeyFrame(stateTime, true);
            case IDLE:
                return Assets.playerIdle.getKeyFrame(stateTime, true);
            default:
                break;
        }
        return null;
    }

    public enum Condition {IDLE, MOVE, JAB, KICK}
}