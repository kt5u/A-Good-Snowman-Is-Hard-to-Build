package pt.ipbeja.estig.po2.snowman.model;

import java.util.ArrayList;
import java.util.List;

public class BoardModel {
    private int rows;
    private int cols;
    private List<List<PositionContent>> board;
    private final List<Snowball> snowballs;
    private View view;
    private Monster monster;
    private final LevelManager levelManager;
    private List<List<PositionContent>> baseBoard;

    public BoardModel(LevelManager levelManager) {
        this.levelManager = levelManager;
        this.snowballs = new ArrayList<>();
        this.setupBoardForLevel();
    }

    private void setupBoardForLevel() {
        Level level = levelManager.getCurrentLevel();
        this.rows = level.getRows();
        this.cols = level.getCols();
        this.board = new ArrayList<>();
        this.baseBoard = new ArrayList<>();

        // Limpa completamente o estado anterior
        this.snowballs.clear();
        this.monster = null;

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

        // Cria bordas
        for (int i = 0; i < this.rows; i++) {
            setBaseContent(i, 0, PositionContent.BLOCK);
            setBaseContent(i, this.cols - 1, PositionContent.BLOCK);
        }

        for (int j = 0; j < this.cols; j++) {
            setBaseContent(0, j, PositionContent.BLOCK);
            setBaseContent(this.rows - 1, j, PositionContent.BLOCK);
        }

        // Adiciona blocos internos
        for (int[] pos : level.getBlockPositions()) {
            setBaseContent(pos[0], pos[1], PositionContent.BLOCK);
        }

        // Adiciona neve
        for (int[] pos : level.getSnowPositions()) {
            setBaseContent(pos[0], pos[1], PositionContent.SNOW);
        }

        // Adiciona bolas de neve
        for (int[] pos : level.getSnowballPositions()) {
            Snowball.SnowballSize size = Snowball.SnowballSize.values()[pos[2]];
            createSnowball(pos[0], pos[1], size);
        }

        // Coloca o monstro
        if (isInsideBoard(level.getMonsterRow(), level.getMonsterCol())) {
            this.monster = new Monster(level.getMonsterRow(), level.getMonsterCol());
            this.board.get(level.getMonsterRow()).set(level.getMonsterCol(), PositionContent.MONSTER);
        }
    }

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

    public void createSnowball(int row, int col, Snowball.SnowballSize size) {
        this.board.get(row).set(col, PositionContent.SNOWBALL);
        this.snowballs.add(new Snowball(row, col, size));
        this.notifyView(row, col);
    }

    public void createSnowman(int row, int col) {
        this.board.get(row).set(col, PositionContent.SNOWMAN);
        this.snowballs.removeIf(s -> s.getRow() == row && s.getCol() == col);
        this.notifyView(row, col);

        if (this.view != null) {
            this.view.showWinDialog();
        }
    }

    public PositionContent getContent(int row, int col) {
        if (!this.isInsideBoard(row, col)) return null;
        return this.board.get(row).get(col);
    }

    public PositionContent getBaseContent(int row, int col) {
        if (!this.isInsideBoard(row, col)) return null;
        return this.baseBoard.get(row).get(col);
    }

    public Snowball getSnowballAt(int row, int col) {
        for (Snowball snowball : this.snowballs) {
            if (snowball.getRow() == row && snowball.getCol() == col) {
                return snowball;
            }
        }
        return null;
    }

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

    public void removeSnowball(Snowball snowball) {
        this.snowballs.remove(snowball);
    }

    public boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < this.rows && col >= 0 && col < this.cols;
    }

    private void notifyView(int row, int col) {
        if (this.view != null) {
            this.view.onBoardChanged(row, col, this.board.get(row).get(col));
        }
    }

    public int getMonsterRow() {
        return this.monster != null ? this.monster.getRow() : -1;
    }

    public int getMonsterCol() {
        return this.monster != null ? this.monster.getCol() : -1;
    }

    public boolean tryMoveMonster(Monster.Direction direction) {
        return this.monster != null && this.monster.move(direction, this);
    }

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

    public void nextLevel() {
        levelManager.nextLevel();
        this.resetBoard();
    }

    public int getRows() { return this.rows; }
    public int getCols() { return this.cols; }
    public int getCurrentLevelNumber() { return levelManager.getCurrentLevelNumber(); }
    public int getTotalLevels() { return levelManager.getTotalLevels(); }

    public void setView(View view) {
        this.view = view;
    }
}