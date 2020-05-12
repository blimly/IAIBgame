package inc.heterological.iaibgame.net.server;

import com.badlogic.gdx.math.Vector2;
import inc.heterological.iaibgame.desktop.characters.Player;
import inc.heterological.iaibgame.net.shared.packets.PlayerEntity;
import junit.framework.TestCase;

import java.util.stream.Collectors;

public class OnlineArenaTest extends TestCase {

    public void testKillIfDead() {
        OnlineArena oArena = new OnlineArena();
        PlayerEntity player = new PlayerEntity();
        player.currentState = Player.Condition.IDLE;
        player.facingRight = true;
        player.pos = new Vector2(858, 880);
        player.health = 100;
        player.id = 0;
        oArena.addPlayer(player);
        oArena.update(1);
        oArena.getEnemies().get(1).health -= 100;
        assertEquals(5, oArena.getEnemies().size());
        oArena.update(1);
        assertEquals(4, oArena.getEnemies().size());
    }

    public void testGetHitRightKickAndJab() {
        OnlineArena oArena = new OnlineArena();
        PlayerEntity player = new PlayerEntity();
        player.currentState = Player.Condition.KICK;
        player.facingRight = true;
        player.pos = new Vector2(858, 880);
        player.health = 100;
        player.id = 0;
        oArena.addPlayer(player);
        oArena.update(1);
        oArena.getPlayers().get(0).pos = new Vector2(910, 1200);
        oArena.getHit(oArena.getEnemies().get(0));
        assertEquals(94, oArena.getEnemies().get(0).health);
        oArena.getPlayers().get(0).currentState = Player.Condition.JAB;
        oArena.getHit(oArena.getEnemies().get(0));
        assertEquals(82, oArena.getEnemies().get(0).health);
    }

    public void testGetHitLeftKickAndJab() {
        OnlineArena oArena = new OnlineArena();
        PlayerEntity player = new PlayerEntity();
        player.currentState = Player.Condition.KICK;
        player.facingRight = false;
        player.pos = new Vector2(858, 880);
        player.health = 100;
        player.id = 0;
        oArena.addPlayer(player);
        oArena.update(1);
        oArena.getPlayers().get(0).pos = new Vector2(915, 1200);
        oArena.getHit(oArena.getEnemies().get(0));
        assertEquals(94, oArena.getEnemies().get(0).health);
        oArena.getPlayers().get(0).currentState = Player.Condition.JAB;
        oArena.getHit(oArena.getEnemies().get(0));
        assertEquals(82, oArena.getEnemies().get(0).health);
    }

    public void testAttackPlayers() {
    }

    public void testGetNearestTargetAndUpdate() {
        OnlineArena oArena = new OnlineArena();
        PlayerEntity player = new PlayerEntity();
        player.currentState = Player.Condition.IDLE;
        player.facingRight = true;
        player.pos = new Vector2(858, 880);
        player.health = 100;
        player.id = 0;
        oArena.addPlayer(player);
        oArena.update(1);
        oArena.getPlayers().get(0).pos = new Vector2(922, 1212);
        oArena.update(1);
        assertEquals(oArena.getPlayers().get(0).pos, oArena.getEnemies().get(0).target);
    }


    public void testEnemySeek() {
        OnlineArena oArena = new OnlineArena();
        PlayerEntity player = new PlayerEntity();
        player.currentState = Player.Condition.KICK;
        player.facingRight = false;
        player.pos = new Vector2(858, 880);
        player.health = 100;
        player.id = 0;
        oArena.addPlayer(player);
        oArena.update(1);
        oArena.enemySeek(oArena.getEnemies().get(0), new Vector2(920,1200));
        assertEquals(new Vector2((float) -1710.0, (float) -0.2), oArena.getEnemies().get(0).acc);
    }

    public void testRemovePlayer() {
        OnlineArena oArena = new OnlineArena();
        oArena.addPlayer(new PlayerEntity());
        assertEquals(1, oArena.getPlayers().size());
        oArena.removePlayer(0);
        assertEquals(0, oArena.getPlayers().size());
        assertFalse(oArena.removePlayer(0));
    }

}