package inc.heterological.iaibgame.net.server;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import inc.heterological.iaibgame.net.shared.packets.AddPlayer;
import inc.heterological.iaibgame.net.shared.packets.Play;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;
import inc.heterological.iaibgame.net.shared.packets.RemovePlayer;

public class ServerListener extends Listener {

    public void connected(Connection c) {
        // Tell everybody that new player joined
        AddPlayer addPlayer = new AddPlayer();
        addPlayer.playerID = c.getID();
        GameServer.server.sendToAllExceptTCP(c.getID(), addPlayer);

        // Add all the players who are already in game to client's multiplayer arena
        Play.Players playersInGame = new Play.Players();
        playersInGame.players = GameServer.players;
        c.sendTCP(playersInGame);

        // Make the connection a new PlayerEntity in the server
        PlayerEntity player = new PlayerEntity();
        player.pos = Vector2.Zero;
        player.id = c.getID();
        GameServer.players.put(c.getID(), player);

        //Log.info("Connection received: " + c.getID());
    }

    public void received(Connection c, Object o) {
        if (o instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) o;
            GameServer.players.get(c.getID()).pos = player.pos;
            player.id = c.getID();
            GameServer.server.sendToAllExceptTCP(c.getID(), player);
        }
        if (o instanceof RemovePlayer) {
            RemovePlayer remove = (RemovePlayer) o;
            GameServer.players.remove(remove.playerID);
            ServerLogic.players.remove(remove.playerID);
            GameServer.server.sendToAllExceptTCP(c.getID(), remove);
        }
    }
    public void disconnected(Connection c) {
        GameServer.players.remove(c.getID());
        ServerLogic.players.remove(c.getID());
        RemovePlayer removePlayer = new RemovePlayer();
        removePlayer.playerID = c.getID();
        GameServer.server.sendToAllExceptTCP(c.getID(), removePlayer);
        Log.info("Client disconnected: " + c.getID());
    }
}
