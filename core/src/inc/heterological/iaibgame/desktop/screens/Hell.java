package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.Main;

public class Hell implements Screen {

    private Main game;

    OrthographicCamera camera;
    float stateTime;

    public Hell(Main game) {

        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        stateTime = 0f;
    }

    @Override
    public void show() {
        Assets.hell.loop();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        checkButtons();
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = Assets.run.getKeyFrame(stateTime, true);
        game.batch.begin();
            game.batch.draw(currentFrame, 0, 0, 640, 640);
            game.font.draw(game.batch, "There is no help in hell.", 100, 100);
            game.font.draw(game.batch, "RUN WHILE YOU STILL CAN!", 100, 60);
        game.batch.end();
    }

    public void checkButtons() {
        if (!Gdx.input.isKeyPressed(Input.Keys.H)) {
            Assets.hell.stop();
            game.setScreen(new MainMenu(game));
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
