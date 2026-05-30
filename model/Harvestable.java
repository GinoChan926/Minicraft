package org.minicraft02160.model;

// Introduced after refactoring for the Strategy Pattern, it specifies if and item can be used to harvest a resource or not (not all items can do that)
public interface Harvestable {
    boolean canHarvest();
}