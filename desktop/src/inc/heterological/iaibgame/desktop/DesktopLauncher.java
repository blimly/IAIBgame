package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
<<<<<<< HEAD

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "I AM INFO BOY";
		config.width = 720;
		config.height = 480;
		config.resizable = false;
		config.addIcon("images/icon.png", Files.FileType.Internal);
		new LwjglApplication(new Main(), config);
=======
import inc.heterological.iaibgame.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration window = new LwjglApplicationConfiguration();
		window.title = "I AM INFO BOY";
		window.width = 720;
		window.height = 480;
		window.resizable = false;
		window.addIcon("images/icon.png", Files.FileType.Internal);
		new LwjglApplication(new Main(), window);
>>>>>>> origin/develop
	}
}
