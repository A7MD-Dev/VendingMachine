package system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import system.api.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDepositAmountException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDepositAmountException(InvalidDepositAmountException e) {
        ErrorResponse errorResponse = new ErrorResponse("Bad Request", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NoUsersFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoUsersFoundException(NoUsersFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Not Found", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse("Not Found", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Duplicated Username", e.getMessage()));
    }

    @ExceptionHandler(NotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleNotAllowedException(NotAllowedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Not Allowed", e.getMessage()));
    }

    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<ErrorResponse> handleInvalidQuantityException(InvalidQuantityException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Invalid Quantity", e.getMessage()));
    }

    @ExceptionHandler(WrongDepositException.class)
    public ResponseEntity<ErrorResponse> handleWrongDepositException(WrongDepositException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Wrong Deposit", e.getMessage()));
    }

    @ExceptionHandler(NoProductsAvailableException.class)
    public ResponseEntity<ErrorResponse> handleNoProductsAvailableException(NoProductsAvailableException e) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorResponse("No Products Available", e.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Product Not Found", e.getMessage()));
    }

    @ExceptionHandler(ProductCreationFailedException.class)
    public ResponseEntity<ErrorResponse> handleProductCreationFailedException(ProductCreationFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Product Creation Failed", e.getMessage()));
    }
}
