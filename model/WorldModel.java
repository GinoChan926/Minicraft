package org.minicraft02160.model;

// The WorldModel class contains the 2D array of TileModels
public class WorldModel {

    private final TileModel[][] world;
    private final int rows;
    private final int cols;
    private Player player;

    // creates the world with the specified number of rows and columns, initializing the TileModels
    public WorldModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.world = new TileModel[rows][cols];
        initTileModels();
    }

    private void initTileModels() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                world[row][col] = new TileModel(col, row);
            }
        }
    }

    public void setTileAt(int row, int col, TileModel tileModel) {
        if (row >= 0 && col >= 0 && row < rows && col < cols) {
            world[row][col] = tileModel;
        }
    }

    public TileModel getTileModel(int x, int y) {
        if (x >= 0 && y >= 0 && x < cols && y < rows) {
            return world[y][x];
        }
        return null;
    }


    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
}