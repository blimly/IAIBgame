package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
<<<<<<< HEAD
import inc.heterological.iaibgame.desktop.characters.Enemy;

public class Healthbar {

    Enemy enemy;

    public Healthbar(Enemy enemy) {
        this.enemy = enemy;
    }

    public void drawHealth(SpriteBatch batch) {
        batch.setColor(Color.BLACK);
        batch.draw(Assets.blank, enemy.position.x - 4, enemy.position.y + 68, 72, 12);
        batch.setColor(Color.GRAY);
        batch.draw(Assets.blank, enemy.position.x , enemy.position.y + 70, 64, 8);
        if (enemy.maxHealth >= enemy.currentHealth && enemy.currentHealth > enemy.maxHealth * 0.75) {
            batch.setColor(Color.GREEN);
        }
        else if (enemy.maxHealth * 0.75 >= enemy.currentHealth && enemy.currentHealth > enemy.maxHealth * 0.5) {
            batch.setColor(Color.YELLOW);
        }
        else if (enemy.maxHealth * 0.5 >= enemy.currentHealth && enemy.currentHealth> enemy.maxHealth * 0.25) {
            batch.setColor(Color.ORANGE);
        }
        else if (enemy.maxHealth * 0.25 >= enemy.currentHealth && enemy.currentHealth> 0) {
            batch.setColor(Color.RED);
        }
        float helthPercent = enemy.currentHealth / enemy.maxHealth;
        batch.draw(Assets.blank, enemy.position.x, enemy.position.y + 70, 64 * helthPercent, 8);
=======
import com.badlogic.gdx.math.Vector2;

public class Healthbar {

    public static void drawHealth(SpriteBatch batch, Vector2 entityPos, int health) {

        batch.setColor(Color.BLACK);
        batch.draw(Assets.blank, entityPos.x - 4, entityPos.y + 68, 72, 12);
        batch.setColor(Color.GRAY);
        batch.draw(Assets.blank, entityPos.x, entityPos.y + 70, 64, 8);

        if (health <= 100 && health > 75) {
            batch.setColor(Color.valueOf("20e2de"));
        } else if (health <= 75 && health > 50) {
            batch.setColor(Color.valueOf("2999ac"));
        } else if (health <= 50 && health > 25) {
            batch.setColor(Color.valueOf("317d9c"));
        } else if (health <= 25 && health > 0) {
            batch.setColor(Color.valueOf("414883"));
        }

        float healthInPercent = health / 100f;

        batch.draw(Assets.blank, entityPos.x, entityPos.y + 70, 64 * healthInPercent, 8);
>>>>>>> origin/develop
        batch.setColor(Color.WHITE);
    }

}
