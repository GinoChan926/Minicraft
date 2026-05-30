package org.minicraft02160.model.recipe;

import org.minicraft02160.model.Recipe;
import org.minicraft02160.model.Items.IronAxeItem;
import org.minicraft02160.model.Item;
import java.util.HashMap;
import java.util.Map;

public class IronAxeRecipe extends Recipe {
    public IronAxeRecipe() {
        super("Iron Axe", createIngredients(), true);
    }

    private static Map<String, Integer> createIngredients() {
        Map<String, Integer> ingredients = new HashMap<>();
        ingredients.put("Stone", 1);
        ingredients.put("Wood", 2);
        return ingredients;
    }

    @Override
    public Item craft() {
        return new IronAxeItem();
    }
}
