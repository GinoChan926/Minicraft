package org.minicraft02160.controller;

import org.minicraft02160.model.Player;
import org.minicraft02160.view.Tile;
import org.minicraft02160.view.World;

import javax.swing.*;
import java.awt.*;

public class GameTransitionController {
    // What does the controllers need from ChunckManger
    public interface ChunkManagerPort{
        void saveCurrentScreen(World world);
        void loadNewScreen(World world, int nextX, int nextY);
        int getChunkX();
        int getChunkY();
    }

    private final ChunkManagerPort chunkmanager;

    public GameTransitionController(ChunkManagerPort chunkmanager) {
        this.chunkmanager = chunkmanager;
    }

    public boolean transition(World world, Player player, int dx, int dy) {
        SpawnResolver spawnResolver = new SpawnResolver(world.getRows(), world.getCols(), world::isTileWalkable);
        chunkmanager.saveCurrentScreen(world);
        chunkmanager.loadNewScreen(world, chunkmanager.getChunkX() + dx, chunkmanager.getChunkY() + dy);

        int preferredX = player.getX();
        int preferredY = player.getY();

        int oldChunkX = chunkmanager.getChunkX();
        int oldChunkY = chunkmanager.getChunkY();
        int oldX = player.getX();
        int oldY = player.getY();

        // Spawn player on opposite edge of destination chunk
        if (dx == -1) preferredX = world.getCols() - 1;
        if (dx == 1) preferredX = 0;
        if (dy == -1) preferredY = world.getRows() - 1;
        if (dy == 1) preferredY = 0;

        Point spawn = spawnResolver.resolveSafeSpawn(preferredX, preferredY, dx, dy);
        if (spawn == null) {
            // No valid spawn found, revert to old chunk and position
            chunkmanager.loadNewScreen(world, oldChunkX, oldChunkY);
            player.setPosition(oldX, oldY);
            world.getTileView(player.getX(), player.getY()).setContainsPlayer(true);
            return false;
        }

        player.setPosition(spawn.x, spawn.y);
        world.getTileView(player.getX(), player.getY()).setContainsPlayer(true);

        world.revalidate();
        world.repaint();
        return true;
    }

    public void startTransition(World world, Player player, int dx, int dy) {
        world.setFading(true);

        new Thread(() -> {
            boolean ok = transition(world, player, dx, dy);
            SwingUtilities.invokeLater(() -> {
                if (!ok) {
                    Tile tile = world.getTileView(player.getX(), player.getY());
                    if (tile != null) tile.setContainsPlayer(true);
                }

                javax.swing.Timer unlockTimer = new javax.swing.Timer(120, e -> {
                    world.setFading(false);
                    ((javax.swing.Timer) e.getSource()).stop();
                });
                unlockTimer.setRepeats(false);
                unlockTimer.start();
            });
        }, "transition-thread").start();
    }
}
