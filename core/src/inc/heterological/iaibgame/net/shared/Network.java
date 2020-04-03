package inc.heterological.iaibgame.net.shared;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;

public class Network {

    public static void register(Kryo kryo) {
        kryo.register(UpdateX.class);
        kryo.register(UpdateY.class);
        kryo.register(AddPlayer.class);
        kryo.register(RemovePlayer.class);
        kryo.register(PlayerEntity.class);
        kryo.register(OnlinePlayer.class);
    }

    public static class UpdateX {
        public int id, x;
    }
    public static class UpdateY {
        public int id, y;
    }

    public static class AddPlayer {
        public int playerID;
    }
    public static class RemovePlayer {
        public int playerID;
    }

    public static class PlayerEntity {
        public int x, y;
        public int id;
        public Connection c;
    }

    public static class OnlinePlayer {
        public int x, y;
        public int id;
    }

}
