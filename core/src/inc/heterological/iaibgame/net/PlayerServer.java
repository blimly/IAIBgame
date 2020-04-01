package inc.heterological.iaibgame.net;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import inc.heterological.iaibgame.desktop.characters.Player;
import inc.heterological.iaibgame.net.Network.AddPlayer;
import inc.heterological.iaibgame.net.Network.Login;
import inc.heterological.iaibgame.net.Network.MovePlayer;
import inc.heterological.iaibgame.net.Network.Register;
import inc.heterological.iaibgame.net.Network.RegistrationRequired;
import inc.heterological.iaibgame.net.Network.RemovePlayer;
import inc.heterological.iaibgame.net.Network.UpdatePlayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PlayerServer {
    Server server;
    HashSet<Player> loggedIn = new HashSet();
    static Map<Integer, Player> players = new HashMap<Integer, Player>();

    public PlayerServer() throws IOException {
        server = new Server() {
            protected Connection newConnection() {
                return new PlayerConnection();
            }
        };

        Network.register(server.getKryo());
        server.addListener(new Listener() {
            public void received (Connection c, Object object) {
                // We know all connections for this server are actually CharacterConnections.
                PlayerConnection connection = (PlayerConnection)c;
                Player character = connection.player;

                if (object instanceof Login) {
                    // Ignore if already logged in.
                    if (character != null) return;

                    // Reject if the name is invalid.
                    String name = ((Login)object).name;
                    if (!isValid(name)) {
                        c.close();
                        return;
                    }

                    // Reject if already logged in.
                    for (Player other : loggedIn) {
                        if (other.username.equals(name)) {
                            c.close();
                            return;
                        }
                    }

                    character = loadPlayer(name);

                    // Reject if couldn't load character.
                    if (character == null) {
                        c.sendTCP(new RegistrationRequired());
                        return;
                    }

                    loggedIn(connection, character);
                    return;
                }

                if (object instanceof Register) {
                    // Ignore if already logged in.
                    if (character != null) return;

                    Register register = (Register)object;

                    // Reject if the login is invalid.
                    if (!isValid(register.name)) {
                        c.close();
                        return;
                    }
                    if (!isValid(register.otherStuff)) {
                        c.close();
                        return;
                    }

                    // Reject if character alread exists.
                    if (loadPlayer(register.name) != null) {
                        c.close();
                        return;
                    }

                    character = new Player();
                    character.username = register.name;
                    character.bounds.x = 0;
                    character.bounds.y = 0;
                    if (!savePlayer(character)) {
                        c.close();
                        return;
                    }

                    loggedIn(connection, character);
                    return;
                }

                if (object instanceof MovePlayer) {
                    // Ignore if not logged in.
                    if (character == null) return;

                    MovePlayer msg = (MovePlayer) object;

                    // Ignore if invalid move.
                    if (Math.abs(msg.x) != 1 && Math.abs(msg.y) != 1) return;

                    character.bounds.x += msg.x;
                    character.bounds.y += msg.y;
                    if (!savePlayer(character)) {
                        connection.close();
                        return;
                    }

                    UpdatePlayer update = new UpdatePlayer();
                    update.id = character.id;
                    update.x = (int) character.bounds.x;
                    update.y = (int) character.bounds.y;
                    server.sendToAllTCP(update);
                    return;
                }
            }

            private boolean isValid (String value) {
                if (value == null) return false;
                value = value.trim();
                if (value.length() == 0) return false;
                return true;
            }

            public void disconnected (Connection c) {
                PlayerConnection connection = (PlayerConnection)c;
                if (connection.player != null) {
                    loggedIn.remove(connection.player);

                    RemovePlayer removeCharacter = new RemovePlayer();
                    removeCharacter.id = connection.player.id;
                    server.sendToAllTCP(removeCharacter);
                }
            }
        });

        server.bind(Network.TCPport, Network.UDPport);
        server.start();
    }

    void loggedIn(PlayerConnection c, Player player) {
        c.player = player;

        for (Player other : loggedIn) {
            AddPlayer addPlayer = new AddPlayer();
            addPlayer.player = other;
            c.sendTCP(addPlayer);
        }

        loggedIn.add(player);

        AddPlayer addPlayer = new AddPlayer();
        addPlayer.player = player;
        server.sendToAllTCP(addPlayer);
    }

    boolean savePlayer (Player player) {
        File file = new File("players", player.username.toLowerCase());
        file.getParentFile().mkdirs();

        if (player.id == 0) {
            String[] children = file.getParentFile().list();
            if (children == null) return false;
            player.id = children.length + 1;
        }

        try (DataOutputStream output = new DataOutputStream(new FileOutputStream(file))) {
            output.writeInt(player.id);
            output.writeInt((int) player.bounds.x);
            output.writeInt((int) player.bounds.y);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    Player loadPlayer(String name) {
        File file = new File("characters", name.toLowerCase());
        if (!file.exists()) return null;
        try (DataInputStream input = new DataInputStream(new FileInputStream(file))) {
            Player player = new Player();
            player.id = input.readInt();
            player.username = name;
            player.bounds.x = input.readInt();
            player.bounds.y = input.readInt();
            input.close();
            return player;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    static class PlayerConnection extends Connection {
        public Player player;
    }

    public static void main(String[] args) throws IOException {
        Log.set(Log.LEVEL_DEBUG);
        new PlayerServer();
    }
}
