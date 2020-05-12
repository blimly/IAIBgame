package inc.heterological.iaibgame.net.server;

import inc.heterological.iaibgame.net.shared.packets.Play;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;
import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

public class ServerLogicTest extends TestCase {

    public void testLoadArena() {
        ServerLogic serverLogic = new ServerLogic();
        serverLogic.loadArena();
        assertNotSame(null, serverLogic.getOnlineArena());
    }

    public void testTestRun() {
        ServerLogic serverLogic = new ServerLogic();
        Set<PlayerEntity> players = new HashSet<>();
        players.add(new PlayerEntity());
        serverLogic.run(players);
        assertNotNull(serverLogic.getLogicThread());
    }

    public void testAddPlayer() {
        ServerLogic serverLogic = new ServerLogic();
        serverLogic.loadArena();
        serverLogic.addPlayer(new PlayerEntity());
        assertEquals(1, ServerLogic.players.size());
    }

    public void testUpdatePlayers() {
    }

    public void testUpdateEnemies() {
        ServerLogic serverLogic = new ServerLogic();
        serverLogic.loadArena();
        Play.EntitiesToBeRemoved eRemoved = new Play.EntitiesToBeRemoved();
        Set<Integer> enemiIDs = new HashSet<>();
        enemiIDs.add(24);
        enemiIDs.add(23);
        OnlineArena.enemiesToRemove.addAll(enemiIDs);

    }

    public void testGetEnemies() {
        ServerLogic serverLogic = new ServerLogic();
        serverLogic.loadArena();
        assertEquals(0, serverLogic.getOnlineArena().getEnemies().size());
    }
}