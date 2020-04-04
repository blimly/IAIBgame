package inc.heterological.iaibgame.net.client;

import com.esotericsoftware.kryonet.Client;
import inc.heterological.iaibgame.net.shared.Network;

import java.io.IOException;

public class GameClient {
    public Client client;

    public GameClient() {
        client = new Client();

    }
    public void connect() {
        Network.register(client.getKryo());
        client.start();

        try {
            client.connect(50000, "localhost", 5201, 5200);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.addListener(new ClientListener());
    }
}
