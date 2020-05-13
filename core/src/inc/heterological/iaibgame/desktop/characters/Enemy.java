package inc.heterological.iaibgame.desktop.characters;

<<<<<<< HEAD
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.Healthbar;
=======
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.desktop.Assets;
>>>>>>> origin/develop

public class Enemy {

    public Vector2 position;
<<<<<<< HEAD
    public float sense;
    public float currentHealth;
    public float maxHealth;
    protected Healthbar healthbar = new Healthbar(this);
    private boolean alive = true;

    public float getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(float currentHealth) {
        if (currentHealth > 0) {
            this.currentHealth = currentHealth;
        } else {
            alive = false;
        }
    }

    public Enemy(Vector2 position, float sense, float health) {
        this.position = position;
        this.currentHealth = health;
        this.maxHealth = health;
        this.sense = sense;
    }
    public TextureRegion getCurrentFrame(float delta) {
        return Assets.enemy1.getKeyFrame(delta, true);
    }

    public void drawEnemyAndHealthbar(SpriteBatch batch, float statetime) {
        if (alive) {
            batch.draw(this.getCurrentFrame(statetime), position.x, position.y, 64, 64);
            healthbar.drawHealth(batch);
        }
    }

    public void move(Vector2 dest, float delta) {
        if (position.dst(dest) < sense) {
            position.lerp(dest, delta);
        }
    }

=======

    public Enemy(Vector2 position) {
        this.position = position;
    }

    public TextureRegion getCurrentFrame(float stateTime, ENEMY_TYPE type) {
        switch (type) {

            case ZOMBIE:
                return Assets.zombie.getKeyFrame(stateTime, true);
            case BOB_RUNNING:
                return Assets.bob_run.getKeyFrame(stateTime, true);
            case BOB_FLEEING:
                return Assets.bob_flee.getKeyFrame(stateTime, true);
            case HEALER_WALKING:
                return Assets.healer_walking.getKeyFrame(stateTime, true);
            case HEALER_HEALING:
                return Assets.healer_healing.getKeyFrame(stateTime, true);
        }
        return null;
    }

    public TextureRegion getHealingParticles(float statetime) {
        return Assets.healing.getKeyFrame(statetime, true);
    }

    public enum ENEMY_TYPE {ZOMBIE, BOB_RUNNING, BOB_FLEEING, HEALER_WALKING, HEALER_HEALING}

>>>>>>> origin/develop
}
