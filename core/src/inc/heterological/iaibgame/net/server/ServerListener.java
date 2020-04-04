package inc.heterological.iaibgame.net.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import inc.heterological.iaibgame.net.shared.packets.AddPlayer;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;
import inc.heterological.iaibgame.net.shared.packets.RemovePlayer;
import inc.heterological.iaibgame.net.shared.packets.UpdateX;
import inc.heterological.iaibgame.net.shared.packets.UpdateY;

public class ServerListener extends Listener {

    public void connected(Connection c) {
        PlayerEntity player = new PlayerEntity();
        player.x = 0;
        player.y = 0;
        player.c = c;
        AddPlayer addPlayer = new AddPlayer();
        addPlayer.playerID = c.getID();
        GameServer.server.sendToAllExceptTCP(c.getID(), addPlayer);

        for (PlayerEntity p : GameServer.players.values()) {
            AddPlayer addPlayer2 = new AddPlayer();
            addPlayer2.playerID = p.c.getID();
            c.sendTCP(addPlayer2);
        }
        GameServer.players.put(c.getID(), player);
        Log.info("Connection received");
    }

    public void received(Connection c, Object o) {
        if (o instanceof UpdateX) {
            UpdateX updateX = (UpdateX) o;
            GameServer.players.get(c.getID()).x = updateX.x;
            updateX.id = c.getID();
            GameServer.server.sendToAllExceptUDP(c.getID(), updateX);
        } else if (o instanceof UpdateY) {
            UpdateY updateY = (UpdateY) o;
            GameServer.players.get(c.getID()).y = updateY.y;
            updateY.id = c.getID();
            GameServer.server.sendToAllExceptUDP(c.getID(), updateY);
        }
    }
    public void disconnected(Connection c) {
        GameServer.players.remove(c.getID());
        RemovePlayer removePlayer = new RemovePlayer();
        removePlayer.playerID = c.getID();
        GameServer.server.sendToAllExceptTCP(c.getID(), removePlayer);
        Log.info("Client disconnected: " + c.getID());
    }
}
