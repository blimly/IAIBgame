package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Assets {

    public static Texture texture_start;
    public static Sprite start_sprite;
    public static Texture texture_exit;
    public static Sprite exit_sprite;

    public static Texture mainTextureBack;
    public static Sprite mainSpriteBack;
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
    public static Sound no;

    public static void load() {
        texture_start = new Texture(Gdx.files.internal("images/startButton.png"));
        start_sprite = new Sprite(texture_start);
        texture_exit = new Texture(Gdx.files.internal("images/exitButton.png"));
        exit_sprite = new Sprite(texture_exit);

        mainTextureBack = new Texture(Gdx.files.internal("images/backgrounds/splash.png"));
        mainSpriteBack = new Sprite(mainTextureBack);
        textureBack = new Texture(Gdx.files.internal("images/backgrounds/levels.png"));
        spriteBack = new Sprite(textureBack, 0, 0, 720, 160);

        textureSheet = new Texture(Gdx.files.internal("images/characters/spritesheet.png"));
        player = new Sprite(textureSheet, 0, 0, 16, 16);
        loadingBanner = new Sprite(textureSheet, 0, 32, 160, 16);

        TextureRegion[][] temp = TextureRegion.split(textureSheet, 16, 16);
        frames = new TextureRegion[8];
        System.arraycopy(temp[1], 0, frames, 0, 8);
        loading = new Animation(0.1f, frames);

        backgound_loop = Gdx.audio.newSound(Gdx.files.internal("audio/bg_loop.wav"));
        ooyeah = Gdx.audio.newSound(Gdx.files.internal("audio/ohoh_yeh.wav"));
        no = Gdx.audio.newSound(Gdx.files.internal("audio/no.wav"));
    }
}