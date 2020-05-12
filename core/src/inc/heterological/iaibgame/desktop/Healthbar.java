package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Healthbar {

    public static void drawHealth(SpriteBatch batch, Vector2 entityPos, int health) {

        batch.setColor(Color.BLACK);
        batch.draw(Assets.blank, entityPos.x - 4, entityPos.y + 68, 72, 12);
        batch.setColor(Color.GRAY);
        batch.draw(Assets.blank, entityPos.x , entityPos.y + 70, 64, 8);

        if (health <= 100 && health > 75) {
            batch.setColor(Color.GREEN);
        }
        else if (health <= 75 && health > 50) {
            batch.setColor(Color.YELLOW);
        }
        else if (health <= 50 && health > 25) {
            batch.setColor(Color.ORANGE);
        }
        else if (health <= 25 && health > 0) {
            batch.setColor(Color.RED);
        }

        float healthInPercent = health / 100f;

        batch.draw(Assets.blank, entityPos.x, entityPos.y + 70, 64 * healthInPercent, 8);
        batch.setColor(Color.WHITE);
    }

}
