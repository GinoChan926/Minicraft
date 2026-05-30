package org.minicraft02160;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.minicraft02160.controller.*;
import org.minicraft02160.model.Player;
import org.minicraft02160.model.WorldModel;
import org.minicraft02160.model.extras.CardinalPoints;
import org.minicraft02160.model.extras.ResourceType;
import org.minicraft02160.model.TileTypes.GrassTile;
import org.minicraft02160.model.worldengine.TerrainType;
import org.minicraft02160.view.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExplorationSteps {

    private World world;
    private Player player;
    private ChunkManager chunkManager;
    private WorldController worldController;
    private WorldModel model;
    private TileViewManager tileViewManager;

    private void initWorld() {
        chunkManager = new ChunkManager();
        model = new WorldModel(10, 20);
        tileViewManager = new TileViewManager(model);
        worldController = new WorldController(model, chunkManager, tileViewManager);
        world = WorldFactory.create(model, worldController, tileViewManager);

        TerrainType[][] map = WorldGenerator.generateMap(10, 20);
        worldController.populateFromMap(map, world);
    }

    private void initPlayer(int col, int row) {
        player = new Player(col, row, CardinalPoints.E);
        player.setWorld(world);
        worldController.setPlayer(player);
        model.setPlayer(player);
    }

    private void makeWalkable(int row, int col) {
        GrassTile tile = new GrassTile(world);
        tile.setResource(ResourceType.GRASS);
        world.setTileAt(row, col, tile);
    }

    private void move(String direction) {
        int dx = 0, dy = 0;
        switch (direction.toLowerCase()) {
            case "right" -> dx = 1;
            case "left" -> dx = -1;
            case "up" -> dy = -1;
            case "down" -> dy = 1;
        }
        int nextX = player.getX() + dx;
        int nextY = player.getY() + dy;

        if (!world.isInBounds(nextX, nextY)) {
            worldController.transition(world, player, dx, dy);
        } else {
            worldController.handleMove(world, dx, dy);
        }
    }

    @Given("a generated world with a player at row {int}, col {int}")
    public void aGeneratedWorldWithAPlayerAtRowCol(int row, int col) {
        initWorld();
        initPlayer(col, row);
    }

    @And("the tile at row {int}, col {int} is walkable")
    public void theTileAtRowColIsWalkable(int row, int col) {
        makeWalkable(row, col);
    }

    @When("the player presses {word}")
    public void thePlayerPresses(String key) {
        move(key);
    }

    @Then("the player position becomes row {int}, col {int}")
    public void thePlayerPositionBecomesRowCol(int row, int col) {
        assertEquals(col, player.getX());
        assertEquals(row, player.getY());
    }

    @Given("a generated world with a player at {word} boundary")
    public void aGeneratedWorldWithAPlayerAtBoundary(String boundary) {
        initWorld();

        int row = world.getRows() / 2;
        int col = world.getCols() / 2;

        switch (boundary.toLowerCase()) {
            case "east" -> col = world.getCols() - 1;
            case "west" -> col = 0;
            case "north" -> row = 0;
            case "south" -> row = world.getRows() - 1;
        }

        initPlayer(col, row);
    }

    @And("current chunk coordinates are x {int}, y {int}")
    public void currentChunkCoordinatesAreXY(int x, int y) {
        assertEquals(x, chunkManager.getChunkX());
        assertEquals(y, chunkManager.getChunkY());
    }

    @Then("the current chunk coordinates are x {int}, y {int}")
    public void theCurrentChunkCoordinatesAre(int x, int y) {
        assertEquals(x, chunkManager.getChunkX());
        assertEquals(y, chunkManager.getChunkY());
    }

    @And("the player spawns on {word} edge of the new chunk")
    public void thePlayerSpawnsOnEdge(String edge) {
        switch (edge.toLowerCase()) {
            case "west" -> assertEquals(0, player.getX());
            case "east" -> assertEquals(world.getCols() - 1, player.getX());
            case "north" -> assertEquals(0, player.getY());
            case "south" -> assertEquals(world.getRows() - 1, player.getY());
        }
    }

    @Given("a player transitions from chunk x {int}, y {int} to chunk x {int}, y {int}")
    public void aPlayerTransitionsFromChunkToChunk(int fromX, int fromY, int toX, int toY) {
        initWorld();

        int dx = toX - fromX;
        int dy = toY - fromY;

        int row = world.getRows() / 2;
        int col = world.getCols() / 2;

        if (dx == 1) col = world.getCols() - 1;
        else if (dx == -1) col = 0;
        else if (dy == 1) row = world.getRows() - 1;
        else if (dy == -1) row = 0;

        initPlayer(col, row);

        worldController.transition(world, player, dx, dy);
    }

    @And("a resource is placed in chunk x {int}, y {int} at row {int}, col {int}")
    public void aResourceIsPlaced(int chunkX, int chunkY, int row, int col) {
        assertEquals(chunkX, chunkManager.getChunkX());
        assertEquals(chunkY, chunkManager.getChunkY());
        world.setResource(row, col, ResourceType.APPLE_TREE);
    }

    @When("the player returns to chunk x {int}, y {int} later")
    public void thePlayerReturnsToChunk(int targetX, int targetY) {
        worldController.transition(world, player, -1, 0);
        worldController.transition(world, player, 1, 0);

        assertEquals(targetX, chunkManager.getChunkX());
        assertEquals(targetY, chunkManager.getChunkY());
    }

    @Then("the resource still exists at row {int}, col {int}")
    public void theResourceStillExists(int row, int col) {
        assertEquals(ResourceType.APPLE_TREE, world.getTileView(col, row).getResourceType());
    }
}
