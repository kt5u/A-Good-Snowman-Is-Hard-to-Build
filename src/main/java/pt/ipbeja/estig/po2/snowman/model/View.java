package pt.ipbeja.estig.po2.snowman.model;

/**
 * Interface for views to observe the board model
 *
 * @author Denis Cicau 25442
 * @author Ângelo Teresa 25441
 */
public interface View {
    void onBoardChanged(int row, int col, PositionContent content);
    void showWinDialog();
}
