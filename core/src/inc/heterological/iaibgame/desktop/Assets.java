package inc.heterological.iaibgame.desktop;

import com.badlogic.gdx.Gdx;
<<<<<<< HEAD
import com.badlogic.gdx.audio.Sound;
=======
>>>>>>> origin/develop
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
<<<<<<< HEAD


public class Assets {
    public static BitmapFont font;
    public static BitmapFont byeFont;
=======
import inc.heterological.iaibgame.desktop.managers.SoundEffects;

import java.util.Arrays;


public class Assets {

    public static BitmapFont font;
    public static BitmapFont bigFont;
    public static BitmapFont hugeFont;
>>>>>>> origin/develop

    public static Texture blank;

    public static Texture buttons;
    public static Sprite play;
    public static Sprite exit;
    public static Sprite multiplayer;
    public static Sprite singleplayer;

<<<<<<< HEAD
=======
    public static Texture playerTex1;
    public static Texture selectArrow;

>>>>>>> origin/develop
    public static Sprite mainSpriteBack1;
    public static Sprite mainSpriteBack2;

    public static Texture levelsTexture;
    public static Sprite spriteBack;

<<<<<<< HEAD
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
=======
    public static Texture mpArenaTex;
    public static TextureRegion[] arenaButton;

    public static Texture playerTex;
    public static Animation<TextureRegion> playerIdle;
    public static Animation<TextureRegion> playerMove;
    public static Animation<TextureRegion> playerKick;
    public static Animation<TextureRegion> playerJab;
    public static Animation<TextureRegion> zombie;
    public static Animation<TextureRegion> bob_run;
    public static Animation<TextureRegion> bob_flee;
    public static Animation<TextureRegion> healer_walking;
    public static Animation<TextureRegion> healer_healing;
    public static Animation<TextureRegion> healing;
    public static Texture textureSheet;
    public static Animation<TextureRegion> loading;
    private static Texture zombieTex;
    private static Texture bouncingBobTex;
    private static Texture healerTex;
    private static Texture healingTex;
    private static Texture loadingTex;

    public static void load() {
        loadFonts();
>>>>>>> origin/develop
        loadSprites();
        loadAnimations();
        loadSounds();
    }

    public static BitmapFont getFont() {
        return font;
    }

<<<<<<< HEAD
    private static void loadFont() {
=======
    private static void loadFonts() {
>>>>>>> origin/develop
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Pixeboy-z8XGD.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font = gen.generateFont(parameter);
        font.setColor(Color.valueOf("39c6e6"));
<<<<<<< HEAD
        parameter.size = 240;
        byeFont = gen.generateFont(parameter);
        byeFont.setColor(Color.valueOf("39c6e6"));
=======
        parameter.size = 150;
        bigFont = gen.generateFont(parameter);
        bigFont.setColor(Color.valueOf("39c6e6"));
        parameter.size = 300;
        hugeFont = gen.generateFont(parameter);
        hugeFont.setColor(Color.valueOf("39c6e6"));
>>>>>>> origin/develop
    }

    private static void loadSprites() {
        blank = new Texture(Gdx.files.internal("images/blank.png"));
<<<<<<< HEAD
        buttons = new Texture(Gdx.files.internal("ui/buttons.png"));
        play = new Sprite(new TextureRegion(buttons, 0, 0, 64, 24));
        exit = new Sprite(new TextureRegion(buttons, 0, 24, 64, 24));
        singleplayer = new Sprite(new TextureRegion(buttons, 0, 48, 192, 24));
        multiplayer =  new Sprite(new TextureRegion(buttons, 0, 72, 192, 24));
=======

        selectArrow = new Texture(Gdx.files.internal("images/SelectArrow.png"));
        buttons = new Texture(Gdx.files.internal("ui/buttons.png"));
        playerTex1 = new Texture(Gdx.files.internal("images/characters/MainCharacter_ONE.png"));
        play = new Sprite(new TextureRegion(buttons, 0, 0, 64, 24));
        exit = new Sprite(new TextureRegion(buttons, 0, 24, 64, 24));

        singleplayer = new Sprite(new TextureRegion(buttons, 0, 48, 192, 24));
        multiplayer = new Sprite(new TextureRegion(buttons, 0, 72, 192, 24));
>>>>>>> origin/develop

        mainSpriteBack1 = new Sprite(new Texture(Gdx.files.internal("images/backgrounds/menu2.png")));
        mainSpriteBack2 = new Sprite(new Texture(Gdx.files.internal("images/backgrounds/menu2.png")));

<<<<<<< HEAD
        levelsTexture = new Texture(Gdx.files.internal("images/backgrounds/levels.png"));
        spriteBack = new Sprite(levelsTexture, 0, 0, 720, 160);

        // easter egg

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
        playerTex = new Texture(Gdx.files.internal("images/characters/maincharacter.png"));
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
=======
        mpArenaTex = new Texture(Gdx.files.internal("images/arenas/mp-arena.png"));

        Texture buttonSheet = new Texture(Gdx.files.internal("images/arena_big_button.png"));
        arenaButton = TextureRegion.split(buttonSheet, 32, 21)[0];
    }

    private static void loadSounds() {
        SoundEffects.load("audio/menu_ambient.wav", "MenuBackground", 1, 0.025f); //used
        SoundEffects.load("audio/LobbyMusic.wav", "LobbyMusic", 3, 0.025f);  //used
        SoundEffects.load("audio/ChangeScreen.wav", "ChangeScreen", 6, 0.05f); //used
        SoundEffects.load("audio/bg_loop.wav", "BattleMusic", 7, 0.01f); //used
        SoundEffects.load("audio/BreakEnemyBone.wav", "BreakEnemyBone", 9, 0.01f); //used
        SoundEffects.load("audio/EnemyEscaping.wav", "EnemyEscaping", 11, 0.1f); //used badly
        SoundEffects.load("audio/PlayerGettingAttacked.wav", "PlayerGettingAttacked", 12, 0.01f); //used badly
        SoundEffects.load("audio/Whoosh1.wav", "Woosh1", 13, 0.1f); //used
        SoundEffects.load("audio/Whoosh2.wav", "Woosh2", 14, 0.1f); //used
        SoundEffects.load("audio/Whoosh3.wav", "Woosh3", 15, 0.1f);
        SoundEffects.loadMusic("audio/Jab.wav", "Jab", 0.5f); //used
        SoundEffects.loadMusic("audio/Kick.wav", "Kick", 0.01f); //used
        SoundEffects.loadMusic("audio/EnemyAttackSound-Huwawa.wav", "Huwawa", 0.5f);
        SoundEffects.loadMusic("audio/HealerHealingEnemies.wav", "HealerHealingEnemies", 0.8f); //used badly
        SoundEffects.loadMusic("audio/SelectButton.wav", "Navigate", 0.9f);  //used
        SoundEffects.loadMusic("audio/bg_loop.wav", "BattleMusic", 0.6f);  //used

    }

    private static void loadAnimations() {
        playerTex = new Texture(Gdx.files.internal("images/characters/MainCharacter_ONE-Sheet-newKick.png"));
        zombieTex = new Texture(Gdx.files.internal("images/characters/Enemy1.png"));
        bouncingBobTex = new Texture(Gdx.files.internal("images/characters/bouncing_bob.png"));
        healerTex = new Texture(Gdx.files.internal("images/characters/healer_sam.png"));
        healingTex = new Texture(Gdx.files.internal("images/healing.png"));
        loadingTex = new Texture(Gdx.files.internal("images/backgrounds/loading.png"));

        TextureRegion[][] player_temp = TextureRegion.split(playerTex, 16, 16);
        TextureRegion[] playerIdleSprites = Arrays.copyOfRange(player_temp[0], 0, 6, TextureRegion[].class);
        TextureRegion[] playerMoveSprites = Arrays.copyOfRange(player_temp[0], 6, 11, TextureRegion[].class);
        TextureRegion[] playerKickSprites = Arrays.copyOfRange(player_temp[0], 12, 16, TextureRegion[].class);
        TextureRegion[] playerJabSprites = Arrays.copyOfRange(player_temp[0], 16, 21, TextureRegion[].class);

        TextureRegion[][] temp = TextureRegion.split(loadingTex, 45, 72);
        TextureRegion[] loading_frames = new TextureRegion[37 * 7];
        int index = 0;
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 37; x++) {
                loading_frames[index++] = temp[y][x];
            }
        }
        loading = new Animation<>(0.02f, loading_frames);

        playerIdle = new Animation<>(0.15f, playerIdleSprites);
        playerMove = new Animation<>(0.15f, playerMoveSprites);
        playerKick = new Animation<>(0.1f, playerKickSprites);
        playerJab = new Animation<>(0.05f, playerJabSprites);

        TextureRegion[][] zombie_temp = TextureRegion.split(zombieTex, 16, 16);
        zombie = new Animation<>(0.2f, zombie_temp[0]);

        TextureRegion[][] bob_temp = TextureRegion.split(bouncingBobTex, 16, 16);
        bob_run = new Animation<>(0.2f, Arrays.copyOfRange(bob_temp[0], 0, 8, TextureRegion[].class));
        bob_flee = new Animation<>(0.2f, Arrays.copyOfRange(bob_temp[0], 8, 12, TextureRegion[].class));

        TextureRegion[][] healer_temp = TextureRegion.split(healerTex, 16, 16);
        healer_walking = new Animation<>(0.2f, Arrays.copyOfRange(healer_temp[0], 0, 4, TextureRegion[].class));
        healer_healing = new Animation<>(0.2f, Arrays.copyOfRange(healer_temp[0], 4, 6, TextureRegion[].class));

        TextureRegion[][] heal_temp = TextureRegion.split(healingTex, 16, 16);
        healing = new Animation<>(0.1f, heal_temp[0]);
    }

    public static void dispose() {
>>>>>>> origin/develop
    }
}