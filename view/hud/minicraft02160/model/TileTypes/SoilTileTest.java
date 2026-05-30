package org.minicraft02160.model.TileTypes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SoilTileTest {

    @Test
    void isWalkable() {
        SoilTile soil = new SoilTile(null);
        assertTrue(soil.isWalkable());

    }
}