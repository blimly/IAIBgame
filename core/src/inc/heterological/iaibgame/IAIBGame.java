package inc.heterological.iaibgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class IAIBGame implements Screen {
	Game game;
	OrthographicCamera camera;
	SpriteBatch batch;
	float stateTime;
	Vector3 touch;
	Player player;
	Rectangle loadingRect;
	Rectangle screenRect;

	public IAIBGame(Game game) {
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
		Assets.backgound_loop.loop();
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
			player.bounds.x -= 5;
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.bounds.x += 5;
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.bounds.y -= 5;
		} else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.bounds.y += 5;
		}
		if (Gdx.input.isTouched()) {
			touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touch);
			player.bounds.x = (int) touch.x - 32;
			player.bounds.y = (int) touch.y - 32;
		}
		if(player.bounds.overlaps(loadingRect)) {
			player.bounds.y = 240;
			player.bounds.x = 300;
			Assets.backgound_loop.pause();
			Assets.ooyeah.play(9f);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Assets.backgound_loop.resume();
		}
		if(!player.bounds.overlaps(screenRect)) {
			player.bounds.y = 240;
			player.bounds.x = 300;
			Assets.backgound_loop.pause();
			Assets.finicebordel.play(10f);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Assets.backgound_loop.resume();
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
