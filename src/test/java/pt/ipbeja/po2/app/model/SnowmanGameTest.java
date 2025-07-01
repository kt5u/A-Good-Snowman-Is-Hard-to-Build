package pt.ipbeja.po2.app.model;

import org.junit.jupiter.api.Test;
import pt.ipbeja.estig.po2.snowman.model.*;

import static org.junit.jupiter.api.Assertions.*;

public class SnowmanGameTest {

    @Test
    public void testMonsterToTheLeft() {
        BoardModel board = new BoardModel(8, 8);
        Monster monster = new Monster(1, 1);

        // Libertar posição à esquerda
        board.setContent(1, 0, PositionContent.NO_SNOW);

        assertTrue(monster.move(Monster.Direction.LEFT, board));
        assertEquals(0, monster.getCol());
    }

    @Test
    public void testCreateAverageSnowball() {
        BoardModel board = new BoardModel(8, 8);

        // Posicionar monstro ao lado da bola pequena
        Monster monster = new Monster(2, 3);
        // Criar bola pequena à esquerda do monstro
        board.createSnowball(2, 2, Snowball.SnowballSize.SMALL);
        // Colocar neve à esquerda da bola
        board.setContent(2, 1, PositionContent.SNOW);

        // Mover monstro para empurrar a bola para a neve (direção LEFT)
        assertTrue(monster.move(Monster.Direction.LEFT, board));

        Snowball ball = board.getSnowballAt(2, 1);
        assertNotNull(ball);
        assertEquals(Snowball.SnowballSize.AVERAGE, ball.getSize());
    }

    @Test
    public void testCreateBigSnowball() {
        BoardModel board = new BoardModel(8, 8);
        // Posicionar monstro ao lado da bola média
        Monster monster = new Monster(3, 4);

        // Criar bola média à esquerda do monstro
        board.createSnowball(3, 3, Snowball.SnowballSize.AVERAGE);
        // Colocar neve à esquerda da bola
        board.setContent(3, 2, PositionContent.SNOW);

        // Empurrar bola média para a neve
        assertTrue(monster.move(Monster.Direction.LEFT, board));

        Snowball ball = board.getSnowballAt(3, 2);
        assertNotNull(ball);
        assertEquals(Snowball.SnowballSize.LARGE, ball.getSize());
    }

    @Test
    public void testMaintainBigSnowball() {
        BoardModel board = new BoardModel(8, 8);
        // Posicionar monstro ao lado da bola grande
        Monster monster = new Monster(4, 5);

        // Criar bola grande à esquerda do monstro
        board.createSnowball(4, 4, Snowball.SnowballSize.LARGE);
        // Colocar neve à esquerda da bola
        board.setContent(4, 3, PositionContent.SNOW);

        // Tentar empurrar (não deve crescer mais)
        assertTrue(monster.move(Monster.Direction.LEFT, board));

        Snowball ball = board.getSnowballAt(4, 3);
        assertNotNull(ball);
        assertEquals(Snowball.SnowballSize.LARGE, ball.getSize()); // Permanece grande
    }

    @Test
    public void testAverageBigSnowman() {
        BoardModel board = new BoardModel(8, 8);
        Monster monster = new Monster(5, 5);

        // Criar bola grande
        board.createSnowball(5, 6, Snowball.SnowballSize.LARGE);
        // Criar bola média
        board.createSnowball(5, 7, Snowball.SnowballSize.AVERAGE);

        // Empurrar média para cima da grande
        monster.setPosition(5, 8);
        monster.move(Monster.Direction.LEFT, board);

        Snowball ball = board.getSnowballAt(5, 6);
        assertNotNull(ball);
        assertEquals(Snowball.SnowballSize.BIG_AVERAGE, ball.getSize()); // Combinação
        assertNotEquals(PositionContent.SNOWMAN, board.getContent(5, 6)); // Não é snowman completo
    }

    @Test
    public void testCompleteSnowman() {
        BoardModel board = new BoardModel(8, 8);

        // 1. Configurar o cenário:
        // - Criar combinação BIG_AVERAGE (LARGE + AVERAGE) na posição (6,6)
        board.createSnowball(6, 6, Snowball.SnowballSize.BIG_AVERAGE);
        // - Criar bola pequena à direita (6,7)
        board.createSnowball(6, 7, Snowball.SnowballSize.SMALL);
        // - Posicionar monstro à direita da bola pequena (6,8)
        Monster monster = new Monster(6, 8);

        // 2. Executar a ação:
        // Empurrar a bola pequena (esquerda) para a combinação BIG_AVERAGE
        assertTrue(monster.move(Monster.Direction.LEFT, board));

        // 3. Verificações:
        // - A posição (6,6) deve conter um SNOWMAN
        assertEquals(PositionContent.SNOWMAN, board.getContent(6, 6));

        // - O monstro deve estar na posição (6,7) após o movimento
        assertEquals(6, monster.getRow());
        assertEquals(7, monster.getCol());
    }
}