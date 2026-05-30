package org.minicraft02160.view;

import org.minicraft02160.controller.CraftingMenuController;
import org.minicraft02160.model.CraftingMenuModel;
import org.minicraft02160.model.Item;
import org.minicraft02160.model.Player;
import org.minicraft02160.model.Recipe;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

public class CraftingMenu {

    private final CraftingMenuModel model;
    private final CraftingMenuController controller;
    private final Player player;

    public CraftingMenu(CraftingMenuModel model,
                        CraftingMenuController controller,
                        Player player) {
        this.model = model;
        this.controller = controller;
        this.player = player;
    }

    public void toggle() {
        controller.toggle();
    }

    public boolean isOpen() {
        return model.isOpen();
    }

    public void keyPressed(KeyEvent e) {
        controller.keyPressed(e);
    }

    public void render(Graphics g) {
        if (!model.isOpen()) return;

        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(50, 50, 500, 350);

        g.setColor(Color.WHITE);
        g.drawRect(50, 50, 500, 350);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("=== CRAFTING MENU ===", 170, 80);

        g.setFont(new Font("Arial", Font.BOLD, 13));
        if (model.getCurrentTab() == 0) {
            g.setColor(Color.YELLOW);
            g.drawString("[RECIPES]", 80, 105);
            g.setColor(Color.GRAY);
            g.drawString("[INVENTORY]", 180, 105);
        } else {
            g.setColor(Color.GRAY);
            g.drawString("[RECIPES]", 80, 105);
            g.setColor(Color.YELLOW);
            g.drawString("[INVENTORY]", 180, 105);
        }

        g.setColor(Color.GRAY);
        g.drawLine(60, 112, 540, 112);

        if (model.getCurrentTab() == 0) {
            List<Recipe> recipes = model.getRecipes();

            if (recipes.isEmpty()) {
                g.setColor(Color.GRAY);
                g.setFont(new Font("Arial", Font.PLAIN, 14));
                g.drawString("No recipes available.", 80, 140);
            } else {
                g.setFont(new Font("Arial", Font.BOLD, 13));
                g.setColor(Color.LIGHT_GRAY);
                g.drawString("Recipes:", 80, 130);

                for (int i = 0; i < recipes.size(); i++) {
                    Recipe r = recipes.get(i);
                    boolean canCraft = r.canCraft(player.getInventory());
                    boolean isSelected = (i == model.getSelectedIndex());

                    if (isSelected) {
                        g.setColor(Color.YELLOW);
                    } else if (canCraft) {
                        g.setColor(Color.GREEN);
                    } else {
                        g.setColor(Color.RED);
                    }

                    String prefix = isSelected ? "> " : "  ";
                    g.setFont(new Font("Arial", Font.PLAIN, 13));
                    g.drawString(prefix + r.getOutputName(), 80, 155 + i * 25);
                }

                g.setColor(Color.GRAY);
                g.drawLine(260, 118, 260, 370);

                Recipe selected = recipes.get(model.getSelectedIndex());

                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 13));
                g.drawString("Ingredients:", 280, 130);

                if (selected.isRequireWorkbench()) {
                    g.setColor(Color.ORANGE);
                    g.setFont(new Font("Arial", Font.PLAIN, 11));
                    g.drawString("* Requires Workbench", 280, 145);
                }

                int ingredientY = 165;
                g.setFont(new Font("Arial", Font.PLAIN, 13));

                for (Map.Entry<String, Integer> entry : selected.getIngredients().entrySet()) {
                    String ingredientName = entry.getKey();
                    int needed = entry.getValue();
                    int have = 0;

                    for (Item item : player.getInventory().getAllItems()) {
                        if (item.getName().equals(ingredientName)) {
                            have++;
                        }
                    }

                    if (have >= needed) {
                        g.setColor(Color.GREEN);
                    } else {
                        g.setColor(Color.RED);
                    }

                    g.drawString(ingredientName + ": " + have + "/" + needed, 280, ingredientY);
                    ingredientY += 22;
                }

                if (selected.canCraft(player.getInventory())) {
                    g.setColor(Color.GREEN);
                    g.drawString("[ENTER] Craft it!", 280, ingredientY + 10);
                } else {
                    g.setColor(Color.RED);
                    g.drawString("Missing materials!", 280, ingredientY + 10);
                }
            }

            g.setColor(new Color(255, 255, 255, 150));
            g.setFont(new Font("Arial", Font.PLAIN, 11));
            g.drawString("[W/S] Navigate    [ENTER] Craft    [I] Inventory    [C] Close", 80, 385);

        } else {
            List<Item> inventoryItems = player.getInventory().getAllItems();

            g.setFont(new Font("Arial", Font.BOLD, 13));
            g.setColor(Color.LIGHT_GRAY);
            g.drawString("Your Items (" + inventoryItems.size()
                    + "/" + player.getInventory().getMaxCapacity() + "):", 80, 130);

            if (inventoryItems.isEmpty()) {
                g.setColor(Color.GRAY);
                g.setFont(new Font("Arial", Font.PLAIN, 13));
                g.drawString("Inventory is empty.", 80, 155);
            } else {
                for (int i = 0; i < inventoryItems.size(); i++) {
                    Item item = inventoryItems.get(i);
                    boolean isSelected = (i == model.getSelectedIndex());

                    if (isSelected) {
                        g.setColor(Color.YELLOW);
                    } else {
                        g.setColor(Color.WHITE);
                    }

                    String prefix = isSelected ? "> " : "  ";
                    g.setFont(new Font("Arial", Font.PLAIN, 13));
                    g.drawString(prefix + item.getName(), 80, 155 + i * 25);
                }

                if (model.getSelectedIndex() < inventoryItems.size()) {
                    Item selectedItem = inventoryItems.get(model.getSelectedIndex());

                    g.setColor(Color.GRAY);
                    g.drawLine(260, 118, 260, 370);

                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Arial", Font.BOLD, 13));
                    g.drawString("Selected:", 280, 130);

                    g.setColor(Color.YELLOW);
                    g.setFont(new Font("Arial", Font.PLAIN, 13));
                    g.drawString(selectedItem.getName(), 280, 155);

                    g.setColor(Color.RED);
                    g.setFont(new Font("Arial", Font.PLAIN, 12));
                    g.drawString("[D] Drop this item", 280, 210);
                }
            }

            g.setColor(new Color(255, 255, 255, 150));
            g.setFont(new Font("Arial", Font.PLAIN, 11));
            g.drawString("[W/S] Navigate    [R] Equip Item    [D] Drop Item    [I] Recipes    [C] Close", 80, 385);
        }
    }
}