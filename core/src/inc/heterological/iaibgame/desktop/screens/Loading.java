package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.Main;

public class Loading implements Screen {
    private Main game;
    private Stage stage;

    OrthographicCamera camera;
    SpriteBatch batch;
    float stateTime;
    Rectangle loadingRect;

    public Loading(Main game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        batch = new SpriteBatch();
        stateTime = 0f;
        loadingRect = new Rectangle(288, 208, 64, 64);

    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        stateTime += Gdx.graphics.getDeltaTime();
        Assets.current_frame = (TextureRegion) Assets.loading.getKeyFrame(stateTime, true);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(Assets.current_frame, 288, 208, 64, 64);
        batch.end();
    }

    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new IAIBGame(game));
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
