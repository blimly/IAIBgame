package inc.heterological.iaibgame.desktop.managers;


import inc.heterological.iaibgame.desktop.screens.*;


public class GameStateManager {

    private GameState gameState;

    public static final int MENU = 0;
    public static final int LOBBY = 4;
    public static final int PLAY_MULTIPLAYER = 5;

    public GameStateManager() {
        setGameState(MENU);
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

    public GameState getGameState() {
        return gameState;
    }

    public void update(float dt) {
        gameState.update(dt);
    }

    public void draw() {
        gameState.draw();
    }
}
