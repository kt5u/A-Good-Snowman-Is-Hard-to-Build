package pt.ipbeja.estig.po2.snowman.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import pt.ipbeja.estig.po2.snowman.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Game board GUI component
 */
public class SnowmanBoard extends GridPane implements View {
    private final BoardModel boardModel;
    private BoardCell[][] cells;
    private final List<String> movesHistory;
    private final Main mainApp;
    private final List<Monster.Direction> moveSequence = new ArrayList<>();
    private Timeline replayTimeline;
    private int replayIndex;
    private boolean isReplaying = false;

    /**
     * Creates the game board
     * @param boardModel The board model
     * @param mainApp The main application
     */
    public SnowmanBoard(BoardModel boardModel, Main mainApp) {
        this.boardModel = boardModel;
        this.mainApp = mainApp;
        this.cells = new BoardCell[boardModel.getRows()][boardModel.getCols()];
        this.movesHistory = new ArrayList<>();
        this.boardModel.setView(this);

        this.createBoard();
        this.setupKeyHandlers();
        this.setFocusTraversable(true);
        this.setOnMouseClicked(e -> this.requestFocus());
    }

    /**
     * Sets up keyboard handlers for movement
     */
    private void setupKeyHandlers() {
        this.setOnKeyPressed(event -> {
            if (isReplaying) return;

            Monster.Direction direction = null;
            KeyCode code = event.getCode();

            switch (code) {
                case UP: direction = Monster.Direction.UP; break;
                case DOWN: direction = Monster.Direction.DOWN; break;
                case LEFT: direction = Monster.Direction.LEFT; break;
                case RIGHT: direction = Monster.Direction.RIGHT; break;
                case Q: direction = Monster.Direction.UP_LEFT; break;
                case E: direction = Monster.Direction.UP_RIGHT; break;
                case Z: direction = Monster.Direction.DOWN_LEFT; break;
                case C: direction = Monster.Direction.DOWN_RIGHT; break;
            }

            if (direction != null) {
                int oldRow = boardModel.getMonsterRow();
                int oldCol = boardModel.getMonsterCol();

                if (boardModel.tryMoveMonster(direction)) {
                    logMove(oldRow, oldCol,
                            boardModel.getMonsterRow(),
                            boardModel.getMonsterCol(),
                            direction);
                }
                this.requestFocus();
            }
        });
    }

    /**
     * Logs a player move
     * @param oldRow Starting row
     * @param oldCol Starting column
     * @param newRow Ending row
     * @param newCol Ending column
     * @param direction Move direction
     */
    private void logMove(int oldRow, int oldCol, int newRow, int newCol, Monster.Direction direction) {
        String move = String.format("(%d, %c) → (%d, %c)",
                oldRow + 1,
                'A' + oldCol,
                newRow + 1,
                'A' + newCol);

        movesHistory.add(move);
        moveSequence.add(direction);
        mainApp.addMove(move);
    }

    /**
     * Creates the game board GUI
     */
    private void createBoard() {
        this.setGridLinesVisible(true);

        for (int row = 0; row < this.boardModel.getRows(); row++) {
            for (int col = 0; col < this.boardModel.getCols(); col++) {
                BoardCell cell = new BoardCell(row, col, this.boardModel);
                this.cells[row][col] = cell;
                this.add(cell, col, row);
            }
        }
    }

    @Override
    public void onBoardChanged(int row, int col, PositionContent content) {
        this.updateCell(row, col);
    }

    /**
     * Updates a specific cell
     * @param row Cell row
     * @param col Cell column
     */
    public void updateCell(int row, int col) {
        BoardCell cell = this.cells[row][col];
        if (cell != null) {
            cell.update();
        }
    }

    @Override
    public void showWinDialog() {
        int totalMoves = this.movesHistory.size();
        GameSaver.saveSnowmanData(this.boardModel, this.mainApp.getMovesText(), totalMoves);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Congratulations!");

        boolean lastLevel = (boardModel.getCurrentLevelNumber() == boardModel.getTotalLevels());

        if (lastLevel) {
            alert.setHeaderText("Game Completed!");
            alert.setContentText("You finished all levels! Amazing!");
        } else {
            alert.setHeaderText("Level " + boardModel.getCurrentLevelNumber() + " Completed!");
            alert.setContentText("You successfully built a snowman! Ready for next level?");
        }

        ButtonType nextLevelButton = new ButtonType("Next Level");
        ButtonType restartButton = new ButtonType("Restart Level");
        ButtonType replayButton = new ButtonType("Replay");
        ButtonType closeButton = new ButtonType("Close");

        if (lastLevel) {
            alert.getButtonTypes().setAll(restartButton, replayButton, closeButton);
        } else {
            alert.getButtonTypes().setAll(nextLevelButton, restartButton, replayButton, closeButton);
        }

        ButtonType result = alert.showAndWait().orElse(closeButton);

        if (result == nextLevelButton) {
            this.nextLevel();
        }
        else if (result == restartButton) {
            this.resetGame();
        }
        else if (result == replayButton) {
            this.startReplay();
        }
    }

    /**
     * Starts replaying the level
     */
    private void startReplay() {
        if (replayTimeline != null) {
            replayTimeline.stop();
        }

        this.resetGameForReplay();
        replayIndex = 0;
        isReplaying = true;
        mainApp.clearMoves();

        replayTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), event -> playNextMove())
        );
        replayTimeline.setCycleCount(moveSequence.size());
        replayTimeline.setOnFinished(e -> isReplaying = false);
        replayTimeline.play();
    }

    /**
     * Plays the next move in the replay sequence
     */
    private void playNextMove() {
        if (replayIndex < moveSequence.size()) {
            Monster.Direction dir = moveSequence.get(replayIndex);
            boardModel.tryMoveMonster(dir);
            replayIndex++;

            int oldRow = boardModel.getMonsterRow();
            int oldCol = boardModel.getMonsterCol();
            String move = String.format("(%d, %c) → (%d, %c)",
                    oldRow + 1,
                    'A' + oldCol,
                    boardModel.getMonsterRow() + 1,
                    'A' + boardModel.getMonsterCol());
            mainApp.addMove(move);
        }
    }

    /**
     * Resets the game for replay
     */
    private void resetGameForReplay() {
        movesHistory.clear();
        mainApp.clearMoves();
        boardModel.resetBoard();
        recreateBoard();
    }

    /**
     * Advances to the next level
     */
    private void nextLevel() {
        this.movesHistory.clear();
        this.moveSequence.clear();
        this.mainApp.clearMoves();
        this.boardModel.nextLevel();
        this.recreateBoard();
    }

    /**
     * Recreates the game board GUI
     */
    private void recreateBoard() {
        this.getChildren().clear();
        this.cells = new BoardCell[boardModel.getRows()][boardModel.getCols()];

        for (int row = 0; row < this.boardModel.getRows(); row++) {
            for (int col = 0; col < this.boardModel.getCols(); col++) {
                BoardCell cell = new BoardCell(row, col, this.boardModel);
                this.cells[row][col] = cell;
                this.add(cell, col, row);
            }
        }
    }

    /**
     * Resets the current level
     */
    public void resetGame() {
        this.movesHistory.clear();
        this.moveSequence.clear();
        this.mainApp.clearMoves();
        this.boardModel.resetBoard();
        this.recreateBoard();
    }
}