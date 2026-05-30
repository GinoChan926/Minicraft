package org.minicraft02160.model.Items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceItemTest {

    ResourceItem resourceItemApple = new ResourceItem("apple", true, 10);
    ResourceItem resourceItemStone = new ResourceItem("stone", false, 0);

    @Test
    void isConsumable() {
        assertTrue(resourceItemApple.isConsumable());

        assertFalse(resourceItemStone.isConsumable());
    }

    @Test
    void getHealthRestore() {
        assertEquals(10, resourceItemApple.getHealthRestore());
        assertEquals(0, resourceItemStone.getHealthRestore());
    }
}