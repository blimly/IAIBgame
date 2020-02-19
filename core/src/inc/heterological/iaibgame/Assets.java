package inc.heterological.iaibgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Assets {

    public static Texture textureBack;
    public static Sprite spriteBack;

    public static Texture textureSheet;
    public static Sprite player;
    public static TextureRegion[] frames;
    public static TextureRegion current_frame;
    public static Animation loading;
    public static Sprite loadingBanner;

    public static Sound backgound_loop;
    public static Sound ooyeah;
    public static Sound finicebordel;

    public static void load() {
        textureBack = new Texture(Gdx.files.internal("images/backgrounds/splash.png"));
        textureBack.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        spriteBack = new Sprite(textureBack);

        textureSheet = new Texture(Gdx.files.internal("images/characters/spritesheet.png"));
        player = new Sprite(textureSheet, 0, 0, 16, 16);
        loadingBanner = new Sprite(textureSheet, 0, 32, 160, 16);

        TextureRegion[][] temp = TextureRegion.split(textureSheet, 16, 16);
        frames = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            frames[i] = temp[1][i];
        }
        loading = new Animation(0.1f, frames);

        backgound_loop = Gdx.audio.newSound(Gdx.files.internal("audio/bg_loop.wav"));
        ooyeah = Gdx.audio.newSound(Gdx.files.internal("audio/ohoh_yeh.wav"));
        finicebordel = Gdx.audio.newSound(Gdx.files.internal("audio/finicebordel.wav"));
    }
}