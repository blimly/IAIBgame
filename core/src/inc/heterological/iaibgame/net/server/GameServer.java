package inc.heterological.iaibgame.net.server;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.net.shared.Network;
import inc.heterological.iaibgame.net.shared.packets.ArenaButtonChange;
import inc.heterological.iaibgame.net.shared.packets.Play;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameServer {

    static Server server;
    public static Map<Integer, PlayerEntity> players = new ConcurrentHashMap<>();
    private ServerLogic serverLogic;

    public GameServer() throws IOException {
        server = new Server(16384, 2048 * 2);
        Main.server = this;
        server.bind(5201, 5200);
        Network.register(server.getKryo());
        serverLogic = new ServerLogic();
        server.start();
        server.addListener(new ServerListener());
        onStartGame();
    }

    public void updateArena(Play.EntitiesToBeRemoved entitiesRemoved) {
        Play.Players serverPlayers = new Play.Players();
        serverPlayers.players = players;
        ServerLogic.players = players;

        Play.Enemies enemies = new Play.Enemies();
        enemies.enemies = serverLogic.getEnemies();
        server.sendToAllUDP(enemies);

        if (ServerLogic.buttonChanged) {
            ArenaButtonChange buttonChangePacket = new ArenaButtonChange();
            buttonChangePacket.state = ServerLogic.buttonState;
            server.sendToAllTCP(buttonChangePacket);
            ServerLogic.buttonChanged = false;
            Log.info("Send button change packet");
        }

        server.sendToAllUDP(entitiesRemoved);
    }

    public void onStartGame() {
        serverLogic.loadArena();
        for (PlayerEntity player : players.values()) {
            serverLogic.addPlayer(player);
        }
        serverLogic.run(new HashSet<>(players.values()));
        Log.info("Server world is ready!");
    }
}
