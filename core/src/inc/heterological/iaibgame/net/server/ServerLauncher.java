package inc.heterological.iaibgame.net.server;

import com.esotericsoftware.minlog.Log;

public class ServerLauncher {
    public static void main(String[] args) {
        try {
            Log.set(Log.LEVEL_DEBUG);
            new GameServer();

        } catch (Exception e) {
            Log.error("Could not start server.");
            Log.error(e.getMessage());
        }
    }
}
