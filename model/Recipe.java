package org.minicraft02160.model;

import java.util.Map;

// abstract class for the crafting recipes, used for the Template Method pattern
// each recipe will have a name, a list of ingredients, and whether it requires a workbench
public abstract class Recipe {
    private String outputName;
    private Map<String, Integer> ingredients;
    private boolean requireWorkbench;

    public Recipe(String outputName, Map<String, Integer> ingredients, boolean requireWorkbench) {
        this.outputName = outputName;
        this.ingredients = ingredients;
        this.requireWorkbench = requireWorkbench;
    }

    // check if the provider, which is the inventory, has all required ingredients
    public boolean canCraft(CraftingMaterialProvider provider) {
        for (String itemName : ingredients.keySet()) {
            int needed = ingredients.get(itemName);
            if (!provider.hasItem(itemName, needed)) {
                return false;
            }
        }
        return true;
    }


    public void consumeIngredients(CraftingMaterialProvider provider) {
        for (String itemName : ingredients.keySet()) {
            provider.removeItem(itemName, ingredients.get(itemName));
        }
    }

    // subclasses will define their own crafting behavriour
    public abstract Item craft();

    public String getOutputName() {
        return outputName;
    }
    public Map<String, Integer> getIngredients() {
        return ingredients;
    }
    public boolean isRequireWorkbench() {
        return requireWorkbench;
    }
}
