package pt.ipbeja.estig.po2.snowman.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipbeja.estig.po2.snowman.model.BoardModel;
import pt.ipbeja.estig.po2.snowman.model.LevelManager;

/**
 * Main application class
 */
public class Main extends Application {
    private final TextArea movesTextArea = new TextArea();
    private LevelManager levelManager;
    private SnowmanBoard board;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        levelManager = new LevelManager();
        BoardModel boardModel = new BoardModel(levelManager);
        board = new SnowmanBoard(boardModel, this);

        Button restartButton = new Button("Restart Level");
        restartButton.setFocusTraversable(false);
        restartButton.setOnAction(e ->  {
            board.resetGame();
            board.requestFocus();
        });


        this.setupMovesTextArea();

        VBox root = new VBox(restartButton, board, this.movesTextArea);
        int cellSize = BoardCell.CELL_SIZE;
        Scene scene = new Scene(root,
                boardModel.getCols() * cellSize,
                boardModel.getRows() * cellSize + 170); // Increased for button

        scene.setOnMouseClicked(event -> board.requestFocus());
        primaryStage.setScene(scene);
        primaryStage.setTitle("A Good Snowman Is Hard To Build");
        primaryStage.show();
        board.requestFocus();
    }

    /**
     * Configures the moves text area
     */
    private void setupMovesTextArea() {
        this.movesTextArea.setEditable(false);
        this.movesTextArea.setPrefHeight(100);
        this.movesTextArea.setStyle("-fx-font-family: monospace;");
        this.movesTextArea.setFocusTraversable(false);
    }

    /**
     * Adds a move description to the text area
     * @param moveDescription The description of the move
     */
    public void addMove(String moveDescription) {
        Platform.runLater(() -> this.movesTextArea.appendText(moveDescription + "\n"));
    }

    /**
     * Gets the moves text
     * @return The moves history
     */
    public String getMovesText() {
        return this.movesTextArea.getText();
    }

    /**
     * Clears the moves history
     */
    public void clearMoves() {
        Platform.runLater(this.movesTextArea::clear);
    }
}