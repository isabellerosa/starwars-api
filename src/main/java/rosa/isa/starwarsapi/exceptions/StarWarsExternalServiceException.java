package rosa.isa.starwarsapi.exceptions;

public class StarWarsExternalServiceException extends RuntimeException {
    public StarWarsExternalServiceException(String message) {
        super(message);
    }

    public StarWarsExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public StarWarsExternalServiceException(Throwable cause) {
        super(cause);
    }

    public StarWarsExternalServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
