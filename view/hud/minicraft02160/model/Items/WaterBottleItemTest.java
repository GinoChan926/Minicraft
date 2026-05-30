package org.minicraft02160.model.Items;

import org.junit.jupiter.api.Test;
import org.minicraft02160.model.extras.EquipmentSlot;

import static org.junit.jupiter.api.Assertions.*;

class WaterBottleItemTest {
    WaterBottleItem waterBottle = new WaterBottleItem();

    @Test
    void getSlot() {
        assertEquals(EquipmentSlot.OFF_HAND, waterBottle.getSlot());
    }

    @Test
    void canCollectWater() {
        assertTrue(waterBottle.canCollectWater());
    }
}