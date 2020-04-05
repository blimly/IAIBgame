package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.managers.GameStateManager;

public class Hell extends GameState {

    private Main game;

    OrthographicCamera camera;
    float stateTime;
    SpriteBatch batch;

    protected Hell(GameStateManager gsm, Main game) {
        super(gsm);
        this.game = game;
        init();
    }



    public void show() {
        Assets.hell.loop();
    }


    @Override
    public void init() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        stateTime = 0f;
        batch = new SpriteBatch();
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        handleInput();
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = Assets.run.getKeyFrame(stateTime, true);
        batch.begin();
        batch.draw(currentFrame, 0, 0, 640, 640);
        game.font.draw(batch, "There is no help in hell.", 100, 100);
        game.font.draw(batch, "RUN WHILE YOU STILL CAN!", 100, 60);
        batch.end();
    }

    @Override
    public void handleInput() {
        if (!Gdx.input.isKeyPressed(Input.Keys.H)) {
            Assets.hell.stop();
            stateManager.setGameState(GameStateManager.MENU);
        }
    }

    @Override
    public void dispose() {

    }
}
