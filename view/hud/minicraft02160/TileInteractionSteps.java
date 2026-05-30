package org.minicraft02160;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.minicraft02160.controller.HudManager;
import org.minicraft02160.controller.TileActionController;
import org.minicraft02160.controller.WorldController;
import org.minicraft02160.model.*;
import org.minicraft02160.model.extras.CardinalPoints;
import org.minicraft02160.model.TileTypes.GrassTile;
import org.minicraft02160.model.TileTypes.SoilTile;
import org.minicraft02160.model.worldengine.TerrainType;
import org.minicraft02160.view.Tile;
import org.minicraft02160.view.TileViewManager;
import org.minicraft02160.view.World;
import org.minicraft02160.model.Items.TileItem;

import static org.junit.jupiter.api.Assertions.*;

public class TileInteractionSteps {

    private World world;
    private WorldModel worldModel;
    private Player player;
    private final TileActionController tileActionController = new TileActionController();
    private int inventorySizeBefore;

    @Given("a world with a player standing on a grass tile")
    public void aWorldWithAPlayerStandingOnAGrassTile() {
        worldModel = new WorldModel(5, 5);
        TileViewManager tvm = new TileViewManager(worldModel);
        HudManager hudManager = new HudManager();
        WorldController controller = new WorldController(worldModel, null, tvm);

        world = new World(worldModel, controller, tvm, hudManager);
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                GrassTile tile = new GrassTile(world);
                world.setTileAt(r, c, tile);
            }
        }
        player = new Player(2, 2, CardinalPoints.E);
        worldModel.setPlayer(player);
        player.setWorld(world);
    }

    @Given("the player is standing on a SOIL tile")
    public void thePlayerIsStandingOnASoilTile() {
        SoilTile soil = new SoilTile(world);
        world.setTileAt(player.getY(), player.getX(), soil);
        soil.setContainsPlayer(true);
    }

    @Given("the player has picked up a grass tile")
    public void thePlayerHasPickedUpAGrassTile() {
        TileItem tileItem = new TileItem(TerrainType.GRASS);
        player.getInventory().add(tileItem);
    }

    @And("the player equips the tile in main hand")
    public void thePlayerEquipsTheTileInMainHand() {
        Inventory inventory = player.getInventory();
        for (Item item : inventory.getAllItems()) {
            if (item instanceof TileItem tileItem) {
                (tileItem).use(player);
                break;
            }
        }
    }

    @When("the player interacts with the tile under them by pressing Q")
    public void thePlayerPressesQ() {
        tileActionController.handleTileAction(world);
    }

    @Then("the tile under the player should be SOIL")
    public void theTileUnderThePlayerShouldBeSOIL() {
        Tile tile = world.getTileView(player.getX(), player.getY());
        assertEquals(TerrainType.SOIL, tile.getTerrainType());
    }

    @Then("the tile under the player should be GRASS")
    public void theTileUnderThePlayerShouldBeGRASS() {
        Tile tile = world.getTileView(player.getX(), player.getY());
        assertEquals(TerrainType.GRASS, tile.getTerrainType());
    }

    @Then("the player inventory should contain a {string}")
    public void thePlayerInventoryShouldContainA(String itemName) {
        boolean found = player.getInventory().getAllItems().stream()
                .anyMatch(i -> i.getName().equals(itemName));
        assertTrue(found);
    }

    @And("the player inventory should be empty")
    public void thePlayerInventoryShouldBeEmptyTile() {
        assertTrue(player.getInventory().isEmpty());
    }

    @Given("the player inventory is full")
    public void thePlayerInventoryIsFull() {
        Inventory inventory = player.getInventory();
        while (!inventory.isFull()) {
            inventory.add(new TileItem(TerrainType.GRASS));
        }
        inventorySizeBefore = inventory.getSize();
    }

    @Then("the player inventory size should remain the same")
    public void thePlayerInventorySizeShouldRemainTheSame() {
        assertEquals(inventorySizeBefore, player.getInventory().getSize());
    }

    @Then("the tile under the player should contain the player")
    public void theTileUnderThePlayerShouldContainThePlayer() {
        Tile tile = world.getTileView(player.getX(), player.getY());
        assertTrue(tile.getTileModel().isContainsPlayer());
    }
}

