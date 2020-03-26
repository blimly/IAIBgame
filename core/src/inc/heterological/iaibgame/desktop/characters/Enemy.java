package inc.heterological.iaibgame.desktop.characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.Healthbar;

public class Enemy {

    public Vector2 position;
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

}
