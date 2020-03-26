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

public class MainMenu implements Screen {

    final Main game;

    OrthographicCamera camera;
    Vector2 touch;
    Button playButton;
    Button exitButton;

    float bg_scroll_y;

    public MainMenu(Main game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        touch = new Vector2();
        playButton = new Button(64 * 3, 24 * 3, 235, 120, Assets.play);
        exitButton = new Button(64 * 3, 24 * 3, 235, 30, Assets.exit);
        bg_scroll_y = 0;
    }

    @Override
    public void show() {
        Assets.menu_loop.loop(0.7f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        checkButtons(touch);
        bg_scroll_y -= 20 * Gdx.graphics.getDeltaTime();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
            game.batch.draw(Assets.mainSpriteBack1, bg_scroll_y % 640, 0, 640, 480);
            game.batch.draw(Assets.mainSpriteBack2, bg_scroll_y % 640 + 640, 0, 640, 480);
            if (game.hasPlayedOnce) {
                game.font.draw(game.batch, "press  [ h ]  for help", 245, 460);
            }

        playButton.drawButton(game.batch);
            exitButton.drawButton(game.batch);
        game.batch.end();
    }

    public void checkButtons(Vector2 touch) {
        if(Gdx.input.isTouched()) {
            touch.set(Gdx.input.getX(), Main.GAME_HEIGHT - Gdx.input.getY());
            if(playButton.clicked(touch)) {
                game.setScreen(new ChooseSingleOrMulti(game));
            }
            if(exitButton.clicked(touch)) {
                game.setScreen(new Bye(game));
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            game.setScreen(new Hell(game));
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
