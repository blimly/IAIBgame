package inc.heterological.iaibgame.desktop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import inc.heterological.iaibgame.desktop.Assets;
import inc.heterological.iaibgame.desktop.Main;
import inc.heterological.iaibgame.desktop.characters.Player;

import javax.swing.*;
import java.awt.*;

public class IAIBGame extends Component implements Screen {
	Main game;
	OrthographicCamera camera;
	SpriteBatch batch;
	float stateTime;
	Vector3 touch;
	Player player;
	Rectangle loadingRect;
	Rectangle screenRect;
	private static final int MOVE_SPEED = 3;


	public IAIBGame(Main game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 640, 480);
		touch = new Vector3();
		batch = new SpriteBatch();
		stateTime = 0f;
		player = new Player();
		loadingRect = new Rectangle(288, 100, 64, 64);
		screenRect = new Rectangle(64, 64, 640 - 126, 480 - 126);

	}

	@Override
	public void show() {
		player.bounds.y = 240;
		player.bounds.x = 300;
		Assets.backgound_loop.loop(0.1f);
	}

	@Override
	public void render(float delta) {
		stateTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0.12f, 0.11f, 0.22f, 1f); // clear color magenta
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		stateTime += Gdx.graphics.getDeltaTime();
		Assets.current_frame = (TextureRegion) Assets.loading.getKeyFrame(stateTime, true);
		generalUpdate(touch, camera);


		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(Assets.spriteBack, 0, 0, 2160, 480);
		batch.draw(player.getCurrentFrame(stateTime), player.bounds.x, player.bounds.y, player.bounds.width, player.bounds.height);

		batch.end();
	}

	public void generalUpdate(Vector3 touch, OrthographicCamera camera) {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.bounds.x -= MOVE_SPEED;
			camera.position.x -= MOVE_SPEED;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.bounds.x += MOVE_SPEED;
			camera.position.x += MOVE_SPEED;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.bounds.y -= MOVE_SPEED;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.bounds.y += MOVE_SPEED;
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {
		// only on android
	}

	@Override
	public void resume() {
		// only on android
	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
