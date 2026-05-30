package org.minicraft02160.controller.handlers;

import org.minicraft02160.model.ResourceInteractionHandler;
import org.minicraft02160.model.EquipableItem;
import org.minicraft02160.model.Harvestable;
import org.minicraft02160.model.Inventory;
import org.minicraft02160.model.Items.ResourceItem;
import org.minicraft02160.model.Player;
import org.minicraft02160.model.extras.ResourceType;
import org.minicraft02160.view.Tile;
import org.minicraft02160.view.World;

//Manages Interaction with AppleTree, both wood and apple
public class AppleTreeHandler implements ResourceInteractionHandler {

    // Check
    @Override
    public boolean canHandle(ResourceType resourceType) {
        return resourceType == ResourceType.APPLE_TREE
                || resourceType == ResourceType.EMPTY_APPLE_TREE;
    }

    @Override
    public void handle(Player player, Tile tile, World world) {
        EquipableItem mainHand = player.getEquippedMainHand();

        if (mainHand instanceof Harvestable harvestable && harvestable.canHarvest()) {
            harvestWood(player, tile, mainHand, world);
        } else if (tile.getResourceType() == ResourceType.APPLE_TREE) {
            harvestApple(player, tile, world);
        }
    }

    private void harvestApple(Player player, Tile tile, World world) {
        ResourceItem apple = new ResourceItem("Apple", true, 10);
        Inventory inventory = player.getInventory();

        if (inventory.add(apple)) {
            tile.setResource(ResourceType.EMPTY_APPLE_TREE);
            System.out.println("Harvested apple!");
        } else {
            System.out.println("Inventory is full!");
            world.dropItemAt(apple, player.getX(), player.getY());
        }
    }

    private void harvestWood(Player player, Tile tile, EquipableItem axe, World world) {
        if (axe.isBroken()) {
            System.out.println("Tool is broken!");
            player.unequipMainHand();
            return;
        }

        ResourceItem wood = new ResourceItem("Wood", false, 0);
        Inventory inventory = player.getInventory();

        if (inventory.add(wood)) {
            tile.setResource(ResourceType.EMPTY_APPLE_TREE);
            axe.reduceDurability(1);
            System.out.println("Harvested wood! Axe durability: " + axe.getDurability());

            if (axe.isBroken()) {
                player.unequipMainHand();
                System.out.println("Axe broke!");
            }
        } else {
            System.out.println("Inventory is full!");
            world.dropItemAt(wood, player.getX(), player.getY());
        }
    }
}