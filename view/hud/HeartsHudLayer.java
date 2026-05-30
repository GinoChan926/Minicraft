package org.minicraft02160.view.hud;

import org.minicraft02160.model.Player;
import org.minicraft02160.view.World;
import org.minicraft02160.view.WorldRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public final class HeartsHudLayer implements HudLayer {
    private static final int PADDING_X = 12;
    private static final int PADDING_Y = 12;
    private static final int HEART_SCALE = 1;
    private static final int HEART_GAP_PX = 6;

    private final BufferedImage heartSprite;

    public HeartsHudLayer() {
        this.heartSprite = loadHeartSprite();
    }

    @Override
    public void paint(Graphics2D g2d, WorldRenderer.WorldView view) {
        Player player = view.getPlayerView();
        if (player == null) return;

        int current = Math.max(0, player.getLives());
        int max = Math.max(0, player.getMaxLives());
        if (max == 0) return;

        int heartW = Math.max(1, heartSprite.getWidth() * HEART_SCALE);
        int heartH = Math.max(1, heartSprite.getHeight() * HEART_SCALE);

        int x = PADDING_X;
        int y = PADDING_Y;

        Object oldAA = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < max; i++) {
            if (i < current) {
                g2d.drawImage(heartSprite, x, y, heartW, heartH, null);
            } else {
                Composite old = g2d.getComposite();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
                g2d.drawImage(heartSprite, x, y, heartW, heartH, null);
                g2d.setComposite(old);
            }
            x += heartW + HEART_GAP_PX;
        }

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAA);
    }

    private BufferedImage loadHeartSprite() {
        try {
            var stream = getClass().getClassLoader().getResourceAsStream("heart.png");
            if (stream == null) {
                return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            }
            return ImageIO.read(stream);
        } catch (IOException e) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }
    }
}

