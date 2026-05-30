package org.minicraft02160.model.recipe;

import org.minicraft02160.model.Recipe;
import org.minicraft02160.model.Inventory;

import java.util.ArrayList;
import java.util.List;

public class RecipeRegistry {
    private static List<Recipe> recipes = new ArrayList<>();

    static {
        recipes.add(new BackpackRecipe());
        recipes.add(new IronAxeRecipe());
        recipes.add(new ShieldRecipe());
        recipes.add(new WaterBottleRecipe());
        recipes.add(new LighterWithFireRecipe());
        recipes.add(new SuperAxeRecipe());
    }

    public static List<Recipe> getAllRecipes() {
        List<Recipe> allRecipe = new ArrayList<>();
        for (Recipe recipe : recipes) {
            allRecipe.add(recipe);
        }
        return allRecipe;
    }

    public static List<Recipe> getCraftableRecipe(Inventory inventory) {
        List<Recipe> craftable = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.canCraft(inventory)) {
                craftable.add(recipe);
            }
        }
        return craftable;
    }
}

