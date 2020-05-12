package inc.heterological.iaibgame.net.shared.packets;

import com.badlogic.gdx.math.Vector2;
import junit.framework.TestCase;

public class EnemyEntityTest extends TestCase {

    public void testTestToString() {
        EnemyEntity enemy = new EnemyEntity();
        enemy.pos = new Vector2(0,0);
        enemy.health = 100;
        enemy.acc = new Vector2(0, 0);
        enemy.id = 0;
        enemy.target = new Vector2(100, 100);
        enemy.vel = new Vector2(0, 0);

        assertEquals("EnemyEntity{pos=(0.0,0.0), id=0, target=(100.0,100.0)}", enemy.toString());
    }
}