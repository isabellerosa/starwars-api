package rosa.isa.starwarsapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import rosa.isa.starwarsapi.exceptions.PlanetAlreadyExistsException;
import rosa.isa.starwarsapi.exceptions.PlanetNotFoundException;

@ControllerAdvice
public class PlanetControllerAdvice {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PlanetAlreadyExistsException.class)
    void planetAlreadyExistsExceptionHandler() {
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void methodArgumentNotValidExceptionHandler() {
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PlanetNotFoundException.class)
    public void planetNotFoundExceptionHandler() {
    }
}