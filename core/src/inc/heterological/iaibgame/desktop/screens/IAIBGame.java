package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.WindowedMean;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.Main;
import inc.heterological.iaibgame.desktop.characters.Player;
import inc.heterological.iaibgame.desktop.managers.GameInputProcessor;
import inc.heterological.iaibgame.desktop.managers.GameKeys;
import inc.heterological.iaibgame.desktop.managers.GameStateManager;
import inc.heterological.iaibgame.desktop.managers.MusicPlayer;

import java.util.ArrayList;
import java.util.List;


public class IAIBGame extends ApplicationAdapter {

	// Main game;
	public static OrthographicCamera camera;
	public static GameStateManager gameStateManager;
	SpriteBatch batch;
	public static int WIDTH;
	public static int HEIGHT;

	// float stateTime;
	// Vector3 touch;
	// Player player;
	// Rectangle loadingRect;
	// Rectangle screenRect;

/*
	public IAIBGame(Main game) {


		this.game = game;

		touch = new Vector3();
		batch = game.batch;
		stateTime = 0f;
		loadingRect = new Rectangle(288, 100, 64, 64);
		screenRect = new Rectangle(64, 64, 640 - 126, 480 - 126);
		player = new Player();

	}

 */

	@Override
	public void create() {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		batch = new SpriteBatch();

		camera = new OrthographicCamera(WIDTH, HEIGHT);
		camera.setToOrtho(false, 640, 480);

		Gdx.input.setInputProcessor(new GameInputProcessor());

		// Load music files to string
		MusicPlayer.load("audio/menu_ambient.wav", "menu");

		//new game state manager for render
		gameStateManager = new GameStateManager();
	}

	/*
	@Override
	public void show() {
		/*
		game.hasPlayedOnce = true;
		player.bounds.y = 240;
		player.bounds.x = 300;
		Assets.menu_loop.stop();
		Assets.backgound_loop.loop(0.1f);



	}

	 */

	@Override
	public void render() {
		/*
		stateTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		Assets.current_frame = Assets.loading.getKeyFrame(stateTime, true);
		generalUpdate(touch, camera);

		GameKeys.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(Assets.spriteBack, 0, 0, 2160, 480);
		batch.draw(player.getCurrentFrame(stateTime), player.bounds.x, player.bounds.y, player.bounds.width, player.bounds.height);

		batch.end();

		 */
		Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f); // clear color magenta
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gameStateManager.update(Gdx.graphics.getDeltaTime());
		gameStateManager.draw();
		GameKeys.update();

	}

	public void generalUpdate(Vector3 touch, OrthographicCamera camera) {
		/*
		double delta = Gdx.graphics.getDeltaTime();
		if (GameKeys.isPressed(GameKeys.LEFT)) {
			player.moveLeft(delta);
			camera.position.x -= 200 * delta;
		}
		if (GameKeys.isPressed(GameKeys.RIGHT)) {
			player.moveRight(delta);
			camera.position.x += 200 * delta;
		}
		if (GameKeys.isPressed(GameKeys.UP)) {
			player.moveUp(delta);
		}
		if (GameKeys.isPressed(GameKeys.DOWN)) {
			player.moveDown(delta);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			game.setScreen(new Bye(game));
		}

		if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
			Assets.backgound_loop.stop();
			game.setScreen(new MainMenu(game));
		}

		 */
	}


	@Override
	public void dispose() {

	}
}
