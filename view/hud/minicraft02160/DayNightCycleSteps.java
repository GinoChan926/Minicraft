package org.minicraft02160;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.minicraft02160.controller.DayNightCycle;
import org.minicraft02160.model.extras.CardinalPoints;
import org.minicraft02160.model.Items.LighterWithFireItem;
import org.minicraft02160.model.Player;

import static org.junit.jupiter.api.Assertions.*;

public class DayNightCycleSteps {

    private DayNightCycle cycle;
    private Player player;
    private LighterWithFireItem lighter;

    @Given("a day length of {int} seconds")
    public void setDayLength(int length) {
        cycle = new DayNightCycle(length);
    }

    @Given("the player has a Lighter with Fire equipped")
    public void equipLighter() {
        player = new Player(4, 4, CardinalPoints.E);
        lighter = new LighterWithFireItem();

        player.getInventory().add(lighter);
        player.getInventory().equip(lighter);
    }

    @Given("the player does not have a Lighter with Fire")
    public void noLighter() {
        player = new Player(4, 4, CardinalPoints.E);
        lighter = null;
    }

    @When("{int} seconds have passed")
    public void advanceTime(int seconds) {
        if (cycle == null) {
            cycle = new DayNightCycle(30);
        }

        for (int i = 0; i < seconds; i++) {
            cycle.tick();
        }
    }

    @When("the player unequips the Lighter with Fire")
    public void unequipLighter() {
        if (lighter != null) {
            player.getInventory().unequip(lighter);
        }
    }

    @When("the player equips a Lighter with Fire")
    public void equipNewLighter() {
        lighter = new LighterWithFireItem();
        player.getInventory().add(lighter);
        player.getInventory().equip(lighter);
    }

    @Then("it should be daytime")
    public void verifyDaytime() {
        assertFalse(cycle.isNight());
    }

    @Then("it should be nighttime")
    public void verifyNighttime() {
        assertTrue(cycle.isNight());
    }

    @Then("the darkness at tile {int}, {int} should be {int}")
    public void verifyDarkness(int tileX, int tileY, int expected) {
        int actual = cycle.getDarkness(tileX, tileY, player);
        assertEquals(expected, actual);
    }
}