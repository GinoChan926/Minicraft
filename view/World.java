package org.minicraft02160.view;

import org.minicraft02160.controller.*;
import org.minicraft02160.model.*;
import org.minicraft02160.model.extras.ResourceType;
import org.minicraft02160.model.Player;
import org.minicraft02160.model.WorldModel;
import org.minicraft02160.view.hud.HudLayer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class World extends JPanel implements WorldRenderer.WorldView, WorldPort, TileAccessor, WorldRefreshPort {

    private final int rows;
    private final int cols;
    private final TileViewManager tileViewManager;
    private final HudManager hudManager;
    private WorldRenderer worldRenderer;
    private JPanel glassPane;
    private final WorldController worldController;
    private Runnable tickHandler;

    public World(WorldModel model, WorldController worldController,
                 TileViewManager tileViewManager, HudManager hudManager) {
        this.rows = model.getRows();
        this.cols = model.getCols();
        this.tileViewManager = tileViewManager;
        this.hudManager = hudManager;
        this.worldController = worldController;

        setLayout(new GridLayout(rows, cols));
        setPreferredSize(new Dimension(cols * Tile.PIXEL_SIZE, rows * Tile.PIXEL_SIZE));
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (worldRenderer != null) worldRenderer.render(g);
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public void setRenderer(WorldRenderer r) { this.worldRenderer = r; }
    public void setGlassPane(JPanel pane) { this.glassPane = pane; }
    public void repaintOverlay() { if (glassPane != null) glassPane.repaint(); }

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < cols && y >= 0 && y < rows;
    }

    @Override
    public Tile getTileView(int x, int y) {
        return tileViewManager.getTile(x, y);
    }

    public void setResource(int row, int col, ResourceType resource) {
        Tile t = tileViewManager.getTile(col, row);
        if (t == null) return;
        t.setResource(resource);
        Runnable update = () -> tileViewManager.updateContainerCell(this, row, col);
        if (SwingUtilities.isEventDispatchThread()) update.run();
        else SwingUtilities.invokeLater(update);
    }

    public void setTileAt(int row, int col, Tile tile) {
        updateTileView(row, col, tile);
    }

    @Override
    public boolean isTileWalkable(int x, int y) {
        Tile tile = tileViewManager.getTile(x, y);
        return tile != null && tile.isWalkable();
    }

    public void updateTileView(int row, int col, Tile tile) {
        tileViewManager.setTileView(row, col, tile);
        Runnable update = () -> tileViewManager.updateContainerCell(this, row, col);
        if (SwingUtilities.isEventDispatchThread()) update.run();
        else SwingUtilities.invokeLater(update);
    }

    @Override
    public void dropItemAt(Item item, int x, int y) {
        repaint();
    }

    @Override
    public void refreshWorld() {
        repaint();
        repaintOverlay();
    }

    @Override public void renderTiles(Graphics g) { tileViewManager.renderTiles(g); }
    @Override public Player getPlayerView() { return worldController.getPlayer(); }
    @Override public List<HudLayer> getHudLayersView() { return hudManager.getHudLayers(); }
    @Override public DayNightCycle getDayNightCycle() { return worldController.getDayNightCycle(); }
    @Override public HudManager getHudManager() { return hudManager; }

    private boolean isFading = false;

    public void setFading(boolean fading) {
        this.isFading = fading;
        repaint();
        repaintOverlay();
    }

    public boolean isInputLocked() { return isFading; }

    public void tick() {
        if (tickHandler != null) tickHandler.run();
    }

    public void setTickHandler(Runnable tickHandler) {
        this.tickHandler = tickHandler;
    }

    public CraftingMenu getCraftingMenu() {
        return hudManager.getCraftingMenu();
    }

    public boolean handleMenuKey(java.awt.event.KeyEvent e) {
        boolean handled = hudManager.handleMenuKey(e);
        if (handled) {
            repaint();
            repaintOverlay();
        }
        return handled;
    }
}