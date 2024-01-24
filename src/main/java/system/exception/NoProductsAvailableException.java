package system.exception;

public class NoProductsAvailableException extends RuntimeException {
    public NoProductsAvailableException(String message) {
        super(message);
    }
}
