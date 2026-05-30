package org.minicraft02160.controller;

import org.minicraft02160.model.WorldModel;
import org.minicraft02160.model.worldengine.*;
import org.minicraft02160.view.TileViewManager;
import org.minicraft02160.view.World;
import org.minicraft02160.view.WorldFactory;

import javax.swing.*;
import java.util.List;

public final class WorldGenerator {

    private WorldGenerator() {}

    public static World generate(int rows, int cols) {
        TerrainType[][] map = generateMap(rows, cols);

        WorldModel model = new WorldModel(rows, cols);
        ChunkManager chunkManager = new ChunkManager();
        TileViewManager tileViewManager = new TileViewManager(model);

        WorldController worldController = new WorldController(model, chunkManager, tileViewManager);

        World world = WorldFactory.create(model, worldController, tileViewManager);

        Runnable populate = () -> {
            worldController.populateFromMap(map, world);
            world.revalidate();
            world.repaint();
        }; //this block is wrapped so it can be run on the correct thread

        if (SwingUtilities.isEventDispatchThread()) {
            populate.run();
        } else {
            try {
                SwingUtilities.invokeAndWait(populate);
            } catch (InterruptedException | java.lang.reflect.InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        return world;
    }

    public static TerrainType[][] generateMap(int rows, int cols) {
        // using classes from worldengine to generate clusters
        // World engine contains steps for how the clusters are build
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
        return map;
    }
}
