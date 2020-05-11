package inc.heterological.iaibgame.desktop.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class SoundEffects {

    private static HashMap<String, Sound> soundEffects = new HashMap<>();

    public static void load(String path, String name) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
        soundEffects.put(name, sound);
    }

    public static void loop(String name) {
        soundEffects.get(name).loop();
    }

    public static void play(String name, float volume) {
        soundEffects.get(name).play(volume);
    }
    public static void stop(String name) {
        soundEffects.get(name).stop();
    }

    public static void stopAll() {
        for (Sound s: soundEffects.values()) {
            s.stop();
        }
    }
}
