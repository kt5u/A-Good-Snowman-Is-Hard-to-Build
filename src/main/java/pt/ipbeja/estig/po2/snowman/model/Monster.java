package pt.ipbeja.estig.po2.snowman.model;

/**
 * Represents the player-controlled monster that moves and pushes snowballs
 *
 * @author [Seu Nome]
 * @author [Número de Aluno]
 */
public class Monster extends MobileElement {

    private PositionContent originalNewCellContent;

    /**
     * Possible movement directions including diagonals
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT,
        UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT
    }

    /**
     * Creates a monster at specified position
     * @param row row position
     * @param col column position
     */
    public Monster(int row, int col) {
        super(row, col);
    }

    /**
     * Moves the monster in the specified direction
     * @param direction direction to move
     * @param board game board
     * @return true if movement was successful, false otherwise
     */
    public boolean move(Direction direction, BoardModel board) {
        assert direction != null : "Direction cannot be null";
        assert board != null : "BoardModel cannot be null";

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
            if (snowball != null && this.isCombinedSnowball(snowball.getSize())) {
                return this.unstackSnowballs(board, newRow, newCol, direction);
            }
            return this.pushSnowball(direction, board, newRow, newCol);
        }

        return this.moveTo(newRow, newCol, board);
    }

    /**
     * Calculates new position based on direction
     * @param direction movement direction
     * @return array with new [row, col]
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
     * Sets monster position
     * @param row new row
     * @param col new column
     */
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    private boolean pushSnowball(Direction direction, BoardModel board, int ballRow, int ballCol) {
        Snowball snowball = board.getSnowballAt(ballRow, ballCol);
        if (snowball == null || this.isCombinedSnowball(snowball.getSize())) {
            return false;
        }

        // CORREÇÃO: Calcular posição de empurrão a partir da bola, não do monstro
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
            return this.stackSnowballs(board, ballRow, ballCol, pushRow, pushCol, snowball);
        }
        else if (pushTarget == PositionContent.SNOW) {
            return this.growSnowball(board, ballRow, ballCol, pushRow, pushCol, snowball);
        }
        else {
            return this.moveSnowball(board, ballRow, ballCol, pushRow, pushCol, snowball);
        }
    }

    /**
     * CORREÇÃO: Método sobrecarregado para calcular posição a partir de qualquer coordenada
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

    private boolean unstackSnowballs(BoardModel board, int ballRow, int ballCol, Direction direction) {
        Snowball combinedSnowball = board.getSnowballAt(ballRow, ballCol);
        if (combinedSnowball == null || !this.isCombinedSnowball(combinedSnowball.getSize())) {
            return false;
        }

        // Get the smaller component (the one on top)
        Snowball.SnowballSize smallerSize = this.getSmallerComponent(combinedSnowball.getSize());
        Snowball.SnowballSize largerSize = this.getLargerComponent(combinedSnowball.getSize());

        // Calculate position for the smaller snowball (in push direction)
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

        // Perform unstack:
        // 1. Remove the combined snowball
        board.removeSnowball(combinedSnowball);

        // 2. Put back the larger snowball in original position
        board.createSnowball(ballRow, ballCol, largerSize);

        // 3. Place the smaller snowball in push direction
        board.createSnowball(smallBallRow, smallBallCol, smallerSize);

        // Monster doesn't move but consumes input
        return true;
    }

    private boolean isCombinedSnowball(Snowball.SnowballSize size) {
        return size == Snowball.SnowballSize.BIG_AVERAGE ||
                size == Snowball.SnowballSize.BIG_SMALL ||
                size == Snowball.SnowballSize.AVERAGE_SMALL;
    }

    private Snowball.SnowballSize getSmallerComponent(Snowball.SnowballSize size) {
        switch (size) {
            case BIG_AVERAGE: return Snowball.SnowballSize.AVERAGE;
            case BIG_SMALL: return Snowball.SnowballSize.SMALL;
            case AVERAGE_SMALL: return Snowball.SnowballSize.SMALL;
            default: return null;
        }
    }

    private Snowball.SnowballSize getLargerComponent(Snowball.SnowballSize size) {
        switch (size) {
            case BIG_AVERAGE: return Snowball.SnowballSize.LARGE;
            case BIG_SMALL: return Snowball.SnowballSize.LARGE;
            case AVERAGE_SMALL: return Snowball.SnowballSize.AVERAGE;
            default: return null;
        }
    }

    private boolean stackSnowballs(BoardModel board, int fromRow, int fromCol,
                                   int toRow, int toCol, Snowball snowball1) {
        Snowball snowball2 = board.getSnowballAt(toRow, toCol);
        if (snowball2 == null) return false;

        // Only allow stacking if snowball1 is smaller than snowball2
        if (snowball1.getSize().ordinal() >= snowball2.getSize().ordinal()) {
            return false;
        }

        if (this.canCreateSnowman(snowball1, snowball2)) {
            board.createSnowman(toRow, toCol);
            return this.moveTo(fromRow, fromCol, board);
        }

        Snowball.SnowballSize newSize = this.getCombinedSize(snowball1, snowball2);
        if (newSize != null) {
            board.removeSnowball(snowball1);
            board.removeSnowball(snowball2);
            board.createSnowball(toRow, toCol, newSize);
            return this.moveTo(fromRow, fromCol, board);
        }

        return false;
    }

    private boolean canCreateSnowman(Snowball s1, Snowball s2) {
        boolean[] sizes = new boolean[3];
        this.checkSnowballSize(s1.getSize(), sizes);
        this.checkSnowballSize(s2.getSize(), sizes);
        return sizes[0] && sizes[1] && sizes[2];
    }

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

    private boolean growSnowball(BoardModel board, int fromRow, int fromCol,
                                 int toRow, int toCol, Snowball snowball) {
        // Só cresce se não for uma bola de neve grande
        if (snowball.getSize() != Snowball.SnowballSize.LARGE) {
            snowball.grow(null);

            // Remove a neve (altera o conteúdo base para NO_SNOW)
            board.setBaseContent(toRow, toCol, PositionContent.NO_SNOW);
        }
        return this.moveSnowball(board, fromRow, fromCol, toRow, toCol, snowball);
    }

    private boolean moveSnowball(BoardModel board, int fromRow, int fromCol,
                                 int toRow, int toCol, Snowball snowball) {
        snowball.setPosition(toRow, toCol);
        board.setContent(fromRow, fromCol, board.getBaseContent(fromRow, fromCol));
        board.setContent(toRow, toCol, PositionContent.SNOWBALL);
        return this.moveTo(fromRow, fromCol, board);
    }

    private boolean moveTo(int newRow, int newCol, BoardModel board) {
        // Salva o conteúdo original da célula atual
        PositionContent currentContent = board.getContent(this.row, this.col);
        PositionContent restoreContent = currentContent;

        // Se o monstro está sobre neve, mantemos a neve
        if (currentContent != PositionContent.MONSTER) {
            restoreContent = currentContent;
        } else {
            // Se já era monstro, restaura o conteúdo base
            restoreContent = board.getBaseContent(this.row, this.col);
        }

        // Restaura o conteúdo da célula atual
        board.setContent(this.row, this.col, restoreContent);

        // Atualiza a posição do monstro
        this.row = newRow;
        this.col = newCol;

        // Salva o conteúdo original da nova célula
        PositionContent newCellContent = board.getContent(newRow, newCol);
        this.originalNewCellContent = newCellContent;

        // Coloca o monstro na nova célula, preservando o estado base
        board.setContent(newRow, newCol, PositionContent.MONSTER);
        return true;
    }
}