package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.Button;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.managers.GameStateManager;

public class ChooseSingleOrMulti extends GameState {

    OrthographicCamera camera;
    Vector2 touch;

    Button singleplayer;
    Button multiplayer;
    SpriteBatch batch;

    public ChooseSingleOrMulti(GameStateManager gsm) {
        super(gsm);
        init();
    }


    @Override
    public void init() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        touch = new Vector2();
        batch = new SpriteBatch();

        singleplayer = new Button(526, 72, 57, 300, Assets.singleplayer);
        multiplayer = new Button(526, 72, 57, 200, Assets.multiplayer);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
        camera.update();
        handleInput();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        singleplayer.drawButton(batch);
        multiplayer.drawButton(batch);
        batch.end();  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isTouched()) {
            touch.set(Gdx.input.getX(), Main.GAME_HEIGHT - Gdx.input.getY());
            if(singleplayer.clicked(touch)) {
                stateManager.setGameState(GameStateManager.PLAY_SINGLEPLAYER);
            }
            if(multiplayer.clicked(touch)) {
                stateManager.setGameState(GameStateManager.LOBBY);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            stateManager.setGameState(GameStateManager.MENU);
        }
    }

    @Override
    public void dispose() {

    }
}
