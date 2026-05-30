package org.minicraft02160.model.worldengine;

public interface TerrainGeneration {
    void apply(TerrainType[][] map, int rows, int cols);
}
