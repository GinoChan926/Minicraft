package org.minicraft02160.model.worldengine;


import java.util.Random;

public class BuildCluster implements TerrainGeneration {
    private static final Random random = new Random();
    private final TerrainType type;
    private final int seedCount;
    private final int growthSteps;

    public BuildCluster(TerrainType type, int seedCount, int growthSteps) {
        this.type = type;
        this.seedCount = seedCount;
        this.growthSteps = growthSteps;
    }


    @Override
    public void apply(TerrainType[][] map, int rows, int cols) {

        for (int i = 0; i < seedCount; i++) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);

            map[row][col] = type;

            int currentRow = row;
            int currentCol = col;

            for (int j = 0; j < growthSteps; j++) {
                int direction = random.nextInt(4);

                if (direction == 0 && currentRow > 0) currentRow--;
                else if (direction == 1 && currentRow < rows - 1) currentRow++;
                else if (direction == 2 && currentCol > 0) currentCol--;
                else if (direction == 3 && currentCol < cols - 1) currentCol++;

                map[currentRow][currentCol] = type;
            }
        }
    }

}
