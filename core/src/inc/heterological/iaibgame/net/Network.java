package inc.heterological.iaibgame.net;

import com.esotericsoftware.kryo.Kryo;
import inc.heterological.iaibgame.desktop.characters.Player;

public class Network {

    static public final int TCPport = 5201;
    static public final int UDPport = 5200;
    static public final String serverIP = "193.40.255.23";

    static public void register (Kryo kryo) {
        kryo.register(Login.class);
        kryo.register(RegistrationRequired.class);
        kryo.register(Register.class);
        kryo.register(AddPlayer.class);
        kryo.register(UpdatePlayer.class);
        kryo.register(RemovePlayer.class);
        kryo.register(Character.class);
        kryo.register(MovePlayer.class);
    }

    static public class Login {
        public String name;
    }

    static public class RegistrationRequired {
    }

    static public class Register {
        public String name;
        public String otherStuff;
    }

    static public class UpdatePlayer {
        public int id, x, y;
    }

    static public class AddPlayer {
        public Player player;
    }

    static public class RemovePlayer {
        public int id;
    }

    static public class MovePlayer {
        public int x, y;
    }
}
