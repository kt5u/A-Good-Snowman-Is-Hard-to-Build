// Monster.java
package pt.ipbeja.estig.po2.snowman.model;

public class Monster extends MobileElement {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public Monster(int row, int col) {
        super(row, col);
    }

    public boolean move(Direction direction, BoardModel board) {
        int newRow = row;
        int newCol = col;

        switch (direction) {
            case UP -> newRow--;
            case DOWN -> newRow++;
            case LEFT -> newCol--;
            case RIGHT -> newCol++;
        }

        // Check if new position is valid
        if (board.isInsideBoard(newRow, newCol) &&
                board.getContent(newRow, newCol) != PositionContent.BLOCK) {

            // Update position
            this.row = newRow;
            this.col = newCol;
            return true;
        }
        return false;
    }
}