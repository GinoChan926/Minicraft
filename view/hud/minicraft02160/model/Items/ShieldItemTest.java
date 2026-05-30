package org.minicraft02160.model.Items;

import org.junit.jupiter.api.Test;
import org.minicraft02160.model.Inventory;
import org.minicraft02160.model.extras.EquipmentSlot;

import static org.junit.jupiter.api.Assertions.*;

class ShieldItemTest {
    ShieldItem shield = new ShieldItem();
    Inventory inventory = new Inventory();

    @Test
    void getSlot() {
        assertEquals(EquipmentSlot.OFF_HAND, shield.getSlot());
    }

    @Test
    void onEquip() {
        inventory.equip(shield);
        assertTrue(inventory.hasEquipped(EquipmentSlot.OFF_HAND));
        assertEquals(Inventory.SHIELD_BONUS_DEFENSE, inventory.getBonusDefense());
    }

    @Test
    void onUnequip() {
        inventory.equip(shield);
        inventory.unequip(shield);
        assertFalse(inventory.hasEquipped(EquipmentSlot.OFF_HAND));
        assertEquals(0, inventory.getBonusDefense());
    }
}