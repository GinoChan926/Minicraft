package org.minicraft02160.controller;

import org.minicraft02160.model.extras.ResourceType;
import org.minicraft02160.view.Tile;
import org.minicraft02160.view.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class RegrowthManager {
    private final List<RegrowthEntry> entries = new ArrayList<>();

    public void addRegrowth(int x, int y, ResourceType type, int regrowthTime) {
        entries.add(new RegrowthEntry(x, y, type, regrowthTime));
    }

    public void tick(World world) {
        Iterator<RegrowthEntry> it = entries.iterator();
        while (it.hasNext()) {
            RegrowthEntry entry = it.next();
            entry.remainingSeconds--;
            if (entry.remainingSeconds <= 0) {
                if (world.isInBounds(entry.x, entry.y)) {
                    Tile tile = world.getTileView(entry.x, entry.y);

                    // only regrow if tile is still empty
                    if (tile != null && tile.getResourceType() == null) {
                        tile.setResource(entry.type);
                    }
                }
                // remove entry no matter what, prevent having multiple unused entries
                it.remove();
            }
        }
    }

    public int getPendingCount() {
        return entries.size();
    }

    private static class RegrowthEntry {
        int x, y;
        ResourceType type;
        int remainingSeconds;
        RegrowthEntry(int x, int y, ResourceType type, int delaySeconds) {
            this.x = x;
            this.y = y;
            this.type = type;
            this.remainingSeconds = delaySeconds;
        }
    }
}
