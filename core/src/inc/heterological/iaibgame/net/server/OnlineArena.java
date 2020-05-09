package inc.heterological.iaibgame.net.server;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import inc.heterological.iaibgame.desktop.arena_objects.ArenaButton;
import inc.heterological.iaibgame.desktop.characters.Player;
import inc.heterological.iaibgame.net.shared.packets.EnemyEntity;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class OnlineArena implements Disposable {

    private ArenaButton arenaButton;
    private Set<Boolean> onButton;
    private Map<Integer, PlayerEntity> players;
    private Map<Integer, EnemyEntity> enemies;
    private int newEnemyId;
    private double stateTime;

    private Vector2 playerTarget;
    EnemyEntity entity;

    public static Set<Integer> enemiesToRemove = new HashSet<>();

    private Vector2 center;
    private static final int PLAYER_RADIUS = 20;
    private static final int ENEMY_PERCEPTION_RADIUS = 400;

    private float maxSpeed = 5f;
    private float maxForce = 0.20f;

    public OnlineArena() {
        newEnemyId = 0;
        enemies = new ConcurrentHashMap<>();
        for (int i = 0; i < 1; i++ ) {
            spawnEnemy(100 + i * 2, 800);
        }
        players = new ConcurrentHashMap<>();
    }

    private void spawnEnemy(float x, float y) {
        entity = new EnemyEntity();
        entity.health = 100;
        entity.pos = new Vector2(x, y);
        entity.vel = new Vector2(0, 1);
        entity.acc = Vector2.Zero;
        entity.target = new Vector2(5000, 5000);
        entity.attackTimer = 0;
        entity.attacking = false;
        enemies.put(newEnemyId, entity);
        newEnemyId++;
    }

    public void update(float delta) {
        center = new Vector2(912, 912);
        for (EnemyEntity enemy : enemies.values()) {
            getNearestTarget(enemy);
            enemySeek(enemy, enemy.target);
            collideWithWall(enemy);
            collideWithOtherEnemies(enemy);
            getHit(enemy);
            attackTarget(enemy);
            if (enemy.health <= 0) {
                enemies.remove(enemy.id);
                continue;
            }

            enemy.vel.add(enemy.acc);
            enemy.vel.clamp(0, maxSpeed);
            enemy.pos.add(enemy.vel);
            enemy.acc.scl(0, 0);
        }
    }

    public void killIfDead(EnemyEntity enemy) {
        if (enemy.health <= 0) {
            enemies.remove(enemy.id);
        }
    }

    public void attackTarget(EnemyEntity enemy) {
        float d = enemy.pos.dst(enemy.target);
        if (d > 0 && d < PLAYER_RADIUS * 2) {
            if (enemy.attackTimer < 10) {
                enemy.attackTimer++;
                enemy.attacking = false;
            } else {
                enemy.attackTimer = 0;
                enemy.attacking = true;
            }
        } else {
            enemy.attacking = false;
        }
    }

    public void getHit(EnemyEntity enemy) {
        float hitRadius = PLAYER_RADIUS * 3f;
        int kickAngle = 140 / 2; // 140 degrees in front of player
        int jabAngle = 90 / 2;

        for (PlayerEntity player : players.values()) {
            float d = player.pos.dst(enemy.pos);

            if (d > 0 && d < hitRadius) {
                Vector2 diff = new Vector2(enemy.pos.cpy()).sub(player.pos);
                if (player.currentState == Player.Condition.KICK) {
                    if (player.facingRight && (diff.angle() < kickAngle || diff.angle() > (360 - kickAngle))) {
                        diff.setLength(hitRadius - d);
                        enemy.health -= diff.len() / 10;
                        diff.scl(20f);
                        enemy.acc.add(diff);
                    }
                    if (!player.facingRight && diff.angle() > (180 - kickAngle) && diff.angle() < (180 + kickAngle)) {
                        diff.setLength(hitRadius - d);
                        enemy.health -= diff.len() / 10;
                        diff.scl(20f);
                        enemy.acc.add(diff);
                    }
                }
                if (player.currentState == Player.Condition.JAB) {
                    if (player.facingRight && (diff.angle() < jabAngle || diff.angle() > (360 - jabAngle))) {
                        diff.setLength(hitRadius - d);
                        enemy.health -= diff.len() / 5;
                        diff.scl(10f);
                        enemy.acc.add(diff);
                    }
                    if (!player.facingRight && diff.angle() > (180 - jabAngle) && diff.angle() < (180 + jabAngle)) {
                        diff.setLength(hitRadius - d);
                        enemy.health -= diff.len() / 5;
                        diff.scl(10f);
                        enemy.acc.add(diff);
                    }
                }

            }
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
