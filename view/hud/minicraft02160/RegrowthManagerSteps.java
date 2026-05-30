package org.minicraft02160;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

import org.minicraft02160.controller.RegrowthManager;
import org.minicraft02160.controller.WorldGenerator;
import org.minicraft02160.model.extras.ResourceType;
import org.minicraft02160.view.*;

public class RegrowthManagerSteps {

    private World world;
    private RegrowthManager regrowthManager;

    @Before
    public void setup() {
        world = WorldGenerator.generate(18, 36);
        regrowthManager = new RegrowthManager();
    }

    @Given("tile {int}, {int} has no resource")
    public void tileHasNoResource(int x, int y) {
        world.getTileView(x, y).setResource(null);
    }

    @Given("tile {int}, {int} has resource APPLE_TREE")
    public void tileHasResource(int x, int y) {
        world.getTileView(x, y).setResource(ResourceType.APPLE_TREE);
    }

    @When("a regrowth is scheduled at tile {int}, {int} with type APPLE_TREE and delay {int}")
    public void scheduleRegrowth(int x, int y, int delay) {
        regrowthManager.addRegrowth(x, y, ResourceType.APPLE_TREE, delay);
    }

    @When("{int} ticks have passed")
    public void advanceTicks(int ticks) {
        for (int i = 0; i < ticks; i++) {
            regrowthManager.tick(world);
        }
    }

    @Then("the pending regrowth count should be {int}")
    public void verifyPendingCount(int expected) {
        assertEquals(expected, regrowthManager.getPendingCount());
    }

    @Then("tile {int}, {int} should have resource APPLE_TREE")
    public void verifyTileHasResource(int x, int y) {
        assertEquals(ResourceType.APPLE_TREE, world.getTileView(x, y).getResourceType());
    }

    @Then("tile {int}, {int} should have no resource")
    public void verifyTileHasNoResource(int x, int y) {
        assertNull(world.getTileView(x, y).getResourceType());
    }

    @Then("tile {int}, {int} should still have no resource")
    public void verifyTileStillHasNoResource(int x, int y) {
        assertNull(world.getTileView(x, y).getResourceType());
    }
}