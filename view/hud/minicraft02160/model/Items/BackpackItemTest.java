package org.minicraft02160.model.Items;

import org.junit.jupiter.api.Test;
import org.minicraft02160.model.Inventory;
import org.minicraft02160.model.extras.EquipmentSlot;

import static org.junit.jupiter.api.Assertions.*;

class BackpackItemTest {
    BackpackItem backpack = new BackpackItem();

    @Test
    void getSlot() {
        assertEquals(EquipmentSlot.BACK, backpack.getSlot());
    }

    @Test
    void onEquip() {
        Inventory inventory = new Inventory();
        int initialCapacity = inventory.getMaxCapacity();

        backpack.onEquip(inventory);
        assertEquals(initialCapacity + Inventory.BACKPACK_BONUS_SLOTS, inventory.getMaxCapacity());
    }


    @Test
    void onUnequip() {
        Inventory inventory = new Inventory();
        int initialCapacity = inventory.getMaxCapacity();

        backpack.onEquip(inventory);
        backpack.onUnequip(inventory);
        assertEquals(initialCapacity, inventory.getMaxCapacity());
    }
}