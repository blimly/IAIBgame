package inc.heterological.iaibgame.net.server;

import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.minlog.Log;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.net.shared.packets.EnemyEntity;
import inc.heterological.iaibgame.net.shared.packets.Play;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ServerLogic implements Disposable {
    private OnlineArena onlineArena;
    private ServerLogicThread logicThread;

    public static Map<Integer, PlayerEntity> players = new HashMap<>();
    public static Map<Integer, EnemyEntity> enemies = new HashMap<>();

    public void loadArena() {
        if (onlineArena != null) {
            onlineArena.dispose();
        }
        onlineArena = new OnlineArena();
    }

    public void run(Set<PlayerEntity> players) {
        if (logicThread != null) logicThread.close();
        logicThread = new ServerLogicThread(players, enemies);
        logicThread.start();
    }

    public void addPlayer(PlayerEntity player) {
        onlineArena.addPlayer(player);
        players.put(player.id, player);
    }

    public void updatePlayers(Set<PlayerEntity> plyrs, Play.EntitiesToBeRemoved entitiesRemoved) {
        for (PlayerEntity player : plyrs) {
            if (onlineArena.getPlayers().containsValue(player)) {
                onlineArena.getPlayers().get(player.id).pos = player.pos;
            } else if (!players.containsValue(player)) {
                onlineArena.removePlayer(player.id);
            } else {
                addPlayer(player);
            }
            players.get(player.id).pos = player.pos;
        }
    }

    public void updateEnemies(Map<Integer, EnemyEntity>  ene, Play.EntitiesToBeRemoved entitiesRemoved) {
        for (EnemyEntity enemy : ene.values()) {
            enemy.pos = onlineArena.getEnemies().get(enemy.id).pos;
        }
        enemies = onlineArena.getEnemies();
        Log.info(String.valueOf(enemies.get(0).pos.x));
    }

    public Map<Integer, EnemyEntity> getEnemies() {
        return enemies;
    }

    @Override
    public void dispose() {
        if (onlineArena != null) onlineArena.dispose();
        if (logicThread != null) logicThread.close();
        System.gc();
    }

    public class ServerLogicThread extends Thread {
        private boolean running = true;
        private Set<PlayerEntity> players;
        private Map<Integer, EnemyEntity> enemies;

        public ServerLogicThread(Set<PlayerEntity> players, Map<Integer, EnemyEntity>  enemies) {
            this.players = players;
            this.enemies = enemies;
        }

        public void close() { running = false; }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            while (running) {
                // runs 30 times a second
                float delta = (System.currentTimeMillis() - start) / 1000f;
                start = System.currentTimeMillis();

                onlineArena.update(delta);

                Play.EntitiesToBeRemoved entitiesRemoved = new Play.EntitiesToBeRemoved();

                // update player locations in server
                updatePlayers(players, entitiesRemoved);
                // update the enemy AI in server
                updateEnemies(enemies, entitiesRemoved);

                Main.server.updateArena(entitiesRemoved);

                try {
                    Thread.sleep(33);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
