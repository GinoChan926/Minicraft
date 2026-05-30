package org.minicraft02160.model.TileTypes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaterTileTest {

    @Test
    void isWalkable() {
        WaterTile water = new WaterTile(null);
        assertTrue(water.isWalkable());
    }
}