package sg.edu.nus.iss.day24_lecture.exception;

public class AccountBlockedAndDisabledException extends RuntimeException {
    
    public AccountBlockedAndDisabledException() {
        super();
    }

    public AccountBlockedAndDisabledException(String message) {
        super(message);
    }

    public AccountBlockedAndDisabledException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountBlockedAndDisabledException(Throwable cause) {
        super(cause);
    }
}
