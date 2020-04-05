package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.Assets;

public class Loading implements Screen {
    private Main game;

    OrthographicCamera camera;
    float stateTime;
    Rectangle loadingRect;

    public Loading(Main game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        stateTime = 0f;
        loadingRect = new Rectangle(288, 208, 64, 64);
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        /*
        Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        stateTime += Gdx.graphics.getDeltaTime();
        Assets.current_frame = Assets.loading.getKeyFrame(stateTime, true);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(Assets.current_frame, 288, 208, 64, 64);
            game.font.draw(game.batch, "Loading", 288, 50);
        game.batch.end();

        if (stateTime > 2) {

            dispose();
        }

         */
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
