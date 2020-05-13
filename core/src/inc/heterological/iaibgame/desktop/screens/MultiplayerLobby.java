package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Gdx;
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
        SoundEffects.loop("LobbyMusic", 0.25f);
    }

    @Override
    public void update(float dt) {
        statetime += dt;
        if (statetime > 3) {
            stateManager.setGameState(GameStateManager.PLAY_MULTIPLAYER);
            SoundEffects.stopAll();
        }
        handleInput();

    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        Main.batch.setProjectionMatrix(camera.combined);
        Main.batch.begin();
        Main.batch.draw(Assets.loading.getKeyFrame(statetime), 230, 150, 180, 288);
        Assets.font.draw(Main.batch, "Connecting...", Main.GAME_WIDTH / 2f - 100, 100);
        Main.batch.end();

    }

    @Override
    public void handleInput() {
        if (GameKeys.isPressed(GameKeys.BACKSPACE)) {
            stateManager.setGameState(GameStateManager.MENU);
            SoundEffects.play("ChangeScreen", 0.1f);
            SoundEffects.stop("LobbyMusic");
        }
        if (GameKeys.isPressed(GameKeys.ENTER)) {
            stateManager.setGameState(GameStateManager.PLAY_MULTIPLAYER);
            SoundEffects.stopAll();
        }
    }

    @Override
    public void dispose() {

    }
}
