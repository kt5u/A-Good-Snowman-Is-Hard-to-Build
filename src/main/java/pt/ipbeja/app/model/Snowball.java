package pt.ipbeja.app.model;

public class Snowball {
    public enum SnowballSize {
        SMALL,
        MEDIUM,
        LARGE
    }

    private int row;               // Row on the board
    private int col;               // Column on the board
    private SnowballSize size;     // Size of the snowball

    public Snowball(int row, int col, SnowballSize size) {
        this.row = row;
        this.col = col;
        this.size = size;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public SnowballSize getSize() {
        return size;
    }

    public void setSize(SnowballSize size) {
        this.size = size;
    }

    // Grow method to increase size (if possible)
    public void grow() {
        switch (this.size) {
            case SMALL:
                this.size = SnowballSize.MEDIUM;
                break;
            case MEDIUM:
                this.size = SnowballSize.LARGE;
                break;
            case LARGE:
                // Largest the ball can be
                break;
        }
    }

    @Override
    public String toString() {
        return "Snowball{" +
                "row=" + row +
                ", col=" + col +
                ", size=" + size +
                '}';
    }
}
