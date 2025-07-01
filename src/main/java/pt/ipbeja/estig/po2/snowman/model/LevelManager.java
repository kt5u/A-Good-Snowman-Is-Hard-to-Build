package pt.ipbeja.estig.po2.snowman.model;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {
    private final List<Level> levels;
    private int currentLevelIndex;

    public LevelManager() {
        this.levels = new ArrayList<>();
        this.currentLevelIndex = 0;
        this.createLevels();
    }

    private void createLevels() {
        // Todos os níveis são 8x8
        final int SIZE = 8;

        // Nível 1
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

        // Nível 2 - com blocos internos
        Level level2 = new Level(SIZE, SIZE);
        // Adiciona blocos internos
        level2.addBlock(2, 2);
        level2.addBlock(2, 5);
        level2.addBlock(5, 2);
        level2.addBlock(5, 5);
        // Neve
        level2.addSnow(3, 3);
        level2.addSnow(4, 4);
        level2.addSnow(3, 4);
        level2.addSnow(4, 3);
        // Bolas de neve
        level2.addSnowball(1, 3, Snowball.SnowballSize.SMALL);
        level2.addSnowball(3, 1, Snowball.SnowballSize.SMALL);
        level2.addSnowball(6, 3, Snowball.SnowballSize.SMALL);
        // Monstro
        level2.setMonsterPosition(1, 1);
        levels.add(level2);

        // Nível 3 - mais complexo
        Level level3 = new Level(SIZE, SIZE);
        // Blocos em forma de cruz
        for (int i = 2; i < 6; i++) {
            level3.addBlock(3, i);
            level3.addBlock(4, i);
        }
        // Neve
        level3.addSnow(1, 1);
        level3.addSnow(1, 6);
        level3.addSnow(6, 1);
        level3.addSnow(6, 6);
        // Bolas de neve
        level3.addSnowball(2, 2, Snowball.SnowballSize.SMALL);
        level3.addSnowball(2, 5, Snowball.SnowballSize.SMALL);
        level3.addSnowball(5, 2, Snowball.SnowballSize.SMALL);
        level3.addSnowball(5, 5, Snowball.SnowballSize.SMALL);
        // Monstro
        level3.setMonsterPosition(1, 4);
        levels.add(level3);
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    public void nextLevel() {
        if (currentLevelIndex < levels.size() - 1) {
            currentLevelIndex++;
        }
    }

    public int getCurrentLevelNumber() {
        return currentLevelIndex + 1;
    }

    public int getTotalLevels() {
        return levels.size();
    }
}