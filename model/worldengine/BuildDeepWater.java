package org.minicraft02160.model.worldengine;

public class BuildDeepWater extends AbstractSurroundingHelper{
    public BuildDeepWater(){};


    @Override
    public void apply(TerrainType[][] map, int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (map[row][col].equals(TerrainType.WATER) && ! nearType(map, row, col, rows, cols, TerrainType.SAND)) {
                    map[row][col] = TerrainType.DEEP_WATER;
                }
            }
        }
    }
}
