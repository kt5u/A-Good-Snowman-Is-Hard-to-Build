package pt.ipbeja.estig.po2.snowman.model;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final int rows;
    private final int cols;
    private final List<int[]> snowPositions;
    private final List<int[]> snowballPositions;
    private final List<int[]> blockPositions; // Novo
    private int monsterRow;
    private int monsterCol;

    public Level(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.snowPositions = new ArrayList<>();
        this.snowballPositions = new ArrayList<>();
        this.blockPositions = new ArrayList<>(); // Inicializado
    }

    public void addSnow(int row, int col) {
        snowPositions.add(new int[]{row, col});
    }

    public void addSnowball(int row, int col, Snowball.SnowballSize size) {
        snowballPositions.add(new int[]{row, col, size.ordinal()});
    }

    public void addBlock(int row, int col) { // Novo método
        blockPositions.add(new int[]{row, col});
    }

    public void setMonsterPosition(int row, int col) {
        this.monsterRow = row;
        this.monsterCol = col;
    }

    // Getters
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public List<int[]> getSnowPositions() { return snowPositions; }
    public List<int[]> getSnowballPositions() { return snowballPositions; }
    public List<int[]> getBlockPositions() { return blockPositions; } // Novo getter
    public int getMonsterRow() { return monsterRow; }
    public int getMonsterCol() { return monsterCol; }
}