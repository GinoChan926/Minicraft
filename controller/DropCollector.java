package org.minicraft02160.controller;

import org.minicraft02160.model.Inventory;
import org.minicraft02160.model.Items.ResourceItem;
import org.minicraft02160.model.Player;

import java.util.List;

public class DropCollector {

    private final DroppedConsumableManager droppedConsumableManager;

    public DropCollector(DroppedConsumableManager droppedConsumableManager) {
        this.droppedConsumableManager = droppedConsumableManager;
    }

    // Checks validity of input, collects consumables form tile
    public void tryCollect(Player player) {
        if (player == null) return;

        int x = player.getX();
        int y = player.getY();

        List<DroppedConsumableEntry> entries = droppedConsumableManager.collectAt(x, y);

        if (entries.isEmpty()) return;

        Inventory inventory = player.getInventory();

        // try to add collected items to inventory
        for (DroppedConsumableEntry entry : entries) {
            if (inventory.isFull()) {
                droppedConsumableManager.drop(entry.getConsumableItem(), x, y);
                System.out.println("Inventory full, cannot pick up: " + entry.getConsumableItem().name());
                continue;
            }

            // Convert Consumables to Item in Inventory
            ResourceItem item = new ResourceItem(entry.getConsumableItem().name(), false, 0);
            inventory.add(item);
            System.out.println("Picked up: " + item.getName());
        }
    }
}