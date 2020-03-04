package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.Main;

public class MainMenu implements Screen {
    private Main game;

    OrthographicCamera camera;
    SpriteBatch batch;
    Vector3 touch;

    float stateTime;

    public MainMenu(Main game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        batch = new SpriteBatch();
        stateTime = 0f;
        touch = new Vector3();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        update(touch);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
            batch.draw(Assets.mainTextureBack, 0, 0, 640, 480);
            batch.draw(Assets.start_sprite, 320 - 85, 120, 171, 63);
            batch.draw(Assets.exit_sprite, 320 - 85, 50, 171, 63);
        batch.end();
    }

    public void update(Vector3 touch) {
        if(Gdx.input.isTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            if(touch.x >= 235 && touch.x <= 406 && touch.y >= 480 - 183 && touch.y <= 480 - 120) {
                game.setScreen(new Loading(game));
            }
            if(touch.x >= 235 && touch.x <= 406 && touch.y >= 367 && touch.y <= 430) {
                Gdx.app.exit();
            }
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
