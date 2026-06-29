package assertion.in.junit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssertionDemoTest {

    private AssertionDemo demo;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        demo = new AssertionDemo();
    }

    @Test
    void testAdd_withAssertEquals() {
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
    void testIsEven_withAssertTrueAndFalse() {
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
    void testToUpperCaseOrNull_withNullAssertions() {
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
    void testRequirePositive_withAssertThrows() {
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

