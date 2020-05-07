package inc.heterological.iaibgame.desktop.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.arena_objects.ArenaButton;
import inc.heterological.iaibgame.desktop.characters.Enemy;
import inc.heterological.iaibgame.desktop.characters.Player;
import inc.heterological.iaibgame.desktop.managers.GameKeys;
import inc.heterological.iaibgame.desktop.managers.GameStateManager;
import inc.heterological.iaibgame.net.client.GameClient;
import inc.heterological.iaibgame.net.shared.packets.EnemyEntity;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;
import inc.heterological.iaibgame.net.shared.packets.RemovePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MultiplayerArena extends GameState{

    OrthographicCamera camera;
    float stateTime;
    float delta;
    // online stuff
    static Player player = new Player();
    static GameClient gameClient;
    public static Map<Integer, PlayerEntity> players = new HashMap<>();
    public static Map<Integer, EnemyEntity> enemies = new HashMap<>(); // enemie positions on server


    private ArenaButton arenaButton;
    private Set<Boolean> onButton;
    private final Enemy dummyEnemy = new Enemy(Vector2.Zero, 10, 100);

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

        // move on server
        //if (player.onlineBounds.x != player.position.x || player.onlineBounds.y != player.position.y) {
            PlayerEntity packet = new PlayerEntity();
            packet.pos = player.position;
            packet.facingRight = player.facingRight;
            packet.currentState = player.currentState;
            gameClient.client.sendUDP(packet);
            player.onlineBounds.setPosition(player.position);
        //}

    }

    @Override
    public void init() {
        stateTime = 0f;
        gameClient = new GameClient();
        camera = Main.camera;
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
        //updateOnButtons();
        //arenaButton.draw(batch, 480, 480, onButton);

        // draw online players
        for (PlayerEntity onlinePlayer : players.values()) {
            String posString = (int) onlinePlayer.pos.x + "  " + (int) onlinePlayer.pos.y + "";
            Assets.font.draw(Main.batch, posString, onlinePlayer.pos.x, onlinePlayer.pos.y + 80);
            System.out.println(onlinePlayer.currentState);
            if (onlinePlayer.facingRight) {
                Main.batch.draw(Player.getFrameBasedUponCondition(onlinePlayer.currentState, stateTime), onlinePlayer.pos.x, onlinePlayer.pos.y, 64, 64);
            } else {
                Main.batch.draw(Player.getFrameBasedUponCondition(onlinePlayer.currentState, stateTime), onlinePlayer.pos.x + 64, onlinePlayer.pos.y, -64, 64);
            }
        }

        // draw enemies on server
        for (EnemyEntity onlineEnemy : enemies.values()) {
            String posString = (int) onlineEnemy.pos.x + "  " + (int) onlineEnemy.pos.y + "";
            Assets.font.draw(Main.batch, posString, onlineEnemy.pos.x, onlineEnemy.pos.y + 80);
            Main.batch.draw(dummyEnemy.getCurrentFrame(stateTime), onlineEnemy.pos.x, onlineEnemy.pos.y, 64, 64);
        }

        // draw myself
        //Assets.font.draw(Main.batch, player.currentState.toString(), player.position.x, player.position.y + 70);

        if (player.facingRight) {
            Main.batch.draw(player.getCurrentFrame(stateTime, delta), player.position.x, player.position.y , player.width, player.height);
        } else {
            Main.batch.draw(player.getCurrentFrame(stateTime, delta), player.position.x+player.width, player.position.y , -player.width, player.height);
        }
        //System.out.println(players.toString());

        //onButton.clear();
        Main.batch.end();
        handleInput();
    }

    private void updateOnButtons() {
        onButton.add(arenaButton.playerOnButton((int) player.position.x, (int) player.position.y));
        for (PlayerEntity onlinePlayer : players.values()) {
            onButton.add(arenaButton.playerOnButton((int)onlinePlayer.pos.x, (int)onlinePlayer.pos.y));
        }
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
