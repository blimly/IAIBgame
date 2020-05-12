package inc.heterological.iaibgame.desktop.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import inc.heterological.iaibgame.net.client.GameClient;
import inc.heterological.iaibgame.net.shared.packets.EnemyEntity;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;
import inc.heterological.iaibgame.net.shared.packets.RemovePlayer;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MultiplayerArena extends GameState {

    public static Map<Integer, PlayerEntity> players = new ConcurrentHashMap<>();
    public static Map<Integer, EnemyEntity> enemies = new ConcurrentHashMap<>(); // enemie positions on server
    public static ArenaButton.ARENA_BUTTON_STATE onlineButtonState = ArenaButton.ARENA_BUTTON_STATE.UP;
    // online stuff
    static Player player = new Player();
    static GameClient gameClient;
    private final Enemy enemy = new Enemy(Vector2.Zero);
    OrthographicCamera camera;
    float stateTime;
    float delta;
    private ArenaButton arenaButton;
    private Set<Boolean> onButton;

    public MultiplayerArena(GameStateManager gsm) {
        super(gsm);
        init();
        show();
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

            camera.position.lerp(new Vector3(player.position.x + player.width / 2f, player.position.y + player.height / 2f, 0), delta);
        } else {
            camera.position.lerp(new Vector3(912, 912, 0), delta);
            if (camera.zoom < 2.5f) {
                camera.zoom += delta / 10f;
            }
        }


        for (EnemyEntity onlineEnemy : enemies.values()) {
            float distToEnemy = onlineEnemy.pos.dst(player.position);
            if (onlineEnemy.attacking && distToEnemy < 40) {
                player.health -= (40 - distToEnemy) / 100;
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

    @Override
    public void init() {
        arenaButton = new ArenaButton(848, 870, ArenaButton.ARENA_BUTTON_STATE.UP);
        onlineButtonState = ArenaButton.ARENA_BUTTON_STATE.UP;
        stateTime = 0f;
        gameClient = new GameClient();
        camera = Main.camera;
        Main.camera.position.set(player.position, 0f);
        Main.camera.zoom = 1f;
        player = new Player();
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
            Assets.font.draw(Main.batch, "GAME OVER", 900, 1000);
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
