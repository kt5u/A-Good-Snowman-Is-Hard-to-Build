package pt.ipbeja.app.gui;

import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ImageUtil {
    // Imagens básicas do jogo
    public static final Image MONSTER = loadImage("monster.png");
    public static final Image SNOWBALL_SMALL = loadImage("snowball_small.png");
    public static final Image SNOWBALL_MEDIUM = loadImage("snowball_medium.png");
    public static final Image SNOWBALL_LARGE = loadImage("snowball_large.png");
    public static final Image SNOWMAN = loadImage("snowman.png");
    public static final Image SNOW = loadImage("snow.png");
    public static final Image BLOCK = loadImage("block.png");
    public static final Image NO_SNOW = loadImage("ground.png");

    private static Image loadImage(String filename) {
        try {
            String path = "/assets/" + filename;
            InputStream stream = ImageUtil.class.getResourceAsStream(path);
            if (stream == null) {
                throw new IOException("Arquivo não encontrado: " + path);
            }
            return new Image(stream);
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
            return createFallbackImage();
        }
    }

    private static Image createFallbackImage() {
        // Cria uma imagem vermelha programaticamente como fallback
        return new Image(Objects.requireNonNull(
                ImageUtil.class.getResourceAsStream("/assets/fallback.png")
        ), 50, 50, true, true);
    }

    private ImageUtil() {}
}