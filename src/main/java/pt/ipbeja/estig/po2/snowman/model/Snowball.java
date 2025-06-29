package pt.ipbeja.estig.po2.snowman.model;

public class Snowball extends MobileElement {

    public enum SnowballSize {
        SMALL,
        AVERAGE,
        LARGE,
        /*
        BIG-AVERAGE,
        BIG-SMALL,
        AVERAGE-SMALL
         */
    }

    private SnowballSize size;

    public Snowball(int row, int col, SnowballSize size) {
        super(row, col);
        this.size = size;
    }

    public SnowballSize getSize() {
        return size;
    }

    public void setSize(SnowballSize size) {
        this.size = size;
    }

    public void grow() {
        switch (this.size) {
            case SMALL -> this.size = SnowballSize.AVERAGE;
            case AVERAGE -> this.size = SnowballSize.LARGE;
            case LARGE -> {} // já é o máximo
        }
    }

    @Override
    public String toString() {
        return "Snowball{" +
                "row=" + getRow() +
                ", col=" + getCol() +
                ", size=" + size +
                '}';
    }
}
