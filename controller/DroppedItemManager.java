package org.minicraft02160.controller;

import org.minicraft02160.model.Item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DroppedItemManager {
    private static final int MAX_ITEMS=100;
    private final List<DroppedItemEntry> droppedItems;

    public DroppedItemManager() {
        this.droppedItems = new ArrayList<>();
    }

    // drops items a specific position
    public void dropItem(Item item, int x, int y) {
        if (item == null) {
            System.out.println("No item to drop");
            return;
        }
        else if (droppedItems.size() >= MAX_ITEMS) {
            System.out.println("Too many items to drop");
            return;
        }
        droppedItems.add(new DroppedItemEntry(item, x, y));
    }

    // update all dropped items
    public void tick() {
        Iterator<DroppedItemEntry> iterator = droppedItems.iterator();
        while (iterator.hasNext()) {
            DroppedItemEntry entry = iterator.next();
            entry.tick();
            if (entry.isExpired()) { // remove if expired
                System.out.println("Item expired: " + entry.getItem().getName());
                iterator.remove();
            }
        }
    }

    // collects and removes all items at position
    public List<Item> collectItemsAt(int x, int y) {
        List<Item> collected = new ArrayList<>();
        Iterator<DroppedItemEntry> iterator = droppedItems.iterator();
        while (iterator.hasNext()) {
            DroppedItemEntry entry = iterator.next();
            if (entry.getX() == x && entry.getY() == y) {
                collected.add(entry.getItem());
                iterator.remove();
            }
        }
        return collected;
    }
    public List<DroppedItemEntry> getAllDroppedItems() {
        return droppedItems;
    }

    // manages one item laying on the ground
    public static class DroppedItemEntry {

        private static final int LIFETIME = 300;

        private final Item item;
        private final int x;
        private final int y;
        private int ticksRemaining;

        public DroppedItemEntry(Item item, int x, int y) {
            this.item = item;
            this.x = x;
            this.y = y;
            this.ticksRemaining = LIFETIME;
        }

        public void tick() {
            ticksRemaining--;
        }
        public boolean isExpired() {
            return ticksRemaining <= 0;
        }
        public boolean isBlinking() {
            return ticksRemaining <= 60;
        }

        public Item getItem() {
            return item;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        public int getTicksRemaining() {
            return ticksRemaining;
        }

    }
}
