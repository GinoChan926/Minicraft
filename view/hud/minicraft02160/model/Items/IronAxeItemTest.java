package org.minicraft02160.model.Items;

import org.junit.jupiter.api.Test;
import org.minicraft02160.model.Inventory;

import static org.junit.jupiter.api.Assertions.*;

class IronAxeItemTest {
    IronAxeItem axe = new IronAxeItem();

    @Test
    void onUnequip() {
        Inventory inventory = new Inventory();
        axe.onEquip(inventory);
        assertEquals(Inventory.AXE_BONUS_ATTACK, inventory.getBonusAttack());

        axe.onUnequip(inventory);
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