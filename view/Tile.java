package org.minicraft02160.view;

import org.minicraft02160.controller.DayNightCycle;
import org.minicraft02160.controller.DroppedConsumableEntry;
import org.minicraft02160.model.extras.ResourceType;
import org.minicraft02160.model.MobModel;
import org.minicraft02160.model.Player;
import org.minicraft02160.model.TileModel;
import org.minicraft02160.model.worldengine.TerrainType;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends JPanel implements TileAccessor {

    public static final int PIXEL_SIZE = 51;

    private final TileModel tileModel;
    private final MobRenderer mobRenderer;
    private World world;
    protected BufferedImage image;
    private DroppedConsumableEntry droppedEntry;

    public Tile(World world) {
        super(true);
        this.world = world;
        this.tileModel = new TileModel(0, 0);
        this.mobRenderer = new MobRenderer();
        setMinimumSize(new Dimension(PIXEL_SIZE, PIXEL_SIZE));
        setMaximumSize(getMinimumSize());
        setPreferredSize(getMinimumSize());
    }

    public void setTilePosition(int x, int y) {
        tileModel.setTileX(x);
        tileModel.setTileY(y);
    }

    public void setDroppedEntry(DroppedConsumableEntry entry) {
        this.droppedEntry = entry;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        renderBase(g2d);
        renderResource(g2d);
        mobRenderer.render(g2d, tileModel.getMob());
        renderPlayer(g2d);
        renderDroppedItem(g2d);
        renderDarkness(g2d);
    }

    public void render(Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            g2d.translate(x, y);
            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );
            renderBase(g2d);
            renderResource(g2d);
            mobRenderer.render(g2d, tileModel.getMob());
            renderPlayer(g2d);
            renderDroppedItem(g2d);
            renderDarkness(g2d);
        } finally {
            g2d.dispose();
        }
    }

    private void renderBase(Graphics2D g2d) {
        g2d.drawImage(image, 0, 0, PIXEL_SIZE, PIXEL_SIZE, null);
    }

    private void renderPlayer(Graphics2D g2d) {
        if (!tileModel.isContainsPlayer()) return;
        if (world == null || world.getPlayerView() == null) return;
        g2d.drawImage(world.getPlayerView().getImagePlayer(), 0, 0, null);
    }

    private void renderResource(Graphics2D g2d) {
        ResourceType resourceType = tileModel.getResourceType();
        if (resourceType != null && resourceType.hasImage()) {
            g2d.drawImage(resourceType.getImage(), 0, 0, null);
        }
    }

    private void renderDroppedItem(Graphics2D g2d) {
        if (droppedEntry == null || droppedEntry.isExpired()) return;

        if (droppedEntry.isBlinking()) {
            long now = System.currentTimeMillis();
            if ((now / 200) % 2 == 0) return;
        }

        Image img = droppedEntry.getDropImage();

        if (img != null) {
            g2d.drawImage(img, 2, 2, PIXEL_SIZE - 4, PIXEL_SIZE - 4, null);
        }
    }

    private void renderDarkness(Graphics2D g2d) {
        if (world == null) return;
        DayNightCycle cycle = world.getDayNightCycle();
        if (cycle == null) return;
        Player player = world.getPlayerView();
        int darkness = cycle.getDarkness(
                tileModel.getTileX(),
                tileModel.getTileY(),
                player
        );
        if (darkness > 0) {
            g2d.setColor(new Color(0, 0, 0, darkness));
            g2d.fillRect(0, 0, PIXEL_SIZE, PIXEL_SIZE);
        }
    }

    @Override
    public Tile getTileView(int x, int y) { return this; }

    public boolean isWalkable() { return tileModel.isWalkable(); }

    public void setMob(MobModel mob) {
        tileModel.setMob(mob);
        repaint();
    }

    public MobModel getMob() { return tileModel.getMob(); }

    public void setContainsPlayer(boolean containsPlayer) {
        tileModel.setContainsPlayer(containsPlayer);
        repaint();
    }

    public void setResource(ResourceType resourceType) {
        tileModel.setResourceType(resourceType);
        repaint();
    }

    public ResourceType getResourceType() { return tileModel.getResourceType(); }

    public void setTerrainType(TerrainType type) {
        tileModel.setTerrainType(type);
        repaint();
    }

    public TerrainType getTerrainType() { return tileModel.getTerrainType(); }
    public TileModel getTileModel() { return tileModel; }
}