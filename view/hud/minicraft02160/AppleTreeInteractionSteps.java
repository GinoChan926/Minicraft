package org.minicraft02160;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.minicraft02160.model.Player;
import org.minicraft02160.model.TileModel;
import org.minicraft02160.model.WorldModel;
import org.minicraft02160.model.extras.CardinalPoints;
import org.minicraft02160.model.extras.ResourceType;

public class AppleTreeInteractionSteps {
    private Player player;
    private TileModel tileModel;

    @Given("world model of size row: {int}, column: {int}")
    public void worldOfSizeRowColumn(int arg0, int arg1) {
        WorldModel worldModel = new WorldModel(arg0, arg1);
    }

    @And("a GRASS tile at with apple tree resource at coordinates row: {int}, column: {int}")
    public void aGRASSTileAtWithAppleTreeResourceAtCoordinatesRowColumn(int arg0, int arg1) {
        tileModel = new TileModel(arg1, arg0);
        tileModel.setTerrainType(org.minicraft02160.model.worldengine.TerrainType.GRASS);
        tileModel.setResourceType(org.minicraft02160.model.extras.ResourceType.APPLE_TREE);
    }

    @And("player at coordinate row: {int}, column: {int} and facing {string}")
    public void playerAtCoordinateRowColumnAndFacing(int arg0, int arg1, String arg2) {
        CardinalPoints direction = CardinalPoints.valueOf(arg2);
        player = new Player(arg1, arg0, direction);
    }

    @When("player interacts with apple tree")
    public void playerInteractsWithAppleTree() {
        if (tileModel.getResourceType() == ResourceType.APPLE_TREE) {
            tileModel.setResourceType(ResourceType.EMPTY_APPLE_TREE);
        }
    }

    @Then("tile resource updates to empty apple tree")
    public void tileResourceUpdatesToEmptyAppleTree() {
        assert tileModel.getResourceType() == ResourceType.EMPTY_APPLE_TREE;
    }


}