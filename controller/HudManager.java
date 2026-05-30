package org.minicraft02160.controller;

import org.minicraft02160.view.CraftingMenu;
import org.minicraft02160.view.WorldRenderer;
import org.minicraft02160.view.hud.HudLayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HudManager {
    private final List<HudLayer> hudLayers = new ArrayList<>();
    private CraftingMenu craftingMenu;

    public void add(HudLayer layer) {
        if (layer != null) hudLayers.add(layer);
    }

    public void remove(HudLayer layer) {
        hudLayers.remove(layer);
    }

    public List<HudLayer> getHudLayers() {
        return Collections.unmodifiableList(hudLayers);
    }

    public void render(Graphics g, WorldRenderer.WorldView view) {
        if (g == null || view == null) return;

        // use copy of HUD rendering so it doesn't clash with world rendering state
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            for (HudLayer layer : hudLayers) {
                layer.paint(g2d, view);
            }
        } finally { // dispose copied graphics
            g2d.dispose();
        }
    }

    public void setCraftingMenu(CraftingMenu menu) {
        this.craftingMenu = menu;
    }

    public CraftingMenu getCraftingMenu() {
        return craftingMenu;
    }

    public boolean handleMenuKey(KeyEvent e) {
        if (e == null) return false;
        if (craftingMenu == null) return false;

        if (craftingMenu.isOpen()) {
            craftingMenu.keyPressed(e);
            return true;
        }

        char ch = e.getKeyChar();
        if (ch == 'c' || ch == 'C') { // prevents double handling (only toggle when closed)
            craftingMenu.toggle();
            return true;
        }

        return false;
    }
}
