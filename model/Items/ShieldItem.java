package org.minicraft02160.model.Items;

import org.minicraft02160.model.EquipableItem;
import org.minicraft02160.model.extras.EquipmentSlot;
import org.minicraft02160.model.Inventory;

public class ShieldItem extends EquipableItem{
    public ShieldItem() {
        super("Shield");
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.OFF_HAND;
    }

    @Override
    public void onEquip(Inventory inventory) {
        inventory.addBonusDefense(Inventory.SHIELD_BONUS_DEFENSE);
    }

    @Override
    public void onUnequip(Inventory inventory) {
        inventory.addBonusDefense(-Inventory.SHIELD_BONUS_DEFENSE);
    }
}
