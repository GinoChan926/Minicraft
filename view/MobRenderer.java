package org.minicraft02160.view;

import org.minicraft02160.model.MobModel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MobRenderer {

    public void render(Graphics2D g2d, MobModel mob) {
        if (mob == null || !mob.isAlive()) return;

        Image sprite = mob.getType().getSprite();
        renderSprite(g2d, sprite);
        renderHealthBar(g2d, mob);
    }

    private void renderSprite(Graphics2D g2d, Image sprite) {
        g2d.drawImage(sprite, 4, 4, null);
    }


    private void renderHealthBar(Graphics2D g2d, MobModel mob) {
        int barW = Tile.PIXEL_SIZE - 16;
        int barH = 6;
        int barX = 8;
        int barY = 2;

        g2d.setColor(Color.RED);
        g2d.fillRect(barX, barY, barW, barH);

        g2d.setColor(Color.GREEN);
        double pct = (double) mob.getCurrentHealth() / mob.getMaxHealth();
        g2d.fillRect(barX, barY, (int) (barW * pct), barH);
    }

}