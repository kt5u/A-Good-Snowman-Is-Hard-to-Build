package pt.ipbeja.estig.po2.snowman.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game level
 */
public class Level {
    private final int rows;
    private final int cols;
    private final List<int[]> snowPositions;
    private final List<int[]> snowballPositions;
    private final List<int[]> blockPositions;
    private int monsterRow;
    private int monsterCol;

    /**
     * Creates a level
     * @param rows Number of rows
     * @param cols Number of columns
     */
    public Level(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.snowPositions = new ArrayList<>();
        this.snowballPositions = new ArrayList<>();
        this.blockPositions = new ArrayList<>();
    }

    /**
     * Adds snow at a position
     * @param row Row position
     * @param col Column position
     */
    public void addSnow(int row, int col) {
        snowPositions.add(new int[]{row, col});
    }

    /**
     * Adds a snowball at a position
     * @param row Row position
     * @param col Column position
     * @param size Snowball size
     */
    public void addSnowball(int row, int col, Snowball.SnowballSize size) {
        snowballPositions.add(new int[]{row, col, size.ordinal()});
    }

    /**
     * Adds a block at a position
     * @param row Row position
     * @param col Column position
     */
    public void addBlock(int row, int col) {
        blockPositions.add(new int[]{row, col});
    }

    /**
     * Sets monster position
     * @param row Row position
     * @param col Column position
     */
    public void setMonsterPosition(int row, int col) {
        this.monsterRow = row;
        this.monsterCol = col;
    }

    // Getters
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public List<int[]> getSnowPositions() { return snowPositions; }
    public List<int[]> getSnowballPositions() { return snowballPositions; }
    public List<int[]> getBlockPositions() { return blockPositions; }
    public int getMonsterRow() { return monsterRow; }
    public int getMonsterCol() { return monsterCol; }
}