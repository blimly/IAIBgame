package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.arena_objects.ArenaButton;
import inc.heterological.iaibgame.desktop.characters.Enemy;
import inc.heterological.iaibgame.desktop.characters.Player;
import inc.heterological.iaibgame.desktop.managers.GameStateManager;
import inc.heterological.iaibgame.net.client.GameClient;
import inc.heterological.iaibgame.net.shared.packets.OnlineEnemy;
import inc.heterological.iaibgame.net.shared.packets.OnlinePlayer;
import inc.heterological.iaibgame.net.shared.packets.UpdateX;
import inc.heterological.iaibgame.net.shared.packets.UpdateY;
import java.util.*;

public class MultiplayerArena extends GameState{

    OrthographicCamera camera;
    SpriteBatch batch;
    float stateTime;
    // online stuff
    static Player player = new Player();
    static GameClient gameClient;
    public static Map<Integer, OnlinePlayer> players = new HashMap<>();
    public static Map<Integer, OnlineEnemy> enemies = new HashMap<>();


    private ArenaButton arenaButton;
    private Set<Boolean> onButton;


    ArrayList<Enemy> e;

    public MultiplayerArena(GameStateManager gsm) {
        super(gsm);
        init();
        show();
        arenaButton = new ArenaButton(480, 480);
        onButton = new HashSet<>();
        e = new ArrayList<>();
        e.add(new Enemy(new Vector2(0, 0), 0, 100));
        e.add(new Enemy(new Vector2(0, 0), 0, 100));
        e.add(new Enemy(new Vector2(0, 0), 0, 100));
        e.add(new Enemy(new Vector2(0, 0), 0, 100));
    }

    public void show() {
        gameClient.connect();
    }

    public void update() {
        double delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveLeft(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.moveUp(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.moveDown(delta);
        }
        // move on online
        if (player.onlineBounds.x != player.bounds.x) {
            UpdateX packet = new UpdateX();
            packet.x = (int) player.bounds.x;
            gameClient.client.sendUDP(packet);
            player.onlineBounds.x = player.bounds.x;
        }
        if (player.onlineBounds.y != player.bounds.y) {
            UpdateY packet = new UpdateY();
            packet.y = (int) player.bounds.y;
            gameClient.client.sendUDP(packet);
            player.onlineBounds.y = player.bounds.y;
        }
    }

    @Override
    public void init() {
        stateTime = 0f;
        gameClient = new GameClient();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
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

        batch.draw(Assets.mpArenaTex, -player.bounds.x, -player.bounds.y, 1024, 1024);
        updateOnButtons();
        arenaButton.draw(batch, 480 - (int) player.bounds.x, 480 - (int) player.bounds.y, onButton);

        //Assets.font.draw(batch, "X: " + player.bounds.x, 5, 40);
        //Assets.font.draw(batch, "Y: " + player.bounds.y, 5, 20);
        if (arenaButton.isActivated) {
            e.get(0).position.x = 480 + 0 - player.bounds.x;
            e.get(0).position.y = 480 + 200 - player.bounds.y;
            e.get(1).position.x = 480 + 0 - player.bounds.x;
            e.get(1).position.y = 480 + -200 - player.bounds.y;
            e.get(2).position.x = 480 + 200 - player.bounds.x;
            e.get(2).position.y = 480 + 0 - player.bounds.y;
            e.get(3).position.x = 480 + -200 - player.bounds.x;
            e.get(3).position.y = 480 + 0 - player.bounds.y;

            for (Enemy enemy : e) {
                enemy.drawEnemyAndHealthbar(batch, stateTime);
            }
        }

        batch.draw(player.getCurrentFrame(stateTime), 288, 208 , player.bounds.width, player.bounds.height);

        for (OnlinePlayer onlinePlayer : players.values()) {
            batch.draw(player.getCurrentFrame(stateTime), 288+onlinePlayer.x-player.bounds.x, 208+onlinePlayer.y-player.bounds.y, 64, 64);
        }

        onButton.clear();
        batch.end();
    }

    private void updateOnButtons() {
        onButton.add(arenaButton.playerOnButton((int) player.bounds.x, (int) player.bounds.y));
        for (OnlinePlayer onlinePlayer : players.values()) {
            onButton.add(arenaButton.playerOnButton(onlinePlayer.x, onlinePlayer.y));
        }
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            stateManager.setGameState(GameStateManager.MENU);
        }
    }

    @Override
    public void dispose() {

    }
}
