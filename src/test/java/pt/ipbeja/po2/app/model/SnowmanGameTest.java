package pt.ipbeja.po2.app.model;

import org.junit.jupiter.api.Test;
import pt.ipbeja.estig.po2.snowman.model.*;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class SnowmanGameTest {

    @Test
    public void testMonsterToTheLeft() {
        // Configuração
        Level level = new Level(8, 8);
        level.setMonsterPosition(1, 1);
        BoardModel board = createBoardForTest(level);

        // Libertar posição à esquerda
        board.setBaseContent(1, 0, PositionContent.NO_SNOW);

        Monster monster = board.getMonster();
        boolean moved = monster.move(Monster.Direction.LEFT, board);

        // Verificação
        assertTrue(moved);
        assertEquals(1, monster.getRow());
        assertEquals(0, monster.getCol());
    }

    @Test
    public void testCreateAverageSnowball() {
        // Configuração
        Level level = new Level(8, 8);
        level.setMonsterPosition(2, 3);
        BoardModel board = createBoardForTest(level);
        board.createSnowball(2, 2, Snowball.SnowballSize.SMALL);
        board.setBaseContent(2, 1, PositionContent.SNOW);

        Monster monster = board.getMonster();
        boolean moved = monster.move(Monster.Direction.LEFT, board);

        // Verificação
        assertTrue(moved);
        Snowball ball = board.getSnowballAt(2, 1);
        assertNotNull(ball);
        assertEquals(Snowball.SnowballSize.AVERAGE, ball.getSize());
    }

    @Test
    public void testCreateBigSnowball() {
        // Configuração
        Level level = new Level(8, 8);
        level.setMonsterPosition(3, 4);
        BoardModel board = createBoardForTest(level);
        board.createSnowball(3, 3, Snowball.SnowballSize.AVERAGE);
        board.setBaseContent(3, 2, PositionContent.SNOW);

        Monster monster = board.getMonster();
        boolean moved = monster.move(Monster.Direction.LEFT, board);

        // Verificação
        assertTrue(moved);
        Snowball ball = board.getSnowballAt(3, 2);
        assertNotNull(ball);
        assertEquals(Snowball.SnowballSize.LARGE, ball.getSize());
    }

    @Test
    public void testMaintainBigSnowball() {
        // Configuração
        Level level = new Level(8, 8);
        level.setMonsterPosition(4, 5);
        BoardModel board = createBoardForTest(level);
        board.createSnowball(4, 4, Snowball.SnowballSize.LARGE);
        board.setBaseContent(4, 3, PositionContent.SNOW);

        Monster monster = board.getMonster();
        boolean moved = monster.move(Monster.Direction.LEFT, board);

        // Verificação
        assertTrue(moved);
        Snowball ball = board.getSnowballAt(4, 3);
        assertNotNull(ball);
        assertEquals(Snowball.SnowballSize.LARGE, ball.getSize());
    }

    @Test
    public void testAverageBigSnowman() {
        // Configuração
        Level level = new Level(8, 8);
        level.setMonsterPosition(5, 5); // Posição válida (dentro do tabuleiro 8x8)
        BoardModel board = createBoardForTest(level);
        board.createSnowball(5, 3, Snowball.SnowballSize.LARGE);
        board.createSnowball(5, 4, Snowball.SnowballSize.AVERAGE);

        Monster monster = board.getMonster();
        boolean moved = monster.move(Monster.Direction.LEFT, board);

        // Verificação
        assertTrue(moved);
        Snowball ball = board.getSnowballAt(5, 3);
        assertNotNull(ball);
        assertEquals(Snowball.SnowballSize.BIG_AVERAGE, ball.getSize());
    }

    @Test
    public void testCompleteSnowman() {
        // Configuração
        Level level = new Level(8, 8);
        level.setMonsterPosition(6, 5); // Posição válida (dentro do tabuleiro 8x8)
        BoardModel board = createBoardForTest(level);
        board.createSnowball(6, 3, Snowball.SnowballSize.BIG_AVERAGE);
        board.createSnowball(6, 4, Snowball.SnowballSize.SMALL);

        Monster monster = board.getMonster();
        boolean moved = monster.move(Monster.Direction.LEFT, board);

        // Verificação
        assertTrue(moved);
        assertEquals(PositionContent.SNOWMAN, board.getContent(6, 3));
        assertEquals(6, monster.getRow());
        assertEquals(4, monster.getCol());
    }

    // Método auxiliar para criar o board corretamente
    private BoardModel createBoardForTest(Level level) {
        TestLevelManager levelManager = new TestLevelManager(level);
        BoardModel board = new BoardModel(levelManager);
        board.resetBoard(); // Força a criação do monstro
        return board;
    }

    // Classe auxiliar para testes
    static class TestLevelManager extends LevelManager {
        private final ArrayList<Level> customLevels = new ArrayList<>();

        public TestLevelManager(Level customLevel) {
            this.customLevels.add(customLevel);
        }

        @Override
        public Level getCurrentLevel() {
            return customLevels.get(0);
        }
    }
}