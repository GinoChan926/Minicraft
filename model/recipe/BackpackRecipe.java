package org.minicraft02160.model.recipe;

import org.minicraft02160.model.Recipe;
import org.minicraft02160.model.Items.BackpackItem;
import org.minicraft02160.model.Item;
import java.util.HashMap;
import java.util.Map;

public class BackpackRecipe extends Recipe {
    public BackpackRecipe() {
        super("Backpack", createIngredients(), true);
    }

    private static Map<String, Integer> createIngredients() {
        Map<String, Integer> ingredients = new HashMap<>();
        ingredients.put("Fur", 1);
        ingredients.put("Wool", 2);
        return ingredients;
    }

    @Override
    public Item craft() {
        return new BackpackItem();
    }
}
