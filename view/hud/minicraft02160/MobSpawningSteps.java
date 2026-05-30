package org.minicraft02160;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.minicraft02160.controller.ChunkManager;
import org.minicraft02160.controller.MobController;
import org.minicraft02160.controller.MobSpawner;
import org.minicraft02160.controller.WorldController;
import org.minicraft02160.model.Player;
import org.minicraft02160.model.WorldModel;
import org.minicraft02160.model.extras.CardinalPoints;
import org.minicraft02160.model.extras.ConsumableItem;
import org.minicraft02160.model.extras.MobType;
import org.minicraft02160.model.worldengine.*;
import org.minicraft02160.view.TileViewManager;
import org.minicraft02160.view.World;
import org.minicraft02160.view.WorldFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MobSpawningSteps {
    private MobSpawner mobSpawner;
    private World world;
    private Player player;
    private ChunkManager chunkManager;
    private WorldModel model;
    private TileViewManager tileViewManager;
    private WorldController worldController;
    private List<ConsumableItem> drops;

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

    @Given("the creation interval is set to {int} seconds")
    public void theCreationIntervalIsSetToSeconds(int arg0) {
        initWorld();
        mobSpawner.setSpawnInterval(arg0);
    }

    @When("{int} seconds of game time has passed")
    public void secondsOfGameTimeHasPassed(int arg0) {
        mobSpawner.advanceTime(arg0);
    }

    @And("one mob should have been created")
    public void oneMobShouldHaveBeenCreated() {
        assert mobSpawner.getSpawnedMobs().size() == 1;
    }

    @Given("there is a list of survival region of the mobs")
    public void thereIsAListOfSurvivalRegionOfTheMobs() {
        initWorld();
        for (MobType type : MobType.values()) {
            List<String> validTiles = mobSpawner.getValidTilesForMob(type);
            assertTrue(validTiles != null && !validTiles.isEmpty());
        }
    }

    @When("a mob is created")
    public void aMobIsCreated() {
        mobSpawner.advanceTime(mobSpawner.getSpawnInterval());
    }

    @Then("it should be on its specific region of tiles")
    public void itShouldBeOnItsSpecificRegionOfTiles() {
        for (MobController mob : mobSpawner.getSpawnedMobs()) {
            MobType type = mob.getType();
            List<String> validTiles = mobSpawner.getValidTilesForMob(type);
            String tileName = world.getTileView(mob.getX(), mob.getY()).getClass().getSimpleName();
            assertTrue(validTiles.contains(tileName));
        }
    }

    @Given("the maximum mobs per region is {int}")
    public void theMaximumMobsPerRegionIs(int arg0) {
        initWorld();
        mobSpawner.setMaxMobsPerRegion(arg0);
    }

    @When("mobs are created over time")
    public void mobsAreCreatedOverTime() {
        for (int i = 0; i < 10; i++) {
            mobSpawner.advanceTime(mobSpawner.getSpawnInterval());
        }
    }

    @Then("no region should exceed the maximum mob limit")
    public void noRegionShouldExceedTheMaximumMobLimit() {
        for (List<MobController> regionMobs : mobSpawner.getRegions().values()) {
            assertTrue(regionMobs.size() <= mobSpawner.getMaxMobsPerRegion());
        }
    }

    @Given("a mob is spawned")
    public void aMobIsSpawned() {
        initWorld();
        int attempts = 0;
        while (mobSpawner.getSpawnedMobs().isEmpty() && attempts < 100) {
            mobSpawner.advanceTime(mobSpawner.getSpawnInterval());
            attempts++;
        }
        assert mobSpawner.getSpawnedMobs().size() == 1;
    }

    @When("the player defeats the mob")
    public void thePlayerDefeatsTheMob() {
        MobController mob = mobSpawner.getSpawnedMobs().get(0);
        drops = mobSpawner.defeatMob(mob);
        mob.getModel().setAlive(false);
    }

    @Then("the mob should drop at least one consumable item")
    public void theMobShouldDropAtLeastOneConsumableItem() {
        assert drops != null && !drops.isEmpty();
    }
}
