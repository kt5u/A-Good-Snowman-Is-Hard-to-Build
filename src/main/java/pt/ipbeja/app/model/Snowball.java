package pt.ipbeja.app.model;

public class Snowball{
    public enum SnowballSize {
        SMALL,
        MEDIUM,
        LARGE
    }

    private SnowballSize size;

    public Snowball(SnowballSize size) {
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
            case SMALL:
                this.size = SnowballSize.MEDIUM;
                break;
            case MEDIUM:
                this.size = SnowballSize.LARGE;
                break;
            case LARGE:
                break;
        }
    }

    @Override
    public String toString() {
        return "SnowballType(" + size + ")";
    }
}
