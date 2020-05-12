package inc.heterological.iaibgame.desktop.characters;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void moveLeft() {
        Player player = new Player();
        player.moveLeft(1);
        assertEquals(880, player.position.x, 0);
        assertFalse(player.facingRight);
    }

    @Test
    public void moveRight() {
        Player player = new Player();
        player.moveRight(1);
        assertEquals(880, player.position.x, 0);
        assertTrue(player.facingRight);
    }

    @Test
    public void moveUp() {
        Player player = new Player();
        player.moveUp(1);
        assertEquals(600, player.position.y, 0);
    }

    @Test
    public void moveDown() {
        Player player = new Player();
        player.moveDown(1);
        assertEquals(600, player.position.y, 0);

    }

    @Test
    public void jab() {
        Player player = new Player();
        player.jab();
        assertEquals(Player.Condition.JAB, player.currentState);
    }

    @Test
    public void kick() {
        Player player = new Player();
        player.kick();
        assertEquals(Player.Condition.KICK, player.currentState);
    }

    @Test
    public void stand() {
        Player player = new Player();
        player.stand();
        assertEquals(Player.Condition.IDLE, player.currentState);
    }
}