import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestAIPlayer {

    static int oldvalue;
    public static void resetStaticVariablesWithReflection() throws IllegalAccessException {
        for (Field field : AIPlayer.class.getDeclaredFields()) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true); // Allow access to private fields
                Class<?> fieldType = field.getType();
                if (fieldType.isPrimitive() && fieldType == int.class)
                    field.set(null, oldvalue);

            }
        }
    }

    @BeforeAll
    public static void storeStaticVariable() throws IllegalAccessException {
        for (Field field : AIPlayer.class.getDeclaredFields()) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true); // Allow access to private fields
                Class<?> fieldType = field.getType();
                if (fieldType.isPrimitive()) {
                    if (fieldType == int.class) oldvalue = field.getInt(null);
                }
            }
        }
    }



    @AfterEach
    public void setup() {
        try {
            resetStaticVariablesWithReflection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Order(1)
    @Test
    void testPlayerConstructor() {
        // Test Player constructor and basic getters
        List<Turtle> list = new ArrayList<>();
        Player player1 = new AIPlayer(Gameboard.COLORS[0], list);
        Player player2 = new AIPlayer(Gameboard.COLORS[1], list);

        assertEquals(Gameboard.COLORS[0], player1.revealColor());
        assertEquals(Gameboard.COLORS[1], player2.revealColor());
        assertEquals("AI - 1", player1.getName());
        assertEquals("AI - 2", player2.getName());

    }

    @Order(2)
    @Test
    void testAIPlayerChoosePrompt() {

        Turtle[] turtles = {
                new Turtle("yellow"),
                new Turtle("blue"),
                new Turtle("purple"),
                new Turtle("red"),
                new Turtle("green")

        };
        turtles[0].setCol(1); turtles[0].setRow(2);
        turtles[1].setCol(1); turtles[1].setRow(1);
        turtles[2].setCol(1); turtles[2].setRow(0);
        turtles[3].setCol(3); turtles[3].setRow(1);
        turtles[4].setCol(3); turtles[4].setRow(0);
        List<Turtle> list = new ArrayList<>();
        for (Turtle t: turtles)
            list.add(t);
        /**
         *   Y
         *   B     R
         *   P     G
         */
        Player aiPlayer = new AIPlayer("red", list);

        for (int i = 0; i < 10; i++) { //avoid randominess by repeating test
            String[] options = {"yellow", "blue", "purple"};
            String chosenColor = aiPlayer.choosePrompt(Gameboard.ARROW_TYPE, options);
            assertTrue(java.util.Arrays.asList(options).contains(chosenColor));

            chosenColor = aiPlayer.choosePrompt(Gameboard.DOUBLE_ARROW_TYPE, options);
            assertTrue(java.util.Arrays.asList(options).contains(chosenColor));

            chosenColor = aiPlayer.choosePrompt(Gameboard.RAINBOW + "+1", Gameboard.COLORS);
            assertEquals("red", chosenColor);

            chosenColor = aiPlayer.choosePrompt(Gameboard.RAINBOW + "-1", Gameboard.COLORS);
            assertTrue(!chosenColor.equals("red") && !chosenColor.equals("green"));
        }
    }


}

