package pt.ipbeja.estig.po2.snowman.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import pt.ipbeja.estig.po2.snowman.model.PositionContent;

public class BoardCell extends StackPane {
    private final int SIZE = 100;
    private final ImageView background;
    private final ImageView content;
    private final int row;
    private final int col;

    public BoardCell(int row, int col, PositionContent initialContent) {
        this.row = row;
        this.col = col;
        this.background = new ImageView();
        this.content = new ImageView();

        background.setFitWidth(SIZE);
        background.setFitHeight(SIZE);
        background.setPreserveRatio(false);

        content.setFitWidth(SIZE);
        content.setFitHeight(SIZE);
        content.setPreserveRatio(false); // ou true se preferires manter proporção

        setMinSize(SIZE, SIZE);
        setMaxSize(SIZE, SIZE);
        setPrefSize(SIZE, SIZE);
        setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-padding: 0;");
        setFocusTraversable(false);

        this.getChildren().addAll(background, content);

        this.setClip(new javafx.scene.shape.Rectangle(SIZE, SIZE)); // <-- impede "vazamentos"
        updateContent(initialContent);
    }

    public void updateContent(PositionContent content) {
        this.background.setImage(getBackgroundImage(content));
        this.content.setImage(getContentImage(content));
    }

    private Image getBackgroundImage(PositionContent content) {
        return switch (content) {
            case SNOW -> ImageUtil.SNOW;
            case BLOCK -> ImageUtil.BLOCK;
            default -> ImageUtil.NO_SNOW;
        };
    }

    private Image getContentImage(PositionContent content) {
        return switch (content) {
            case MONSTER -> ImageUtil.MONSTER;
            case SNOWBALL -> getSnowballImage();
            case SNOWMAN -> ImageUtil.SNOWMAN;
            default -> null;
        };
    }

    private Image getSnowballImage() {
        return ImageUtil.SNOWBALL_SMALL;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
}
