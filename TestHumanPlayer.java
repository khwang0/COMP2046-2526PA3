import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestHumanPlayer {

    @Order(1)
    @Test
    void testPlayerConstructor() {
        // Test Player constructor and basic getters
        Player player1 = new HumanPlayer("Alex", "red");
        Player player2 = new HumanPlayer("Bob", "blue");

        assertEquals("Alex", player1.getName());
        assertEquals("red", player1.revealColor());
        assertEquals("Bob", player2.getName());
        assertEquals("blue", player2.revealColor());
    }

    @Order(4)
    @Test
    void testToString() {
        Player player = new HumanPlayer("TestPlayer", "red");
        player.drawCard(new PlayCard("red", "+1"));
        player.drawCard(new PlayCard("blue", "+1"));

        String result = player.toString();

        System.err.println(result);
        // Verify the format
        assertTrue(result.startsWith("TestPlayer(red) has the following cards:"));
        assertTrue(result.contains("PlayCard{red,+1}"));
        assertTrue(result.contains("PlayCard{blue,+1}"));
        assertFalse(result.contains("2:")); // Index 2 should not be present
    }

    @Order(2)
    @Test
    void testChoosePromptBasic() {

        String[] positiveTestCases = { "green", "blue", "red"};
        String[] expectedResult = {"green", "blue", "red"};

        HashMap<String, Boolean> results = new HashMap<>();
        Thread[] inputThreads = new Thread[positiveTestCases.length];

        // save the original output streams
        PrintStream originalOut = System.out;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        Player player1 = new HumanPlayer("Alex", "red");

        for (int i = 0; i < positiveTestCases.length; i++) {
            final int index = i;
            inputThreads[i] = new Thread(() -> {
                try {
                    InputStream inputStream = System.in;
                    System.setIn(new ByteArrayInputStream(positiveTestCases[index].getBytes()));

                    String returnvalue = player1.choosePrompt(Gameboard.ARROW_TYPE, new String[]{"red", "green", "blue"});
                    results.put(positiveTestCases[index], returnvalue.equals(expectedResult[index]) );
                    System.setIn(inputStream);
                } catch (Exception e) {}
            });
        }

        for (int i = 0; i < positiveTestCases.length; i++) {
            try {
                inputThreads[i].start();
                inputThreads[i].join(50);
            } catch (InterruptedException e) {}
        }
        System.setOut(originalOut);

        for (int i = 0; i < positiveTestCases.length; i++) {
            if(!results.containsKey(positiveTestCases[i])){
                fail("InputTest failed for " + positiveTestCases[i]);
            }
            assertTrue(results.get(positiveTestCases[i]));
        }

        System.out.println("\tPassed for positive test cases");
    }


    @Order(3)
    @Test
    void testChoosePromptNegative() {

        String[] negativeTestCases = { "1", "2", "yellow", "purple"};

        HashMap<String, Boolean> results = new HashMap<>();
        Thread[] inputThreads = new Thread[negativeTestCases.length];

        // save the original output streams
        PrintStream originalOut = System.out;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        Player player1 = new HumanPlayer("Alex", "red");

        for (int i = 0; i < negativeTestCases.length; i++) {
            final int index = i;
            inputThreads[i] = new Thread(() -> {
                try {
                    InputStream inputStream = System.in;
                    System.setIn(new ByteArrayInputStream(negativeTestCases[index].getBytes()));

                    String returnvalue = player1.choosePrompt(Gameboard.ARROW_TYPE, new String[]{"red", "green", "blue"});
                    results.put(negativeTestCases[index], returnvalue.equals(negativeTestCases[index]) );
                    System.setIn(inputStream);
                } catch (Exception e) {
                }
            });
        }

        for (int i = 0; i < negativeTestCases.length; i++) {
            try {
                inputThreads[i].start();
                inputThreads[i].join(50);
            } catch (InterruptedException e) {}
        }
        System.setOut(originalOut);

        for (int i = 0; i < negativeTestCases.length; i++) {
            if(results.containsKey(negativeTestCases[i])){
                fail("InputTest failed for " + negativeTestCases[i]);
            }
        }

        System.out.println("\tPassed for negative test cases");
    }

    @Order(4)
    @Test
    void testPlayCard() {
        String[] positiveTestCases = { "1", "2", "3", "4", "0"};
        final int[] expectedoutputIndex = {1, 2, 3, 4, 0};

        String[] negativeTestCase = {"5", "-1", "aa"};


        HashMap<String, Boolean> results = new HashMap<>();
        Thread[] inputThreads = new Thread[positiveTestCases.length + negativeTestCase.length];

        // save the original output streams
        PrintStream originalOut = System.out;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        final PlayCard[] p = new PlayCard[5];
        for (int j = 0; j < 5; j++)
            p[j] = new PlayCard(Gameboard.COLORS[j], "+1");

        for (int i = 0; i < positiveTestCases.length; i++) {
            final int index = i;
            inputThreads[i] = new Thread(() -> {
                try {
                    InputStream inputStream = System.in;
                    System.setIn(new ByteArrayInputStream(positiveTestCases[index].getBytes()));

                    Player player1 = new HumanPlayer("Alex", "red");
                    for (int j = 0; j < 5; j++)
                        player1.drawCard(p[j]);

                    PlayCard returnvalue = player1.chooseCardToPlay();
                    results.put(positiveTestCases[index], returnvalue == p[expectedoutputIndex[index]]);
                    System.setIn(inputStream);
                } catch (Exception e) {
                }
            });
        }

        for (int i = 0; i < negativeTestCase.length; i++) {
            final int index = i;
            inputThreads[i + positiveTestCases.length] = new Thread(() -> {
                try {
                    InputStream inputStream = System.in;
                    System.setIn(new ByteArrayInputStream(negativeTestCase[index].getBytes()));

                    Player player1 = new HumanPlayer("Alex", "red");
                    for (int j = 0; j < 5; j++)
                        player1.drawCard(p[j]);

                    PlayCard returnvalue = player1.chooseCardToPlay();
                    results.put(negativeTestCase[index], returnvalue == null);
                    System.setIn(inputStream);
                } catch (Exception e) {
                }
            });
        }

        for (int i = 0; i < positiveTestCases.length; i++) {
            try {
                inputThreads[i].start();
                inputThreads[i].join(50);
            } catch (InterruptedException e) {}
        }
        System.setOut(originalOut);

        for (int i = 0; i < positiveTestCases.length; i++) {
            if (!results.containsKey(positiveTestCases[i])){
                fail("InputTest failed for " + positiveTestCases[i]);
            }
            assertTrue(results.get(positiveTestCases[i]));
        }

        for (int i = 0; i < negativeTestCase.length; i++) {
            if (results.containsKey(negativeTestCase[i])){
                fail("InputTest failed for " + negativeTestCase[i]);
            }
        }

        System.out.println("\tPassed for positive test cases");


    }




}

