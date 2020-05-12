package inc.heterological.iaibgame.desktop.managers;

import inc.heterological.iaibgame.desktop.screens.GameState;
import inc.heterological.iaibgame.desktop.screens.MainMenu;
import inc.heterological.iaibgame.desktop.screens.MultiplayerArena;
import inc.heterological.iaibgame.desktop.screens.MultiplayerLobby;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameStateManagerTest {

    @Test
    public void setGetGameState() {
        GameStateManager gsm = new GameStateManager();
        gsm.setGameState(GameStateManager.LOBBY);
        assertEquals(new MultiplayerLobby(gsm), gsm.getGameState());
    }
}