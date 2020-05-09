package inc.heterological.iaibgame.net.shared.packets;

import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.desktop.characters.Player;

import java.io.Serializable;

public class PlayerEntity implements Serializable {

    public Player.Condition currentState;
    public Vector2 pos;
    public int health;
    public int id;
    public boolean facingRight;
    public long latency;
}
