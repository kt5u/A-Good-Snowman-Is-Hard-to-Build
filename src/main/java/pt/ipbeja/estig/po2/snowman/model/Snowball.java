package pt.ipbeja.estig.po2.snowman.model;

/**
 * Represents a snowball
 */
public class Snowball extends MobileElement {
    public enum SnowballSize {
        SMALL,          // Small snowball
        AVERAGE,        // Medium snowball
        LARGE,          // Large snowball
        BIG_AVERAGE,    // Large + Average
        BIG_SMALL,      // Large + Small
        AVERAGE_SMALL   // Average + Small
    }

    private SnowballSize size;

    /**
     * Creates a snowball
     * @param row Row position
     * @param col Column position
     * @param size Snowball size
     */
    public Snowball(int row, int col, SnowballSize size) {
        super(row, col);
        this.size = size;
    }

    /**
     * Gets snowball size
     * @return Snowball size
     */
    public SnowballSize getSize() {
        return size;
    }

    /**
     * Grows the snowball
     * @param otherSize Other snowball size (null for snow)
     */
    public void grow(SnowballSize otherSize) {
        if (otherSize == null) {
            switch (this.size) {
                case SMALL: this.size = SnowballSize.AVERAGE; break;
                case AVERAGE: this.size = SnowballSize.LARGE; break;
            }
        } else {
            this.size = combineSizes(this.size, otherSize);
        }
    }

    /**
     * Combines two snowball sizes
     * @param size1 First size
     * @param size2 Second size
     * @return Combined size
     */
    private SnowballSize combineSizes(SnowballSize size1, SnowballSize size2) {
        if (size1.ordinal() < size2.ordinal()) {
            SnowballSize temp = size1;
            size1 = size2;
            size2 = temp;
        }

        return switch (size1) {
            case LARGE -> switch (size2) {
                case AVERAGE -> SnowballSize.BIG_AVERAGE;
                case SMALL -> SnowballSize.BIG_SMALL;
                default -> size1;
            };
            case AVERAGE -> size2 == SnowballSize.SMALL ?
                    SnowballSize.AVERAGE_SMALL : size1;
            default -> size1;
        };
    }

    /**
     * Sets snowball position
     * @param row New row
     * @param col New column
     */
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
}