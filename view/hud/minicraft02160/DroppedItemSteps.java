package org.minicraft02160;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.minicraft02160.controller.DroppedItemManager;
import org.minicraft02160.model.Item;
import org.minicraft02160.model.Items.ResourceItem;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DroppedItemSteps {

    private DroppedItemManager manager;
    private List<Item> collectedItems;

    @Given("the dropped item manager exists")
    public void theDroppedItemManagerExists() {
        manager = new DroppedItemManager();
    }

    @When("an item {string} is dropped at position {int} {int}")
    public void anItemIsDroppedAtPosition(String itemName, int x, int y) {
        manager.dropItem(new ResourceItem(itemName, true, 0), x, y);
    }

    @When("a null item is dropped at position {int} {int}")
    public void aNullItemIsDroppedAtPosition(int x, int y) {
        manager.dropItem(null, x, y);
    }

    @When("items are collected at position {int} {int}")
    public void itemsAreCollectedAtPosition(int x, int y) {
        collectedItems = manager.collectItemsAt(x, y);
    }

    @Then("there should be {int} dropped item in the world")
    public void thereShouldBeDroppedItem(int count) {
        assertEquals(count, manager.getAllDroppedItems().size());
    }

    @Then("there should be {int} dropped items in the world")
    public void thereShouldBeDroppedItems(int count) {
        assertEquals(count, manager.getAllDroppedItems().size());
    }

    @Then("the collected items should contain {string}")
    public void theCollectedItemsShouldContain(String itemName) {
        assertTrue(collectedItems.stream()
                .anyMatch(item -> item.getName().equals(itemName)));
    }
}
