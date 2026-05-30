package org.minicraft02160.model.recipe;

import org.minicraft02160.model.Recipe;
import org.minicraft02160.model.Items.LighterWithFireItem;
import org.minicraft02160.model.Item;
import java.util.HashMap;
import java.util.Map;

public class LighterWithFireRecipe extends Recipe {
    public LighterWithFireRecipe() {
        super("Lighter With Fire", createIngredients(), true);
    }

    private static Map<String, Integer> createIngredients() {
        Map<String, Integer> ingredients = new HashMap<>();
        ingredients.put("Wool", 2);
        ingredients.put("Fur", 2);
        ingredients.put("Wood", 2);
        return ingredients;
    }

    @Override
    public Item craft() {
        return new LighterWithFireItem();
    }
}
