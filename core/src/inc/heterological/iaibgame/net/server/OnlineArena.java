package inc.heterological.iaibgame.net.server;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import inc.heterological.iaibgame.desktop.arena_objects.ArenaButton;
import inc.heterological.iaibgame.desktop.characters.Enemy;
import inc.heterological.iaibgame.net.shared.packets.EnemyEntity;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class OnlineArena implements Disposable {

    private ArenaButton arenaButton;
    private Set<Boolean> onButton;
    private Map<Integer, PlayerEntity> players;
    private Map<Integer, EnemyEntity> enemies;
    private int newPlayerId;
    private double stateTime;


    public OnlineArena() {
        enemies = new HashMap<>();
        Enemy enemy = new Enemy(Vector2.Zero, 10, 100);
        EnemyEntity entity = new EnemyEntity();
        entity.pos = Vector2.Zero;
        enemies.put(0, entity);
        players = new HashMap<>();
    }

    public void update(float delta) {
        stateTime += delta;
        // should  update enemy locations
        for (EnemyEntity enemy : enemies.values()) {
            //enemy.updatePosition()

            enemy.pos.x = 480 + 100 * (float) cos(stateTime);
            enemy.pos.y = 480 + 100 * (float) sin(stateTime);

        }
    }

    public void addEnemy(int enemyId) {
        //enemies.put(enemyId, new Enemy(Vector2.Zero, 10, 100));
    }

    public Map<Integer, PlayerEntity> getPlayers() {
        return players;
    }

    public Map<Integer, EnemyEntity> getEnemies() {
        return enemies;
    }

    public void addPlayer(PlayerEntity player) {
        players.put(player.id, player);
    }

    public boolean removePlayer(int playerId) {
        if (players.containsKey(playerId)) {
            players.remove(playerId);
            return true;
        }
        return false;
    }

    @Override
    public void dispose() {

    }
}
