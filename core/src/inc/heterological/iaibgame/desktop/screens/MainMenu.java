package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Gdx;
<<<<<<< HEAD
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
=======
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.Button;
import inc.heterological.iaibgame.desktop.SelectArrow;
import inc.heterological.iaibgame.desktop.managers.GameKeys;
import inc.heterological.iaibgame.desktop.managers.GameStateManager;
import inc.heterological.iaibgame.desktop.managers.SoundEffects;

public class MainMenu extends GameState {

>>>>>>> origin/develop

    OrthographicCamera camera;
    Vector2 touch;
    Button playButton;
    Button exitButton;
<<<<<<< HEAD

    float bg_scroll_y;

    public MainMenu(Main game) {
        this.game = game;
=======
    SelectArrow arrow = new SelectArrow(180, 120, 120, 30, 90);

    float bg_scroll_y;

    public MainMenu(GameStateManager gsm) {
        super(gsm);
        init();
    }


    @Override
    public void init() {
>>>>>>> origin/develop
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        touch = new Vector2();
        playButton = new Button(64 * 3, 24 * 3, 235, 120, Assets.play);
        exitButton = new Button(64 * 3, 24 * 3, 235, 30, Assets.exit);
        bg_scroll_y = 0;
<<<<<<< HEAD
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
=======
        SoundEffects.loop("MenuBackground");
    }

    @Override
    public void update(float dt) {
        handleInput();
        if (GameKeys.isPressed(GameKeys.ENTER)) {
            if (arrow.y == 120) {
                stateManager.setGameState(GameStateManager.LOBBY);
                SoundEffects.play("ChangeScreen");
            }
            if (arrow.y == 30) {
                Gdx.app.exit();
                SoundEffects.play("ChangeScreen");
            }
        }
        if (GameKeys.isPressed(GameKeys.UP)) {
            arrow.moveArrow(GameKeys.UP);
            SoundEffects.playMusic("Navigate");
        }
        if (GameKeys.isPressed(GameKeys.DOWN)) {
            arrow.moveArrow(GameKeys.DOWN);
            SoundEffects.playMusic("Navigate");
        }
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        handleInput();
        bg_scroll_y -= 20 * Gdx.graphics.getDeltaTime();

        Main.batch.setProjectionMatrix(camera.combined);
        Main.batch.begin();
        Main.batch.draw(Assets.mainSpriteBack1, bg_scroll_y % 1280, 0, 1280, 480);
        Main.batch.draw(Assets.mainSpriteBack2, bg_scroll_y % 1280 + 1280, 0, 1280, 480);
        arrow.drawArrow(Main.batch);
        playButton.drawButton(Main.batch);
        exitButton.drawButton(Main.batch);
        Main.batch.end();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            touch.set(Gdx.input.getX(), Main.GAME_HEIGHT - Gdx.input.getY());
            if (playButton.clicked(touch)) {
                stateManager.setGameState(GameStateManager.LOBBY);
                SoundEffects.play("ChangeScreen");
            }
            if (exitButton.clicked(touch)) {
                SoundEffects.play("ChangeScreen");
                Gdx.app.exit();
            }
        }
>>>>>>> origin/develop

    }

    @Override
    public void dispose() {
<<<<<<< HEAD
=======
        Assets.dispose();
>>>>>>> origin/develop
    }
}
