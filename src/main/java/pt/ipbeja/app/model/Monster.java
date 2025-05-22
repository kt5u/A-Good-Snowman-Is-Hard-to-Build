package pt.ipbeja.app.model;

public class Monster extends MobileElement {

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public Monster(int row, int col) {
        super(row, col);
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP -> row--;
            case DOWN -> row++;
            case LEFT -> col--;
            case RIGHT -> col++;
        }
    }
}
