package pt.ipbeja.app.model;

public class Monster {

    public enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private int row;
    private int col;

    public Monster(int row, int col){
        this.row = row;
        this.col = col;
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:    row--; break;
            case DOWN:  row++; break;
            case LEFT:  col--; break;
            case RIGHT: col++; break;
        }
    }
}
