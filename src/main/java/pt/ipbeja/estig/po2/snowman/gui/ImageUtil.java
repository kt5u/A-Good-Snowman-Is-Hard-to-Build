package pt.ipbeja.estig.po2.snowman.gui;

import javafx.scene.image.Image;

/**
 * Utility class for loading game images
 *
 * @author Denis Cicau 25442
 * @author Ângelo Teresa 25441
 */
public class ImageUtil {
    public static final Image MONSTER = loadImage("monster.png");
    public static final Image SNOWBALL_SMALL = loadImage("images/snowball_small.jpg");
    public static final Image SNOWBALL_MEDIUM = loadImage("images/snowball_medium.jpg");
    public static final Image SNOWBALL_LARGE = loadImage("images/snowball_large.jpg");
    public static final Image SNOWBALL_BIG_AVERAGE = loadImage("images/snowball_big_avg.jpg");
    public static final Image SNOWBALL_BIG_SMALL = loadImage("images/snowball_big_small.jpg");
    public static final Image SNOWBALL_AVERAGE_SMALL = loadImage("images/snowball_avg_small.jpg");
    public static final Image SNOWMAN = loadImage("images/snowman.jpg");
    public static final Image SNOW = loadImage("images/snow.jpg");
    public static final Image BLOCK = loadImage("images/block.jpg");
    public static final Image NO_SNOW = loadImage("images/ground.jpg");

    /**
     * Loads an image from the resources folder
     * @param filename The image filename
     * @return The loaded image
     */
    private static Image loadImage(String filename) {
        return new Image(ImageUtil.class.getResource("/" + filename).toExternalForm());
    }

    // Prevent instantiation
    private ImageUtil() {}
}