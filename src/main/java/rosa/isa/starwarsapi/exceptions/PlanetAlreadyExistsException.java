package rosa.isa.starwarsapi.exceptions;

public class PlanetAlreadyExistsException extends RuntimeException {

    public PlanetAlreadyExistsException(String message) {
        super(message);
    }

    public PlanetAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlanetAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public PlanetAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
