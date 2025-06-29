package pt.ipbeja.estig.po2.snowman.gui;

import javafx.scene.image.Image;

public class ImageUtil {
    // Imagens básicas do jogo
    public static final Image MONSTER = loadImage("monster.png");
    public static final Image SNOWBALL_SMALL = loadImage("images/snowball_small.jpg");
    public static final Image SNOWBALL_MEDIUM = loadImage("images/snowball_medium.jpg");
    public static final Image SNOWBALL_LARGE = loadImage("images/snowball_large.jpg");
    public static final Image SNOWMAN = loadImage("images/snowman.jpg");
    public static final Image SNOW = loadImage("images/snow.jpg");
    public static final Image BLOCK = loadImage("images/block.jpg");
    public static final Image NO_SNOW = loadImage("images/ground.jpg");


    private static Image loadImage(String filename) {
        return new Image(ImageUtil.class.getResource("/" + filename).toExternalForm());
    }

    private ImageUtil() {}
}