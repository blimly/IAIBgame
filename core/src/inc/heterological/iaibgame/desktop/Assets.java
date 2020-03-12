package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


public class Assets {
    public static BitmapFont font;

    public static Texture texture_start;
    public static Sprite start_sprite;
    public static Texture texture_exit;
    public static Sprite exit_sprite;

    public static Texture mainTextureBack;
    public static Sprite mainSpriteBack;
    public static Texture textureBack;
    public static Sprite spriteBack;

    public static Texture playerTex;
    public static Animation player;

    public static Texture textureSheet;
    public static TextureRegion[] loading_frames;
    public static TextureRegion current_frame;
    public static Animation loading;

    public static Sound backgound_loop;
    public static Sound ooyeah;
    public static Sound no;

    public static void load() {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Pixeboy-z8XGD.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 256;
        font = gen.generateFont(parameter);
        font.setColor(Color.valueOf("d50cff"));
        texture_start = new Texture(Gdx.files.internal("ui/play.png"));
        start_sprite = new Sprite(texture_start);
        texture_exit = new Texture(Gdx.files.internal("ui/exit.png"));
        exit_sprite = new Sprite(texture_exit);

        mainTextureBack = new Texture(Gdx.files.internal("images/backgrounds/menu.png"));
        mainSpriteBack = new Sprite(mainTextureBack);
        textureBack = new Texture(Gdx.files.internal("images/backgrounds/levels.png"));
        spriteBack = new Sprite(textureBack, 0, 0, 720, 160);

        textureSheet = new Texture(Gdx.files.internal("images/characters/spritesheet.png"));

        playerTex = new Texture(Gdx.files.internal("images/characters/maincharacter.png"));

        TextureRegion[][] loading_temp = TextureRegion.split(textureSheet, 16, 16);
        TextureRegion[][] player_temp = TextureRegion.split(playerTex, 16, 16);

        loading_frames = new TextureRegion[8];
        System.arraycopy(loading_temp[1], 0, loading_frames, 0, 8);
        loading = new Animation(0.1f, loading_frames);

        player = new Animation(0.2f, player_temp[0]);

        backgound_loop = Gdx.audio.newSound(Gdx.files.internal("audio/bg_loop.wav"));
        ooyeah = Gdx.audio.newSound(Gdx.files.internal("audio/ohoh_yeh.wav"));
        no = Gdx.audio.newSound(Gdx.files.internal("audio/no.wav"));
    }
}