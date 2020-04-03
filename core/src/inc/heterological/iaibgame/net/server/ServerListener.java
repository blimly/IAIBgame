package inc.heterological.iaibgame.net.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import inc.heterological.iaibgame.net.shared.Network;

public class ServerListener extends Listener {

    public void connected(Connection c) {
        Network.PlayerEntity player = new Network.PlayerEntity();
        player.x = 0;
        player.y = 0;
        player.c = c;
        Network.AddPlayer addPlayer = new Network.AddPlayer();
        addPlayer.playerID = c.getID();
        GameServer.server.sendToAllExceptTCP(c.getID(), addPlayer);

        for (Network.PlayerEntity p : GameServer.players.values()) {
            Network.AddPlayer addPlayer2 = new Network.AddPlayer();
            addPlayer2.playerID = p.c.getID();
            c.sendTCP(addPlayer2);
        }
        GameServer.players.put(c.getID(), player);
        Log.info("Connection received");
    }

    public void received(Connection c, Object o) {
        if (o instanceof Network.UpdateX) {
            Network.UpdateX updateX = (Network.UpdateX) o;
            GameServer.players.get(c.getID()).x = updateX.x;
            updateX.id = c.getID();
            GameServer.server.sendToAllExceptUDP(c.getID(), updateX);
            Log.info("Player " + c.getID() + " updadet X");
        } else if (o instanceof Network.UpdateY) {
            Network.UpdateY updateY = (Network.UpdateY) o;
            GameServer.players.get(c.getID()).y = updateY.y;
            updateY.id = c.getID();
            GameServer.server.sendToAllExceptUDP(c.getID(), updateY);
            Log.info("Player " + c.getID() + " updadet Y");
        }
    }
    public void disconnected(Connection c) {
        GameServer.players.remove(c.getID());
        Network.RemovePlayer removePlayer = new Network.RemovePlayer();
        removePlayer.playerID = c.getID();
        GameServer.server.sendToAllExceptTCP(c.getID(), removePlayer);
        Log.info("Client disconnected: " + c.getID());
    }
}
