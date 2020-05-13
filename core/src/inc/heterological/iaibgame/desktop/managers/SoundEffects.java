package inc.heterological.iaibgame.desktop.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class SoundEffects {

    private static HashMap<String, Sound> soundEffects = new HashMap<>();
    private static HashMap<String, Music> music = new HashMap<>();

    public static void load(String path, String name, int soundId, float volume) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
        sound.setVolume(soundId, volume);
        soundEffects.put(name, sound);
    }

    public static void loadMusic(String path, String name, float volume) {
        Music musicObj = Gdx.audio.newMusic(Gdx.files.internal(path));
        musicObj.setVolume(volume);
        music.put(name, musicObj);
    }

    public static void loop(String name) {
        soundEffects.get(name).loop();
    }

    public static void play(String name) {
        soundEffects.get(name).play();
    }

    public static void stop(String name) {
        soundEffects.get(name).stop();
    }


    public static void stopAll() {
        for (Sound s : soundEffects.values()) {
            s.stop();
        }
    }

    public static Music getMusicToPlay(String name) {
        return music.get(name);
    }

    public static void playMusic(String name) {
        getMusicToPlay(name).play();
    }

    public static void loopMusic(String name) {
        getMusicToPlay(name).setLooping(true);
    }


    public static void stopAllMusic() {
        for (Music music : music.values()) {
            music.stop();
        }
    }
}
