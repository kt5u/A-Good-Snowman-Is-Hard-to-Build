package pt.ipbeja.estig.po2.snowman.model;

/**
 * Represents the player-controlled monster
 */
public class Monster extends MobileElement {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT,
        UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT
    }

    /**
     * Creates a monster
     * @param row Starting row
     * @param col Starting column
     */
    public Monster(int row, int col) {
        super(row, col);
    }

    /**
     * Moves the monster
     * @param direction Movement direction
     * @param board Game board
     * @return True if move was successful
     */
    public boolean move(Direction direction, BoardModel board) {
        int[] newPos = calculateNewPosition(direction);
        int newRow = newPos[0];
        int newCol = newPos[1];

        if (!board.isInsideBoard(newRow, newCol)) {
            return false;
        }

        PositionContent target = board.getContent(newRow, newCol);

        if (target == PositionContent.BLOCK) {
            return false;
        }

        if (target == PositionContent.SNOWBALL) {
            Snowball snowball = board.getSnowballAt(newRow, newCol);
            if (snowball != null && isCombinedSnowball(snowball.getSize())) {
                return unstackSnowballs(board, newRow, newCol, direction);
            }
            return pushSnowball(direction, board, newRow, newCol);
        }

        return moveTo(newRow, newCol, board);
    }

    /**
     * Calculates new position based on direction
     * @param direction Movement direction
     * @return New position [row, col]
     */
    private int[] calculateNewPosition(Direction direction) {
        int newRow = this.row;
        int newCol = this.col;

        switch (direction) {
            case UP: newRow--; break;
            case DOWN: newRow++; break;
            case LEFT: newCol--; break;
            case RIGHT: newCol++; break;
            case UP_LEFT: newRow--; newCol--; break;
            case UP_RIGHT: newRow--; newCol++; break;
            case DOWN_LEFT: newRow++; newCol--; break;
            case DOWN_RIGHT: newRow++; newCol++; break;
        }

        return new int[]{newRow, newCol};
    }

    /**
     * Pushes a snowball
     * @param direction Push direction
     * @param board Game board
     * @param ballRow Snowball row
     * @param ballCol Snowball column
     * @return True if push was successful
     */
    private boolean pushSnowball(Direction direction, BoardModel board, int ballRow, int ballCol) {
        Snowball snowball = board.getSnowballAt(ballRow, ballCol);
        if (snowball == null || isCombinedSnowball(snowball.getSize())) {
            return false;
        }

        int[] pushPos = calculateNewPosition(direction, ballRow, ballCol);
        int pushRow = pushPos[0];
        int pushCol = pushPos[1];

        if (!board.isInsideBoard(pushRow, pushCol)) {
            return false;
        }

        PositionContent pushTarget = board.getContent(pushRow, pushCol);

        if (pushTarget == PositionContent.BLOCK) {
            return false;
        }

        if (pushTarget == PositionContent.SNOWBALL) {
            return stackSnowballs(board, ballRow, ballCol, pushRow, pushCol, snowball);
        }
        else if (pushTarget == PositionContent.SNOW) {
            return growSnowball(board, ballRow, ballCol, pushRow, pushCol, snowball);
        }
        else {
            return moveSnowball(board, ballRow, ballCol, pushRow, pushCol, snowball);
        }
    }

    /**
     * Calculates new position from start position
     * @param direction Movement direction
     * @param startRow Start row
     * @param startCol Start column
     * @return New position [row, col]
     */
    private int[] calculateNewPosition(Direction direction, int startRow, int startCol) {
        int newRow = startRow;
        int newCol = startCol;

        switch (direction) {
            case UP: newRow--; break;
            case DOWN: newRow++; break;
            case LEFT: newCol--; break;
            case RIGHT: newCol++; break;
            case UP_LEFT: newRow--; newCol--; break;
            case UP_RIGHT: newRow--; newCol++; break;
            case DOWN_LEFT: newRow++; newCol--; break;
            case DOWN_RIGHT: newRow++; newCol++; break;
        }

        return new int[]{newRow, newCol};
    }

    /**
     * Unstacks snowballs
     * @param board Game board
     * @param ballRow Snowball row
     * @param ballCol Snowball column
     * @param direction Unstack direction
     * @return True if unstack was successful
     */
    private boolean unstackSnowballs(BoardModel board, int ballRow, int ballCol, Direction direction) {
        Snowball combinedSnowball = board.getSnowballAt(ballRow, ballCol);
        if (combinedSnowball == null || !isCombinedSnowball(combinedSnowball.getSize())) {
            return false;
        }

        Snowball.SnowballSize smallerSize = getSmallerComponent(combinedSnowball.getSize());
        Snowball.SnowballSize largerSize = getLargerComponent(combinedSnowball.getSize());

        int[] smallBallPos = calculateNewPosition(direction, ballRow, ballCol);
        int smallBallRow = smallBallPos[0];
        int smallBallCol = smallBallPos[1];

        if (!board.isInsideBoard(smallBallRow, smallBallCol)) {
            return false;
        }

        PositionContent target = board.getContent(smallBallRow, smallBallCol);
        if (target != PositionContent.NO_SNOW && target != PositionContent.SNOW) {
            return false;
        }

        board.removeSnowball(combinedSnowball);
        board.createSnowball(ballRow, ballCol, largerSize);
        board.createSnowball(smallBallRow, smallBallCol, smallerSize);
        return true;
    }

    /**
     * Checks if snowball is combined
     * @param size Snowball size
     * @return True if combined
     */
    private boolean isCombinedSnowball(Snowball.SnowballSize size) {
        return size == Snowball.SnowballSize.BIG_AVERAGE ||
                size == Snowball.SnowballSize.BIG_SMALL ||
                size == Snowball.SnowballSize.AVERAGE_SMALL;
    }

    /**
     * Gets smaller component of combined snowball
     * @param size Combined size
     * @return Smaller component size
     */
    private Snowball.SnowballSize getSmallerComponent(Snowball.SnowballSize size) {
        switch (size) {
            case BIG_AVERAGE: return Snowball.SnowballSize.AVERAGE;
            case BIG_SMALL: return Snowball.SnowballSize.SMALL;
            case AVERAGE_SMALL: return Snowball.SnowballSize.SMALL;
            default: return null;
        }
    }

    /**
     * Gets larger component of combined snowball
     * @param size Combined size
     * @return Larger component size
     */
    private Snowball.SnowballSize getLargerComponent(Snowball.SnowballSize size) {
        switch (size) {
            case BIG_AVERAGE: return Snowball.SnowballSize.LARGE;
            case BIG_SMALL: return Snowball.SnowballSize.LARGE;
            case AVERAGE_SMALL: return Snowball.SnowballSize.AVERAGE;
            default: return null;
        }
    }

    /**
     * Stacks snowballs
     * @param board Game board
     * @param fromRow Source row
     * @param fromCol Source column
     * @param toRow Target row
     * @param toCol Target column
     * @param snowball1 Snowball to stack
     * @return True if stack was successful
     */
    private boolean stackSnowballs(BoardModel board, int fromRow, int fromCol,
                                   int toRow, int toCol, Snowball snowball1) {
        Snowball snowball2 = board.getSnowballAt(toRow, toCol);
        if (snowball2 == null) return false;

        if (snowball1.getSize().ordinal() >= snowball2.getSize().ordinal()) {
            return false;
        }

        if (canCreateSnowman(snowball1, snowball2)) {
            board.createSnowman(toRow, toCol);
            return moveTo(fromRow, fromCol, board);
        }

        Snowball.SnowballSize newSize = getCombinedSize(snowball1, snowball2);
        if (newSize != null) {
            board.removeSnowball(snowball1);
            board.removeSnowball(snowball2);
            board.createSnowball(toRow, toCol, newSize);
            return moveTo(fromRow, fromCol, board);
        }

        return false;
    }

    /**
     * Checks if snowman can be created
     * @param s1 First snowball
     * @param s2 Second snowball
     * @return True if snowman can be created
     */
    private boolean canCreateSnowman(Snowball s1, Snowball s2) {
        boolean[] sizes = new boolean[3];
        checkSnowballSize(s1.getSize(), sizes);
        checkSnowballSize(s2.getSize(), sizes);
        return sizes[0] && sizes[1] && sizes[2];
    }

    /**
     * Checks snowball size components
     * @param size Snowball size
     * @param sizes Component flags
     */
    private void checkSnowballSize(Snowball.SnowballSize size, boolean[] sizes) {
        switch(size) {
            case SMALL: sizes[0] = true; break;
            case AVERAGE: sizes[1] = true; break;
            case LARGE: sizes[2] = true; break;
            case AVERAGE_SMALL:
                sizes[0] = true;
                sizes[1] = true;
                break;
            case BIG_AVERAGE:
                sizes[1] = true;
                sizes[2] = true;
                break;
            case BIG_SMALL:
                sizes[0] = true;
                sizes[2] = true;
                break;
        }
    }

    /**
     * Gets combined size of two snowballs
     * @param s1 First snowball
     * @param s2 Second snowball
     * @return Combined size
     */
    private Snowball.SnowballSize getCombinedSize(Snowball s1, Snowball s2) {
        if ((s1.getSize() == Snowball.SnowballSize.SMALL && s2.getSize() == Snowball.SnowballSize.AVERAGE) ||
                (s1.getSize() == Snowball.SnowballSize.AVERAGE && s2.getSize() == Snowball.SnowballSize.SMALL)) {
            return Snowball.SnowballSize.AVERAGE_SMALL;
        }

        if ((s1.getSize() == Snowball.SnowballSize.AVERAGE && s2.getSize() == Snowball.SnowballSize.LARGE) ||
                (s1.getSize() == Snowball.SnowballSize.LARGE && s2.getSize() == Snowball.SnowballSize.AVERAGE)) {
            return Snowball.SnowballSize.BIG_AVERAGE;
        }

        if ((s1.getSize() == Snowball.SnowballSize.SMALL && s2.getSize() == Snowball.SnowballSize.LARGE) ||
                (s1.getSize() == Snowball.SnowballSize.LARGE && s2.getSize() == Snowball.SnowballSize.SMALL)) {
            return Snowball.SnowballSize.BIG_SMALL;
        }

        return null;
    }

    /**
     * Grows snowball on snow
     * @param board Game board
     * @param fromRow Source row
     * @param fromCol Source column
     * @param toRow Target row
     * @param toCol Target column
     * @param snowball Snowball to grow
     * @return True if growth was successful
     */
    private boolean growSnowball(BoardModel board, int fromRow, int fromCol,
                                 int toRow, int toCol, Snowball snowball) {
        if (snowball.getSize() != Snowball.SnowballSize.LARGE) {
            snowball.grow(null);
            board.setBaseContent(toRow, toCol, PositionContent.NO_SNOW);
        }
        return moveSnowball(board, fromRow, fromCol, toRow, toCol, snowball);
    }

    /**
     * Moves a snowball
     * @param board Game board
     * @param fromRow Source row
     * @param fromCol Source column
     * @param toRow Target row
     * @param toCol Target column
     * @param snowball Snowball to move
     * @return True if move was successful
     */
    private boolean moveSnowball(BoardModel board, int fromRow, int fromCol,
                                 int toRow, int toCol, Snowball snowball) {
        snowball.setPosition(toRow, toCol);
        board.setContent(fromRow, fromCol, board.getBaseContent(fromRow, fromCol));
        board.setContent(toRow, toCol, PositionContent.SNOWBALL);
        return moveTo(fromRow, fromCol, board);
    }

    /**
     * Moves monster to position
     * @param newRow Target row
     * @param newCol Target column
     * @param board Game board
     * @return True if move was successful
     */
    private boolean moveTo(int newRow, int newCol, BoardModel board) {
        PositionContent currentContent = board.getContent(this.row, this.col);
        PositionContent restoreContent = currentContent == PositionContent.MONSTER ?
                board.getBaseContent(this.row, this.col) : currentContent;

        board.setContent(this.row, this.col, restoreContent);
        this.row = newRow;
        this.col = newCol;
        board.setContent(newRow, newCol, PositionContent.MONSTER);
        return true;
    }
}