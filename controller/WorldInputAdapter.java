package org.minicraft02160.controller;

import org.minicraft02160.view.World;

import java.awt.event.KeyEvent;

public class WorldInputAdapter implements WorldInputPort {

    private final World world;
    private final WorldController controller;

    public WorldInputAdapter(World world, WorldController controller) {
        this.world = world;
        this.controller = controller;
    }

    @Override
    public boolean isInputLocked() {
        return world.isInputLocked();
    }

    @Override
    public void handleInteractionKey() {
        controller.triggerInteraction(world);
    }

    @Override
    public void handleTileActionKey() {
        controller.handleTileAction(world);
    }

    @Override
    public boolean handleMenuKey(KeyEvent e) {
        return world.handleMenuKey(e);
    }

    @Override
    public void handleMoveKey(int dx, int dy) {
        controller.handleMove(world, dx, dy);
    }
}
