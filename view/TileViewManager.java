package org.minicraft02160.view;

import org.minicraft02160.model.WorldModel;

import javax.swing.*;
import java.awt.*;

public class TileViewManager {

    private final WorldModel worldModel;
    private final Tile[][] tileViews;


    public TileViewManager(WorldModel worldModel) {
        this.worldModel = worldModel;
        this.tileViews = new Tile[worldModel.getRows()][worldModel.getCols()];
    }
    public void setTileView(int row, int col, Tile tile) {
        if (row >= 0 && col >= 0 && row < worldModel.getRows() && col < worldModel.getCols()) {
            tileViews[row][col] = tile;
            tile.setTilePosition(col, row);
            worldModel.setTileAt(row, col, tile.getTileModel());
        }
    }

    public Tile getTile(int x, int y) {
        // x is column index (0..cols-1), y is row index (0..rows-1)
        if (x >= 0 && y >= 0 && x < worldModel.getCols() && y < worldModel.getRows()) {
            return tileViews[y][x];
        }
        return null;
    }

    public void renderTiles(Graphics g) {
        if (g == null) return;

        for (int row = 0; row < worldModel.getRows(); row++) {
            for (int col = 0; col < worldModel.getCols(); col++) {
                Tile t = getTile(col, row);
                if (t != null) {
                    t.render(g, col * Tile.PIXEL_SIZE, row * Tile.PIXEL_SIZE);
                }
            }
        }
    }

    public void updateContainerCell(java.awt.Container worldContainer, int row, int col) {
        if (worldContainer == null) return;
        if (row < 0 || col < 0 || row >= getRows() || col >= getCols()) return;

        int index = row * getCols() + col;

        if (index < worldContainer.getComponentCount()) {
            worldContainer.remove(index);
        }

        Tile t = tileViews[row][col];
        if (t == null) {
            JPanel placeholder = new JPanel();
            placeholder.setPreferredSize(new Dimension(Tile.PIXEL_SIZE, Tile.PIXEL_SIZE));
            worldContainer.add(placeholder, index);
        } else {
            worldContainer.add(t, index);
        }

        worldContainer.revalidate();
        worldContainer.repaint();
    }

    public int getRows() {
        return worldModel.getRows();
    }

    public int getCols() {
        return worldModel.getCols();
    }

    public void addAllToContainer(java.awt.Container worldContainer) {
        if (worldContainer == null) return;
        worldContainer.removeAll();

        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getCols(); col++) {
                Tile t = tileViews[row][col];
                if (t == null) {
                    // placeholder so GridLayout keeps its shape
                    JPanel placeholder = new JPanel();
                    placeholder.setPreferredSize(new Dimension(Tile.PIXEL_SIZE, Tile.PIXEL_SIZE));
                    worldContainer.add(placeholder);
                } else {
                    worldContainer.add(t);
                }
            }
        }

        worldContainer.revalidate();
        worldContainer.repaint();
    }


}
