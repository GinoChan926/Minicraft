package org.minicraft02160;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.minicraft02160.controller.HudManager;
import org.minicraft02160.controller.WorldController;
import org.minicraft02160.model.TileTypes.DeepWaterTile;
import org.minicraft02160.model.TileTypes.SandTile;
import org.minicraft02160.model.TileTypes.StoneTile;
import org.minicraft02160.model.TileTypes.WaterTile;
import org.minicraft02160.model.WorldModel;
import org.minicraft02160.model.worldengine.*;
import org.minicraft02160.view.MapToWorldConverter;
import org.minicraft02160.view.Tile;
import org.minicraft02160.view.TileViewManager;
import org.minicraft02160.view.World;

import static org.junit.jupiter.api.Assertions.*;

public class TileDistributionSteps {

    private World world;
    private TerrainType[][] map;
    private TerrainType[][] mapBeforeDeepWater;

    private int rows;
    private int cols;

    @Given("a world exists")
    public void aWorldExists() {
        rows = 20;
        cols = 20;
        map = new TerrainType[rows][cols];
        new BuildGrassBase().apply(map, rows, cols);
        updateWorldFromMap();
    }

    @Given("a world with deep water")
    public void aWorldWithDeepWater() {
        rows = 30;
        cols = 30;
        map = new TerrainType[rows][cols];

        new BuildGrassBase().apply(map, rows, cols);


        for (int row = 8; row <= 20; row++) {
            for (int col = 8; col <= 20; col++) {
                map[row][col] = TerrainType.WATER;
            }
        }

        new BuildSandEdges().apply(map, rows, cols);

        mapBeforeDeepWater = copyMap(map);

        new BuildDeepWater().apply(map, rows, cols);
        updateWorldFromMap();
    }

    @And("water patches are on")
    public void waterPatchesAreOn() {
        // Intentionally left blank because the setup is controlled directly in the steps
    }

    @When("asked to generate a water patch")
    public void askedToGenerateAWaterPatch() {
        rows = 20;
        cols = 20;
        map = new TerrainType[rows][cols];

        new BuildGrassBase().apply(map, rows, cols);
        new BuildCluster(TerrainType.WATER, 4, 10).apply(map, rows, cols);

        updateWorldFromMap();
    }

    @When("asked to generate a stone patch")
    public void askedToGenerateAStonePatch() {
        rows = 20;
        cols = 20;
        map = new TerrainType[rows][cols];

        new BuildGrassBase().apply(map, rows, cols);
        new BuildCluster(TerrainType.STONE, 4, 8).apply(map, rows, cols);

        updateWorldFromMap();
    }

    @When("sand is being added")
    public void sandIsBeingAdded() {
        rows = 20;
        cols = 20;
        map = new TerrainType[rows][cols];

        new BuildGrassBase().apply(map, rows, cols);
        new BuildCluster(TerrainType.WATER, 4, 10).apply(map, rows, cols);
        new BuildSandEdges().apply(map, rows, cols);

        updateWorldFromMap();
    }

    @When("deep water is being added")
    public void deepWaterIsBeingAdded() {
        rows = 30;
        cols = 30;
        map = new TerrainType[rows][cols];

        new BuildGrassBase().apply(map, rows, cols);

        for (int row = 8; row <= 20; row++) {
            for (int col = 8; col <= 20; col++) {
                map[row][col] = TerrainType.WATER;
            }
        }

        new BuildSandEdges().apply(map, rows, cols);

        mapBeforeDeepWater = copyMap(map);

        new BuildDeepWater().apply(map, rows, cols);
        updateWorldFromMap();
    }

    @When("a player tries to walk on deep water")
    public void aPlayerTriesToWalkOnDeepWater() {

    }

    @Then("a water patch is being created")
    public void aWaterPatchIsBeingCreated() {
        assertTrue(hasAnyTileOfType(WaterTile.class), "Expected at least one water tile");
    }

    @Then("a stone patch is being created")
    public void aStonePatchIsBeingCreated() {
        assertTrue(hasAnyTileOfType(StoneTile.class), "Expected at least one stone tile");
    }

    @Then("sand should exist around water")
    public void sandShouldExistAroundWater() {
        assertTrue(hasAnyTileOfType(SandTile.class), "Expected at least one sand tile");
    }

    @Then("every sand tile should be adjacent to at least one water tile")
    public void everySandTileShouldBeAdjacentToAtLeastOneWaterTile() {
        for (int row = 0; row < world.getRows(); row++) {
            for (int col = 0; col < world.getCols(); col++) {
                if (world.getTileView(col, row) instanceof SandTile) {
                    assertTrue(
                            hasAdjacentTileOfType(col, row, WaterTile.class, DeepWaterTile.class),
                            "Sand tile at (" + col + "," + row + ") is not next to water"
                    );
                }
            }
        }
    }

    @Then("deep water tiles should exist inside water areas")
    public void deepWaterTilesShouldExistInsideWaterAreas() {
        assertTrue(hasAnyTileOfType(DeepWaterTile.class), "Expected at least one deep water tile");
    }

    @Then("no deep water tile should be adjacent to sand")
    public void noDeepWaterTileShouldBeAdjacentToSand() {
        for (int row = 0; row < world.getRows(); row++) {
            for (int col = 0; col < world.getCols(); col++) {
                if (world.getTileView(col, row) instanceof DeepWaterTile) {
                    assertFalse(
                            hasAdjacentTileOfType(col, row, SandTile.class),
                            "Deep water tile at (" + col + "," + row + ") is next to sand"
                    );
                }
            }
        }
    }

    @Then("every deep water tile should come from a water tile")
    public void everyDeepWaterTileShouldComeFromAWaterTile() {
        boolean foundDeepWater = false;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (world.getTileView(col, row) instanceof DeepWaterTile) {
                    foundDeepWater = true;
                    assertEquals(
                            TerrainType.WATER,
                            mapBeforeDeepWater[row][col],
                            "Deep water at (" + col + "," + row + ") did not come from a water tile"
                    );
                }
            }
        }

        assertTrue(foundDeepWater, "Expected at least one deep water tile");
    }

    @Then("the movement should be blocked")
    public void theMovementShouldBeBlocked() {
        DeepWaterTile tile = findDeepWaterTile();
        assertNotNull(tile, "Expected at least one deep water tile");
        assertFalse(tile.isWalkable(), "Deep water should not be walkable");
    }

    private void updateWorldFromMap() {
        WorldModel model = new WorldModel(rows, cols);
        TileViewManager tvm = new TileViewManager(model);
        HudManager hudManager = new HudManager();
        WorldController controller = new WorldController(model, null, tvm);

        world = new World(model, controller, tvm, hudManager);
        new MapToWorldConverter().apply(map, tvm, world, rows, cols);
    }

    private TerrainType[][] copyMap(TerrainType[][] original) {
        TerrainType[][] copy = new TerrainType[original.length][original[0].length];
        for (int r = 0; r < original.length; r++) {
            System.arraycopy(original[r], 0, copy[r], 0, original[r].length);
        }
        return copy;
    }

    private boolean hasAnyTileOfType(Class<?> tileClass) {
        for (int row = 0; row < world.getRows(); row++) {
            for (int col = 0; col < world.getCols(); col++) {
                if (tileClass.isInstance(world.getTileView(col, row))) {
                    return true;
                }
            }
        }
        return false;
    }

    @SafeVarargs
    private final boolean hasAdjacentTileOfType(int col, int row, Class<?>... tileClasses) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;

                int newCol = col + dx;
                int newRow = row + dy;

                if (isInsideWorld(newCol, newRow)) {
                    Tile tile = world.getTileView(newCol, newRow);

                    for (Class<?> tileClass : tileClasses) {
                        if (tileClass.isInstance(tile)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isInsideWorld(int col, int row) {
        return col >= 0 && col < world.getCols() && row >= 0 && row < world.getRows();
    }

    private DeepWaterTile findDeepWaterTile() {
        for (int row = 0; row < world.getRows(); row++) {
            for (int col = 0; col < world.getCols(); col++) {
                if (world.getTileView(col, row) instanceof DeepWaterTile) {
                    return (DeepWaterTile) world.getTileView(col, row);
                }
            }
        }
        return null;
    }
}