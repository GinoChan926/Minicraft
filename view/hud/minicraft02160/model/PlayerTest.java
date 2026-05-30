package org.minicraft02160.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.minicraft02160.model.extras.CardinalPoints;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    private final WorldPort walkableWorld = new WorldPort() {
        @Override public void repaint() {}
        @Override public boolean isTileWalkable(int x, int y) { return true; }
        @Override public void dropItemAt(Item item, int x, int y) {}
    };


    @BeforeEach
    void setUp() {
        player = new Player(5, 5, CardinalPoints.N);
        player.setWorld(walkableWorld);
    }

    @Test
    void moveNorth() {
        player.moveNorth();
        assertEquals(4, player.getY());
        assertEquals(CardinalPoints.N, player.getDirection());
    }

    @Test
    void moveSouth() {
        player.moveSouth();
        assertEquals(6, player.getY());
        assertEquals(CardinalPoints.S, player.getDirection());
    }

    @Test
    void moveEast() {
        player.moveEast();
        assertEquals(6, player.getX());
        assertEquals(CardinalPoints.E, player.getDirection());
    }

    @Test
    void moveWest() {
        player.moveWest();
        assertEquals(4, player.getX());
        assertEquals(CardinalPoints.W, player.getDirection());
    }

    @Test
    void getX() {
        assertEquals(5, player.getX());
    }

    @Test
    void getY() {
        assertEquals(5, player.getY());
    }

    @Test
    void setPosition() {
        player.setPosition(1, 7);
        assertEquals(1, player.getX());
        assertEquals(7, player.getY());
    }

    @Test
    void getDirection() {
        assertEquals(CardinalPoints.N, player.getDirection());
    }

    @Test
    void getLives() {
        assertEquals(5, player.getLives());
    }

    @Test
    void getMaxLives() {
        assertEquals(5, player.getMaxLives());
    }

    @Test
    void isAlive() {
        assertTrue(player.isAlive());
        player.takeDamage(1);
        assertTrue(player.isAlive());
    }

    @Test
    void gainLife() {
        player.takeDamage(2);
        assertEquals(3, player.getLives());
        player.gainLife();
        assertEquals(4, player.getLives());
    }

    @Test
    void getInventory() {
        assertNotNull(player.getInventory());
    }

    @Test
    void getEquippedMainHand() {
        assertNull(player.getEquippedMainHand());
    }

    @Test
    void getEquippedOffHand() {
        assertNull(player.getEquippedOffHand());
    }

    @Test
    void unequipMainHand() {
        // No exception should be thrown even if nothing is equipped
        player.unequipMainHand();
    }

    @Test
    void setWorld() {
        assertDoesNotThrow(() -> player.setWorld(walkableWorld));
    }

    @Test
    void takeDamage() {
        player.takeDamage(3);
        assertEquals(2, player.getLives());
        assertTrue(player.isAlive());

        player.takeDamage(2);
        assertEquals(0, player.getLives());
        assertFalse(player.isAlive());
    }


    @Test
    void getWorld() {
        assertEquals(walkableWorld, player.getWorld());
    }
}