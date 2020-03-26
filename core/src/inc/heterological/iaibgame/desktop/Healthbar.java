package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inc.heterological.iaibgame.desktop.characters.Enemy;

public class Healthbar {

    Enemy enemy;

    public Healthbar(Enemy enemy) {
        this.enemy = enemy;
    }

    public void drawHealth(SpriteBatch batch) {
        if (enemy.maxHealth >= enemy.currentHealth && enemy.currentHealth > enemy.maxHealth * 0.75) {
            batch.draw(Assets.healthgreen,enemy.position.x, enemy.position.y + 70, 64, 24);
        }
        else if (enemy.maxHealth * 0.75 >= enemy.currentHealth && enemy.currentHealth > enemy.maxHealth * 0.5) {
            batch.draw(Assets.healthyellow, enemy.position.x, enemy.position.y + 70, 64, 24);
        }
        else if (enemy.maxHealth * 0.5 >= enemy.currentHealth && enemy.currentHealth> enemy.maxHealth * 0.25) {
            batch.draw(Assets.healthorange,enemy.position.x, enemy.position.y + 70, 64, 24);
        }
        else if (enemy.maxHealth * 0.25 >= enemy.currentHealth && enemy.currentHealth> 0) {
            batch.draw(Assets.healthred, enemy.position.x, enemy.position.y + 70, 64, 24);
        }
        else if (enemy.currentHealth == 0) {
            batch.draw(Assets.healthempty,enemy.position.x, enemy.position.y + 70, 64, 24);
        }
    }

}
