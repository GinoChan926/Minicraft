package org.minicraft02160.view;

import org.minicraft02160.model.TileTypes.TileFactory;
import org.minicraft02160.model.worldengine.TerrainType;

import javax.swing.*;

public class MapToWorldConverter {
    public void apply(TerrainType[][] map, TileViewManager tvm, World world, int rows, int cols) {
        if (map == null || world == null || tvm == null) return;

        Runnable populate = () -> {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    Tile tile = TileFactory.create(map[r][c], world);
                    tvm.setTileView(r, c, tile);
                }
            }
            tvm.addAllToContainer(world);
        };

        if (SwingUtilities.isEventDispatchThread()) {
            populate.run();
        } else {
            try {
                SwingUtilities.invokeAndWait(populate);
            } catch (InterruptedException | java.lang.reflect.InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
