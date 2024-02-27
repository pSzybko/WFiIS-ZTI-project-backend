package zti.projekt.exception;

public class UserRegistrationException extends RuntimeException {
    public UserRegistrationException() {
        super("Couldn't register new user!");
    }
}
