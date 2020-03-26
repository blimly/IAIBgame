package inc.heterological.iaibgame.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {
    private GameClient playerClient;

    public ClientListener(GameClient client) {
        this.playerClient = client;
    }

    @Override
    public void connected(Connection connection) {

    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof Network.RegistrationRequired) {
            Network.Register register = new Network.Register();
            register.name = playerClient.name;
            playerClient.client.sendTCP(register);
        }

        if (object instanceof Network.AddPlayer) {
            Network.AddPlayer msg = (Network.AddPlayer) object;
            playerClient.ui.addPlayer(msg.player);
        }

        if (object instanceof Network.UpdatePlayer) {
            playerClient.ui.updatePlayer((Network.UpdatePlayer) object);
        }

        if (object instanceof Network.RemovePlayer) {
            Network.RemovePlayer msg = (Network.RemovePlayer) object;
            playerClient.ui.removePlayer(msg.id);
        }
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("Connection dropped.");
        System.exit(0);
    }
}
