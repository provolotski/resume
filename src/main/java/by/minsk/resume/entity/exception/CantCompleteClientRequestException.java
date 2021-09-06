package by.minsk.resume.entity.exception;

public class CantCompleteClientRequestException extends RuntimeException{

    public CantCompleteClientRequestException(String message) {
        super(message);
    }

    public CantCompleteClientRequestException(Throwable cause) {
        super(cause);
    }

    public CantCompleteClientRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
