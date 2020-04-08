package inc.heterological.iaibgame.net.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import inc.heterological.iaibgame.desktop.screens.MultiplayerArena;
import inc.heterological.iaibgame.net.shared.packets.AddEnemy;
import inc.heterological.iaibgame.net.shared.packets.AddPlayer;
import inc.heterological.iaibgame.net.shared.packets.OnlineEnemy;
import inc.heterological.iaibgame.net.shared.packets.OnlinePlayer;
import inc.heterological.iaibgame.net.shared.packets.RemovePlayer;
import inc.heterological.iaibgame.net.shared.packets.UpdateEnemy;
import inc.heterological.iaibgame.net.shared.packets.UpdateX;
import inc.heterological.iaibgame.net.shared.packets.UpdateY;

public class ClientListener extends Listener {

    public void received(Connection c, Object o) {
        if (o instanceof AddPlayer) {
            AddPlayer packet = (AddPlayer) o;
            OnlinePlayer newPlayer = new OnlinePlayer();
            MultiplayerArena.players.put(packet.playerID, newPlayer);
            Log.info("Player " + packet.playerID + " joined the game");
        } else if (o instanceof RemovePlayer) {
            RemovePlayer packet = (RemovePlayer) o;
            MultiplayerArena.players.remove(packet.playerID);
            Log.info("Player " + packet.playerID + " left the game");
        } else if (o instanceof UpdateX) {
            UpdateX packet = (UpdateX) o;
            MultiplayerArena.players.get(packet.id).x = packet.x;
        } else if (o instanceof UpdateY) {
            UpdateY packet = (UpdateY) o;
            MultiplayerArena.players.get(packet.id).y = packet.y;
        } else if (o instanceof AddEnemy) {
            AddEnemy packet = (AddEnemy) o;
            OnlineEnemy newEnemy = new OnlineEnemy();
            MultiplayerArena.enemies.put(packet.id, newEnemy);
        } else if (o instanceof UpdateEnemy) {
            UpdateEnemy packet = (UpdateEnemy) o;
            MultiplayerArena.enemies.get(packet.id).pos = packet.pos;
        }
    }

}
