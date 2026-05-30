package org.minicraft02160.model.worldengine;

public class BaseGrass implements TerrainGeneration {

    @Override
    public void apply(TerrainType[][] map, int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                map[row][col] = TerrainType.GRASS;
            }
        }
    }
}
