package inc.heterological.iaibgame.net.shared.packets;

import com.esotericsoftware.kryonet.Connection;

import java.io.Serializable;

public class PlayerEntity implements Serializable {
    public int x, y;
    public int id;
    public Connection c;
}
