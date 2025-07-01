package pt.ipbeja.estig.po2.snowman.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.ipbeja.estig.po2.snowman.model.BoardModel;
import pt.ipbeja.estig.po2.snowman.model.LevelManager;

public class Main extends Application {
    private final TextArea movesTextArea = new TextArea();
    private LevelManager levelManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        levelManager = new LevelManager();
        BoardModel boardModel = new BoardModel(levelManager);
        SnowmanBoard board = new SnowmanBoard(boardModel, this);

        this.setupMovesTextArea();

        VBox root = new VBox(board, this.movesTextArea);
        int cellSize = BoardCell.CELL_SIZE;
        Scene scene = new Scene(root,
                boardModel.getCols() * cellSize,
                boardModel.getRows() * cellSize + 120);

        scene.setOnMouseClicked(event -> board.requestFocus());
        primaryStage.setScene(scene);
        primaryStage.setTitle("A Good Snowman Is Hard To Build");
        primaryStage.show();
        board.requestFocus();
    }

    private void setupMovesTextArea() {
        this.movesTextArea.setEditable(false);
        this.movesTextArea.setPrefHeight(100);
        this.movesTextArea.setStyle("-fx-font-family: monospace;");
        this.movesTextArea.setFocusTraversable(false);
    }

    public void addMove(String moveDescription) {
        Platform.runLater(() -> this.movesTextArea.appendText(moveDescription + "\n"));
    }

    public String getMovesText() {
        return this.movesTextArea.getText();
    }

    public void clearMoves() {
        Platform.runLater(this.movesTextArea::clear);
    }
}