package org.minicraft02160.model.recipe;

import org.minicraft02160.model.Recipe;
import org.minicraft02160.model.Items.ShieldItem;
import org.minicraft02160.model.Item;
import java.util.HashMap;
import java.util.Map;

public class ShieldRecipe extends Recipe {
    public ShieldRecipe() {
        super("Shield", createIngredients(), true);
    }

    private static Map<String, Integer> createIngredients() {
        Map<String, Integer> ingredients = new HashMap<>();
        ingredients.put("Stone", 3);
        ingredients.put("Wood", 1);
        return ingredients;
    }

    @Override
    public Item craft() {
        return new ShieldItem();
    }
}
