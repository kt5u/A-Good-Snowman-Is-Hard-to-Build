package pt.ipbeja.app.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import pt.ipbeja.app.model.PositionContent;

public class BoardCell extends StackPane {
    private final ImageView background;
    private ImageView content;
    private final int row;
    private final int col;

    public BoardCell(int row, int col, PositionContent initialContent) {
        this.row = row;
        this.col = col;
        this.background = new ImageView();
        this.content = new ImageView();

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
            case SNOWBALL: return getSnowballImage(); // Novo método para bolas de neve
            case SNOWMAN: return ImageUtil.SNOWMAN;
            default: return null;
        }
    }

    private Image getSnowballImage() {
        // Você precisará ter acesso ao tamanho da bola de neve aqui
        // Isso pode exigir mudanças na estrutura
        return ImageUtil.SNOWBALL_SMALL; // Exemplo temporário
    }

    // Getters
    public int getRow() { return row; }
    public int getCol() { return col; }
}