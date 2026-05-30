package org.minicraft02160.model.Items;

import org.junit.jupiter.api.Test;
import org.minicraft02160.model.Inventory;
import org.minicraft02160.model.extras.EquipmentSlot;

import static org.junit.jupiter.api.Assertions.*;

class SuperAxeItemTest {
    SuperAxeItem axe = new SuperAxeItem();

    @Test
    void getSlot() {
        assertEquals(EquipmentSlot.MAIN_HAND, axe.getSlot());
    }

    @Test
    void onEquip() {
        Inventory inventory = new Inventory();
        inventory.equip(axe);
        assertTrue(inventory.hasEquipped(EquipmentSlot.MAIN_HAND));
        assertEquals(Inventory.SUPER_AXE_BONUS_ATTACK, inventory.getBonusAttack());
    }

    @Test
    void onUnequip() {
        Inventory inventory = new Inventory();
        inventory.equip(axe);
        inventory.unequip(axe);
        assertFalse(inventory.hasEquipped(EquipmentSlot.MAIN_HAND));
        assertEquals(0, inventory.getBonusAttack());
    }

    @Test
    void canHarvest() {
        assertTrue(axe.canHarvest());

        axe.reduceDurability(axe.getDurability());
        assertTrue(axe.isBroken());
        assertFalse(axe.canHarvest());
    }
}