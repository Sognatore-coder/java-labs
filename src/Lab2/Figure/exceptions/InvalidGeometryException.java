package Lab2.Figure.exceptions;

public class InvalidGeometryException extends RuntimeException {
    public InvalidGeometryException(String message) {
        super(message);
    }
}