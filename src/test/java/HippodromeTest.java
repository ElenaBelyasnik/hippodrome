import org.junit.jupiter.api.Test;
import org.mockito.configuration.IMockitoConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {
    /*Проверить, что при передаче в конструктор null, будет выброшено IllegalArgumentException*/
    @Test
    public void hippodromeThrowsIllegalArgumentExceptionWhenGetNull() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    /*Проверить, что при передаче в конструктор null,
    выброшенное исключение будет содержать сообщение "Horses cannot be null."*/
    @Test
    public void runMessageHorsesCanNotBeNull() {
        try {
            new Hippodrome(null);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be null.", e.getMessage());
        }
    }

    /*Проверить, что при передаче в конструктор null, будет выброшено IllegalArgumentException*/
    @Test
    public void hippodromeThrowsIllegalArgumentExceptionWhenGetEmptyList() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }


    /*Проверить, что при передаче в конструктор пустого списка,
    выброшенное исключение будет содержать сообщение "Horses cannot be empty."*/
    @Test
    public void runMessageHorsesCanNotBeEmpty() {
        try {
            new Hippodrome(new ArrayList<Horse>());
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }

    /*Проверить, что метод  getHorses возвращает список,
    который содержит те же объекты и в той же последовательности,
    что и список который был передан в конструктор.
    При создании объекта Hippodrome передай в конструктор список из 30 разных лошадей;*/
    @Test
    public void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int horseNum = 1; horseNum <= 30; horseNum++) {
            horses.add(new Horse("horse" + horseNum, 1, 1));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    /*Проверить, что метод вызывает метод move у всех лошадей.
    При создании объекта Hippodrome передай в конструктор список
    из 50 моков лошадей и воспользуйся методом verify.*/
    @Test
    public void move() {
        List<Horse> horses = new ArrayList<>();
        for (int horseNum = 1; horseNum <= 50; horseNum++) {
            horses.add(mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse horse : horses) {
            verify(horse).move();
        }
    }

    /*Проверить, что метод возвращает лошадь с самым большим значением distance.*/
    @Test
    public void getWinner() {
        Horse horse1 = new Horse("horse1", 1, 1);
        Horse horse2 = new Horse("horse2", 1, 2);
        Horse horse3 = new Horse("horse3", 1, 3);
        Horse horse4 = new Horse("horse4", 1, 4);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3, horse4));

        assertSame(horse4, hippodrome.getWinner());
    }
}
