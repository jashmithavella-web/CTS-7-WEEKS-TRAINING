package exercise_4.aaa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrangeActAssertDemoTest {

    private ArrangeActAssertDemo demo;

    @BeforeEach
    void setUp() {
        demo = new ArrangeActAssertDemo();
    }

    @Test
    void add_ArrangeActAssert() {
        // Arrange
        int a = 2;
        int b = 3;
        int expected = 5;

        // Act
        int actual = demo.add(a, b);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void isEven_ArrangeActAssert() {
        // Arrange
        int evenValue = 10;
        int oddValue = 9;

        // Act
        boolean evenResult = demo.isEven(evenValue);
        boolean oddResult = demo.isEven(oddValue);

        // Assert
        assertTrue(evenResult);
        assertFalse(oddResult);
    }

    @Test
    void toUpperCaseOrNull_ArrangeActAssert() {
        // Arrange
        String inputNull = null;
        String input = "hello";
        String expected = "HELLO";

        // Act
        String resultNull = demo.toUpperCaseOrNull(inputNull);
        String result = demo.toUpperCaseOrNull(input);

        // Assert
        assertNull(resultNull);
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void requirePositive_throws_ArrangeActAssert() {
        // Arrange
        int invalidValue = -1;
        String expectedMessage = "value must be positive";

        // Act + Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> demo.requirePositive(invalidValue)
        );

        assertEquals(expectedMessage, ex.getMessage());
    }
}

