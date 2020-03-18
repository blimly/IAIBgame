package inc.heterological.iaibgame.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import inc.heterological.iaibgame.desktop.characters.Player;

import java.io.IOException;

public class Packages extends Listener {

    Client client;
    String ip = "localhost";

    static public final int port = 5201;


    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Login.class);
        kryo.register(RegistrationRequired.class);
        kryo.register(Register.class);
        kryo.register(AddPlayer.class);
        kryo.register(PacketUpdateX.class);
        kryo.register(PacketUpdateY.class);
        kryo.register(RemovePlayer.class);
        kryo.register(Character.class);
        kryo.register(MovePlayer.class);
    }

    public void connect() {
        client = new Client();
        client.getKryo().register(PacketUpdateX.class);
        client.getKryo().register(PacketUpdateY.class);
        client.getKryo().register(AddPlayer.class);
        client.getKryo().register(RemovePlayer.class);
        client.addListener(this);

        client.start();
        try {
            client.connect(5000, ip, port, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public class Login {
        public String name;
    }

    static public class RegistrationRequired {
    }

    static public class Register {
        public String name;
    }

    static public class PacketUpdateY {
        public int id;
        public float y;
    }

    static public class PacketUpdateX {
        public int id;
        public float x;
    }

    static public class AddPlayer {
        public Player player;
        public int id;
    }

    static public class RemovePlayer {
        public int id;
    }

    static public class MovePlayer {
        public int x, y;
    }
}
