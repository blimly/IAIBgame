package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.characters.Player;
import inc.heterological.iaibgame.net.client.GameClient;
import inc.heterological.iaibgame.net.shared.Network;

import java.util.HashMap;
import java.util.Map;

public class MultiplayerArena implements Screen {

    private Main game;
    OrthographicCamera camera;
    SpriteBatch batch;
    float stateTime;

    // online stuff
    static Player player = new Player();
    static GameClient gameClient = new GameClient();
    public static Map<Integer, Network.OnlinePlayer> players = new HashMap<>();

    public MultiplayerArena(Main game) {
        this.game = game;
        stateTime = 0f;
        batch = game.batch;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
    }

    @Override
    public void show() {
        System.out.println("Trying to connnect");
        gameClient.connect();
        System.out.println("connected to server");
    }

    @Override
    public void render(float delta) {
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
        for (Network.OnlinePlayer onlinePlayer : players.values()) {
            batch.draw(player.getCurrentFrame(stateTime), onlinePlayer.x, onlinePlayer.y, 64, 64);
        }
        batch.end();
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
            Network.UpdateX packet = new Network.UpdateX();
            packet.x = (int) player.bounds.x;
            gameClient.client.sendUDP(packet);
            player.onlineBounds.x = player.bounds.x;
        }
        if (player.onlineBounds.y != player.bounds.y) {
            Network.UpdateY packet = new Network.UpdateY();
            packet.y = (int) player.bounds.y;
            gameClient.client.sendUDP(packet);
            player.onlineBounds.y = player.bounds.y;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
