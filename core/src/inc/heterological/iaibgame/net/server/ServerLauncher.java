package inc.heterological.iaibgame.net.server;

import com.esotericsoftware.minlog.Log;

import java.io.IOException;

public class ServerLauncher {
    public static void main(String[] args) {
        try {
            //Log.set(Log.LEVEL_DEBUG);
            Log.set(Log.LEVEL_WARN);
            new GameServer();
        } catch (IOException e) {
            Log.error("Could not start server.");
            Log.error(e.getMessage());
        }
    }
}
