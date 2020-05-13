package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Gdx;
<<<<<<< HEAD
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.Main;

public class MultiplayerLobby implements Screen {

    final Main game;
    OrthographicCamera camera;
    Vector2 touch;

    public MultiplayerLobby(Main game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
    }

    @Override
    public void show() {
=======
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.managers.GameKeys;
import inc.heterological.iaibgame.desktop.managers.GameStateManager;
import inc.heterological.iaibgame.desktop.managers.SoundEffects;

public class MultiplayerLobby extends GameState {

    OrthographicCamera camera;
    private float statetime;

    public MultiplayerLobby(GameStateManager gsm) {
        super(gsm);
        init();
    }


    @Override
    public void init() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        statetime = 0;
        SoundEffects.loop("LobbyMusic");
    }

    @Override
    public void update(float dt) {
        statetime += dt;
        if (statetime > 3) {
            stateManager.setGameState(GameStateManager.PLAY_MULTIPLAYER);
            SoundEffects.stopAll();
        }
        handleInput();
>>>>>>> origin/develop

    }

    @Override
<<<<<<< HEAD
    public void render(float delta) {
        Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
            Assets.font.draw(game.batch, "Multiplayer Lobby", 100, Main.GAME_HEIGHT - 100);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            game.setScreen(new ChooseSingleOrMulti(game));
        }
    }

    @Override
    public void resize(int width, int height) {
=======
    public void draw() {
        Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        Main.batch.setProjectionMatrix(camera.combined);
        Main.batch.begin();
        Main.batch.draw(Assets.loading.getKeyFrame(statetime), 230, 150, 180, 288);
        Assets.font.draw(Main.batch, "Connecting...", Main.GAME_WIDTH / 2f - 100, 100);
        Main.batch.end();
>>>>>>> origin/develop

    }

    @Override
<<<<<<< HEAD
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

=======
    public void handleInput() {
        if (GameKeys.isPressed(GameKeys.BACKSPACE)) {
            SoundEffects.play("ChangeScreen");
            SoundEffects.stop("LobbyMusic");
            SoundEffects.stop("MenuBackground");
            stateManager.setGameState(GameStateManager.MENU);
        }
        if (GameKeys.isPressed(GameKeys.ENTER)) {
            SoundEffects.stopAll();
            stateManager.setGameState(GameStateManager.PLAY_MULTIPLAYER);
        }
>>>>>>> origin/develop
    }

    @Override
    public void dispose() {

    }
}
