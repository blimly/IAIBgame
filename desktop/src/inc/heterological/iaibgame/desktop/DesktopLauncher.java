package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration window = new LwjglApplicationConfiguration();
		window.title = "I AM INFO BOY";
		window.width = 720;
		window.height = 480;
		window.resizable = false;
		window.addIcon("images/icon.png", Files.FileType.Internal);
		new LwjglApplication(new Main(), window);
	}
}
