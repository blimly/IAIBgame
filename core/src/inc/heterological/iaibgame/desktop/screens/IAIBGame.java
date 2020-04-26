package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import inc.heterological.iaibgame.Main;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.characters.Enemy;
import inc.heterological.iaibgame.desktop.characters.Player;
import inc.heterological.iaibgame.desktop.managers.GameStateManager;

import java.util.ArrayList;
import java.util.List;


public class IAIBGame extends GameState{
	Main game;
	OrthographicCamera camera;
	SpriteBatch batch;
	float stateTime;
	Vector3 touch;
	Player player;
	Rectangle loadingRect;
	Rectangle screenRect;
	List<Enemy> enemies = new ArrayList<>();

	public IAIBGame(GameStateManager gsm) {
		super(gsm);
		init();
	}


/*
	@Override
	public void show() {
		game.hasPlayedOnce = true;
		player.bounds.y = 240;
		player.bounds.x = 300;
		Assets.menu_loop.stop();
		Assets.backgound_loop.loop(0.1f);
	}


 */

	public void generalUpdate(Vector3 touch, OrthographicCamera camera) {
		double delta = Gdx.graphics.getDeltaTime();
		for (Enemy e : enemies) {
			e.move(new Vector2(player.bounds.x, player.bounds.y), (float) delta);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			for (Enemy e : enemies) {
				e.setCurrentHealth(e.getCurrentHealth() - 1);
			}

		}

	}


	@Override
	public void init() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 640, 480);
		touch = new Vector3();
		batch = new SpriteBatch();
		stateTime = 0f;
		loadingRect = new Rectangle(288, 100, 64, 64);
		screenRect = new Rectangle(64, 64, 640 - 126, 480 - 126);
		player = new Player();
	}

	@Override
	public void update(float delta) {
		handleInput();
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.moveLeft(delta);
			camera.position.x -= 200 * delta;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.moveRight(delta);
			camera.position.x += 200 * delta;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.moveUp(delta);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.moveDown(delta);
		}
	}

	@Override
	public void draw() {
		stateTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		Assets.current_frame = Assets.loading.getKeyFrame(stateTime, true);
		generalUpdate(touch, camera);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(Assets.spriteBack, 0, 0, 2160, 480);
		batch.draw(player.getCurrentFrame(stateTime), player.position.x, player.position.y, player.width, player.height);
		for (Enemy e : enemies) {
			e.drawEnemyAndHealthbar(batch, stateTime);
		}
		batch.end();
	}


	public void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
			Assets.backgound_loop.stop();
			stateManager.setGameState(GameStateManager.MENU);
		}
	}

	@Override
	public void dispose() {

	}
}
