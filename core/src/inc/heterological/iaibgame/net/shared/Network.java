package inc.heterological.iaibgame.net.shared;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import inc.heterological.iaibgame.desktop.ArenaButton;
import inc.heterological.iaibgame.desktop.characters.Enemy;
import inc.heterological.iaibgame.desktop.characters.Player;
import inc.heterological.iaibgame.net.shared.packets.AddEnemy;
import inc.heterological.iaibgame.net.shared.packets.AddPlayer;
import inc.heterological.iaibgame.net.shared.packets.ArenaButtonChange;
import inc.heterological.iaibgame.net.shared.packets.EnemyEntity;
import inc.heterological.iaibgame.net.shared.packets.Play;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;
import inc.heterological.iaibgame.net.shared.packets.RemoveEnemy;
import inc.heterological.iaibgame.net.shared.packets.RemovePlayer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Network {

    public static void register(Kryo kryo) {
        kryo.register(Vector2.class);
        kryo.register(HashMap.class);
        kryo.register(ConcurrentHashMap.class);
        kryo.register(Map.class);
        kryo.register(Integer.class);

        kryo.register(HashSet.class);

        kryo.register(AddPlayer.class);
        kryo.register(RemovePlayer.class);
        kryo.register(PlayerEntity.class);
        kryo.register(Player.Condition.class);
        kryo.register(Enemy.ENEMY_TYPE.class);

        kryo.register(ArenaButtonChange.class);
        kryo.register(ArenaButton.ARENA_BUTTON_STATE.class);


        kryo.register(AddEnemy.class);
        kryo.register(RemoveEnemy.class);
        kryo.register(EnemyEntity.class);

        kryo.register(Play.class);
        kryo.register(Play.Players.class);
        kryo.register(Play.Enemies.class);
        kryo.register(Play.EntitiesToBeRemoved.class);

    }

}
