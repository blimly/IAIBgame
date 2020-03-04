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
    int startX;
    int startY;


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
            batch.draw(Assets.start_sprite, 320 - 90, 260);
            batch.draw(Assets.exit_sprite, 320 - 90, 180);
        batch.end();
    }

    public void update(Vector3 touch) {
        if(Gdx.input.isTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            if(touch.x >= 230 && touch.x <= 410 && touch.y >= 260 && touch.y <= 300) {
                Gdx.app.exit();
            }
            if(touch.x >= 230 && touch.x <= 410 && touch.y >= 180 && touch.y <= 220) {
                game.setScreen(new Loading(game));
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
