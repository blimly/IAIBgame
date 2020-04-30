package inc.heterological.iaibgame.desktop.characters;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnemyTest {

    @Test
    public void setCurrentHealth() {
        Enemy enemy = new Enemy(new Vector2(0,0), 100, 100);
        enemy.setCurrentHealth(150);
        assertEquals(150, enemy.getCurrentHealth(), 0);
    }

    @Test
    public void setCurrentHealthDeath() {
        Enemy enemy = new Enemy(new Vector2(0,0), 100, 100);
        enemy.setCurrentHealth(0);
        assertFalse(enemy.alive);
    }

    @Test
    public void getCurrentFrame() {
    }

    @Test
    public void moveInSense() {
        Enemy enemy = new Enemy(new Vector2(0,0), 100, 100);
        enemy.move(new Vector2(25, 25), 1);
        assertEquals(new Vector2(25,25), enemy.position);
    }

    @Test
    public void moveOutOfSense() {
        Enemy enemy = new Enemy(new Vector2(0,0), 100, 100);
        enemy.move(new Vector2(200, 200), 1);
        assertEquals(new Vector2(0,0), enemy.position);
    }
}