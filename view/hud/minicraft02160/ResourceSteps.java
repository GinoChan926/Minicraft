package org.minicraft02160;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.minicraft02160.controller.WorldGenerator;
import org.minicraft02160.model.extras.ResourceType;
import org.minicraft02160.view.World;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceSteps {

    Integer row;
    Integer column;
    private World world;
    ResourceType resource;

    @Before
    public void setup() {
        world = WorldGenerator.generate(18, 36);
    }

    @Given("apple tree resource")
    public void appleTreeResource() {
        this.resource = ResourceType.APPLE_TREE;
    }

    @And("resource coordinate row: {int}, column: {int}")
    public void resourceCoordinateRowColumn(int x, int y) {
        this.row = x;
        this.column = y;

        this.world.setResource(this.row, this.column, this.resource);
    }


    @And("world of size row: {int}, column: {int}")
    public void worldOfSizeRowColumn(int arg0, int arg1) {
        world = WorldGenerator.generate(arg0, arg1);
    }


    @Then("apple tree exists at coordinate row: {int}, column: {int}")
    public void appleTreeExistsAtCoordinateRowColumn(int arg0, int arg1) {
        // assert that the tile at arg0, arg1 coordinates is of sand
        assertEquals(ResourceType.APPLE_TREE, this.world.getTileView(arg1, arg0).getResourceType());
    }


    @When("world is drawn")
    public void worldIsDrawn() {
        // world is drawn by JFrame
    }
}
