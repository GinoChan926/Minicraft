package org.minicraft02160.controller.handlers;

import org.minicraft02160.model.ResourceInteractionHandler;
import org.minicraft02160.model.EquipableItem;
import org.minicraft02160.model.Inventory;
import org.minicraft02160.model.Items.ResourceItem;
import org.minicraft02160.model.Player;
import org.minicraft02160.model.WaterCollector;
import org.minicraft02160.model.extras.ResourceType;
import org.minicraft02160.view.Tile;
import org.minicraft02160.view.World;
// manages Water interaction with player
public class WaterHandler implements ResourceInteractionHandler {


    @Override
    public boolean canHandle(ResourceType resourceType) {
        return resourceType == ResourceType.WATER;
    }

    // Check for valid player input, handles interaction with water
    @Override
    public void handle(Player player, Tile tile, World world) {
        EquipableItem offHand = player.getEquippedOffHand();

        if (offHand == null
                || !(offHand instanceof WaterCollector wc)
                || !wc.canCollectWater()) {
            System.out.println("Need a water bottle to harvest water!");
            return;
        }

        ResourceItem water = new ResourceItem("Water", true, 10);
        Inventory inventory = player.getInventory();

        if (inventory.add(water)) {
            System.out.println("Collected water!");
        } else {
            System.out.println("Inventory is full!");
            world.dropItemAt(water, player.getX(), player.getY());
        }
    }
}