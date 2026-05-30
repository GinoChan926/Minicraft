package org.minicraft02160.model.Items;

import org.minicraft02160.model.EquipableItem;
import org.minicraft02160.model.Inventory;
import org.minicraft02160.model.extras.EquipmentSlot;
import org.minicraft02160.model.WaterCollector;

public class WaterBottleItem extends EquipableItem implements WaterCollector{
    public WaterBottleItem() {
        super("Water bottle");
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.OFF_HAND;
    }

    @Override
    public boolean canCollectWater() {
        return !isBroken();
    }
}
