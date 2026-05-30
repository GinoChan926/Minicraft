package org.minicraft02160.view;

import org.minicraft02160.controller.ChunkManager;
import org.minicraft02160.controller.HudManager;
import org.minicraft02160.controller.WorldController;
import org.minicraft02160.model.WorldModel;
import org.minicraft02160.view.hud.HeartsHudLayer;

public final class WorldFactory {

    private WorldFactory() {}

    public static World create(WorldModel model, ChunkManager chunkManager) {
        TileViewManager tileViewManager = new TileViewManager(model);
        WorldController wc = new WorldController(model, chunkManager, tileViewManager);
        return create(model, wc, tileViewManager);
    }

    public static World create(WorldModel model, WorldController worldController, TileViewManager tileViewManager) {
        HudManager hudManager = new HudManager();
        hudManager.add(new HeartsHudLayer());

        World world = new World(model, worldController, tileViewManager, hudManager);

        WorldRenderer renderer = new WorldRenderer(world);
        world.setRenderer(renderer);

        return world;
    }

}
