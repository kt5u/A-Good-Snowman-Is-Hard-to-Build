package pt.ipbeja.app.gui;

import javafx.scene.layout.GridPane;
import pt.ipbeja.app.model.BoardModel;
import pt.ipbeja.app.model.PositionContent;
import pt.ipbeja.app.model.View;

public class SnowmanBoard extends GridPane implements View {

    @Override
    public void onBoardChanged(int row, int col, PositionContent content) {
        updateCell(row, col);
    }

    private final BoardModel boardModel;

    public SnowmanBoard(BoardModel boardModel) {
        this.boardModel = boardModel;
        createBoard();
    }

    private void createBoard() {
        this.setGridLinesVisible(true);

        for (int row = 0; row < boardModel.getRows(); row++) {
            for (int col = 0; col < boardModel.getCols(); col++) {
                PositionContent content = boardModel.getContent(row, col);
                BoardCell cell = new BoardCell(row, col, content);
                this.add(cell, col, row);
            }
        }
    }

    public void updateCell(int row, int col) {
        PositionContent content = boardModel.getContent(row, col);
        BoardCell cell = getCellAt(row, col);
        if (cell != null) {
            cell.updateContent(content);
        }
    }

    private BoardCell getCellAt(int row, int col) {
        return (BoardCell) this.getChildren().get(row * boardModel.getCols() + col);
    }
}