package org.minicraft02160.controller;

import org.minicraft02160.model.DropImageProvider;
import org.minicraft02160.model.extras.ConsumableItem;
import org.minicraft02160.view.Tile;
import org.minicraft02160.view.TileAccessor;
import org.minicraft02160.view.WorldRefreshPort;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DroppedConsumableManager {

    private static final int MAX_ITEMS = 100;

    private final List<DroppedConsumableEntry> entries = new ArrayList<>();
    private final DropImageProvider imageProvider;
    private final TileAccessor tileAccessor;
    private final WorldRefreshPort worldRefreshPort;

    public DroppedConsumableManager(DropImageProvider imageProvider,
                                    TileAccessor tileAccessor,
                                    WorldRefreshPort worldRefreshPort) {
        this.imageProvider = imageProvider;
        this.tileAccessor = tileAccessor;
        this.worldRefreshPort = worldRefreshPort;
    }

    //drops a consumable at given world position
    public void drop(ConsumableItem item, int x, int y) {
        if (item == null) return;
        if (entries.size() >= MAX_ITEMS) return;// limit

        // creat and store new dropped item in Entry
        DroppedConsumableEntry entry = new DroppedConsumableEntry(item, imageProvider, x, y);
        entries.add(entry);

        // visual update
        SwingUtilities.invokeLater(() -> {
            notifyTile(x, y, entry);
            worldRefreshPort.refreshWorld();
        });
    }

    // manages all dropped item
    public void tick() {
        boolean changed = false;
        Iterator<DroppedConsumableEntry> it = entries.iterator();
        while (it.hasNext()) {
            DroppedConsumableEntry entry = it.next();
            entry.tick();
            //Remove expired items
            if (entry.isExpired()) {
                final int ex = entry.getX();
                final int ey = entry.getY();
                SwingUtilities.invokeLater(() -> clearTile(ex, ey));
                it.remove();
                changed = true;
            }
        }
        if (changed) {
            SwingUtilities.invokeLater(() -> worldRefreshPort.refreshWorld());
        }
    }

    public List<DroppedConsumableEntry> collectAt(int x, int y) {
        List<DroppedConsumableEntry> result = new ArrayList<>();
        Iterator<DroppedConsumableEntry> it = entries.iterator();
        while (it.hasNext()) {
            DroppedConsumableEntry entry = it.next();
            if (entry.getX() == x && entry.getY() == y) {
                result.add(entry);
                it.remove();
            }
        }
        if (!result.isEmpty()) {
            SwingUtilities.invokeLater(() -> {
                clearTile(x, y);
                worldRefreshPort.refreshWorld();
            });
        }
        return result;
    }


    private void notifyTile(int x, int y, DroppedConsumableEntry entry) {
        Tile tile = tileAccessor.getTileView(x, y);
        if (tile != null) {
            tile.setDroppedEntry(entry);
        }
    }

    private void clearTile(int x, int y) {
        Tile tile = tileAccessor.getTileView(x, y);
        if (tile != null) {
            tile.setDroppedEntry(null);
        }
    }
}