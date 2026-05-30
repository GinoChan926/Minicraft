package org.minicraft02160.model.Items;

import org.minicraft02160.model.EquipableItem;
import org.minicraft02160.model.LightSource;
import org.minicraft02160.model.extras.EquipmentSlot;

public class LighterWithFireItem extends EquipableItem implements LightSource {
    public LighterWithFireItem() {
        super("Lighter With Fire");
        this.setDamage(2);
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.MAIN_HAND;
    }

    @Override
    public int getLightRadiusAt(int distance) {
        if (distance == 0) return 0;
        else if (distance == 1 || distance == 2) return 120;
        else if (distance == 3) return 180;
        else return 215;
    }
}