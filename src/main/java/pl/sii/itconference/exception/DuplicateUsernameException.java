package pl.sii.itconference.exception;

public class DuplicateUsernameException extends RuntimeException {

    public DuplicateUsernameException(String msg) {
        super(msg);
    }
}
