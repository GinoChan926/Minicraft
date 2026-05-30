package org.minicraft02160.model;

// Introduced to implement the Strategy Pattern, which allows the player to use different types of items which have their own implementations
public interface Equipable {
    boolean use(Player player);
    boolean isEquipped();
}