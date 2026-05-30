package org.minicraft02160.model.Items;

import org.minicraft02160.model.EquipableItem;
import org.minicraft02160.model.extras.EquipmentSlot;
import org.minicraft02160.model.Inventory;

public class BackpackItem extends EquipableItem{
    public BackpackItem() {
        super("Backpack");
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.BACK;
    }

    @Override
    public void onEquip(Inventory inventory) {
        inventory.addBonusCapacity(Inventory.BACKPACK_BONUS_SLOTS);
    }

    @Override
    public void onUnequip(Inventory inventory) {
        inventory.addBonusCapacity(-Inventory.BACKPACK_BONUS_SLOTS);
    }
}
