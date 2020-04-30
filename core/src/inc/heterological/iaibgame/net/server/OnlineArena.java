package inc.heterological.iaibgame.net.server;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.minlog.Log;
import inc.heterological.iaibgame.desktop.arena_objects.ArenaButton;
import inc.heterological.iaibgame.net.shared.packets.EnemyEntity;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OnlineArena implements Disposable {

    private ArenaButton arenaButton;
    private Set<Boolean> onButton;
    private Map<Integer, PlayerEntity> players;
    private Map<Integer, EnemyEntity> enemies;
    private int newPlayerId;
    private double stateTime;

    private Vector2 playerTarget;
    EnemyEntity entity;

    private float maxSpeed = 800f;
    private float maxForce = 0.10f;

    public OnlineArena() {
        enemies = new HashMap<>();
        //Enemy enemy = new Enemy(Vector2.Zero, 10, 100);
        entity = new EnemyEntity();
        entity.pos = new Vector2(800, 800);
        entity.vel = new Vector2(0, 2);
        entity.acc = Vector2.Zero;
        enemies.put(0, entity);
        players = new HashMap<>();
        playerTarget = new Vector2(512, 512);
    }

    public void update(float delta) {
        stateTime += delta;
        // should  update enemy locations
        //Log.info(players.toString());
        if (players.size() > 0) {
            playerTarget.set(getNearestTarget());
        } else {
            playerTarget.set(512, 512);
        }


        for (EnemyEntity enemy : enemies.values()) {
            enemy.vel.add(enemy.acc);
            enemy.vel.limit(maxSpeed);
            enemy.pos.add(enemy.vel);
            enemy.acc.scl(0, 0);
            //enemy.pos.add(enemySeek(enemy, playerTarget));
            enemySeek(enemy, playerTarget);
            collideWithWall(enemy.pos);
            Log.info(String.valueOf(enemy));
        }
    }

    public Vector2 getNearestTarget() {
        Vector2 nearest = new Vector2(5000, 5000);
        for (PlayerEntity p : players.values()) {
            if (entity.pos.dst(p.pos) < entity.pos.dst(nearest)) {
                nearest = p.pos;
            }
        }
        return nearest;
    }

    public void applyForce(EnemyEntity enemy, Vector2 force) {
        enemy.acc.add(force);
    }

    private void collideWithWall(Vector2 position) {
        if (position.dst(480, 512) > 512) {
            position.add(new Vector2(512, 512).sub(position).setLength(5));
        }
    }

    public void enemySeek(EnemyEntity enemy, Vector2 target) {
        Vector2 desired = target.sub(enemy.pos);
        desired.nor();
        desired.scl(4); // maxspeed 4
        desired.sub(enemy.vel);
        desired.limit(maxForce);
        applyForce(enemy, desired);
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
