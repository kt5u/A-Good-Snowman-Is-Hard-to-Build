package pt.ipbeja.app.model;

public class BoardModel {
    private final int rows;
    private final int cols;
    private TileModel[][] tiles;

    public BoardModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        tiles = new TileModel[rows][cols];

        // Initialize all tiles as EMPTY
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                tiles[r][c] = TileModel.EMPTY;
            }
        }
    }

    public TileModel getTile(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return null; // Or throw an exception if you prefer
        }
        return tiles[row][col];
    }

    public void setTile(int row, int col, TileModel tile) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            tiles[row][col] = tile;
        }
    }

    public boolean canMove(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false;
        }
        return tiles[row][col] == TileModel.EMPTY;
    }
}
