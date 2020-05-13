package inc.heterological.iaibgame.net.server;

import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.minlog.Log;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.ArenaButton;
import inc.heterological.iaibgame.net.shared.packets.EnemyEntity;
import inc.heterological.iaibgame.net.shared.packets.Play;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ServerLogic implements Disposable {
    public static Map<Integer, PlayerEntity> players = new ConcurrentHashMap<>();
    public static Map<Integer, EnemyEntity> enemies = new ConcurrentHashMap<>();
    public static ArenaButton.ARENA_BUTTON_STATE buttonState = ArenaButton.ARENA_BUTTON_STATE.UP;
    public static boolean buttonChanged = false;
    private OnlineArena onlineArena;
    private ServerLogicThread logicThread;

    public void loadArena() {
        if (onlineArena != null) {
            onlineArena.dispose();
        }
        onlineArena = new OnlineArena();
    }

    public OnlineArena getOnlineArena() {
        return onlineArena;
    }

    public ServerLogicThread getLogicThread() {
        return logicThread;
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
        for (PlayerEntity p : players.values()) {
            if (!plyrs.contains(p)) {
                logicThread.addPlayerToThread(p);
                onlineArena.addPlayer(p);
            }
            onlineArena.getPlayers().get(p.id).pos = p.pos;
            onlineArena.getPlayers().get(p.id).health = p.health;
        }

        for (PlayerEntity p : onlineArena.getPlayers().values()) {
            if (!players.containsValue(p)) {
                onlineArena.removePlayer(p.id);
            }
        }
    }

    public void updateEnemies(Map<Integer, EnemyEntity> ene, Play.EntitiesToBeRemoved entitiesRemoved) {
        for (EnemyEntity enemy : ene.values()) {
            enemy.pos = onlineArena.getEnemies().get(enemy.id).pos;
        }
        entitiesRemoved.enemies.addAll(OnlineArena.enemiesToRemove);
        enemies = onlineArena.getEnemies();
    }

    public void updateArenaButton(ArenaButton.ARENA_BUTTON_STATE state) {
        if (onlineArena.getArenaButtonState() != state) {
            Log.info(onlineArena.getArenaButtonState().toString());
            logicThread.setArenaButtonState(onlineArena.getArenaButtonState());
            buttonState = onlineArena.getArenaButtonState();
            buttonChanged = true;
        } else {
            buttonChanged = false;
        }
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
        private final Set<PlayerEntity> players;
        private final Map<Integer, EnemyEntity> enemies;
        private ArenaButton.ARENA_BUTTON_STATE arenaButtonState;

        public ServerLogicThread(Set<PlayerEntity> players, Map<Integer, EnemyEntity> enemies) {
            this.players = players;
            this.enemies = enemies;
            arenaButtonState = ArenaButton.ARENA_BUTTON_STATE.UP;
        }

        public void close() {
            running = false;
        }

        public void addPlayerToThread(PlayerEntity player) {
            players.add(player);
        }

        public void setArenaButtonState(ArenaButton.ARENA_BUTTON_STATE state) {
            arenaButtonState = state;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            while (running) {
                // runs 30 times a second
                float delta = (System.currentTimeMillis() - start) / 1000f;
                start = System.currentTimeMillis();

                onlineArena.update(delta);

                Play.EntitiesToBeRemoved entitiesRemoved = new Play.EntitiesToBeRemoved();

                // update arena button
                updateArenaButton(arenaButtonState);
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
