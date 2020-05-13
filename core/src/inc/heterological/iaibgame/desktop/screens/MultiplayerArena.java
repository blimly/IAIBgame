package inc.heterological.iaibgame.desktop.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.ArenaButton;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.Healthbar;
import inc.heterological.iaibgame.desktop.characters.Enemy;
import inc.heterological.iaibgame.desktop.characters.Player;
import inc.heterological.iaibgame.desktop.managers.GameKeys;
import inc.heterological.iaibgame.desktop.managers.GameStateManager;
import inc.heterological.iaibgame.desktop.managers.SoundEffects;
import inc.heterological.iaibgame.net.client.GameClient;
import inc.heterological.iaibgame.net.shared.packets.EnemyEntity;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;
import inc.heterological.iaibgame.net.shared.packets.RemovePlayer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MultiplayerArena extends GameState {

    static GameClient gameClient;
    public static Map<Integer, PlayerEntity> players = new ConcurrentHashMap<>();
    public static Map<Integer, EnemyEntity> enemies = new ConcurrentHashMap<>();
    public static ArenaButton.ARENA_BUTTON_STATE onlineButtonState = ArenaButton.ARENA_BUTTON_STATE.UP;

    private boolean gameEnded = false;

    private ArenaButton arenaButton;
    static Player player = new Player();
    private final Enemy enemy = new Enemy(Vector2.Zero);
    OrthographicCamera camera;
    float stateTime;
    float delta;

    public MultiplayerArena(GameStateManager gsm) {
        super(gsm);
        init();
        show();
        SoundEffects.loopMusic("BattleMusic");
        SoundEffects.playMusic("BattleMusic");
    }

    public void show() {
        gameClient.connect();
    }

    public void update() {

        delta = Gdx.graphics.getDeltaTime();
        stateTime += delta;

        if (player.health > 0) {
            if (GameKeys.isDown(GameKeys.LEFT) && GameKeys.isDown(GameKeys.RIGHT)) {
                player.stand();
            } else if (GameKeys.isPressed(GameKeys.KICK)) {
                player.kick();
            } else if (GameKeys.isPressed(GameKeys.JAB)) {
                player.jab();
            } else if (GameKeys.isDown(GameKeys.LEFT)) {
                player.moveLeft(delta);
            } else if (GameKeys.isDown(GameKeys.RIGHT)) {
                player.moveRight(delta);
            } else {
                player.stand();
            }
            if (GameKeys.isDown(GameKeys.UP)) {
                player.moveUp(delta);
            }

            if (GameKeys.isDown(GameKeys.DOWN)) {
                player.moveDown(delta);
            }

            player.updatePlayerPhysics();
        } else {
            gameEnded = true;
        }

        if (gameEnded) {
            zoomOutCamera(delta);
        } else {
            camera.zoom = 1f;
            camera.position.lerp(new Vector3(player.position.x + player.width / 2f, player.position.y + player.height / 2f, 0), delta);
        }

        for (EnemyEntity onlineEnemy : enemies.values()) {
            float distToEnemy = onlineEnemy.pos.dst(player.position);
            boolean playerTookDamage = false;
            if (onlineEnemy.attacking && distToEnemy < 40) {
                player.health -= (40 - distToEnemy) / 100;
                playerTookDamage = true;
                SoundEffects.play("PlayerGettingAttacked");
            }
            if (onlineEnemy.type == Enemy.ENEMY_TYPE.HEALER_HEALING) {
                if (!SoundEffects.getMusicToPlay("HealerHealingEnemies").isPlaying()) {
                    SoundEffects.getMusicToPlay("HealerHealingEnemies").play();
                }
            } else if (onlineEnemy.type == Enemy.ENEMY_TYPE.HEALER_WALKING){
                SoundEffects.getMusicToPlay("HealerHealingEnemies").stop();
            }
            if (onlineEnemy.attacking && playerTookDamage) {
                if (!SoundEffects.getMusicToPlay("Huwawa").isPlaying()) {
                    SoundEffects.getMusicToPlay("Huwawa").play();
                }
            }
        }

        // move on server
        PlayerEntity packet = new PlayerEntity();
        packet.pos = player.position;
        packet.facingRight = player.facingRight;
        packet.currentState = player.currentState;
        packet.health = player.health;
        gameClient.client.sendUDP(packet);
        player.onlineBounds.setPosition(player.position);

    }

    private void zoomOutCamera(float delta) {
        camera.position.lerp(new Vector3(912, 912, 0), delta / 4f);
        if (camera.zoom < 2.5f) {
            camera.zoom += delta / 5f;
        }
    }

    @Override
    public void init() {
        arenaButton = new ArenaButton(848, 870, ArenaButton.ARENA_BUTTON_STATE.UP);
        gameEnded = false;
        stateTime = 0f;
        gameClient = new GameClient();
        camera = Main.camera;
        Main.camera.position.set(player.position, 0f);
        Main.camera.zoom = 1f;
        player = new Player();
        gameEnded = false;
        players.clear();
        enemies.clear();
    }

    @Override
    public void update(float dt) {
        update();
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        Main.batch.setProjectionMatrix(camera.combined);
        Main.batch.begin();
        update();

        Main.batch.draw(Assets.mpArenaTex, 0, 0, 1824, 1824);

        arenaButton.draw(Main.batch, onlineButtonState);

        // draw online players
        for (PlayerEntity onlinePlayer : players.values()) {
            if (onlinePlayer.health > 0) {
                Healthbar.drawHealth(Main.batch, onlinePlayer.pos, onlinePlayer.health);
                if (onlinePlayer.facingRight) {
                    Main.batch.draw(Player.getFrameBasedUponCondition(onlinePlayer.currentState, stateTime), onlinePlayer.pos.x, onlinePlayer.pos.y, 64, 64);
                } else {
                    Main.batch.draw(Player.getFrameBasedUponCondition(onlinePlayer.currentState, stateTime), onlinePlayer.pos.x + 64, onlinePlayer.pos.y, -64, 64);
                }
            }
        }

        // draw enemies on server
        for (EnemyEntity onlineEnemy : enemies.values()) {
            if (onlineEnemy.health > 0) {
                Healthbar.drawHealth(Main.batch, onlineEnemy.pos, onlineEnemy.health);
                if (onlineEnemy.gettingHealed) {
                    Main.batch.draw(enemy.getHealingParticles(stateTime), onlineEnemy.pos.x, onlineEnemy.pos.y + 20, 64, 64);
                }
                Main.batch.draw(enemy.getCurrentFrame(stateTime, onlineEnemy.type), onlineEnemy.pos.x, onlineEnemy.pos.y, 64, 64);
            }
        }

        // draw myself
        if (player.health > 0) {
            Healthbar.drawHealth(Main.batch, player.position, player.health);
            if (player.facingRight) {
                Main.batch.draw(player.getCurrentFrame(stateTime, delta), player.position.x, player.position.y, player.width, player.height);
            } else {
                Main.batch.draw(player.getCurrentFrame(stateTime, delta), player.position.x + player.width, player.position.y, -player.width, player.height);
            }
        } else {
            Assets.bigFont.draw(Main.batch, "GAME OVER", 600, 1200);
            Assets.bigFont.draw(Main.batch, "YOU ARE NOT INFO BOY", 300, 600);
        }

        if (enemies.size() == 0 && player.health > 0 && onlineButtonState == ArenaButton.ARENA_BUTTON_STATE.DOWN) {
            gameEnded = true;
            Assets.bigFont.draw(Main.batch, "WELL DONE", 600, 1300);
            Assets.bigFont.draw(Main.batch, "YOU ARE", 700, 800);
            Assets.hugeFont.draw(Main.batch, "INFO BOY", 400, 600);

        } else {
            gameEnded = false;
        }

        Main.batch.end();
        handleInput();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            SoundEffects.stopAll();
            SoundEffects.stopAllMusic();
            RemovePlayer removePlayer = new RemovePlayer();
            removePlayer.playerID = gameClient.client.getID();
            gameClient.client.sendUDP(removePlayer);
            gameClient.client.close();
            players.clear();
            enemies.clear();
            stateManager.setGameState(GameStateManager.MENU);
        }
    }

    @Override
    public void dispose() {

    }
}
