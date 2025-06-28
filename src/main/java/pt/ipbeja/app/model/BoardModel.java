package pt.ipbeja.app.model;

import javafx.scene.Parent;

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

        for (int r = 0; r < rows; r++) {
            List<PositionContent> row = new ArrayList<>();
            for (int c = 0; c < cols; c++) {
                row.add(PositionContent.NO_SNOW); // Posição inicial vazia
            }
            board.add(row);
        }
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
     * Verifica se pode mover para uma posição
     */
    public boolean canMove(int row, int col) {
        return isInsideBoard(row, col) && board.get(row).get(col) == PositionContent.NO_SNOW;
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
