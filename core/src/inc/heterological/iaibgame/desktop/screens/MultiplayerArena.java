package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inc.heterological.iaibgame.desktop.characters.Player;
import inc.heterological.iaibgame.desktop.managers.GameStateManager;
import inc.heterological.iaibgame.net.client.GameClient;
import inc.heterological.iaibgame.net.shared.packets.OnlinePlayer;
import inc.heterological.iaibgame.net.shared.packets.UpdateX;
import inc.heterological.iaibgame.net.shared.packets.UpdateY;

import java.util.HashMap;
import java.util.Map;

public class MultiplayerArena extends GameState{

    OrthographicCamera camera;
    SpriteBatch batch;
    float stateTime;

    // online stuff
    static Player player = new Player();
    static GameClient gameClient;
    public static Map<Integer, OnlinePlayer> players = new HashMap<>();

    public MultiplayerArena(GameStateManager gsm) {
        super(gsm);
        init();
        show();
    }

    public void show() {
        System.out.println("Trying to connnect");
        gameClient.connect();
        System.out.println("connected to server");
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
            // send players X value
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
        // draw this player
        batch.draw(player.getCurrentFrame(stateTime), player.bounds.x, player.bounds.y, player.bounds.width, player.bounds.height);
        // draw online players
        for (OnlinePlayer onlinePlayer : players.values()) {
            batch.draw(player.getCurrentFrame(stateTime), onlinePlayer.x, onlinePlayer.y, 64, 64);
        }
        batch.end();
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
