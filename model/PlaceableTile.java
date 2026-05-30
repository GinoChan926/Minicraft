package org.minicraft02160.model;

import org.minicraft02160.model.worldengine.TerrainType;

// Introduced after refactoring to implement the Strategy Pattern, defines whether the item can be placed and what terrain type it becomes
public interface PlaceableTile {
    boolean canBePlaced();
    TerrainType getPlaceableTerrainType();
}