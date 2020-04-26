package inc.heterological.iaibgame.net.shared.packets;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class PlayerEntity implements Serializable {

    public Vector2 pos;
    public int id;
    public long latency;
    public String option;
}
