package org.minicraft02160.view;

import org.minicraft02160.controller.DayNightCycle;
import org.minicraft02160.model.extras.EquipmentSlot;
import org.minicraft02160.model.Player;
import org.minicraft02160.model.TileModel;
import org.minicraft02160.view.hud.HudLayer;

import java.awt.*;
import java.util.List;

public class WorldRenderer {

    private final WorldView worldView;

    public WorldRenderer(WorldView worldView) {
        this.worldView = worldView;
    }

    public interface WorldView {
        Player getPlayerView();// view Tile at (x,y)
        List<HudLayer> getHudLayersView();
        int getRows();
        int getCols();
        DayNightCycle getDayNightCycle();

        void renderTiles(Graphics g);

        org.minicraft02160.controller.HudManager getHudManager();
    }


    public void render(Graphics g) {
        worldView.renderTiles(g);

        renderHud(g);

        if (worldView.getHudManager() != null) {
            worldView.getHudManager().render(g, worldView);
        } else {
            renderHudLayers(g);
        }
    }

    private void renderHud(Graphics g) {
        Player player = worldView.getPlayerView();
        if (player == null) return;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Arial", Font.BOLD, 20));

        DayNightCycle dnc = worldView.getDayNightCycle();

        if (player.getInventory().hasEquipped(EquipmentSlot.BACK)) {
            g2d.setColor(Color.GREEN);
            g2d.drawString("\uD83C\uDF92 Backpack", 150, 30);
        }

        if (player.getInventory().hasEquipped(EquipmentSlot.MAIN_HAND)) {
            g2d.setColor(Color.YELLOW);
            g2d.drawString("\u2694  " + player.getInventory()
                    .getEquipped(EquipmentSlot.MAIN_HAND).getName(), 300, 30);
        }

        if (player.getInventory().hasEquipped(EquipmentSlot.OFF_HAND)) {
            g2d.setColor(Color.YELLOW);
            g2d.drawString("\uD83D\uDEE1  " + player.getInventory()
                    .getEquipped(EquipmentSlot.OFF_HAND).getName(), 300, 50);
        }

        g2d.setColor(Color.WHITE);
        g2d.drawString("\uD83D\uDCE6 " + player.getInventory().getSize()
                + "/" + player.getInventory().getMaxCapacity(), 500, 30);
    }

    private void renderHudLayers(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            for (HudLayer layer : worldView.getHudLayersView()) {
                layer.paint(g2d, worldView);
            }
        } finally {
            g2d.dispose();
        }
    }

}
