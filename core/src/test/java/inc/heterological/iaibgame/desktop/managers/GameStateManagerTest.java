package inc.heterological.iaibgame.desktop.managers;

import inc.heterological.iaibgame.desktop.screens.GameState;
import inc.heterological.iaibgame.desktop.screens.MainMenu;
import inc.heterological.iaibgame.desktop.screens.MultiplayerArena;
import inc.heterological.iaibgame.desktop.screens.MultiplayerLobby;
import org.junit.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

public class GameStateManagerTest {

    @Test
    public void setGetGameState() {
        GameStateManager gsm = new GameStateManager();
        gsm.setGameState(GameStateManager.MENU);
        assertThat(gsm.getGameState(), instanceOf(MainMenu.class));
        gsm.setGameState(GameStateManager.LOBBY);
        assertThat(gsm.getGameState(), instanceOf(MultiplayerLobby.class));
        gsm.setGameState(GameStateManager.PLAY_MULTIPLAYER);
        assertThat(gsm.getGameState(), instanceOf(MultiplayerArena.class));
    }
}