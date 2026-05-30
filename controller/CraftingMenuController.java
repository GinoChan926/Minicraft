package org.minicraft02160.controller;

import org.minicraft02160.model.*;
import org.minicraft02160.model.Items.ResourceItem;

import java.awt.event.KeyEvent;
import java.util.List;

public class CraftingMenuController {

    private final CraftingMenuModel model; // Menu stage stored here
    private final Player player;

    public CraftingMenuController(CraftingMenuModel model, Player player) {
        this.model = model;
        this.player = player;
    }

    public void toggle() {
        model.toggle();
    }

    public void navigateDown() {
        int listSize = model.getCurrentTab() == 0
                ? model.getRecipes().size()
                : player.getInventory().getSize();
        if (model.getSelectedIndex() < listSize - 1) {
            model.setSelectedIndex(model.getSelectedIndex() + 1);
        }
    }

    public void navigateUp() {
        if (model.getSelectedIndex() > 0) {
            model.setSelectedIndex(model.getSelectedIndex() - 1);
        }
    }

    public void switchTab() {
        model.setCurrentTab(model.getCurrentTab() == 0 ? 1 : 0);
        model.setSelectedIndex(0);
    }

    //manages crafting the recipe, check for validity
    public boolean craftSelected() {
        if (model.getRecipes().isEmpty()) return false;
        Recipe recipe = model.getRecipes().get(model.getSelectedIndex());

        if (!recipe.canCraft(player.getInventory())) {
            System.out.println("Not enough materials");
            return false;
        }
        if (recipe.isRequireWorkbench() && !model.isAtWorkbench()) {
            System.out.println("Need a workbench");
            return false;
        }
        if (player.getInventory().isFull()) {
            System.out.println("Inventory is full");
            return false;
        }

        // remove used materials, creat the item, add to inventory
        recipe.consumeIngredients(player.getInventory());
        Item crafted = recipe.craft();
        player.getInventory().add(crafted);
        System.out.println("Crafted: " + crafted.getName());
        return true;
    }

    // manages the interaction with item, special: Apple heals player
    public void equipSelected() {
        if (model.getCurrentTab() != 1) return;
        List<Item> inventoryItems = player.getInventory().getAllItems();
        if (inventoryItems.isEmpty()) return;
        if (model.getSelectedIndex() >= inventoryItems.size()) return;

        Item item = inventoryItems.get(model.getSelectedIndex());

        if (item instanceof ResourceItem resourceItem
                && resourceItem.isConsumable()
                && "Apple".equalsIgnoreCase(resourceItem.getName())) {
            if (player.getInventory().remove(item)) {
                for (int i = 0; i < resourceItem.getHealthRestore(); i++) {
                    player.gainLife();
                }
                System.out.println("Ate apple and restored hearts.");
            }
            if (model.getSelectedIndex() >= player.getInventory().getSize()) {
                model.setSelectedIndex(Math.max(0, player.getInventory().getSize() - 1));
            }
            return;
        }

        if (!(item instanceof Equipable equipable)) {
            System.out.println(item.getName() + " cannot be equipped");
            return;
        }

        equipable.use(player);

        if (model.getSelectedIndex() >= player.getInventory().getSize()) {
            model.setSelectedIndex(Math.max(0, player.getInventory().getSize() - 1));
        }
    }

    // manages dropping items on the tiles, removing item from inventory
    public boolean dropSelected() {
        if (model.getCurrentTab() == 0) {
            System.out.println("You are on recipe tab.");
            return false;
        }
        if (player.getInventory().isEmpty()) {
            System.out.println("Your inventory is empty");
            return false;
        }

        List<Item> inventoryItems = player.getInventory().getAllItems();
        Item droppedItem = inventoryItems.get(model.getSelectedIndex());
        player.getInventory().remove(droppedItem);
        player.getWorld().dropItemAt(droppedItem, player.getX(), player.getY());

        if (model.getSelectedIndex() >= player.getInventory().getSize()) {
            model.setSelectedIndex(Math.max(0, player.getInventory().getSize() - 1));
        }
        return true;
    }

    public void keyPressed(KeyEvent e) {
        if (!model.isOpen()) return;
        switch (e.getKeyChar()) {
            case 'w', 'W' -> navigateUp();
            case 's', 'S' -> navigateDown();
            case '\n', ' ' -> craftSelected();
            case 'c', 'C' -> toggle();
            case 'i', 'I' -> switchTab();
            case 'd', 'D' -> dropSelected();
            case 'r', 'R' -> equipSelected();
        }
    }

    public CraftingMenuModel getModel() { return model; }
}