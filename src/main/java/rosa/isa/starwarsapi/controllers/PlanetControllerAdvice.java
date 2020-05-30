package rosa.isa.starwarsapi.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import rosa.isa.starwarsapi.exceptions.PlanetAlreadyExistsException;
import rosa.isa.starwarsapi.exceptions.PlanetNotFoundException;
import rosa.isa.starwarsapi.exceptions.StarWarsExternalServiceException;

@Slf4j
@ControllerAdvice
public class PlanetControllerAdvice {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PlanetAlreadyExistsException.class)
    void planetAlreadyExistsExceptionHandler(PlanetAlreadyExistsException planetAlreadyExistsException) {
        log.error("Planet already exists", planetAlreadyExistsException);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("Planet is invalid", methodArgumentNotValidException);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PlanetNotFoundException.class)
    public void planetNotFoundExceptionHandler(PlanetNotFoundException planetNotFoundException) {
        log.error("Planet not found", planetNotFoundException);
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(StarWarsExternalServiceException.class)
    public void starWarsExternalServiceExceptionHandler(StarWarsExternalServiceException starWarsExternalServiceException) {
        log.error("An error occurred in Star Wars external service", starWarsExternalServiceException);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void generalExceptionHandler(Exception exception) {
        log.error("An unknown error occurred", exception);
    }
}
