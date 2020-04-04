package inc.heterological.iaibgame.net.server;

import com.esotericsoftware.kryonet.Server;
import inc.heterological.iaibgame.net.shared.Network;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameServer {

    static Server server;
    static Map<Integer, PlayerEntity> players = new HashMap<>();

    public GameServer() throws IOException {
        server = new Server();
        server.bind(5201, 5200);
        Network.register(server.getKryo());
        server.start();
        server.addListener(new ServerListener());
    }

}
