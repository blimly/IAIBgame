package inc.heterological.iaibgame.desktop.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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


import java.util.*;

public class MultiplayerArena extends GameState{

    OrthographicCamera camera;
    SpriteBatch batch;
    float stateTime;
    // online stuff
    static Player player = new Player();
    static GameClient gameClient;
    public static Map<Integer, PlayerEntity> players = new HashMap<>();
    public static Map<Integer, EnemyEntity> enemies = new HashMap<>(); // enemie positions on server


    private ArenaButton arenaButton;
    private Set<Boolean> onButton;
    private Enemy dummyEnemy = new Enemy(Vector2.Zero, 10, 100);

    public MultiplayerArena(GameStateManager gsm) {
        super(gsm);
        init();
        show();
    }

    public void show() {
        gameClient.connect();
    }

    public void update() {
        double delta = Gdx.graphics.getDeltaTime();

        if (GameKeys.isDown(GameKeys.LEFT) && GameKeys.isDown(GameKeys.RIGHT)) {
            player.stand();
        } else if (GameKeys.isDown(GameKeys.LEFT)) {
            player.moveLeft(delta);
        } else if (GameKeys.isDown(GameKeys.RIGHT)) {
            player.moveRight(delta);
        } else if (GameKeys.isPressed(GameKeys.KICK)) {
            player.kick();
        } else if (GameKeys.isPressed(GameKeys.JAB) || !player.hasJabbed(stateTime)) {
            player.jab();
        } else {
            player.stand();
        }
        camera.position.lerp(new Vector3(player.position.x + player.width / 2, player.position.y + player.height / 2, 0), (float) delta);

        if (GameKeys.isDown(GameKeys.UP)) {
            player.moveUp(delta);
        }

        if (GameKeys.isDown(GameKeys.DOWN)) {
            player.moveDown(delta);
        }
        camera.position.lerp(new Vector3(player.position.x + player.width / 2f, player.position.y + player.height / 2f, 0), (float) delta);

        // move on server
        if (player.onlineBounds.x != player.position.x || player.onlineBounds.y != player.position.y) {
            PlayerEntity packet = new PlayerEntity();
            packet.pos = player.position;
            gameClient.client.sendUDP(packet);
            player.onlineBounds.setPosition(player.position);
        }
    }

    @Override
    public void init() {
        stateTime = 0f;
        gameClient = new GameClient();
        batch = new SpriteBatch();
        camera = Main.camera;
    }

    @Override
    public void update(float dt) {
        update();
    }

    @Override
    public void draw() {
        stateTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        update();

        batch.draw(Assets.mpArenaTex, 0, 0, 1024, 1024);
        //updateOnButtons();
        //arenaButton.draw(batch, 480, 480, onButton);

        // draw online players
        for (PlayerEntity onlinePlayer : players.values()) {
            batch.draw(player.getCurrentFrame(stateTime), onlinePlayer.pos.x, onlinePlayer.pos.y, 64, 64);
        }

        // draw enemies on server
        for (EnemyEntity onlineEnemy : enemies.values()) {
            batch.draw(dummyEnemy.getCurrentFrame(stateTime), onlineEnemy.pos.x, onlineEnemy.pos.y, 64, 64);
        }

        // draw myself
        batch.draw(player.getCurrentFrame(stateTime), player.position.x, player.position.y , player.width, player.height);

        //onButton.clear();
        batch.end();
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
            players.clear();
            enemies.clear();
            stateManager.setGameState(GameStateManager.MENU);
        }
    }

    @Override
    public void dispose() {

    }
}
