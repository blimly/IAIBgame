package inc.heterological.iaibgame.net.server;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.minlog.Log;
import inc.heterological.iaibgame.desktop.arena_objects.ArenaButton;
import inc.heterological.iaibgame.net.shared.packets.EnemyEntity;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OnlineArena implements Disposable {

    private ArenaButton arenaButton;
    private Set<Boolean> onButton;
    private Map<Integer, PlayerEntity> players;
    private Map<Integer, EnemyEntity> enemies;
    private int newEnemyId;
    private double stateTime;

    private Vector2 playerTarget;
    EnemyEntity entity;

    private Vector2 center;
    private static final int PLAYER_RADIUS = 20;
    private static final int ENEMY_PERCEPTION_RADIUS = 400;

    private float maxSpeed = 5f;
    private float maxForce = 0.20f;

    public OnlineArena() {
        newEnemyId = 0;
        enemies = new HashMap<>();
        for (int i = 0; i < 10; i++ ) {
            spawnEnemy(100 + i * 100, 800);
        }
        players = new HashMap<>();
    }

    private void spawnEnemy(float x, float y) {
        entity = new EnemyEntity();
        entity.pos = new Vector2(x, y);
        entity.vel = new Vector2(0, 1);
        entity.acc = Vector2.Zero;
        entity.target = new Vector2(5000, 5000);
        enemies.put(newEnemyId, entity);
        newEnemyId++;
    }

    public void update(float delta) {
        center = new Vector2(912, 912);
        for (EnemyEntity enemy : enemies.values()) {

            getNearestTarget(enemy);
            enemySeek(enemy, enemy.target);
            //Log.info(enemy.target.toString());
            collideWithWall(enemy);
            collideWithOtherEnemies(enemy);

            enemy.vel.add(enemy.acc);
            enemy.vel.clamp(0, maxSpeed);
            enemy.pos.add(enemy.vel);
            enemy.acc.scl(0, 0);
            //Log.info(enemy.toString());
            //collideWithPlayers(enemy);
        }
    }

    public void getNearestTarget(EnemyEntity entity) {
        entity.target = players.values().stream()
                .map(p -> p.pos)
                .filter(p -> p.dst(entity.pos) < ENEMY_PERCEPTION_RADIUS)
                .min(Comparator.comparing(p -> p.dst(entity.pos)))
                .orElse(center.cpy());
    }

    public void applyForce(EnemyEntity enemy, Vector2 force) {
        enemy.acc.add(force);
    }

    private void collideWithPlayers(EnemyEntity enemy) {
        for (PlayerEntity p : players.values()) {
            if (enemy.pos.dst(p.pos) < PLAYER_RADIUS) {
                Vector2 repulsion = new Vector2();
                repulsion.add(enemy.pos)
                        .sub(p.pos)
                        .setLength(PLAYER_RADIUS - enemy.pos.dst(p.pos));
                //enemy.vel.scl(0);
                enemy.vel.add(repulsion);
            }
        }
    }

    private void collideWithOtherEnemies(EnemyEntity enemy) {
        float desiredSeparation = PLAYER_RADIUS * 3f;
        int count = 0;
        Vector2 separation = Vector2.Zero;

        for (EnemyEntity other : enemies.values()) {
            float d = other.pos.dst(enemy.pos);
            if (d > 0 && d < desiredSeparation) {
                Vector2 diff = new Vector2(enemy.pos.cpy()).sub(other.pos);
                diff.nor();
                separation.add(diff);
                count++;
                Log.info("Enemies near. Dist: " + d);
            }
        }
        if (count > 0) {
            separation.scl(1f / count);
            separation.scl(maxSpeed);
            Vector2 steer = separation.sub(enemy.vel);
            steer.clamp(0, maxForce);
            enemy.acc.add(steer);
        }
    }

    private void collideWithWall(EnemyEntity enemy) {
        float distFromCenter = enemy.pos.dst(880, 912);
        if (distFromCenter > 512) {
            enemy.acc.add(new Vector2(912, 912)
                    .sub(enemy.pos)
                    .setLength(distFromCenter - 512));
        }
    }

    public void enemySeek(EnemyEntity enemy, Vector2 target) {
        float dist = enemy.pos.dst(target);
        Vector2 desired = target.cpy().sub(enemy.pos);
        desired.nor();

        if (dist < PLAYER_RADIUS) {
            // arrive
            //float m = dist / PLAYER_RADIUS * maxSpeed;
            //desired.scl(m);
            desired.scl(0);
        } else {
            // seek
            desired.scl(maxSpeed);
        }

        desired.sub(enemy.vel);
        desired.clamp(0, maxForce);
        enemy.acc.add(desired);
        //applyForce(enemy, desired);
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
