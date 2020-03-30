package inc.heterological.iaibgame.desktop.managers;


import inc.heterological.iaibgame.desktop.screens.GameState;
import inc.heterological.iaibgame.desktop.screens.MenuStateScreen;

public class GameStateManager {

    private GameState gameState;

    public static final int MENU = 0;
    public static final int PLAY = 3;

    public GameStateManager() {
        setGameState(MENU);
    }

    public void setGameState(int state) {
        if (gameState != null) gameState.dispose();

        if (state == MENU) {
            gameState = new MenuStateScreen(this);
        }
        if (state == PLAY) {
            // gamestate = new LevelStateOne(this)
        }
    }

    public void update(float dt) {
        gameState.update(dt);
    }

    public void draw() {
        gameState.draw();
    }
}
