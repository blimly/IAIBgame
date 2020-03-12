package inc.heterological.iaibgame.net;

import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.minlog.Log;
import inc.heterological.iaibgame.desktop.characters.Player;
import inc.heterological.iaibgame.net.Network.*;

public class PlayerClient {
    UI ui;
    Client client;
    String name;

    public PlayerClient () {
        client = new Client();
        client.start();

        // For consistency, the classes to be sent over the network are
        // registered by the same method for both the client and server.
        Network.register(client);

        // ThreadedListener runs the listener methods on a different thread.
        client.addListener(new ThreadedListener(new Listener() {
            public void connected (Connection connection) {
            }

            public void received (Connection connection, Object object) {
                if (object instanceof RegistrationRequired) {
                    Register register = new Register();
                    register.name = name;
                    client.sendTCP(register);
                }

                if (object instanceof AddPlayer) {
                    AddPlayer msg = (AddPlayer)object;
                    ui.addPlayer(msg.player);
                }

                if (object instanceof UpdatePlayer) {
                    ui.updatePlayer((UpdatePlayer)object);
                }

                if (object instanceof RemovePlayer) {
                    RemovePlayer msg = (RemovePlayer)object;
                    ui.removePlayer(msg.id);
                }
            }

            public void disconnected (Connection connection) {
                System.exit(0);
            }
        }));

        ui = new UI();

        String host = ui.inputHost();
        try {
            client.connect(5000, host, Network.port);
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

        public String inputHost () {
            String input = (String)JOptionPane.showInputDialog(null, "Host:", "Connect to server", JOptionPane.QUESTION_MESSAGE,
                    null, null, "localhost");
            if (input == null || input.trim().length() == 0) System.exit(1);
            return input.trim();
        }

        public String inputName () {
            String input = (String)JOptionPane.showInputDialog(null, "Name:", "Connect to server", JOptionPane.QUESTION_MESSAGE,
                    null, null, "Test");
            if (input == null || input.trim().length() == 0) System.exit(1);
            return input.trim();
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
        new PlayerClient();
    }
}