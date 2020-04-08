package inc.heterological.iaibgame.net.shared.packets;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;

import java.io.Serializable;

public class EnemyEntity implements Serializable {
    public Vector2 pos;
    public int id;
    public Connection c;
}
