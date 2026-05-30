package org.minicraft02160.model;

import org.minicraft02160.model.extras.EquipmentSlot;

import java.util.List;

// Class for items that can be equipped by the player, extends the Item class for the item properties, and implements Equippable for the specific behaviour of equipping and unequipping items. It includes durability mechanics, damage values, and methods for equipping and unequipping items
public abstract class EquipableItem extends Item implements Equipable {

    private boolean equipped;
    private int durability;
    private final boolean hasDurability;
    private int damage;

    // Two different constructors for items with and without durability
    public EquipableItem(String name) {
        super(name);
        this.equipped = false;
        this.durability = -1;
        this.hasDurability = false;
        this.damage = 0;
    }

    public EquipableItem(String name, int durability) {
        super(name);
        this.equipped = false;
        this.durability = durability;
        this.hasDurability = true;
        this.damage = 0;
    }

    public abstract EquipmentSlot getSlot();

    @Override
    public boolean isEquipped() {
        return equipped;
    }


    public int getDurability() {
        return durability;
    }

    public void reduceDurability(int amount) {
        if (hasDurability) {
            durability = Math.max(0, durability - amount);
        }
    }

    public boolean isBroken() {
        return hasDurability && durability <= 0;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    // no implementation for onEquip and onUnequip in the EquipableItem class, the behaviours are specified in the specific subclasses
    public void onEquip(Inventory inventory) {
    }

    public void onUnequip(Inventory inventory) {
    }

    // specifies behaviour for equipping the item if not already equipped and not broken, and for unequipping the item if it is already equipped, if invenory is full, then the item is dropped at the location of the player
    @Override
    public boolean use(Player player) {
        Inventory inventory = player.getInventory();

        if (!equipped) {
            if (isBroken()) {
                System.out.println(getName() + " is broken!");
                return false;
            }
            inventory.equip(this);
            equipped = true;
        } else {
            List<Item> overflow = inventory.unequip(this);
            for (Item item : overflow) {
                player.getWorld().dropItemAt(item, player.getX(), player.getY());
            }
            equipped = false;
        }

        return true;
    }
}