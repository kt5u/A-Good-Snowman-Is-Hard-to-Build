package pt.ipbeja.app.model;

public class Snowball extends MobileElement {

    public enum SnowballSize {
        SMALL,
        MEDIUM,
        LARGE
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
            case SMALL -> this.size = SnowballSize.MEDIUM;
            case MEDIUM -> this.size = SnowballSize.LARGE;
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
