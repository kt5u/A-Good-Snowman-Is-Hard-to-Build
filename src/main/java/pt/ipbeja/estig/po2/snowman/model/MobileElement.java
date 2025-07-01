package pt.ipbeja.estig.po2.snowman.model;

/**
 * Abstract class representing movable elements on the board
 *
 * @author Denis Cicau 25442
 * @author Ângelo Teresa 25441
 */
public abstract class MobileElement {
    protected int row;
    protected int col;

    /**
     * Creates a mobile element at specified position
     * @param row the row position
     * @param col the column position
     */
    public MobileElement(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}