package inc.heterological.iaibgame.net.shared.packets;

import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.desktop.characters.Enemy;

import java.io.Serializable;

public class EnemyEntity implements Serializable {

    public Enemy.ENEMY_TYPE type;
    public boolean attacking;
    public int attackTimer;
    public Vector2 pos;
    public Vector2 vel;
    public Vector2 acc;
    public Vector2 target;
    public int id, health;
    public boolean gettingHealed;
    // debugging
    @Override
    public String toString() {
        return "EnemyEntity{" +
                "pos=" + pos +
                ", id=" + id +
                ", target=" + target +
                '}';
    }
}
