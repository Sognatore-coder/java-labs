package Lab2.Figure.exceptions;

public class NegativeValueException extends IllegalArgumentException {
    public NegativeValueException(String message) {
        super(message);
    }
}