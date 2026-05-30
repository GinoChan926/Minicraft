package org.minicraft02160.model;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.minicraft02160.model.extras.MobType.SHEEP;

class TileModelTest {

    private TileModel tile;

    @Test
    void getMob() {
        tile = new TileModel(0, 0);
        assertNull(tile.getMob());

        MobModel mob = new MobModel(SHEEP, 10, 2);
        tile.setMob(mob);
        assertEquals(mob, tile.getMob());
    }

    @Test
    void getTileX() {
        TileModel tile = new TileModel(3, 7);
        assertEquals(3, tile.getTileX());

        tile.setTileX(5);
        assertEquals(5, tile.getTileX());

    }

    @Test
    void getTileY() {
        TileModel tile = new TileModel(3, 7);
        assertEquals(7, tile.getTileY());

        tile.setTileY(9);
        assertEquals(9, tile.getTileY());
    }

    @Test
    void isWalkable() {
        TileModel tile = new TileModel(0, 0);
        assertTrue(tile.isWalkable());

        tile.setWalkable(false);
        assertFalse(tile.isWalkable());
    }
}