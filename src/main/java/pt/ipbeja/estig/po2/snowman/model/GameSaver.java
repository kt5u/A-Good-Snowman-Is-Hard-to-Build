package pt.ipbeja.estig.po2.snowman.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameSaver {
    private static final String FILE_PREFIX = "snowman";
    private static final String FILE_SUFFIX = ".txt";
    private static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";

    /**
     * Salva os dados do jogo quando um snowman é criado.
     * @param boardModel Modelo do tabuleiro (para obter o mapa e posição do snowman)
     * @param movesText Conteúdo do painel de movimentos (Req. 3)
     * @param movesCount Quantidade total de movimentos
     */
    public static void saveSnowmanData(BoardModel boardModel, String movesText, int movesCount) {

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT));
        String fileName = FILE_PREFIX + timestamp + FILE_SUFFIX;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("=== Mapa ===\n");
            for (int row = 0; row < boardModel.getRows(); row++) {
                for (int col = 0; col < boardModel.getCols(); col++) {
                    PositionContent content = boardModel.getContent(row, col);
                    writer.write(content.toString().charAt(0) + " "); // Ex: "M " para MONSTER
                }
                writer.newLine();
            }

            writer.write("\n=== Movimentos ===\n");
            writer.write(movesText);
            writer.newLine();

            writer.write("\nTotal de movimentos: " + movesCount + "\n");

            int snowmanRow = -1, snowmanCol = -1;
            for (int row = 0; row < boardModel.getRows(); row++) {
                for (int col = 0; col < boardModel.getCols(); col++) {
                    if (boardModel.getContent(row, col) == PositionContent.SNOWMAN) {
                        snowmanRow = row;
                        snowmanCol = col;
                    }
                }
            }

            writer.write("Posição do Snowman: (" + (snowmanRow + 1) + ", " + (char)('A' + snowmanCol) + ")\n");

        } catch (IOException e) {
            System.err.println("Erro ao salvar o ficheiro: " + e.getMessage());
        }
    }
}