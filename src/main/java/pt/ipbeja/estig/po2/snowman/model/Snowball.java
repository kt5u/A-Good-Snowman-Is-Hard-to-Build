package pt.ipbeja.estig.po2.snowman.model;

/**
 * Represents a snowball with different size states
 *
 * @author Denis Cicau 25442
 * @author Ângelo Teresa 25441
 */
public class Snowball extends MobileElement {

    /**
     * Possible snowball sizes and combinations
     */
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
     * Creates a snowball at specified position and size
     */
    public Snowball(int row, int col, SnowballSize size) {
        super(row, col);
        this.size = size;
    }

    public SnowballSize getSize() {
        return size;
    }

    /**
     * Grows the snowball when pushed onto snow or combines with another snowball
     * @param otherSize size of other snowball when stacking, null when growing on snow
     */
    public void grow(SnowballSize otherSize) {
        if (otherSize == null) {
            // Normal growth when pushed onto snow
            switch (this.size) {
                case SMALL: this.size = SnowballSize.AVERAGE; break;
                case AVERAGE: this.size = SnowballSize.LARGE; break;
                // LARGE and composites don't grow further
            }
        } else {
            // Stacking with another snowball
            this.size = combineSizes(this.size, otherSize);
        }
    }

    /**
     * Combines two snowball sizes
     */
    private SnowballSize combineSizes(SnowballSize size1, SnowballSize size2) {
        // Order sizes to simplify combinations
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
     * Returns the smaller size when this is a combined snowball
     * @return the smaller size or null if not a combined snowball
     */
    public SnowballSize getSmallerSize() {
        return switch (this.size) {
            case BIG_AVERAGE -> SnowballSize.AVERAGE;
            case BIG_SMALL -> SnowballSize.SMALL;
            case AVERAGE_SMALL -> SnowballSize.SMALL;
            default -> null;
        };
    }

    /**
     * Returns the larger size when this is a combined snowball
     * @return the larger size or null if not a combined snowball
     */
    public SnowballSize getLargerSize() {
        return switch (this.size) {
            case BIG_AVERAGE -> SnowballSize.LARGE;
            case BIG_SMALL -> SnowballSize.LARGE;
            case AVERAGE_SMALL -> SnowballSize.AVERAGE;
            default -> null;
        };
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
}