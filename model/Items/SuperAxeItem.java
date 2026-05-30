package org.minicraft02160.model.Items;

import org.minicraft02160.model.EquipableItem;
import org.minicraft02160.model.Harvestable;
import org.minicraft02160.model.extras.EquipmentSlot;
import org.minicraft02160.model.Inventory;

public class SuperAxeItem extends EquipableItem implements Harvestable {
    public SuperAxeItem() {
        super("Super Axe", 20);
        this.setDamage(10);
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.MAIN_HAND;
    }

    @Override
    public void onEquip(Inventory inventory) {
        inventory.addBonusAttack(Inventory.SUPER_AXE_BONUS_ATTACK);
    }

    @Override
    public void onUnequip(Inventory inventory) {
        inventory.addBonusAttack(-Inventory.SUPER_AXE_BONUS_ATTACK);
    }

    @Override
    public boolean canHarvest() {
        return !isBroken();
    }

}
