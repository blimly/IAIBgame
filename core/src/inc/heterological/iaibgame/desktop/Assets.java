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

import java.util.Arrays;


public class Assets {
    public static BitmapFont font;
    public static BitmapFont byeFont;

    public static Texture blank;

    public static Texture triangle;
    public static Sprite triangleUp;
    public static Sprite triangleDown;

    public static Texture lockTex;
    public static Sprite lock;

    public static Texture buttons;
    public static Sprite play;
    public static Sprite exit;
    public static Sprite multiplayer;
    public static Sprite singleplayer;

    public static Texture playerTex1;
    public static Texture playerTex2;
    public static Texture playerTex3;
    public static Texture playerTex4;
    public static Texture selectArrow;

    public static Sprite player1_select;
    public static Sprite player2_select;
    public static Sprite player3_select;
    public static Sprite player4_select;

    public static Sprite mainSpriteBack1;
    public static Sprite mainSpriteBack2;

    public static Texture levelsTexture;
    public static Sprite spriteBack;

    public static Texture mpArenaTex;
    public static TextureRegion[] arenaButton;

    public static Texture playerTex;
    public static Animation<TextureRegion> playerIdle;
    public static Animation<TextureRegion> playerMove;
    public static Animation<TextureRegion> playerKick;
    public static Animation<TextureRegion> playerJab;

    private static Texture zombieTex;
    public static Animation<TextureRegion> zombie;

    private static Texture bouncingBobTex;
    public static Animation<TextureRegion> bob_run;
    public static Animation<TextureRegion> bob_flee;

    private static Texture healerTex;
    public static Animation<TextureRegion> healer_walking;
    public static Animation<TextureRegion> healer_healing;

    private static Texture healingTex;
    public static Animation<TextureRegion> healing;


    public static Texture textureSheet;
    public static TextureRegion[] loading_frames;
    public static Animation<TextureRegion> loading;

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
    }

    private static void loadSprites() {
        lockTex = new Texture(Gdx.files.internal("images/lock.png"));
        lock = new Sprite(lockTex);
        blank = new Texture(Gdx.files.internal("images/blank.png"));
        triangle = new Texture(Gdx.files.internal("images/triangle.png"));
        triangleUp = new Sprite(triangle);
        triangleUp.flip(false, true);
        triangleDown = new Sprite(triangle);
        selectArrow = new Texture(Gdx.files.internal("images/SelectArrow.png"));
        buttons = new Texture(Gdx.files.internal("ui/buttons.png"));
        playerTex1 = new Texture(Gdx.files.internal("images/characters/MainCharacter_ONE.png"));
        playerTex2 = new Texture(Gdx.files.internal("images/characters/MainCharacter_TWO.png"));
        playerTex3 = new Texture(Gdx.files.internal("images/characters/MainCharacter_THREE.png"));
        playerTex4 = new Texture(Gdx.files.internal("images/characters/MainCharacter_FOUR.png"));
        play = new Sprite(new TextureRegion(buttons, 0, 0, 64, 24));
        exit = new Sprite(new TextureRegion(buttons, 0, 24, 64, 24));
        player1_select = new Sprite(new TextureRegion(playerTex1, 0, 0, 16, 10));
        player2_select = new Sprite(new TextureRegion(playerTex2, 0, 0, 16, 10));
        player3_select = new Sprite(new TextureRegion(playerTex3, 0, 0, 16, 10));
        player4_select = new Sprite(new TextureRegion(playerTex4, 0, 0, 16, 10));
        Assets.player1_select.flip(true,false);
        Assets.player2_select.flip(true,false);
        Assets.player3_select.flip(true,false);
        Assets.player4_select.flip(true,false);
        
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
        playerTex = new Texture(Gdx.files.internal("images/characters/MainCharacter_ONE-Sheet-newKick.png"));
        zombieTex = new Texture(Gdx.files.internal("images/characters/Enemy1.png"));
        bouncingBobTex = new Texture(Gdx.files.internal("images/characters/bouncing_bob.png"));
        healerTex = new Texture(Gdx.files.internal("images/characters/healer_sam.png"));
        healingTex = new Texture(Gdx.files.internal("images/healing.png"));

        TextureRegion[][] loading_temp = TextureRegion.split(textureSheet, 16, 16);
        TextureRegion[][] player_temp = TextureRegion.split(playerTex, 16, 16);
        TextureRegion[] playerIdleSprites = Arrays.copyOfRange(player_temp[0], 0, 6, TextureRegion[].class);
        TextureRegion[] playerMoveSprites = Arrays.copyOfRange(player_temp[0], 6, 11, TextureRegion[].class);
        TextureRegion[] playerKickSprites = Arrays.copyOfRange(player_temp[0], 12, 16, TextureRegion[].class);
        TextureRegion[] playerJabSprites = Arrays.copyOfRange(player_temp[0], 16, 21, TextureRegion[].class);

        loading_frames = new TextureRegion[8];
        System.arraycopy(loading_temp[1], 0, loading_frames, 0, 8);
        loading = new Animation<>(0.1f, loading_frames);

        playerIdle = new Animation<>(0.15f, playerIdleSprites);
        playerMove = new Animation<>(0.15f, playerMoveSprites);
        playerKick = new Animation<>(0.1f, playerKickSprites);
        playerJab = new Animation<>(0.05f, playerJabSprites);

        TextureRegion[][] zombie_temp = TextureRegion.split(zombieTex,16,16);
        zombie = new Animation<>(0.2f, zombie_temp[0]);

        TextureRegion[][] bob_temp = TextureRegion.split(bouncingBobTex, 16, 16);
        bob_run = new Animation<>(0.2f, Arrays.copyOfRange(bob_temp[0], 0, 8, TextureRegion[].class));
        bob_flee = new Animation<>(0.2f, Arrays.copyOfRange(bob_temp[0], 8, 12, TextureRegion[].class));

        TextureRegion[][] healer_temp = TextureRegion.split(healerTex, 16, 16);
        healer_walking = new Animation<>(0.2f, Arrays.copyOfRange(healer_temp[0], 0, 4, TextureRegion[].class));
        healer_healing = new Animation<>(0.2f, Arrays.copyOfRange(healer_temp[0], 4, 6, TextureRegion[].class));

        TextureRegion[][] heal_temp = TextureRegion.split(healingTex,16,16);
        healing = new Animation<>(0.1f, heal_temp[0]);
    }

    public static void dispose() {
    }
}