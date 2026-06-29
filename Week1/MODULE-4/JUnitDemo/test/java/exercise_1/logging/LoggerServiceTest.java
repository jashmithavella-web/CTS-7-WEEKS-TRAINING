package exercise_1.logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoggerServiceTest {

    private LoggerService logger;

    @BeforeEach
    void setUp() {
        logger = new LoggerService();
    }

    @Test
    void log_errorMessage_withCorrectFormat() {
        // Arrange
        LogLevel level = LogLevel.ERROR;
        String message = "Disk full";
        String expected = "ERROR: Disk full";

        // Act
        String actual = logger.log(level, message);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void log_warningLevel_notNullOutput() {
        // Arrange
        LogLevel level = LogLevel.WARNING;
        String message = "Slow response";

        // Act
        String output = logger.log(level, message);

        // Assert
        assertNotNull(output);
        assertTrue(output.startsWith("WARNING: "));
    }

    @Test
    void classify_shouldReturnErrorFor500Plus() {
        // Arrange
        int code = 501;

        // Act
        LogLevel result = logger.classify(code);

        // Assert
        assertEquals(LogLevel.ERROR, result);
    }

    @Test
    void errorMessageOrNull_shouldReturnNullForBlank() {
        // Arrange
        String input = "   ";

        // Act
        String result = logger.errorMessageOrNull(input);

        // Assert
        assertNull(result);
    }

    @Test
    void log_shouldThrow_whenMessageIsNull() {
        // Arrange
        LogLevel level = LogLevel.ERROR;

        // Act + Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> logger.log(level, null)
        );

        assertEquals("message must not be null", ex.getMessage());
    }
}

