package inc.heterological.iaibgame.net.client;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.screens.MultiplayerArena;
import inc.heterological.iaibgame.net.shared.packets.AddEnemy;
import inc.heterological.iaibgame.net.shared.packets.AddPlayer;
import inc.heterological.iaibgame.net.shared.packets.EnemyEntity;
import inc.heterological.iaibgame.net.shared.packets.Play;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;
import inc.heterological.iaibgame.net.shared.packets.RemoveEnemy;
import inc.heterological.iaibgame.net.shared.packets.RemovePlayer;

public class ClientListener extends Listener {

    public void received(Connection c, Object o) {
        if (o instanceof AddPlayer) {
            AddPlayer packet = (AddPlayer) o;
            PlayerEntity newPlayer = new PlayerEntity();
            newPlayer.pos = new Vector2(Main.GAME_HEIGHT / 2f, Main.GAME_WIDTH / 2f);
            MultiplayerArena.players.put(packet.playerID, newPlayer);
            Log.info("Player " + packet.playerID + " joined the game");
        } else if (o instanceof RemovePlayer) {
            RemovePlayer packet = (RemovePlayer) o;
            MultiplayerArena.players.remove(packet.playerID);
            Log.info("Player " + packet.playerID + " left the game");
        } else if (o instanceof PlayerEntity) {
            PlayerEntity packet = (PlayerEntity) o;
            MultiplayerArena.players.get(packet.id).pos = packet.pos;
        }

        else if (o instanceof Play.Players) {
            Play.Players packet = (Play.Players) o;
            for (PlayerEntity p : packet.players.values()) {
                MultiplayerArena.players.put(p.id, p);
            }
        }

        else if (o instanceof AddEnemy) {
            AddEnemy packet = (AddEnemy) o;
            EnemyEntity newEnemy = new EnemyEntity();
            newEnemy.pos = Vector2.Zero;
            MultiplayerArena.enemies.put(packet.id, newEnemy);
        }
        /*
        else if (o instanceof EnemyEntity) {
            EnemyEntity packet = (EnemyEntity) o;
            MultiplayerArena.enemies.get(packet.id).pos = packet.pos;
        }
        */
        else if (o instanceof RemoveEnemy) {
            RemoveEnemy packet = (RemoveEnemy) o;
            MultiplayerArena.players.remove(packet.enemyId);
        }



        else if (o instanceof Play.Enemies) {
            Play.Enemies packet = (Play.Enemies) o;
            MultiplayerArena.enemies = packet.enemies;
        }

    }

}
