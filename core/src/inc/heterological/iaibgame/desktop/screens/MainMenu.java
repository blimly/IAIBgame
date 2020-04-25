package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.Button;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.SelectArrow;
import inc.heterological.iaibgame.desktop.managers.GameStateManager;

public class MainMenu extends GameState{


    OrthographicCamera camera;
    Vector2 touch;
    Button playButton;
    Button exitButton;
    SpriteBatch batch;
    SelectArrow arrow = new SelectArrow(180,120 ,120, 30, 90);

    float bg_scroll_y;

    public MainMenu(GameStateManager gsm) {
        super(gsm);
        init();
    }


    @Override
    public void init() {
        Assets.load();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        touch = new Vector2();
        batch = new SpriteBatch();
        playButton = new Button(64 * 3, 24 * 3, 235, 120, Assets.play);
        exitButton = new Button(64 * 3, 24 * 3, 235, 30, Assets.exit);
        bg_scroll_y = 0;
    }

    @Override
    public void update(float dt) {
        handleInput();
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            if(arrow.y == 120) {
                stateManager.setGameState(GameStateManager.CHOOSE_MODE);
            }
            if(arrow.y == 30) {
                Gdx.app.exit();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            arrow.moveArrow(Input.Keys.UP);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            arrow.moveArrow(Input.Keys.DOWN);
        }
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        handleInput();
        bg_scroll_y -= 20 * Gdx.graphics.getDeltaTime();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(Assets.mainSpriteBack1, bg_scroll_y % 640, 0, 640, 480);
        batch.draw(Assets.mainSpriteBack2, bg_scroll_y % 640 + 640, 0, 640, 480);
        arrow.drawArrow(batch);
        playButton.drawButton(batch);
        exitButton.drawButton(batch);
        batch.end();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isTouched()) {
            touch.set(Gdx.input.getX(), Main.GAME_HEIGHT - Gdx.input.getY());
            if(playButton.clicked(touch)) {
                stateManager.setGameState(GameStateManager.CHOOSE_MODE);
            }
            if(exitButton.clicked(touch)) {
                Gdx.app.exit();
            }
        }

    }

    @Override
    public void dispose() {
        Assets.dispose();
    }
}
