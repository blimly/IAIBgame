package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import inc.heterological.iaibgame.Game;
import inc.heterological.iaibgame.IAIBGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "I AM INFO BOY";
		config.width = 640;
		config.height = 480;
		config.resizable = false;
		new LwjglApplication(new Game(), config);
	}
}
