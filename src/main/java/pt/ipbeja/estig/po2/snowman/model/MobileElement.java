package pt.ipbeja.estig.po2.snowman.model;

/**
 * Abstract class representing movable elements
 */
public abstract class MobileElement {
    protected int row;
    protected int col;

    /**
     * Creates a mobile element
     * @param row Row position
     * @param col Column position
     */
    public MobileElement(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Gets row position
     * @return Row position
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets column position
     * @return Column position
     */
    public int getCol() {
        return col;
    }
}