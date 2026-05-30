package org.minicraft02160.model.Items;

import org.minicraft02160.model.Item;

public class ResourceItem extends Item {
    private boolean consumable;
    private int healthRestore;

    public ResourceItem(String name, boolean consumable, int healthRestore) {
        super(name);
        this.consumable = consumable;
        this.healthRestore = healthRestore;
    }


    public boolean isConsumable() { return consumable; }
    public int getHealthRestore() { return healthRestore; }

}
