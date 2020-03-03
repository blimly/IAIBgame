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

public class IAIBGame implements Screen {
	Main game;
	OrthographicCamera camera;
	SpriteBatch batch;
	float stateTime;
	Vector3 touch;
	Player player;
	Rectangle loadingRect;
	Rectangle screenRect;
	private static final int MOVE_SPEED = 3;
	float zoomamount = 1;

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
		Gdx.gl.glClearColor(1f, 0f, 1f, 1f); // clear color magenta
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		stateTime += Gdx.graphics.getDeltaTime();
		Assets.current_frame = (TextureRegion) Assets.loading.getKeyFrame(stateTime, true);
		generalUpdate(touch, camera);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(Assets.textureBack, 0, 0);
		batch.draw(Assets.current_frame, 288, 100, 64, 64);
		batch.draw(player.image, player.bounds.x, player.bounds.y, player.bounds.width, player.bounds.height);
		if ((int) stateTime % 2 == 0) {
			batch.draw(Assets.loadingBanner, 240, 64);
		}
		batch.end();
	}

	public void generalUpdate(Vector3 touch, OrthographicCamera camera) {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			zoomamount += 0.01f;
			player.bounds.x -= MOVE_SPEED;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			zoomamount -= 0.01f;
			player.bounds.x += MOVE_SPEED;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.bounds.y -= MOVE_SPEED;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.bounds.y += MOVE_SPEED;
		}
		if (Gdx.input.isTouched()) {
			touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touch);
			player.bounds.x = (int) touch.x - 32;
			player.bounds.y = (int) touch.y - 32;
		}
		if(player.bounds.overlaps(loadingRect)) {
			player.bounds.y = 320;
			player.bounds.x = 300;
			Assets.backgound_loop.pause();
			Assets.ooyeah.play(0.6f);
			Assets.backgound_loop.resume();
		}
		if(!player.bounds.overlaps(screenRect)) {
			player.bounds.y = 240;
			player.bounds.x = 300;
			Assets.backgound_loop.pause();
			Assets.no.play(10f);
			Assets.backgound_loop.resume();
		}
		camera.zoom = zoomamount;
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
