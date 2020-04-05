package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.managers.GameKeys;
import inc.heterological.iaibgame.desktop.managers.GameStateManager;

public class MultiplayerLobby extends GameState {


    OrthographicCamera camera;
    Vector2 touch;
    SpriteBatch batch;

    public MultiplayerLobby(GameStateManager gsm) {
        super(gsm);
        init();
    }


    @Override
    public void init() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        batch = new SpriteBatch();
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        Assets.font.draw(batch, "Multiplayer Lobby", 100, Main.GAME_HEIGHT - 100);
        Assets.font.draw(batch, "Press ENTER to go to multiplayer Arena.", 100, Main.GAME_HEIGHT - 300);
        batch.end();

    }

    @Override
    public void handleInput() {
        if (GameKeys.isPressed(GameKeys.BACKSPACE)) {
            stateManager.setGameState(GameStateManager.CHOOSE_MODE);
        }
        if (GameKeys.isPressed(GameKeys.ENTER)) {
            stateManager.setGameState(GameStateManager.PLAY_MULTIPLAYER);
        }
    }

    @Override
    public void dispose() {

    }
}
