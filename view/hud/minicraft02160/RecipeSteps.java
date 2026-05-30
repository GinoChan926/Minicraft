package org.minicraft02160;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import org.minicraft02160.model.Inventory;
import org.minicraft02160.model.Item;
import org.minicraft02160.model.Items.ResourceItem;
import org.minicraft02160.model.recipe.BackpackRecipe;
import org.minicraft02160.model.Recipe;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeSteps {

    private Recipe recipe;
    private Inventory inventory;
    private Item craftedItem;

    @Given("a Backpack recipe exists")
    public void aBackpackRecipeExists() {
        recipe = new BackpackRecipe();
        inventory = new Inventory();
        assertNotNull(recipe);
    }

    @Given("the player has an empty inventory")
    public void thePlayerHasAnEmptyInventory() {
        inventory = new Inventory();
        assertTrue(inventory.isEmpty());
    }

    @Given("the player has these ingredients")
    public void thePlayerHasTheseIngredients(DataTable dataTable) {
        List<List<String>> rows = dataTable.asLists(String.class);
        for (List<String> row : rows) {
            String itemName = row.get(0).trim();
            int quantity = Integer.parseInt(row.get(1).trim());
            for (int i = 0; i < quantity; i++) {
                inventory.addItem(new ResourceItem(itemName, false, 0));
            }
        }
    }

    @When("the player crafts the recipe")
    public void thePlayerCraftsTheRecipe() {
        recipe.consumeIngredients(inventory);
        craftedItem = recipe.craft();
    }

    @Then("the player should be able to craft the recipe")
    public void thePlayerShouldBeAbleToCraftTheRecipe() {
        assertTrue(recipe.canCraft(inventory));
    }

    @Then("the player should not be able to craft the recipe")
    public void thePlayerShouldNotBeAbleToCraftTheRecipe() {
        assertFalse(recipe.canCraft(inventory));
    }

    @Then("the inventory should not contain {string}")
    public void theInventoryShouldNotContain(String itemName) {
        boolean found = inventory.getAllItems().stream()
                .anyMatch(item -> item.getName().equals(itemName));
        assertFalse(found);
    }

    @Then("the crafted item name should be {string}")
    public void theCraftedItemNameShouldBe(String expectedName) {
        assertNotNull(craftedItem);
        assertEquals(expectedName, craftedItem.getName());
    }

    @Then("the recipe should require a workbench")
    public void theRecipeShouldRequireAWorkbench() {
        assertTrue(recipe.isRequireWorkbench());
    }
}