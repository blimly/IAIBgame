package inc.heterological.iaibgame.net.shared;

import com.esotericsoftware.kryo.Kryo;
import inc.heterological.iaibgame.net.shared.packets.AddPlayer;
import inc.heterological.iaibgame.net.shared.packets.OnlinePlayer;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;
import inc.heterological.iaibgame.net.shared.packets.RemovePlayer;
import inc.heterological.iaibgame.net.shared.packets.UpdateX;
import inc.heterological.iaibgame.net.shared.packets.UpdateY;

public class Network {

    public static void register(Kryo kryo) {
        kryo.register(UpdateX.class);
        kryo.register(UpdateY.class);
        kryo.register(AddPlayer.class);
        kryo.register(RemovePlayer.class);
        kryo.register(PlayerEntity.class);
        kryo.register(OnlinePlayer.class);
    }
}
