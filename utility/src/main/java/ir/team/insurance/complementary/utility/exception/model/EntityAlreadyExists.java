package ir.team.insurance.complementary.utility.exception.model;

public class EntityAlreadyExists extends RuntimeException {
    public EntityAlreadyExists() {
        super();
    }

    public EntityAlreadyExists(String message) {
        super(message);
    }

    public EntityAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityAlreadyExists(Throwable cause) {
        super(cause);
    }

    protected EntityAlreadyExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
