import org.junit.jupiter.api.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRainbowPlayCard {

    private Gameboard board;
    @BeforeAll
    private void setup() {
        board = new Gameboard();
        try {
            Field playersField = Gameboard.class.getDeclaredField("players");
            playersField.setAccessible(true);
            Player[] players = (Player[]) playersField.get(board);
            for (int i = 0; i < players.length; i++)
                players[i] = new MockPlayer("green");
            Field currentPlayer = Gameboard.class.getDeclaredField("player");
            currentPlayer.setAccessible(true);
            currentPlayer.set(board, players[0]);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Order(1)
    @Test
    public void testRainbowBasic() {
        RainbowPlayCard plus1 = new RainbowPlayCard("+1", board);
        RainbowPlayCard minus1 = new RainbowPlayCard("-1", board);
        assertEquals(1, plus1.getSteps());
        assertEquals(-1, minus1.getSteps());
        assertEquals("Rainbow{+1}", plus1.toString());
        assertEquals("Rainbow{-1}", minus1.toString());
    }

    @Order(2)
    @Test
    public void testRainbowChooseColor() {
        RainbowPlayCard plus1 = new RainbowPlayCard("+1", board);
        String chosenColor = plus1.getColor();
        assertEquals("green", chosenColor);
    }

    private class MockPlayer extends Player {
        private PlayCard myOnlyCard = new PlayCard("green", "+1");
        private String color;
        public MockPlayer(String color) {
            super(null, null);
            this.color = color;
        }
        @Override
        public String choosePrompt(String promptType, String[] options) {
            return color;
        }
        @Override
        public PlayCard chooseCardToPlay() {
            return myOnlyCard;
        }
    }
}
