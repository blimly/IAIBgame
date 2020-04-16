package inc.heterological.iaibgame.net.shared.packets;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class UpdateEnemy implements Serializable {
    public int id, health;
    public Vector2 pos;
}