package org.minicraft02160.model.worldengine;

// Used for the terrain generation, checks if a adjacent tiles match one specific tile type, used for the Template Method pattern
public abstract class AbstractSurroundingHelper implements TerrainGeneration {

    protected boolean nearType(TerrainType[][] map, int row, int col, int rows, int cols, TerrainType type) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;

                int newRow = row + dy;
                int newCol = col + dx;

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    if (map[newRow][newCol].equals(type)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
