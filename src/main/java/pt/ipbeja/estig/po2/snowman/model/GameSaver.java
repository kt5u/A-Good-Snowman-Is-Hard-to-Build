package pt.ipbeja.estig.po2.snowman.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles game data saving
 */
public class GameSaver {
    private static final String FILE_PREFIX = "snowman";
    private static final String FILE_SUFFIX = ".txt";
    private static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";

    /**
     * Saves game data when a snowman is created
     * @param boardModel Board model
     * @param movesText Moves history
     * @param movesCount Total move count
     */
    public static void saveSnowmanData(BoardModel boardModel, String movesText, int movesCount) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT));
        String fileName = FILE_PREFIX + timestamp + FILE_SUFFIX;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("=== Map ===\n");
            for (int row = 0; row < boardModel.getRows(); row++) {
                for (int col = 0; col < boardModel.getCols(); col++) {
                    PositionContent content = boardModel.getContent(row, col);
                    writer.write(content.toString().charAt(0) + " ");
                }
                writer.newLine();
            }

            writer.write("\n=== Moves ===\n");
            writer.write(movesText);
            writer.newLine();

            writer.write("\nTotal moves: " + movesCount + "\n");

            int snowmanRow = -1, snowmanCol = -1;
            for (int row = 0; row < boardModel.getRows(); row++) {
                for (int col = 0; col < boardModel.getCols(); col++) {
                    if (boardModel.getContent(row, col) == PositionContent.SNOWMAN) {
                        snowmanRow = row;
                        snowmanCol = col;
                    }
                }
            }

            writer.write("Snowman position: (" + (snowmanRow + 1) + ", " + (char)('A' + snowmanCol) + ")\n");
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }
}