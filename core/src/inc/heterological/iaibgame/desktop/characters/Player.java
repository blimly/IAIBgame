package inc.heterological.iaibgame.desktop.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.managers.SoundEffects;


public class Player {
    private static final int MOVE_SPEED = 20;

    public final Rectangle bounds;
    public final Rectangle onlineBounds;
    public Condition currentState;
    public int width = 64;
    public int height = 64;
    public int id;
    public int health;

    public Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private float friction;

    public String username;
    public boolean onButton;

    public boolean facingRight;
    private float attackStateTime = 0;

    public Player() {
        position = new Vector2(880, 600);
        bounds = new Rectangle(position.x, position.y, width, height);
        onlineBounds = new Rectangle(Main.GAME_WIDTH / 2f, Main.GAME_HEIGHT / 2f, width, height);
        health = 100;
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
        friction = 0.93f;
        //ANIMATION
        currentState = Condition.IDLE;
        facingRight = true;
    }

    public void moveLeft(float dt) {
        facingRight = false;
        currentState = Condition.MOVE;
        acceleration.add(dt * -MOVE_SPEED, 0);
    }

    public void moveRight(float dt) {
        facingRight = true;
        currentState = Condition.MOVE;
        acceleration.add(dt * MOVE_SPEED, 0);
    }

    public void moveUp(float dt) {
        currentState = Condition.MOVE;
        acceleration.add(0, dt * MOVE_SPEED);
    }

    public void moveDown(float dt) {
        currentState = Condition.MOVE;
        acceleration.add(0, dt * -MOVE_SPEED);
    }


    public void updatePlayerPhysics() {
        collideWithWall();
        velocity.add(acceleration)
                .limit(MOVE_SPEED)
                .scl(friction);
        position.add(velocity);
        acceleration.scl(0, 0);
    }

    private void collideWithWall() {
        float distFromCenter = position.dst(880, 912);
        if (distFromCenter > 512) {
            velocity.scl(-0.5f);
            acceleration.add(new Vector2(912, 912)
                    .sub(position)
                    .setLength(distFromCenter - 512));
        }
    }

    public void jab() {
        SoundEffects.play("Woosh1");
        currentState = Condition.JAB;
    }

    public void kick() {
        SoundEffects.play("Woosh2");
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
                    boolean jab = true;
                    attackStateTime += delta;
                    return Assets.playerJab.getKeyFrame(attackStateTime, false);
                } else {
                    boolean jab = false;
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
                return null;
        }
    }

    // For animating online players
    public static TextureRegion getFrameBasedUponCondition(Condition state, float stateTime) {
        switch (state) {
            case KICK:
                return Assets.playerKick.getKeyFrame(stateTime, false);
            case JAB:
                return Assets.playerJab.getKeyFrame(stateTime, false);
            case MOVE:
                return Assets.playerMove.getKeyFrame(stateTime, true);
            default:
                return Assets.playerIdle.getKeyFrame(stateTime, true);
        }
    }

    public enum Condition {IDLE, MOVE, JAB, KICK}
}