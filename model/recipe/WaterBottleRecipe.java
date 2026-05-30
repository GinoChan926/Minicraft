package org.minicraft02160.model.recipe;

import org.minicraft02160.model.Recipe;
import org.minicraft02160.model.Items.WaterBottleItem;
import org.minicraft02160.model.Item;
import java.util.HashMap;
import java.util.Map;

public class WaterBottleRecipe extends Recipe {
    public WaterBottleRecipe() {
        super("WaterBottle", createIngredients(), true);
    }

    private static Map<String, Integer> createIngredients() {
        Map<String, Integer> ingredients = new HashMap<>();
        ingredients.put("Sand", 2);
        ingredients.put("Stone", 1);
        ingredients.put("Wood", 1);
        return ingredients;
    }

    @Override
    public Item craft() {
        return new WaterBottleItem();
    }
}
