package pt.ipbeja.estig.po2.snowman.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Game board model
 */
public class BoardModel {
    private int rows;
    private int cols;
    private List<List<PositionContent>> board;
    private final List<Snowball> snowballs;
    private View view;
    private Monster monster;
    private final LevelManager levelManager;
    private List<List<PositionContent>> baseBoard;

    /**
     * Creates the board model
     * @param levelManager The level manager
     */
    public BoardModel(LevelManager levelManager) {
        this.levelManager = levelManager;
        this.snowballs = new ArrayList<>();
        this.setupBoardForLevel();
    }

    /**
     * Sets up the board for the current level
     */
    private void setupBoardForLevel() {
        Level level = levelManager.getCurrentLevel();
        this.rows = level.getRows();
        this.cols = level.getCols();
        this.board = new ArrayList<>();
        this.baseBoard = new ArrayList<>();

        snowballs.clear();
        monster = null;

        for (int row = 0; row < this.rows; row++) {
            List<PositionContent> line = new ArrayList<>();
            List<PositionContent> baseLine = new ArrayList<>();
            for (int col = 0; col < this.cols; col++) {
                line.add(PositionContent.NO_SNOW);
                baseLine.add(PositionContent.NO_SNOW);
            }
            this.board.add(line);
            this.baseBoard.add(baseLine);
        }

        // Create borders
        for (int i = 0; i < this.rows; i++) {
            setBaseContent(i, 0, PositionContent.BLOCK);
            setBaseContent(i, this.cols - 1, PositionContent.BLOCK);
        }

        for (int j = 0; j < this.cols; j++) {
            setBaseContent(0, j, PositionContent.BLOCK);
            setBaseContent(this.rows - 1, j, PositionContent.BLOCK);
        }

        // Add internal blocks
        for (int[] pos : level.getBlockPositions()) {
            setBaseContent(pos[0], pos[1], PositionContent.BLOCK);
        }

        // Add snow
        for (int[] pos : level.getSnowPositions()) {
            setBaseContent(pos[0], pos[1], PositionContent.SNOW);
        }

        // Add snowballs
        for (int[] pos : level.getSnowballPositions()) {
            Snowball.SnowballSize size = Snowball.SnowballSize.values()[pos[2]];
            createSnowball(pos[0], pos[1], size);
        }

        // Place monster
        if (isInsideBoard(level.getMonsterRow(), level.getMonsterCol())) {
            this.monster = new Monster(level.getMonsterRow(), level.getMonsterCol());
            this.board.get(level.getMonsterRow()).set(level.getMonsterCol(), PositionContent.MONSTER);
        }
    }

    /**
     * Sets base content at a position
     * @param row Row position
     * @param col Column position
     * @param content Content type
     */
    public void setBaseContent(int row, int col, PositionContent content) {
        if (!isInsideBoard(row, col)) return;
        this.baseBoard.get(row).set(col, content);

        PositionContent current = this.board.get(row).get(col);
        if (current != PositionContent.SNOWBALL &&
                current != PositionContent.MONSTER &&
                current != PositionContent.SNOWMAN) {
            this.board.get(row).set(col, content);
            this.notifyView(row, col);
        }
    }

    /**
     * Creates a snowball at a position
     * @param row Row position
     * @param col Column position
     * @param size Snowball size
     */
    public void createSnowball(int row, int col, Snowball.SnowballSize size) {
        this.board.get(row).set(col, PositionContent.SNOWBALL);
        this.snowballs.add(new Snowball(row, col, size));
        this.notifyView(row, col);
    }

    /**
     * Creates a snowman at a position
     * @param row Row position
     * @param col Column position
     */
    public void createSnowman(int row, int col) {
        this.board.get(row).set(col, PositionContent.SNOWMAN);
        this.snowballs.removeIf(s -> s.getRow() == row && s.getCol() == col);
        this.notifyView(row, col);

        if (this.view != null) {
            this.view.showWinDialog();
        }
    }

    /**
     * Gets content at a position
     * @param row Row position
     * @param col Column position
     * @return The content type
     */
    public PositionContent getContent(int row, int col) {
        if (!this.isInsideBoard(row, col)) return null;
        return this.board.get(row).get(col);
    }

    /**
     * Gets base content at a position
     * @param row Row position
     * @param col Column position
     * @return The base content type
     */
    public PositionContent getBaseContent(int row, int col) {
        if (!this.isInsideBoard(row, col)) return null;
        return this.baseBoard.get(row).get(col);
    }

    /**
     * Gets snowball at a position
     * @param row Row position
     * @param col Column position
     * @return The snowball or null
     */
    public Snowball getSnowballAt(int row, int col) {
        for (Snowball snowball : this.snowballs) {
            if (snowball.getRow() == row && snowball.getCol() == col) {
                return snowball;
            }
        }
        return null;
    }

    /**
     * Sets content at a position
     * @param row Row position
     * @param col Column position
     * @param content Content type
     */
    public void setContent(int row, int col, PositionContent content) {
        if (!this.isInsideBoard(row, col)) return;

        if (content != PositionContent.SNOWBALL) {
            Snowball toRemove = this.getSnowballAt(row, col);
            if (toRemove != null) {
                this.snowballs.remove(toRemove);
            }
        }

        this.board.get(row).set(col, content);
        this.notifyView(row, col);
    }

    /**
     * Removes a snowball
     * @param snowball Snowball to remove
     */
    public void removeSnowball(Snowball snowball) {
        this.snowballs.remove(snowball);
    }

    /**
     * Checks if position is inside board
     * @param row Row position
     * @param col Column position
     * @return True if position is valid
     */
    public boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < this.rows && col >= 0 && col < this.cols;
    }

    /**
     * Notifies view of a board change
     * @param row Changed row
     * @param col Changed column
     */
    private void notifyView(int row, int col) {
        if (this.view != null) {
            this.view.onBoardChanged(row, col, this.board.get(row).get(col));
        }
    }

    /**
     * Gets monster row
     * @return Monster row position
     */
    public int getMonsterRow() {
        return this.monster != null ? this.monster.getRow() : -1;
    }

    /**
     * Gets monster column
     * @return Monster column position
     */
    public int getMonsterCol() {
        return this.monster != null ? this.monster.getCol() : -1;
    }

    /**
     * Attempts to move the monster
     * @param direction Movement direction
     * @return True if move was successful
     */
    public boolean tryMoveMonster(Monster.Direction direction) {
        return this.monster != null && this.monster.move(direction, this);
    }

    /**
     * Resets the board
     */
    public void resetBoard() {
        this.setupBoardForLevel();
        if (this.view != null) {
            for (int row = 0; row < this.rows; row++) {
                for (int col = 0; col < this.cols; col++) {
                    this.view.onBoardChanged(row, col, this.board.get(row).get(col));
                }
            }
        }
    }

    /**
     * Advances to next level
     */
    public void nextLevel() {
        levelManager.nextLevel();
        this.resetBoard();
    }

    // Getters
    public int getRows() { return this.rows; }
    public int getCols() { return this.cols; }
    public int getCurrentLevelNumber() { return levelManager.getCurrentLevelNumber(); }
    public int getTotalLevels() { return levelManager.getTotalLevels(); }

    /**
     * Sets the view
     * @param view The view to set
     */
    public void setView(View view) {
        this.view = view;
    }
}