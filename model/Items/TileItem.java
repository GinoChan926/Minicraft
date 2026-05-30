package org.minicraft02160.model.Items;

import org.minicraft02160.model.EquipableItem;
import org.minicraft02160.model.PlaceableTile;
import org.minicraft02160.model.extras.EquipmentSlot;
import org.minicraft02160.model.worldengine.TerrainType;

public class TileItem extends EquipableItem implements PlaceableTile {

    private final TerrainType terrainType;

    public TileItem(TerrainType terrainType) {
        super(terrainType.name() + " Tile");
        this.terrainType = terrainType;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.MAIN_HAND;
    }

    @Override
    public boolean canBePlaced() {
        return true;
    }

    @Override
    public TerrainType getPlaceableTerrainType() {
        return terrainType;
    }
}