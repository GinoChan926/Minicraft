package org.minicraft02160.controller;

import org.minicraft02160.model.DropImageProvider;
import org.minicraft02160.model.extras.ConsumableItem;

import java.awt.Image;

// Manages a consumable Item on a tile, and defines when it will disappear
public class DroppedConsumableEntry {

    private static final int LIFETIME = 300;

    private final ConsumableItem consumableItem;
    private final DropImageProvider imageProvider;
    private final int x;
    private final int y;
    private int ticksRemaining;

    public DroppedConsumableEntry(ConsumableItem consumableItem,
                                  DropImageProvider imageProvider,
                                  int x, int y) {
        this.consumableItem = consumableItem;
        this.imageProvider = imageProvider;
        this.x = x;
        this.y = y;
        this.ticksRemaining = LIFETIME;
    }

    public void tick() { ticksRemaining--; } // counting down
    public boolean isExpired() { return ticksRemaining <= 0; }
    public boolean isBlinking() { return ticksRemaining <= 60; }
    public Image getDropImage() { return imageProvider.getImageFor(consumableItem); }
    public ConsumableItem getConsumableItem() { return consumableItem; }
    public int getX() { return x; }
    public int getY() { return y; }
}