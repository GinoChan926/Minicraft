package org.minicraft02160.model.recipe;

import org.minicraft02160.model.Recipe;
import org.minicraft02160.model.Items.SuperAxeItem;
import org.minicraft02160.model.Item;
import java.util.HashMap;
import java.util.Map;

public class SuperAxeRecipe extends Recipe {
    public SuperAxeRecipe() {
        super("Super Axe", createIngredients(), true);
    }

    private static Map<String, Integer> createIngredients() {
        Map<String, Integer> ingredients = new HashMap<>();
        ingredients.put("Iron Axe", 1);
        ingredients.put("Stone", 1);
        ingredients.put("Fur", 1);
        return ingredients;
    }

    @Override
    public Item craft() {
        return new SuperAxeItem();
    }
}
