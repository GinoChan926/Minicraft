package org.minicraft02160;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.minicraft02160.controller.DroppedItemManager;
import org.minicraft02160.model.*;
import org.minicraft02160.model.Items.ResourceItem;
import org.minicraft02160.model.recipe.BackpackRecipe;
import org.minicraft02160.model.recipe.RecipeRegistry;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventorySteps {

    private Item droppedItem;
    private DroppedItemManager droppedItemManager;
    private Inventory inventory;
    private boolean pickupResult;
    private CraftingMenuModel craftingMenu;
    private List<Recipe> recipes;
    private List<Recipe> craftableRecipes;


    @Given("a player drops an item")
    public void aPlayerDropsAnItem() {
        droppedItemManager = new DroppedItemManager();
        droppedItem = new ResourceItem("Apple", true, 0);
        droppedItemManager.dropItem(droppedItem, 5, 5);
    }

    @When("the item is placed in the world")
    public void theItemIsPlacedInTheWorld() {
        assertFalse(droppedItemManager.getAllDroppedItems().isEmpty());
    }

    @Then("the item should remain visible for a limited time")
    public void theItemShouldRemainVisibleForALimitedTime() {
        DroppedItemManager.DroppedItemEntry entry = droppedItemManager.getAllDroppedItems().get(0);
        assertTrue(entry.getTicksRemaining() > 0);
        assertFalse(entry.isExpired());
    }

    @When("the timeout duration expires")
    public void theTimeoutDurationExpires() {
        DroppedItemManager.DroppedItemEntry entry = droppedItemManager.getAllDroppedItems().get(0);
        while (!entry.isExpired()) {
            droppedItemManager.tick();
        }
    }

    @Then("the item should be removed from the world")
    public void theItemShouldBeRemovedFromTheWorld() {
        assertTrue(droppedItemManager.getAllDroppedItems().isEmpty());
    }

    @Given("a player has items in their inventory")
    public void aPlayerHasItemsInTheirInventory() {
        droppedItemManager = new DroppedItemManager();
        inventory = new Inventory();
        inventory.add(new ResourceItem("Apple", true, 10));
        inventory.add(new ResourceItem("Wood", true, 10));
    }

    @When("the player dies")
    public void thePlayerDies() {
        List<Item> items = inventory.getAllItems();
        for (Item item : items) {
            droppedItemManager.dropItem(item, 5, 5);
        }
        inventory.clear();
    }

    @Then("all inventory items should be dropped at the death location")
    public void allInventoryItemsShouldBeDroppedAtTheDeathLocation() {
        assertTrue(inventory.isEmpty());
        assertEquals(2, droppedItemManager.getAllDroppedItems().size());
    }


    @Given("a player has required crafting materials")
    public void aPlayerHasRequiredCraftingMaterials() {
        inventory = new Inventory();
        inventory.add(new ResourceItem("Fur", true, 0));
        inventory.add(new ResourceItem("Wool", true, 0));
        inventory.add(new ResourceItem("Wool", true, 0));
    }

    @When("the player crafts a backpack")
    public void thePlayerCraftsABackpack() {
        BackpackRecipe recipe = new BackpackRecipe();
        assertTrue(recipe.canCraft(inventory));
        recipe.consumeIngredients(inventory);
        Item backpack = recipe.craft();
        if (backpack instanceof Equipable) {
            inventory.equip((EquipableItem) backpack);
        } else {
            inventory.add(backpack);
        }
    }

    @Then("the player’s inventory capacity should increase")
    public void thePlayerSInventoryCapacityShouldIncrease() {
        assertTrue(inventory.getMaxCapacity() > Inventory.BASE_CAPACITY);

    }

    @Given("a player has a full inventory")
    public void aPlayerHasAFullInventory() {
        inventory = new Inventory();
        for (int i = 0; i < Inventory.BASE_CAPACITY; i++) {
            inventory.add(new ResourceItem("Apple", true, 10));
        }
        assertTrue(inventory.isFull());
    }

    @When("the player attempts to pick up an item")
    public void thePlayerAttemptsToPickUpAnItem() {
        pickupResult = inventory.add(new ResourceItem("Apple", true, 10));
    }

    @Then("the item should not be added to the inventory")
    public void theItemShouldNotBeAddedToTheInventory() {
        assertFalse(pickupResult);
        assertEquals(Inventory.BASE_CAPACITY, inventory.getSize());
    }

    @Given("a player has an expanded inventory")
    public void aPlayerHasAnExpandedInventory() {
        inventory = new Inventory();
        inventory.addBonusCapacity(Inventory.BACKPACK_BONUS_SLOTS);
    }

    @When("the player picks up items")
    public void thePlayerPicksUpItems() {
        for (int i = 0; i < Inventory.BASE_CAPACITY + Inventory.BACKPACK_BONUS_SLOTS; i++) {
            inventory.add(new ResourceItem("Apple", true, 10));
        }
    }

    @Then("the inventory should accept items up to the new limit")
    public void theInventoryShouldAcceptItemsUpToTheNewLimit() {
        assertEquals(Inventory.BASE_CAPACITY + Inventory.BACKPACK_BONUS_SLOTS, inventory.getSize());
        assertTrue(inventory.isFull());
    }

    @Given("a player opens the crafting interface")
    public void aPlayerOpensTheCraftingInterface() {
        craftingMenu = new CraftingMenuModel();
        craftingMenu.open();
        recipes = craftingMenu.getRecipes();
    }

    @Then("the player should see a list of available recipes")
    public void thePlayerShouldSeeAListOfAvailableRecipes() {
        assertNotNull(recipes);
        assertFalse(recipes.isEmpty());
    }

    @Given("a player views a crafting recipe")
    public void aPlayerViewsACraftingRecipe() {
        craftingMenu = new CraftingMenuModel();
        craftingMenu.open();
        recipes = craftingMenu.getRecipes();
    }

    @Then("the recipe should display required materials and quantities")
    public void theRecipeShouldDisplayRequiredMaterialsAndQuantities() {
        Recipe recipe = recipes.get(0);
        assertNotNull(recipe.getOutputName());
        assertNotNull(recipe.getIngredients());
        assertFalse(recipe.getIngredients().isEmpty());
    }

    @Given("a player has some materials")
    public void aPlayerHasSomeMaterials() {
        inventory = new Inventory();
        inventory.add(new ResourceItem("Fur", true, 0));
        inventory.add(new ResourceItem("Wool", true, 0));
        inventory.add(new ResourceItem("Wool", true, 0));
    }

    @When("the player opens the crafting interface")
    public void thePlayerOpensTheCraftingInterface() {
        craftingMenu = new CraftingMenuModel();
        craftingMenu.open();
        recipes = craftingMenu.getRecipes();
        craftableRecipes = RecipeRegistry.getCraftableRecipe(inventory);
    }

    @Then("recipes that can be crafted should be highlighted")
    public void recipesThatCanBeCraftedShouldBeHighlighted() {
        assertFalse(craftableRecipes.isEmpty());
    }

    @And("recipes that cannot be crafted should be disabled or dimmed")
    public void recipesThatCannotBeCraftedShouldBeDisabledOrDimmed() {
        assertFalse(recipes.isEmpty());
        for (Recipe recipe : recipes) {
            if (!craftableRecipes.contains(recipe)) {
                assertFalse(recipe.canCraft(inventory));
            }
        }
    }

    @Given("a player gains or drop items")
    public void aPlayerGainsOrDropItems() {
        inventory = new Inventory();
        craftableRecipes = RecipeRegistry.getCraftableRecipe(inventory);
        assertTrue(craftableRecipes.isEmpty());
        inventory.add(new ResourceItem("Fur", true, 0));
        inventory.add(new ResourceItem("Wool", true, 0));
        inventory.add(new ResourceItem("Wool", true, 0));
    }

    @Then("the list of craftable recipes should update accordingly")
    public void theListOfCraftableRecipesShouldUpdateAccordingly() {
        List<Recipe> updatedCraftable = RecipeRegistry.getCraftableRecipe(inventory);
        assertFalse(updatedCraftable.isEmpty());
    }
}
