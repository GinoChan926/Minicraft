package org.minicraft02160.controller.handlers;

import org.minicraft02160.model.ResourceInteractionHandler;
import org.minicraft02160.model.EquipableItem;
import org.minicraft02160.model.Inventory;
import org.minicraft02160.model.Items.ResourceItem;
import org.minicraft02160.model.Player;
import org.minicraft02160.model.extras.ResourceType;
import org.minicraft02160.view.Tile;
import org.minicraft02160.view.World;

// Manages Interaction with Stone
public class StoneHandler implements ResourceInteractionHandler {

    @Override
    public boolean canHandle(ResourceType resourceType) {
        return resourceType == ResourceType.STONE;
    }

    // Check: is player input valid (tool equipped), handles stone actions
    @Override
    public void handle(Player player, Tile tile, World world) {
        EquipableItem mainHand = player.getEquippedMainHand();

        if (mainHand == null || mainHand.isBroken()) {
            System.out.println("Need a tool to harvest stone!");
            return;
        }

        ResourceItem stone = new ResourceItem("Stone", false, 0);
        Inventory inventory = player.getInventory();

        if (inventory.add(stone)) {
            tile.setResource(null);
            mainHand.reduceDurability(1);
            System.out.println("Harvested stone! Tool durability: " + mainHand.getDurability());

            if (mainHand.isBroken()) {
                player.unequipMainHand();
                System.out.println("Tool broke!");
            }
        } else {
            System.out.println("Inventory is full!");
            world.dropItemAt(stone, player.getX(), player.getY());
        }
    }
}