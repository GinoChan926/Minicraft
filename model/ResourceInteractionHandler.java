package org.minicraft02160.model;

import org.minicraft02160.view.Tile;
import org.minicraft02160.model.extras.ResourceType;
import org.minicraft02160.view.World;

// Introduced after refactoring, implementing the Strategy Pattern to handle the interactiong with the different resources
public interface ResourceInteractionHandler {
    boolean canHandle(ResourceType resourceType);
    void handle(Player player, Tile tile, World world);
}