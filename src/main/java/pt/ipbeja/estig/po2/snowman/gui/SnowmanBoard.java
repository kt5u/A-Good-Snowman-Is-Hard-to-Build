package pt.ipbeja.estig.po2.snowman.gui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import pt.ipbeja.estig.po2.snowman.model.BoardModel;
import pt.ipbeja.estig.po2.snowman.model.PositionContent;
import pt.ipbeja.estig.po2.snowman.model.View;
import pt.ipbeja.estig.po2.snowman.model.Monster;
import pt.ipbeja.estig.po2.snowman.model.Monster.Direction;

public class SnowmanBoard extends GridPane implements View {

    private final BoardModel boardModel;
    private final BoardCell[][] cells;
    private Monster monster;



    public SnowmanBoard(BoardModel boardModel) {
        this.boardModel = boardModel;
        this.cells = new BoardCell[boardModel.getRows()][boardModel.getCols()];
        this.boardModel.setView(this);
        createBoard();
        initializeMonster();
        setupKeyHandlers();
    }

    private void initializeMonster() {
        // Find the monster's initial position
        for (int row = 0; row < boardModel.getRows(); row++) {
            for (int col = 0; col < boardModel.getCols(); col++) {
                if (boardModel.getContent(row, col) == PositionContent.MONSTER) {
                    this.monster = new Monster(row, col);
                    break;
                }
            }
        }
    }

    private void setupKeyHandlers() {
        this.setFocusTraversable(true);
        this.requestFocus();

        this.setOnKeyPressed(this::handleKeyPress);
    }

    private void handleKeyPress(KeyEvent event) {
        if (monster == null) return;

        Direction direction = null;
        switch (event.getCode()) {
            case UP: direction = Direction.UP; break;
            case DOWN: direction = Direction.DOWN; break;
            case LEFT: direction = Direction.LEFT; break;
            case RIGHT: direction = Direction.RIGHT; break;
            default: return; // Ignore other keys
        }

        moveMonster(direction);
    }

    private void moveMonster(Direction direction) {
        if (monster == null) return;

        int oldRow = monster.getRow();
        int oldCol = monster.getCol();

        if (monster.move(direction, boardModel)) {
            // Update the board model
            boardModel.setContent(oldRow, oldCol, PositionContent.NO_SNOW);
            boardModel.setContent(monster.getRow(), monster.getCol(), PositionContent.MONSTER);
        }
    }


    private void createBoard() {
        this.setGridLinesVisible(true);

        for (int row = 0; row < boardModel.getRows(); row++) {
            for (int col = 0; col < boardModel.getCols(); col++) {
                PositionContent content = boardModel.getContent(row, col);
                BoardCell cell = new BoardCell(row, col, content);
                cells[row][col] = cell;
                this.add(cell, col, row);
            }
        }
    }

    @Override
    public void onBoardChanged(int row, int col, PositionContent content) {
        updateCell(row, col);
    }

    public void updateCell(int row, int col) {
        BoardCell cell = cells[row][col];
        cell.updateContent(boardModel.getContent(row, col));
    }
}
