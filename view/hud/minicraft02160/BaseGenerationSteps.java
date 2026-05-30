package org.minicraft02160;

import java.util.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.minicraft02160.controller.ChunkManager;
import org.minicraft02160.controller.MobSpawner;
import org.minicraft02160.controller.WorldController;
import org.minicraft02160.model.Player;
import org.minicraft02160.model.WorldModel;
import org.minicraft02160.model.extras.CardinalPoints;
import org.minicraft02160.model.worldengine.*;
import org.minicraft02160.view.TileViewManager;
import org.minicraft02160.view.World;
import org.minicraft02160.view.WorldFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseGenerationSteps {
    private MobSpawner mobSpawner;
    private World world;
    private Player player;
    private ChunkManager chunkManager;
    private WorldModel model;
    private TileViewManager tileViewManager;
    private WorldController worldController;

    public void initWorld() {
        int rows = 10, cols = 20;

        List<TerrainGeneration> steps = List.of(
                new BuildGrassBase(),
                new BuildCluster(TerrainType.WATER, 2, 20),
                new BuildCluster(TerrainType.STONE, 3, 5),
                new BuildCluster(TerrainType.WATER, 2, 4),
                new BuildSandEdges(),
                new BuildDeepWater()
        );

        TerrainType[][] map = new TerrainType[rows][cols];
        for (TerrainGeneration step : steps) {
            step.apply(map, rows, cols);
        }

        chunkManager = new ChunkManager();
        model = new WorldModel(rows, cols);
        tileViewManager = new TileViewManager(model);
        worldController = new WorldController(model, chunkManager, tileViewManager);
        world = WorldFactory.create(model, worldController, tileViewManager);
        worldController.populateFromMap(map, world);
        player = new Player(5, 5, CardinalPoints.E);
        player.setWorld(world);
        worldController.setPlayer(player);
        model.setPlayer(player);
        mobSpawner = new MobSpawner(world, player);
        mobSpawner.setEnabled(true);
    }

    @Given("a world")
    public void aWorld() {
        initWorld();
    }

    @When("the world is generated")
    public void theWorldIsGenerated() {
        assertTrue(world != null);
    }

    @Then("every tile should initially be grass")
    public void everyTileShouldInitiallyBeGrass() {
        int rows = 10, cols = 20;
        TerrainType[][] map = new TerrainType[rows][cols];

        new BuildGrassBase().apply(map, rows, cols);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                assertTrue(map[row][col] == TerrainType.GRASS);
            }
        }
    }

    @Then("every position in the world should contain a tile")
    public void everyPositionInTheWorldShouldContainATile() {
        for (int row = 0; row < world.getRows(); row++) {
            for (int col = 0; col < world.getCols(); col++) {
                assertTrue(world.getTileView(col, row) != null);
            }
        }
    }

    @Then("the world should contain multiple tile types")
    public void theWorldShouldContainMultipleTileTypes() {
        Set<TerrainType> types = new HashSet<>();
        for (int row = 0; row < world.getRows(); row++) {
            for (int col = 0; col < world.getCols(); col++) {
                types.add(world.getTileView(col, row).getTerrainType());
            }
        }
        assertTrue(types.size() > 1);
    }
}
