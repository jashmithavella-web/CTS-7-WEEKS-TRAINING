package exercise_1.logging;

public class LoggerService {

    public String log(LogLevel level, String message) {
        if (level == null) {
            throw new IllegalArgumentException("level must not be null");
        }
        if (message == null) {
            throw new IllegalArgumentException("message must not be null");
        }

        return level + ": " + message;
    }

    public LogLevel classify(int warningCode) {
        if (warningCode >= 500) {
            return LogLevel.ERROR;
        }
        if (warningCode >= 400) {
            return LogLevel.WARNING;
        }
        return LogLevel.INFO;
    }

    public String errorMessageOrNull(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }
        if (classify(input.length() * 100) == LogLevel.ERROR) {
            return "ERROR: " + input;
        }
        return input;
    }
}

