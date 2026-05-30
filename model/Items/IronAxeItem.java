package org.minicraft02160.model.Items;

import org.minicraft02160.model.EquipableItem;
import org.minicraft02160.model.Harvestable;
import org.minicraft02160.model.Inventory;
import org.minicraft02160.model.extras.EquipmentSlot;
import org.minicraft02160.model.Harvestable;

public class IronAxeItem extends EquipableItem implements Harvestable {
    public IronAxeItem() {
        super("Iron Axe", 10);
        this.setDamage(5);
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.MAIN_HAND;
    }

    @Override
    public void onEquip(Inventory inventory) {
        inventory.addBonusAttack(Inventory.AXE_BONUS_ATTACK);
    }

    @Override
    public void onUnequip(Inventory inventory) {
        inventory.addBonusAttack(-Inventory.AXE_BONUS_ATTACK);
    }

    @Override
    public boolean canHarvest() {
        return !isBroken();
    }
}