package org.minicraft02160.model.extras;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsumableItemTest {

    @Test
    void getType() {
        assertTrue(ConsumableItem.WOOL.getType());
        assertFalse(ConsumableItem.FUR.getType());
    }

    @Test
    void values() {
        ConsumableItem[] items = ConsumableItem.values();
        assertEquals(2, items.length);
        assertEquals(ConsumableItem.WOOL, items[0]);
        assertEquals(ConsumableItem.FUR, items[1]);
    }

    @Test
    void valueOf() {
        assertEquals(ConsumableItem.WOOL, ConsumableItem.valueOf("WOOL"));
        assertEquals(ConsumableItem.FUR, ConsumableItem.valueOf("FUR"));
    }
}