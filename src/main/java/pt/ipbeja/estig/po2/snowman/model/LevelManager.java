package pt.ipbeja.estig.po2.snowman.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages game levels
 */
public class LevelManager {
    private final List<Level> levels;
    private int currentLevelIndex;

    /**
     * Creates the level manager
     */
    public LevelManager() {
        this.levels = new ArrayList<>();
        this.currentLevelIndex = 0;
        this.createLevels();
    }

    /**
     * Creates game levels
     */
    private void createLevels() {
        final int SIZE = 8;

        // Level 1
        Level level1 = new Level(SIZE, SIZE);
        level1.addSnow(2, 2);
        level1.addSnow(3, 3);
        level1.addSnow(4, 4);
        level1.addSnow(5, 5);
        level1.addSnowball(2, 3, Snowball.SnowballSize.SMALL);
        level1.addSnowball(3, 5, Snowball.SnowballSize.SMALL);
        level1.addSnowball(5, 2, Snowball.SnowballSize.SMALL);
        level1.setMonsterPosition(1, 1);
        levels.add(level1);

        // Level 2
        Level level2 = new Level(SIZE, SIZE);
        level2.addBlock(2, 2);
        level2.addBlock(4, 2);
        level2.addBlock(2, 4);
        level2.addBlock(4, 4);
        level2.addSnow(1, 3);
        level2.addSnow(1, 4);
        level2.addSnow(1, 5);
        level2.addSnowball(3, 3, Snowball.SnowballSize.AVERAGE);
        level2.addSnowball(1, 2, Snowball.SnowballSize.SMALL);
        level2.addSnowball(2, 3, Snowball.SnowballSize.SMALL);
        level2.setMonsterPosition(1, 1);
        levels.add(level2);

        // Level 3
        Level level3 = new Level(SIZE, SIZE);
        for (int i = 2; i < 6; i++) {
            level3.addBlock(3, i);
            level3.addBlock(4, i);
        }
        level3.addSnow(1, 1);
        level3.addSnow(1, 6);
        level3.addSnow(6, 1);
        level3.addSnow(6, 6);
        level3.addSnowball(2, 2, Snowball.SnowballSize.SMALL);
        level3.addSnowball(2, 5, Snowball.SnowballSize.SMALL);
        level3.addSnowball(5, 2, Snowball.SnowballSize.SMALL);
        level3.addSnowball(5, 5, Snowball.SnowballSize.SMALL);
        level3.setMonsterPosition(1, 4);
        levels.add(level3);
    }

    /**
     * Gets current level
     * @return Current level
     */
    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    /**
     * Advances to next level
     */
    public void nextLevel() {
        if (currentLevelIndex < levels.size() - 1) {
            currentLevelIndex++;
        }
    }

    /**
     * Gets current level number
     * @return Current level number
     */
    public int getCurrentLevelNumber() {
        return currentLevelIndex + 1;
    }

    /**
     * Gets total number of levels
     * @return Total levels
     */
    public int getTotalLevels() {
        return levels.size();
    }
}