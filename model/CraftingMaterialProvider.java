package org.minicraft02160.model;

// Introduced after refactoring using the Strategy Pattern, the Recipe does not depend on the Inventory now, it calls the CraftingMaterialProvider interface to check for materials and consume them, this allows us to have different implementations of CraftingMaterialProvider.
public interface CraftingMaterialProvider {
    boolean hasItem(String itemName, int quantity);
    void removeItem(String itemName, int quantity);
    void addItem(Item item);
}