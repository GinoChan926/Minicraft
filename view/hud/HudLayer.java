package org.minicraft02160.view.hud;

import org.minicraft02160.view.World;
import org.minicraft02160.view.WorldRenderer;

import java.awt.*;

public interface HudLayer {
    void paint(Graphics2D g2d, WorldRenderer.WorldView view);
}

