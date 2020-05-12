package inc.heterological.iaibgame.desktop.characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.managers.SoundEffects;


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
    public static boolean stomping = false;

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

    public void playFootSteps() {
        if (!stomping) {
            stomping = true;
            SoundEffects.loop("FootSteps", 0.3f);
        }

    }

    public void moveLeft(float dt) {
        facingRight = false;
        currentState = Condition.MOVE;
        collideWithWall();
        position.add(dt * -MOVE_SPEED, 0);
        playFootSteps();
    }

    public void moveRight(float dt) {
        facingRight = true;
        currentState = Condition.MOVE;
        collideWithWall();
        position.add(dt * MOVE_SPEED, 0);
        playFootSteps();
    }

    public void moveUp(float dt) {
        currentState = Condition.MOVE;
        collideWithWall();
        position.add(0, dt * MOVE_SPEED);
        playFootSteps();
    }

    public void moveDown(float dt) {
        currentState = Condition.MOVE;
        collideWithWall();
        position.add(0, dt * -MOVE_SPEED);
        playFootSteps();
    }

    private void collideWithWall() {
        if (position.dst(480, 512) > 512) {
            position.add(new Vector2(512, 512).sub(position).setLength(5));
        }
    }

    public void jab() {
        SoundEffects.play("Jab", 0.3f);
        currentState = Condition.JAB;
    }

    public void kick() {
        SoundEffects.play("Kick", 0.3f);
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
                playFootSteps();
                return Assets.playerMove.getKeyFrame(stateTime, true);
            case IDLE:
                return Assets.playerIdle.getKeyFrame(stateTime, true);
            default:
                break;
        }
        return null;
    }

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