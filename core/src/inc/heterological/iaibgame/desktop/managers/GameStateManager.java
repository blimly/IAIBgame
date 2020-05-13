package inc.heterological.iaibgame.desktop.managers;


import inc.heterological.iaibgame.desktop.screens.GameState;
import inc.heterological.iaibgame.desktop.screens.MainMenu;
import inc.heterological.iaibgame.desktop.screens.MultiplayerArena;
import inc.heterological.iaibgame.desktop.screens.MultiplayerLobby;


public class GameStateManager {

    public static final int MENU = 0;
    public static final int LOBBY = 4;
    public static final int PLAY_MULTIPLAYER = 5;
    private GameState gameState;

    public GameStateManager() {
        setGameState(MENU);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(int state) {
        if (gameState != null) gameState.dispose();

        if (state == MENU) {
            gameState = new MainMenu(this);
        }
        if (state == LOBBY) {
            gameState = new MultiplayerLobby(this);
        }
        if (state == PLAY_MULTIPLAYER) {
            gameState = new MultiplayerArena(this);
        }
    }

    public void update(float dt) {
        gameState.update(dt);
    }

    public void draw() {
        gameState.draw();
    }
}
