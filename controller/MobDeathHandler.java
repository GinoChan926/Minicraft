package org.minicraft02160.controller;

import org.minicraft02160.model.Player;
import org.minicraft02160.model.extras.ConsumableItem;

import java.util.List;

public class MobDeathHandler {

    private final DroppedConsumableManager droppedConsumableManager;

    public MobDeathHandler(DroppedConsumableManager droppedConsumableManager) {
        this.droppedConsumableManager = droppedConsumableManager;
    }

    public void onMobDeath(MobController mob, Player player) {
        if (mob == null) return;

        List<ConsumableItem> drops = mob.getDrops();

        for (ConsumableItem drop : drops) {
            int x = mob.getX();
            int y = mob.getY();

            droppedConsumableManager.drop(drop, x, y);

            System.out.println("Dropped: " + drop.name()
                    + " at (" + x + ", " + y + ")");
        }
    }
}