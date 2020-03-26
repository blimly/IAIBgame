package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.Button;
import inc.heterological.iaibgame.desktop.Main;

public class ChooseSingleOrMulti implements Screen {

    final Main game;
    OrthographicCamera camera;
    Vector2 touch;

    Button singleplayer;
    Button multiplayer;

    public ChooseSingleOrMulti(Main game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        touch = new Vector2();

        singleplayer = new Button(526, 72, 57, 300, Assets.singleplayer);
        multiplayer = new Button(526, 72, 57, 200, Assets.multiplayer);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        checkButtons(touch);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        singleplayer.drawButton(game.batch);
        multiplayer.drawButton(game.batch);
        game.batch.end();
    }

    public void checkButtons(Vector2 touch) {
        if(Gdx.input.isTouched()) {
            touch.set(Gdx.input.getX(), Main.GAME_HEIGHT - Gdx.input.getY());
            if(singleplayer.clicked(touch)) {
                game.setScreen(new IAIBGame(game));
            }
            if(multiplayer.clicked(touch)) {
                game.setScreen(new MultiplayerLobby(game));
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
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
