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
    public static BitmapFont byeFont;

    public static Texture blank;

    public static Texture buttons;
    public static Sprite play;
    public static Sprite exit;
    public static Sprite multiplayer;
    public static Sprite singleplayer;

    public static Sprite mainSpriteBack1;
    public static Sprite mainSpriteBack2;

    public static Texture levelsTexture;
    public static Sprite spriteBack;

    public static Texture mpArenaTex;
    public static TextureRegion[] arenaButton;

    public static Texture playerTex;
    public static Animation<TextureRegion> player;

    public static Texture enemy1Tex;
    public static Animation<TextureRegion> enemy1;

    public static Texture textureSheet;
    public static TextureRegion[] loading_frames;
    public static TextureRegion current_frame;
    public static Animation<TextureRegion> loading;

    public static Texture runSheet;
    public static Animation<TextureRegion> run;


    public static Sound menu_loop;
    public static Sound backgound_loop;
    public static Sound ooyeah;
    public static Sound no;
    public static Sound hell;

    public static void load() {
        loadFont();
        loadSprites();
        loadAnimations();
        loadSounds();
    }

    public static BitmapFont getFont() {
        return font;
    }

    private static void loadFont() {
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Pixeboy-z8XGD.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font = gen.generateFont(parameter);
        font.setColor(Color.valueOf("39c6e6"));
        parameter.size = 240;
        byeFont = gen.generateFont(parameter);
        byeFont.setColor(Color.valueOf("39c6e6"));
    }

    private static void loadSprites() {
        blank = new Texture(Gdx.files.internal("images/blank.png"));
        buttons = new Texture(Gdx.files.internal("ui/buttons.png"));
        play = new Sprite(new TextureRegion(buttons, 0, 0, 64, 24));
        exit = new Sprite(new TextureRegion(buttons, 0, 24, 64, 24));
        singleplayer = new Sprite(new TextureRegion(buttons, 0, 48, 192, 24));
        multiplayer =  new Sprite(new TextureRegion(buttons, 0, 72, 192, 24));

        mainSpriteBack1 = new Sprite(new Texture(Gdx.files.internal("images/backgrounds/menu2.png")));
        mainSpriteBack2 = new Sprite(new Texture(Gdx.files.internal("images/backgrounds/menu2.png")));

        levelsTexture = new Texture(Gdx.files.internal("images/backgrounds/levels.png"));
        spriteBack = new Sprite(levelsTexture, 0, 0, 720, 160);

        mpArenaTex = new Texture(Gdx.files.internal("images/arenas/mp-arena.png"));

        Texture buttonSheet = new Texture(Gdx.files.internal("images/arena_big_button.png"));
        arenaButton = TextureRegion.split(buttonSheet, 32, 21)[0];

    }

    private static void loadSounds() {
        menu_loop = Gdx.audio.newSound(Gdx.files.internal("audio/menu_ambient.wav"));
        backgound_loop = Gdx.audio.newSound(Gdx.files.internal("audio/bg_loop.wav"));
        ooyeah = Gdx.audio.newSound(Gdx.files.internal("audio/ohoh_yeh.wav"));
        no = Gdx.audio.newSound(Gdx.files.internal("audio/no.wav"));
        hell = Gdx.audio.newSound(Gdx.files.internal("audio/violent_delights.wav"));
    }

    private static void loadAnimations() {
        textureSheet = new Texture(Gdx.files.internal("images/characters/spritesheet.png"));
        playerTex = new Texture(Gdx.files.internal("images/characters/maincharacter_idle.png"));
        enemy1Tex = new Texture(Gdx.files.internal("images/characters/Enemy1.png"));
        TextureRegion[][] loading_temp = TextureRegion.split(textureSheet, 16, 16);
        TextureRegion[][] player_temp = TextureRegion.split(playerTex, 16, 16);
        TextureRegion[][] enemy1_temp = TextureRegion.split(enemy1Tex,16,16);

        loading_frames = new TextureRegion[8];
        System.arraycopy(loading_temp[1], 0, loading_frames, 0, 8);
        loading = new Animation<>(0.1f, loading_frames);
        player = new Animation<>(0.2f, player_temp[0]);
        enemy1 = new Animation<>(0.3f, enemy1_temp[0]);

        runSheet = new Texture(Gdx.files.internal("images/run-sheet.png"));
        TextureRegion[][] run_temp = TextureRegion.split(runSheet, 500, 500);
        TextureRegion[] runFrames = new TextureRegion[60];
        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (index < 60) runFrames[index++] = run_temp[i][j];

            }
        }
        run = new Animation<>(0.033f, runFrames);

    }

    public static void dispose() {
        font.dispose();
    }
}