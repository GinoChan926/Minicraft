package org.minicraft02160.controller;

import org.minicraft02160.model.ScreenData;
import org.minicraft02160.model.TileTypes.TileFactory;
import org.minicraft02160.view.Tile;
import org.minicraft02160.view.World;
import org.minicraft02160.model.worldengine.TerrainType;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
// Manages loading, saving, and switching between "chunks"/ screens of the world
public class ChunkManager implements GameTransitionController.ChunkManagerPort {

    // Stores previously visited chunks using "x,y" as key
    private final Map<String, ScreenData> worldMemory = new HashMap<>();

    // Current coordinates
    private int chunkX = 0;
    private int chunkY = 0;

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkY() {
        return chunkY;
    }

    // Saves world into memory
    @Override
    public void saveCurrentScreen(World world) {
        int rows = world.getRows();
        int cols = world.getCols();


        ScreenData data = new ScreenData(rows, cols);

        // Loop through every tile, store its terrain + resource type
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                Tile t = world.getTileView(i, j);
                data.tileTypes[j][i] = t.getTerrainType();
                data.resources[j][i] = t.getResourceType();
            }
        }

        // Save chunk using its coordinate key
        worldMemory.put(key(chunkX, chunkY), data);
    }


    @Override
    public void loadNewScreen(World world, int nextX, int nextY) {
        // Update current chunk position
        chunkX = nextX;
        chunkY = nextY;

        final int rows = world.getRows();
        final int cols = world.getCols();
        final String key = key(chunkX, chunkY);

        // Clear current UI safely (Swing requires UI updates on EDT)
        try {
            if (SwingUtilities.isEventDispatchThread()) {
                world.removeAll();
            } else {
                SwingUtilities.invokeAndWait(world::removeAll);
            }
        } catch (InterruptedException | java.lang.reflect.InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        // If already visited chunk → load from memory
        if (worldMemory.containsKey(key)) {

            ScreenData saved = worldMemory.get(key);

            // Rebuild tiles from saved data
            Runnable applySaved = () -> {
                for (int j = 0; j < rows; j++) {
                    for (int i = 0; i < cols; i++) {
                        Tile tile = TileFactory.create(saved.tileTypes[j][i], world);
                        tile.setResource(saved.resources[j][i]);
                        world.setTileAt(j, i, tile);
                    }
                }
                world.revalidate();
                world.repaint();
            };

            // Ensure UI updates happen on EDT
            try {
                if (SwingUtilities.isEventDispatchThread()) applySaved.run();
                else SwingUtilities.invokeAndWait(applySaved);
            } catch (InterruptedException | java.lang.reflect.InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            return;
        }

        // If not then generate a completely new chunk
        TerrainType[][] map = WorldGenerator.generateMap(rows, cols);

        // Apply generated terrain to the world
        Runnable applyGenerated = () -> {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    TerrainType terrain = map[r][c];
                    Tile tile = TileFactory.create(terrain, world);
                    world.setTileAt(r, c, tile);
                }
            }
            world.revalidate();
            world.repaint();
        };

        // Ensure UI updates are on EDT
        try {
            if (SwingUtilities.isEventDispatchThread()) applyGenerated.run();
            else SwingUtilities.invokeAndWait(applyGenerated);
        } catch (InterruptedException | java.lang.reflect.InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    private String key(int x, int y) {
        return x + "," + y;
    }
}