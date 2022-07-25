import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;

public class HorseTest {
    /*Проверить, что при передаче в конструктор первым параметром null,
    будет выброшено IllegalArgumentException*/
    @Test
    public void horseThrowsIllegalArgumentExceptionWhenGetNull() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 2, 5));
    }

    /*Проверить, что при передаче в конструктор первым параметром null,
    выброшенное исключение будет содержать сообщение "Name cannot be null."*/
    @Test
    public void runMessageNameCanNotBeNull() {
        try {
            new Horse(null, 2, 5);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    /*Проверить, что при передаче в конструктор первым параметром пустой строки или строки
    содержащей только пробельные символы (пробел, табуляция и т.д.),
    будет выброшено IllegalArgumentException.*/
    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\t\t", "\n\n\n"})
    public void whenGetSpasesOrTabsOrNullName(String name){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 2, 5));

        assertEquals("Name cannot be blank.", e.getMessage());
    }

    /*Проверить, что при передаче в конструктор вторым параметром отрицательного числа,
    будет выброшено IllegalArgumentException*/
    @Test
    public void theSecondParameterNegativeNumber(){
            assertThrows(IllegalArgumentException.class, () -> new Horse("Baby", -2, 5));
    }

    /*Проверить, что при передаче в конструктор вторым параметром отрицательного числа,
    выброшенное исключение будет содержать сообщение "Speed cannot be negative."*/
    @Test
    public void SpeedCanNotBeNegative(){
        try {
            new Horse("Baby", -2, 5);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    /*Проверить, что при передаче в конструктор третьим параметром отрицательного числа,
    будет выброшено IllegalArgumentException*/
    @Test
    public void theThirdParameterNegativeNumber(){
        assertThrows(IllegalArgumentException.class, () -> new Horse("Baby", 2, -5));
    }

    /*Проверить, что при передаче в конструктор третьим параметром отрицательного числа,
    выброшенное исключение будет содержать сообщение "Distance cannot be negative."*/
    @Test
    public void distanceCanNotBeNegative(){
        try {
            new Horse("Baby", 2, -5);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    /* Проверить, что метод возвращает строку, которая была передана первым параметром в конструктор */
    @Test
    public void checkGetName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Baby", 2, 5);

        Field nameField = Horse.class.getDeclaredField("name");
        nameField.setAccessible(true);
        String nameValue = (String) nameField.get(horse);
        nameField.setAccessible(false);
        assertEquals("Baby", nameValue);
    }

    /*Проверить, что метод возвращает число, которое было передано вторым параметром в конструктор*/
    @Test
    public void checkGetSpeed() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Baby", 2, 5);

        Field speedField = Horse.class.getDeclaredField("speed");
        speedField.setAccessible(true);
        double speedValue = (double) speedField.get(horse);
        speedField.setAccessible(false);
        assertEquals(2, speedValue);
    }

    /*Проверить, что метод возвращает число, которое было передано третьим параметром в конструктор */
    @Test
    public void checkGetDistance() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Baby", 2, 5);

        Field distanceField = Horse.class.getDeclaredField("distance");
        distanceField.setAccessible(true);
        double distanceValue = (double) distanceField.get(horse);
        distanceField.setAccessible(false);
        assertEquals(5, distanceValue);
    }

    /*Проверить, что метод возвращает ноль, если объект был создан с помощью конструктора с двумя параметрами*/
    @Test
    public void returnsNullWhenHorseCreatesByTwoParam() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Baby", 2, 5);

        Field distanceField = Horse.class.getDeclaredField("distance");
        distanceField.setAccessible(true);
        double distanceValue = (double) distanceField.get(horse);
        distanceField.setAccessible(false);
        assertEquals(0, distanceValue);
    }

    /* Проверить, что метод move вызывает внутри метод getRandomDouble с параметрами 0.2 и 0.9.
    Для этого нужно использовать MockedStatic и его метод verify */
    @Test
    public void checkRunningMethodGetRandomDouble(){
        try (MockedStatic<Horse> mockedStatic =  Mockito.mockStatic( Horse.class)) {
            Horse horse = new Horse("Baby", 2);
            horse.move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
            /*mockedStatic.verify(() -> Horse.getRandomDouble(anyDouble(), anyDouble()));*/
        }
    }

    /*Проверить, что метод присваивает дистанции значение высчитанное по формуле:
    distance + speed * getRandomDouble(0.2, 0.9). Для этого нужно замокать getRandomDouble,
    чтобы он возвращал определенные значения, которые нужно задать параметризовав тест.*/
    @ParameterizedTest
    @ValueSource (doubles = {0.2, 0.9, 1.0})
    void move (double rand){
        try (MockedStatic<Horse> mockedStatic =  Mockito.mockStatic( Horse.class)) {
            Horse horse = new Horse("Baby", 2, 5);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(rand);

            horse.move();
            assertEquals(5 + 2*rand, horse.getDistance());
        }
    }



}
