package pt.ipbeja.estig.po2.snowman.model;

/**
 * View interface for game board
 */
public interface View {
    /**
     * Called when board changes
     * @param row Changed row
     * @param col Changed column
     * @param content New content
     */
    void onBoardChanged(int row, int col, PositionContent content);

    /**
     * Shows win dialog
     */
    void showWinDialog();
}