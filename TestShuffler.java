import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestShuffler {
    private class Person {
        int age;
        public Person(int age) {
            this.age = age;
        }
    }



    @Test
    @Order(1)
    public void testShuffle() {
        Shuffler<Integer> shuffler = new Shuffler<>();
        Integer[] array = {11, 22, 33, 44, 50, null, null};
        shuffler.shuffle(array);


        // Check that all non-null elements are still present
        List<Integer> seen = new ArrayList<>();
        int countNonNull = 0;
        for (Integer num : array) {
            if (num != null) {
                seen.add(num);
            }
        }


        assertEquals(5, seen.size());
        assertTrue(seen.contains(11));
        assertTrue(seen.contains(22));
        assertTrue(seen.contains(33));
        assertTrue(seen.contains(44));
        assertTrue(seen.contains(50));


        // Check that nulls are at the end
        assertNull(array[array.length - 1]);
        assertNull(array[array.length - 2]);
    }
    @Test
    @Order(2)
    public void testShuffleActuallyShuffled() {
        Shuffler<Person> shuffler = new Shuffler<>();
        Person[] array = {
                new Person(1),
                new Person(2),
                new Person(3),
                new Person(4),
                new Person(5),
                new Person(6)
        };
        shuffler.shuffle(array);
        // Check that the order has changed
        boolean isShuffled = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i].age != i + 1) {
                isShuffled = true;
                break;
            }
        }
        assertTrue(isShuffled, "Array was not shuffled");
    }
}

