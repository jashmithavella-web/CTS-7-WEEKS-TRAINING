package exercise_4.aaa;

public class ArrangeActAssertDemo {

    public int add(int a, int b) {
        return a + b;
    }

    public boolean isEven(int value) {
        return value % 2 == 0;
    }

    public String toUpperCaseOrNull(String input) {
        if (input == null) {
            return null;
        }
        return input.toUpperCase();
    }

    public void requirePositive(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("value must be positive");
        }
    }
}

