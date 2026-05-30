package org.minicraft02160;

import org.minicraft02160.controller.*;
import org.minicraft02160.model.*;
import org.minicraft02160.model.Items.IronAxeItem;
import org.minicraft02160.model.Items.LighterWithFireItem;
import org.minicraft02160.model.Items.WaterBottleItem;
import org.minicraft02160.model.worldengine.*;
import org.minicraft02160.view.*;
import org.minicraft02160.model.extras.CardinalPoints;

import javax.swing.*;

public class Minicraft {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Minicraft::startGame);
    }

    public static void startGame() {
        Player player = new Player(4, 4, CardinalPoints.E);

        int rows = 15, cols = 22;

        java.util.List<TerrainGeneration> steps = java.util.List.of(
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

        WorldModel worldModel = new WorldModel(rows, cols);
        TileViewManager tileViewManager = new TileViewManager(worldModel);

        ChunkManager chunkManager = new ChunkManager();
        WorldController worldController = new WorldController(worldModel, chunkManager, tileViewManager);

        World world = WorldFactory.create(worldModel, worldController, tileViewManager);

        worldController.populateFromMap(map, world);
        worldController.setPlayer(player);

        player.setWorld(world);
        worldModel.setPlayer(player);

        TileModel startTile = worldModel.getTileModel(player.getX(), player.getY());
        if (startTile != null) startTile.setContainsPlayer(true);

        world.setTickHandler(() -> worldController.tick(world));

        world.repaint();
        world.repaintOverlay();

        org.minicraft02160.model.CraftingMenuModel cmModel = new org.minicraft02160.model.CraftingMenuModel();
        org.minicraft02160.controller.CraftingMenuController cmController =
                new org.minicraft02160.controller.CraftingMenuController(cmModel, player);
        org.minicraft02160.view.CraftingMenu craftingMenu =
                new org.minicraft02160.view.CraftingMenu(cmModel, cmController, player);
        world.getHudManager().setCraftingMenu(craftingMenu);

        org.minicraft02160.controller.InteractionService interactionService =
                new org.minicraft02160.controller.InteractionService(world);

        worldController.setInteractionService(interactionService);

        //InteractionService interactionService = new InteractionService(world);
        //worldController.setInteractionService(interactionService);

        player.getInventory().add(new IronAxeItem());
        player.getInventory().add(new WaterBottleItem());
        player.getInventory().add(new LighterWithFireItem());

        MobSpawner spawner = new MobSpawner(world, player);
        spawner.setEnabled(true);
        spawner.setSpawnInterval(30);
        spawner.setMaxMobsPerRegion(10);
        worldController.setMobSpawner(spawner);

        DropImageProvider imageProvider = new ResourceDropImageProvider();
        DroppedConsumableManager consumableManager =
                new DroppedConsumableManager(imageProvider, world, world);
        MobDeathHandler deathHandler = new MobDeathHandler(consumableManager);
        DropCollector dropCollector = new DropCollector(consumableManager);
        CombatController combatController = new CombatController(spawner, player, deathHandler);
        worldController.setCombatController(combatController);
        worldController.setDropCollector(dropCollector);

        GameWindow window = new GameWindow("Minicraft Demo - v.0.2", world);
        window.show();

        new InputHandler(world, new WorldInputAdapter(world, worldController)).install();

        GameLoopController gameLoop = new GameLoopController(world, player, spawner, combatController, window);
        gameLoop.start();
    }

    public static void restartGame() {
        startGame();
    }
}