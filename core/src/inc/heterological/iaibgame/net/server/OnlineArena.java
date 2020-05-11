package inc.heterological.iaibgame.net.server;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import inc.heterological.iaibgame.desktop.ArenaButton;
import inc.heterological.iaibgame.desktop.characters.Enemy;
import inc.heterological.iaibgame.desktop.characters.Player;
import inc.heterological.iaibgame.net.shared.packets.EnemyEntity;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class OnlineArena implements Disposable {

    private ArenaButton arenaButton;
    private Map<Integer, PlayerEntity> players;
    private Map<Integer, EnemyEntity> enemies;
    private int newEnemyId;
    private boolean gameStarted;

    private Vector2 playerTarget;
    EnemyEntity entity;

    public static Set<Integer> enemiesToRemove = new HashSet<>();

    private Vector2 center;
    private static final int PLAYER_RADIUS = 20;
    private static final int ENEMY_PERCEPTION_RADIUS = 400;

    private float maxSpeed = 5f;
    private float maxForce = 0.20f;

    public OnlineArena() {
        gameStarted = false;
        newEnemyId = 0;
        enemies = new ConcurrentHashMap<>();
        players = new ConcurrentHashMap<>();
        arenaButton = new ArenaButton(848, 870, ArenaButton.ARENA_BUTTON_STATE.UP);
    }

    private void spawnEnemies(float x, float y, int count) {
        for (int i = 0; i < count; i++) {
            entity = new EnemyEntity();
            entity.health = 100;
            entity.pos = new Vector2(x, y);
            entity.vel = new Vector2(0, 1);
            entity.acc = Vector2.Zero;
            entity.target = new Vector2(5000, 5000);
            entity.attackTimer = 0;
            entity.attacking = false;
            entity.type = (i < 1) ? Enemy.ENEMY_TYPE.ZOMBIE : Enemy.ENEMY_TYPE.BOB_RUNNING;
            entity.id = newEnemyId;
            enemies.put(newEnemyId, entity);
            newEnemyId++;
        }
    }

    public void update(float delta) {
        center = new Vector2(912, 912);

        if (!gameStarted) {
            updateArenaButtonState();
        } else {
            for (EnemyEntity enemy : enemies.values()) {
                getNearestTarget(enemy);
                enemySeek(enemy, enemy.target);
                collideWithWall(enemy);
                collideWithOtherEnemies(enemy);
                getHit(enemy);
                attackTarget(enemy);
                updateState(enemy);
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
    }

    public void updateState(EnemyEntity enemy) {
        if (enemy.type == Enemy.ENEMY_TYPE.BOB_RUNNING && enemy.health < 50) {
            enemy.type = Enemy.ENEMY_TYPE.BOB_FLEEING;
        }
    }

    public void updateArenaButtonState() {
        Set<Vector2> playerPositions = players.values().stream()
                .map(p -> p.pos)
                .collect(Collectors.toSet());

        if (playerPositions.stream().anyMatch(ArenaButton::playerOnButton)) {
            arenaButton.state = ArenaButton.ARENA_BUTTON_STATE.READY;
        } else {
            arenaButton.state = ArenaButton.ARENA_BUTTON_STATE.UP;
        }

        if (playerPositions.size() > 0 && playerPositions.stream().allMatch(ArenaButton::playerOnButton)) {
            arenaButton.state = ArenaButton.ARENA_BUTTON_STATE.DOWN;
            spawnEnemies(912, 1200, 5);
            gameStarted = true;
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
                        enemy.vel.scl(-1);
                        enemy.acc.add(diff);
                    }
                    if (!player.facingRight && diff.angle() > (180 - kickAngle) && diff.angle() < (180 + kickAngle)) {
                        diff.setLength(hitRadius - d);
                        enemy.health -= diff.len() / 10;
                        diff.scl(20f);
                        enemy.vel.scl(-1);

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
                .filter(p -> p.health > 0)
                .map(p -> p.pos)
                .filter(p -> p.dst(entity.pos) < ENEMY_PERCEPTION_RADIUS)
                .min(Comparator.comparing(p -> p.dst(entity.pos)))
                .orElse(center.cpy());
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

        if (enemy.type != Enemy.ENEMY_TYPE.BOB_FLEEING) {
            if (dist < PLAYER_RADIUS) {
                desired.scl(0);
            } else {
                desired.scl(maxSpeed);
            }
            desired.sub(enemy.vel);
            desired.clamp(0, maxForce);

        } else {
            desired.add(enemy.vel);
        }


        enemy.acc.add(desired);
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

    public ArenaButton.ARENA_BUTTON_STATE getArenaButtonState() {
        return arenaButton.state;
    }

    @Override
    public void dispose() {

    }
}
