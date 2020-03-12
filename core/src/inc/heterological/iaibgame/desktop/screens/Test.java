package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.Button;
import inc.heterological.iaibgame.desktop.Main;



public class Test implements Screen {

    private Main game;
    private SpriteBatch batch;

    OrthographicCamera camera;
    Stage stage;

    public Test(final Main game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        FitViewport viewport = new FitViewport(640, 480, camera);
        stage = new Stage(viewport);
        Button button1 = new Button(stage, 200, 32, 220, 154,"Start", new Loading(game), game, Color.valueOf("394c83"));
        Button button2 = new Button(stage, 200, 32, 220, 112, "Quit", new Bye(game), game, Color.valueOf("394c83"));

        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(stage);
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(Assets.mainTextureBack, 0, 0, 640, 480);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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

    @Override
    public void show() {

    }
}
