package inc.heterological.iaibgame.net.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import inc.heterological.iaibgame.desktop.screens.MultiplayerArena;
import inc.heterological.iaibgame.net.shared.Network;

public class ClientListener extends Listener {

    public void received(Connection c, Object o) {
        if (o instanceof Network.AddPlayer) {
            Network.AddPlayer packet = (Network.AddPlayer) o;
            Network.OnlinePlayer newPlayer = new Network.OnlinePlayer();
            MultiplayerArena.players.put(packet.playerID, newPlayer);
            Log.info("Player " + packet.playerID + " joined the game");
        } else if (o instanceof Network.RemovePlayer) {
            Network.RemovePlayer packet = (Network.RemovePlayer) o;
            MultiplayerArena.players.remove(packet.playerID);
            Log.info("Player " + packet.playerID + " left the game");
        } else if (o instanceof Network.UpdateX) {
            Network.UpdateX packet = (Network.UpdateX) o;
            MultiplayerArena.players.get(packet.id).x = packet.x;
        } else if (o instanceof Network.UpdateY) {
            Network.UpdateY packet = (Network.UpdateY) o;
            MultiplayerArena.players.get(packet.id).y = packet.y;
        }
    }

}
