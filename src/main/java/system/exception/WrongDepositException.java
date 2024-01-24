package system.exception;

public class WrongDepositException extends RuntimeException {

    public WrongDepositException(String message) {
        super(message);
    }
}
