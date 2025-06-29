package pt.ipbeja.app.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import pt.ipbeja.app.model.PositionContent;

public class BoardCell extends StackPane {
    private final int SIZE = 100;
    private final ImageView background;
    private ImageView content;
    private final int row;
    private final int col;

    public BoardCell(int row, int col, PositionContent initialContent) {
        this.row = row;
        this.col = col;
        this.background = new ImageView();
        this.content = new ImageView();
        setMinSize(SIZE, SIZE);
        setMaxSize(SIZE, SIZE);
        setPrefSize(SIZE, SIZE);
        setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-padding: 0;");
        setFocusTraversable(false);

        this.getChildren().addAll(background, content);
        this.setPrefSize(50, 50);
        updateContent(initialContent);
    }

    public void updateContent(PositionContent content) {
        this.background.setImage(getBackgroundImage(content));
        this.content.setImage(getContentImage(content));
    }

    private Image getBackgroundImage(PositionContent content) {
        switch(content) {
            case SNOW: return ImageUtil.SNOW;
            case BLOCK: return ImageUtil.BLOCK;
            default: return ImageUtil.NO_SNOW;
        }
    }

    private Image getContentImage(PositionContent content) {
        switch(content) {
            case MONSTER: return ImageUtil.MONSTER;
            case SNOWBALL: return getSnowballImage();
            case SNOWMAN: return ImageUtil.SNOWMAN;
            default: return null;
        }
    }

    private Image getSnowballImage() {
        return ImageUtil.SNOWBALL_SMALL;
    }

    // Getters
    public int getRow() { return row; }
    public int getCol() { return col; }
}