import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestArrowPlayCard {
    @Test
    @Order(1)
    public void testToString() {
        ArrowPlayCard card = new ArrowPlayCard(null);
        assertEquals("Arrow PlayCard", card.toString());
    }

    @Test
    @Order(2)
    public void testDoubleArrowToString() {
        DoubleArrowPlayCard card = new DoubleArrowPlayCard(null);
        assertEquals("Double-Arrow PlayCard", card.toString());
    }

    @Test
    @Order(3)
    public void testGetSteps() {
        ArrowPlayCard arrowCard = new ArrowPlayCard(null);
        DoubleArrowPlayCard doubleArrowCard = new DoubleArrowPlayCard(null);

        assertEquals(1, arrowCard.getSteps());
        assertEquals(2, doubleArrowCard.getSteps());
    }

    @Test
    @Order(4)
    public void testGetType() {
        ArrowPlayCard arrowCard = new ArrowPlayCard(null);
        DoubleArrowPlayCard doubleArrowCard = new DoubleArrowPlayCard(null);

        assertEquals(Gameboard.ARROW_TYPE, arrowCard.getType());
        assertEquals(Gameboard.DOUBLE_ARROW_TYPE, doubleArrowCard.getType());
    }

    @Test
    @Order(5)
    public void testHierarchy() {
        ArrowPlayCard arrowCard = new ArrowPlayCard(null);
        DoubleArrowPlayCard doubleArrowCard = new DoubleArrowPlayCard(null);

        assertTrue(arrowCard instanceof PlayCard);
        assertTrue(doubleArrowCard instanceof PlayCard);
        assertTrue(doubleArrowCard instanceof ArrowPlayCard);
    }

    @Test
    @Order(6)
    public void testGetColorSingleLastTurtle() {

        Gameboard board = new Gameboard();
        try {
            Field playersField = Gameboard.class.getDeclaredField("players");
            playersField.setAccessible(true);
            Player[] players = (Player[]) playersField.get(board);
            for (int i = 0; i < players.length; i++)
                players[i] = new MockPlayer();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        List<Turtle> list = board.getTurtleList();
        list.get(0).setCol(5);
        list.get(1).setCol(3);
        list.get(2).setCol(4);
        list.get(3).setCol(2);
        list.get(4).setCol(1); //last turtle
        ArrowPlayCard arrowCard = new ArrowPlayCard(board);
        DoubleArrowPlayCard doubleArrowCard = new DoubleArrowPlayCard(board);
        assertTrue(arrowCard.getColor().equals(list.get(4).getColor()));
        assertTrue(doubleArrowCard.getColor().equals(list.get(4).getColor()));
        assertEquals(1, list.get(4).getCol()); //not moved yet, move is by Gameboard
    }

    private class MockPlayer extends Player {
        private PlayCard myOnlyCard = new PlayCard("Green", "+1");
        public MockPlayer() {
            super(null, null);
        }
        @Override
        public String choosePrompt(String promptType, String[] options) {
            return "Green";
        }
        @Override
        public PlayCard chooseCardToPlay() {
            return myOnlyCard;
        }
    }
}
