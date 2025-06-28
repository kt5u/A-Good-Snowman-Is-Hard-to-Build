package pt.ipbeja.app.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipbeja.app.model.BoardModel;

public class Main extends Application {
    private static final int BOARD_SIZE = 10;
    private static final int CELL_SIZE = 100;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BoardModel boardModel = new BoardModel(BOARD_SIZE, BOARD_SIZE);
        SnowmanBoard board = new SnowmanBoard(boardModel);

        Scene scene = new Scene(board,
                BOARD_SIZE * CELL_SIZE,
                BOARD_SIZE * CELL_SIZE);

        primaryStage.setScene(scene);
        primaryStage.setTitle("A Good Snowman Is Hard To Build");
        primaryStage.show();
    }
}