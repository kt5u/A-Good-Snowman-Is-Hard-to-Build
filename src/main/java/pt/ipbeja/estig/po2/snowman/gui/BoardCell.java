package pt.ipbeja.estig.po2.snowman.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import pt.ipbeja.estig.po2.snowman.model.BoardModel;
import pt.ipbeja.estig.po2.snowman.model.PositionContent;
import pt.ipbeja.estig.po2.snowman.model.Snowball;

/**
 * Represents a single cell on the game board
 *
 * @author Denis Cicau 25442
 * @author Ângelo Teresa 25441
 */
public class BoardCell extends StackPane {
    public static final int CELL_SIZE = 100;
    private final ImageView imageView;
    private final int row;
    private final int col;
    private final BoardModel boardModel;

    /**
     * Creates a new board cell
     * @param row the row position
     * @param col the column position
     * @param boardModel the board model
     */
    public BoardCell(int row, int col, BoardModel boardModel) {
        this.row = row;
        this.col = col;
        this.boardModel = boardModel;
        this.imageView = new ImageView();

        this.setupImageView();
        this.setupCellAppearance();
        this.update();
    }

    /**
     * Configures the image view properties
     */
    private void setupImageView() {
        imageView.setFitWidth(CELL_SIZE);
        imageView.setFitHeight(CELL_SIZE);
        this.getChildren().add(imageView);
    }

    /**
     * Configures the cell appearance
     */
    private void setupCellAppearance() {
        setMinSize(CELL_SIZE, CELL_SIZE);
        setMaxSize(CELL_SIZE, CELL_SIZE);
        setStyle("-fx-background-color: transparent;");
    }

    /**
     * Updates the cell content based on the board state
     */
    public void update() {
        PositionContent content = boardModel.getContent(row, col);
        PositionContent baseContent = boardModel.getBaseContent(row, col);

        if (content == PositionContent.MONSTER ||
                content == PositionContent.SNOWBALL ||
                content == PositionContent.SNOWMAN) {
            imageView.setImage(getImageForContent(content));
        } else {
            imageView.setImage(getImageForContent(baseContent));
        }
    }

    /**
     * Gets the corresponding image for a position content
     * @param content The content type
     * @return The corresponding image
     */
    private Image getImageForContent(PositionContent content) {
        if (content == null) return ImageUtil.NO_SNOW;

        switch (content) {
            case NO_SNOW: return ImageUtil.NO_SNOW;
            case SNOW: return ImageUtil.SNOW;
            case BLOCK: return ImageUtil.BLOCK;
            case MONSTER: return ImageUtil.MONSTER;
            case SNOWBALL: return getSnowballImage();
            case SNOWMAN: return ImageUtil.SNOWMAN;
            default: return ImageUtil.NO_SNOW;
        }
    }

    /**
     * Gets the specific snowball image based on its size
     * @return The snowball image
     */
    private Image getSnowballImage() {
        Snowball snowball = boardModel.getSnowballAt(row, col);
        if (snowball == null) return null;

        switch(snowball.getSize()) {
            case SMALL: return ImageUtil.SNOWBALL_SMALL;
            case AVERAGE: return ImageUtil.SNOWBALL_MEDIUM;
            case LARGE: return ImageUtil.SNOWBALL_LARGE;
            case BIG_AVERAGE: return ImageUtil.SNOWBALL_BIG_AVERAGE;
            case BIG_SMALL: return ImageUtil.SNOWBALL_BIG_SMALL;
            case AVERAGE_SMALL: return ImageUtil.SNOWBALL_AVERAGE_SMALL;
            default: return null;
        }
    }
}