package inc.heterological.iaibgame.net;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import inc.heterological.iaibgame.desktop.characters.Player;
import inc.heterological.iaibgame.net.Network.Login;
import inc.heterological.iaibgame.net.Network.MovePlayer;
import inc.heterological.iaibgame.net.Network.UpdatePlayer;

import java.io.IOException;
import java.util.HashMap;

public class GameClient {
    UI ui;
    Client client;
    String name;

    public GameClient () {
        client = new Client();
        client.start();

        // For consistency, the classes to be sent over the network are
        // registered by the same method for both the client and server.
        Network.register(client.getKryo());

        // ThreadedListener runs the listener methods on a different thread.
        client.addListener(new ClientListener(this));

        ui = new UI();

        try {
            client.connect(5000, Network.serverIP, Network.TCPport);
            // Server communication after connection can go here, or in Listener#connected().
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        name = ui.inputName();
        Login login = new Login();
        login.name = name;
        client.sendTCP(login);

        while (true) {
            int ch;
            try {
                ch = System.in.read();
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }

            MovePlayer msg = new MovePlayer();
            switch (ch) {
                case 'w':
                    msg.y = -1;
                    break;
                case 's':
                    msg.y = 1;
                    break;
                case 'a':
                    msg.x = -1;
                    break;
                case 'd':
                    msg.x = 1;
                    break;
                default:
                    msg = null;
            }
            if (msg != null) client.sendTCP(msg);
        }
    }

    static class UI {
        HashMap<Integer, Player> players = new HashMap();

        public String inputName () {
            return "name";
        }

        public void addPlayer (Player player) {
            players.put(player.id, player);
            System.out.println(player.username + " added at " + player.bounds.x + ", " + player.bounds.y);
        }

        public void updatePlayer (UpdatePlayer msg) {
            Player player = players.get(msg.id);
            if (player == null) return;
            player.bounds.x = msg.x;
            player.bounds.y = msg.y;
            System.out.println(player.username + " moved to " + player.bounds.x + ", " + player.bounds.y);
        }

        public void removePlayer (int id) {
            Player player = players.remove(id);
            if (player != null) System.out.println(player.username + " removed");
        }
    }

    public static void main (String[] args) {
        Log.set(Log.LEVEL_DEBUG);
        new GameClient();
    }
}