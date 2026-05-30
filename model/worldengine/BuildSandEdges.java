package org.minicraft02160.model.worldengine;

public class BuildSandEdges extends AbstractSurroundingHelper{
    public BuildSandEdges(){
    }

    @Override
    public void apply(TerrainType[][] map, int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (map[row][col].equals(TerrainType.GRASS) && nearType(map, row, col, rows, cols, TerrainType.WATER)) {
                    map[row][col] = TerrainType.SAND;
                }
            }
        }
    }
}
