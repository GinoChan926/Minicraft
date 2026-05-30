package org.minicraft02160;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.minicraft02160.controller.MobController;
import org.minicraft02160.model.MobModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.minicraft02160.model.extras.MobType.SHEEP;

public class MobSteps {

    private MobController mob;

    @Given("a SHEEP mob is created at position {int} {int}")
    public void aSHEEPMobIsCreatedAtPosition(int arg0, int arg1) {
        mob = new MobController(new MobModel(SHEEP, arg0, arg1));
    }

    @Then("the mob should be alive")
    public void theMobShouldBeAlive() {
        assertTrue(mob.isAlive());
    }

    @And("the mob health should follow its pre-set value")
    public void theMobHealthShouldFollowItsPreSetValue() {
        assertEquals(SHEEP.getBaseHealth(), mob.getCurrentHealth());
    }

    @And("the health of SHEEP mob is {int}")
    public void theHealthOfSHEEPMobIs(int arg0) {
        assertEquals(arg0, mob.getCurrentHealth());
    }

    @When("the mob takes {int} damage")
    public void theMobTakesDamage(int arg0) {
        mob.takeDamage(arg0);
    }

    @Then("the mob health should be {int}")
    public void theMobHealthShouldBe(int arg0) {
        assertEquals(arg0, mob.getCurrentHealth());
    }

    @And("the mob should still be alive")
    public void theMobShouldStillBeAlive() {
        assertTrue(mob.isAlive());
    }

    @Then("the mob should be dead")
    public void theMobShouldBeDead() {
        assertTrue(!mob.isAlive());
    }

    @When("the mob takes {int} more damage")
    public void theMobTakesMoreDamage(int arg0) {
        mob.takeDamage(arg0);
    }

    @Then("the mob health should remain {int}")
    public void theMobHealthShouldRemain(int arg0) {
        assertEquals(arg0, mob.getCurrentHealth());
    }

    @And("the mob should remain dead")
    public void theMobShouldRemainDead() {
        assertTrue(!mob.isAlive());
    }

    @When("the mob is defeated")
    public void theMobIsDefeated() {
        mob.takeDamage(mob.getCurrentHealth());
    }

    @Then("the drops should contain its pre-set materials")
    public void theDropsShouldContainItsPreSetMaterials() {
        assertTrue(mob.getDrops().contains(SHEEP.getDrop()));
    }

    @Then("the number of drops should be greater than zero")
    public void theNumberOfDropsShouldBeGreaterThanZero() {
        assertTrue(mob.getDrops().size() > 0);
    }
}