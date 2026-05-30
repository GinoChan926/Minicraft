package org.minicraft02160.controller;

import org.minicraft02160.controller.handlers.AppleTreeHandler;
import org.minicraft02160.controller.handlers.StoneHandler;
import org.minicraft02160.controller.handlers.WaterHandler;
import org.minicraft02160.model.Player;
import org.minicraft02160.model.ResourceInteractionHandler;
import org.minicraft02160.model.extras.ResourceType;
import org.minicraft02160.view.Tile;
import org.minicraft02160.view.World;

import java.util.ArrayList;
import java.util.List;

public class InteractionService {

    private final World world;
    private final List<ResourceInteractionHandler> handlers;

    public InteractionService(World world) {
        this.world = world;
        this.handlers = new ArrayList<>();
        // Register handlers in one place so adding new resource
        handlers.add(new AppleTreeHandler());
        handlers.add(new StoneHandler());
        handlers.add(new WaterHandler());
    }

    public void handleResourceInteraction(ResourceType resourceType, Tile tile) {
        if (resourceType == null || world.getPlayerView() == null) {
            return;
        }

        Player player = world.getPlayerView();

        for (ResourceInteractionHandler handler : handlers) {
            // First matching handler owns the interaction, so handler order can matter.
            if (handler.canHandle(resourceType)) {
                handler.handle(player, tile, world);
                return;
            }
        }
    }
}