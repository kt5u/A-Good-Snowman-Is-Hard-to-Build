package pt.ipbeja.estig.po2.snowman.model;

import java.util.ArrayList;
import java.util.List;

/*
 @author Denis Cicau 25442
 @author Ângelo Teresa 25441
*/

public class BoardModel {

    private final int rows;
    private final int cols;
    private final List<List<PositionContent>> board;
    private View view;

    /**
     * Board Constructor
     * @param rows número de linhas
     * @param cols número de colunas
     */
    public BoardModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new ArrayList<>();
        createInitialBoard();
    }

    // In BoardModel.java, add this method:
    public void setView(View view) {
        this.view = view;
    }

    private void createInitialBoard() {
        for (int row = 0; row < rows; row++) {
            List<PositionContent> line = new ArrayList<>();
            for (int col = 0; col < cols; col++) {
                // Exemplo: tudo com neve
                line.add(PositionContent.SNOW);
            }
            board.add(line);
        }

        // Exemplo: colocar um bloco
        board.get(1).set(1, PositionContent.BLOCK);

        // Colocar monstro
        board.get(2).set(2, PositionContent.MONSTER);

        // Colocar bola pequena
        board.get(3).set(3, PositionContent.SNOWBALL);
    }

    /**
     * Retorna o conteúdo de uma posição
     */
    public PositionContent getContent(int row, int col) {
        if (!isInsideBoard(row, col)) {
            throw new IndexOutOfBoundsException("Fora dos limites do tabuleiro.");
        }
        return board.get(row).get(col);
    }

    /**
     * Define o conteúdo de uma posição
     */
    public void setContent(int row, int col, PositionContent content) {
        if (!isInsideBoard(row, col)) {
            throw new IndexOutOfBoundsException("Fora dos limites do tabuleiro.");
        }
        board.get(row).set(col, content);
        if (view != null) {
            view.onBoardChanged(row, col, content);
        }
    }

    /**
     * Verifica se a posição está dentro dos limites do tabuleiro
     */
    public boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    /**
     * Getters das dimensões
     */
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
