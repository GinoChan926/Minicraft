package org.minicraft02160.model;

import org.minicraft02160.model.extras.ResourceType;
import org.minicraft02160.model.worldengine.TerrainType;

// The TileModel holds the information about one signle tile in the world, if it contains the player, what resource and terrain type it has, and if there is a mob on it
public class TileModel {

    private boolean containsPlayer;
    private ResourceType resourceType;
    private TerrainType terrainType;
    private MobModel mob;
    private int tileX;
    private int tileY;
    private boolean walkable;

    public TileModel(int tileX, int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
        this.containsPlayer = false;
        this.resourceType = null;
        this.terrainType = null;
        this.mob = null;
        this.walkable = true;
    }

    public boolean isContainsPlayer() { return containsPlayer; }
    public void setContainsPlayer(boolean containsPlayer) {
        this.containsPlayer = containsPlayer;
    }

    public ResourceType getResourceType() { return resourceType; }
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public TerrainType getTerrainType() { return terrainType; }
    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }

    public MobModel getMob() { return mob; }
    public void setMob(MobModel mob) { this.mob = mob; }

    public void setTileX(int tileX) { this.tileX = tileX; }
    public void setTileY(int tileY) { this.tileY = tileY; }

    public int getTileX() { return tileX; }
    public int getTileY() { return tileY; }


    public boolean isWalkable() { return walkable; }

    public void setWalkable(boolean walkable) { this.walkable = walkable; }
}