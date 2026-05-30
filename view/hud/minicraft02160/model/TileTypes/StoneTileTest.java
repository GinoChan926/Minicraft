package org.minicraft02160.model.TileTypes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoneTileTest {

    @Test
    void isWalkable() {
        StoneTile stone = new StoneTile(null);
        assertFalse(stone.isWalkable());
    }
}