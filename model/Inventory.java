package org.minicraft02160.model;

import org.minicraft02160.model.extras.EquipmentSlot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

// Inventory class takes care of storing player's items, equipping and unequipping items, and providing the crafting materials for crafting recipes
// Inventory implements the CraftingMaterialProvider interface to check if the player has the needed materials for a recipe and to remove materials when crafting is done
public class Inventory implements CraftingMaterialProvider {
    public static final int BASE_CAPACITY = 10;
    public static final int BACKPACK_BONUS_SLOTS = 15;
    public static final int SHIELD_BONUS_DEFENSE = 10;
    public static final int AXE_BONUS_ATTACK = 10;
    public static final int SUPER_AXE_BONUS_ATTACK = 20;

    // array list chosen as it is easy to add and remove items, size is not big, so it will perform well, allows duplicates, which suits the needs here
    private ArrayList<Item> items;
    private int maxCapacity;
    private int bonusAttack;
    private int bonusDefense;
    // equipment slot can only have one item equipped, and we are using the key as the slot type, more efficient for this purpose
    private final Map<EquipmentSlot, EquipableItem> equippedItems = new EnumMap<>(EquipmentSlot.class);

    public Inventory() {
        this.items = new ArrayList<>();
        this.maxCapacity = BASE_CAPACITY;
        this.bonusAttack = 0;
        this.bonusDefense = 0;
    }

    public boolean add(Item item) {
        if (item == null) {
            System.out.println("Cannot add nothing");
            return false;
        }
        if (items.size() >= maxCapacity) {
            System.out.println("Cannot add more items");
            return false;
        }
        items.add(item);
        System.out.println("Added: " + item.getName());
        return true;
    }

    public boolean remove(Item item) {
        if (!items.contains(item)) {
            System.out.println("Cannot find item in inventory");
            return false;
        } else {
            items.remove(item);
            System.out.println("Removed: " + item.getName());
            return true;
        }
    }

    public void clear() {
        items.clear();
        System.out.println("Cleared all items in inventory");
    }

    // equips an item, calls the onEquip of the specific item
    public void equip(EquipableItem item) {
        EquipmentSlot slot = item.getSlot();
        if (equippedItems.containsKey(slot)) {
            System.out.println(slot + " slot already has item equipped");
            return;
        }

        equippedItems.put(slot, item);
        item.onEquip(this);
        System.out.println(item.getName() + " equipped");
    }

    // unequips an item, calls the onUnequip of the specific item, and checks if the item fits into the inventory after unequipping, if it does, it is added, if it does not, it removes the item and returns the list of items that did not fit in the inventory
    public List<Item> unequip(EquipableItem item) {
        EquipmentSlot slot = item.getSlot();
        if (!equippedItems.containsKey(slot)) {
            System.out.println("No " + item.getName() + " to remove");
            return Collections.emptyList();
        }

        equippedItems.remove(slot);
        item.onUnequip(this);

        List<Item> overflow = new ArrayList<>();
        while (items.size() > maxCapacity) {
            overflow.add(items.remove(items.size() - 1));
        }

        System.out.println(item.getName() + " unequipped");
        return overflow;
    }

    public boolean hasEquipped(EquipmentSlot slot) {
        return equippedItems.containsKey(slot);
    }

    public EquipableItem getEquipped(EquipmentSlot slot) {
        return equippedItems.get(slot);
    }

    public void addBonusAttack(int amount) {
        bonusAttack += amount;
    }

    public void addBonusDefense(int amount) {
        bonusDefense += amount;
    }

    public void addBonusCapacity(int amount) {
        maxCapacity += amount;
    }

    public int getBonusAttack() {
        return bonusAttack;
    } //TODO: remove if note used

    public int getBonusDefense() {
        return bonusDefense;
    } //TODO: remove if note used

    public boolean isFull() {
        return items.size() >= maxCapacity;
    }

    public boolean isEmpty() {
        return items.size() == 0;
    }

    public int getSize() {
        return items.size();
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    // checks if the inventory has the quantity of the items needed, used for crafting recipes
    @Override
    public boolean hasItem(String itemName, int quantity) {
        int have = 0;
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                have++;
            }
        }
        return have >= quantity;
    }

    // removes the quantity of the items needed, used for crafting recipes, if the item is not found, it will not remove anything, if the item is found, it will remove it until the quantity is reached
    @Override
    public void removeItem(String itemName, int quantity) {
        int toRemove = quantity;
        for (Item item : new ArrayList<>(items)) {
            if (toRemove <= 0) break;
            if (item.getName().equalsIgnoreCase(itemName)) {
                remove(item);
                toRemove--;
            }
        }
    }

    // adds an item to the inventory, uses add method defined above
    @Override
    public void addItem(Item item) {
        add(item);
    }
}