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
import inc.heterological.iaibgame.desktop.managers.GameStateManager;
import inc.heterological.iaibgame.net.client.GameClient;
import inc.heterological.iaibgame.net.shared.packets.OnlineEnemy;
import inc.heterological.iaibgame.net.shared.packets.OnlinePlayer;
import inc.heterological.iaibgame.net.shared.packets.UpdateX;
import inc.heterological.iaibgame.net.shared.packets.UpdateY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        camera.position.lerp(new Vector3(player.position.x + player.width / 2, player.position.y + player.height / 2, 0), (float) delta);

        // move on online
        if (player.onlineBounds.x != player.bounds.x) {
            UpdateX packet = new UpdateX();
            packet.x = (int) player.position.x;
            gameClient.client.sendUDP(packet);
            player.onlineBounds.x = player.position.x;
        }
        if (player.onlineBounds.y != player.bounds.y) {
            UpdateY packet = new UpdateY();
            packet.y = (int) player.position.y;
            gameClient.client.sendUDP(packet);
            player.onlineBounds.y = player.position.y;
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
        updateOnButtons();
        arenaButton.draw(batch, 480, 480, onButton);

        Assets.font.draw(batch, "X: " + player.position.x, 5, 40);
        Assets.font.draw(batch, "Y: " + player.position.y, 5, 20);
        if (arenaButton.isActivated) {
            e.get(0).position.x = 480;
            e.get(0).position.y = 480 + 200;
            e.get(1).position.x = 480;
            e.get(1).position.y = 480 - 200;
            e.get(2).position.x = 480 + 200;
            e.get(2).position.y = 480;
            e.get(3).position.x = 480 -200;
            e.get(3).position.y = 480;

            for (Enemy enemy : e) {
                enemy.drawEnemyAndHealthbar(batch, stateTime);
            }
        }

        batch.draw(player.getCurrentFrame(stateTime), player.position.x, player.position.y , player.width, player.height);

        for (OnlinePlayer onlinePlayer : players.values()) {
            batch.draw(player.getCurrentFrame(stateTime), 288+onlinePlayer.x, 208+onlinePlayer.y, 64, 64);
        }

        onButton.clear();
        batch.end();
    }

    private void updateOnButtons() {
        onButton.add(arenaButton.playerOnButton((int) player.position.x, (int) player.position.y));
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
