package rosa.isa.starwarsapi.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import rosa.isa.starwarsapi.exceptions.PlanetAlreadyExistsException;
import rosa.isa.starwarsapi.exceptions.PlanetNotFoundException;
import rosa.isa.starwarsapi.exceptions.StarWarsExternalServiceException;
import rosa.isa.starwarsapi.models.CustomErrorResponse;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class PlanetControllerAdvice {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PlanetAlreadyExistsException.class)
    public ResponseEntity<CustomErrorResponse> planetAlreadyExistsExceptionHandler(PlanetAlreadyExistsException planetAlreadyExistsException) {
        log.error("Planet already exists", planetAlreadyExistsException);

        var customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setTimestamp(LocalDateTime.now());
        customErrorResponse.setErrorCode(HttpStatus.CONFLICT.value());
        customErrorResponse.setMessage(planetAlreadyExistsException.getMessage());

        return new ResponseEntity<CustomErrorResponse>(customErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("Planet is invalid", methodArgumentNotValidException);

        var errorMessage = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getDefaultMessage())
                .collect(java.util.stream.Collectors.joining("; "));

        var customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setTimestamp(LocalDateTime.now());
        customErrorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        customErrorResponse.setMessage(errorMessage);

        return new ResponseEntity<CustomErrorResponse>(customErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PlanetNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> planetNotFoundExceptionHandler(PlanetNotFoundException planetNotFoundException) {
        log.error("Planet not found", planetNotFoundException);

        var customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setTimestamp(LocalDateTime.now());
        customErrorResponse.setErrorCode(HttpStatus.NOT_FOUND.value());
        customErrorResponse.setMessage(planetNotFoundException.getMessage());

        return new ResponseEntity<CustomErrorResponse>(customErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(StarWarsExternalServiceException.class)
    public ResponseEntity<CustomErrorResponse> starWarsExternalServiceExceptionHandler(StarWarsExternalServiceException starWarsExternalServiceException) {
        log.error("An error occurred in Star Wars external service", starWarsExternalServiceException);

        var customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setTimestamp(LocalDateTime.now());
        customErrorResponse.setErrorCode(HttpStatus.BAD_GATEWAY.value());
        customErrorResponse.setMessage(starWarsExternalServiceException.getMessage());

        return new ResponseEntity<CustomErrorResponse>(customErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> generalExceptionHandler(Exception exception) {
        log.error("An unknown error occurred", exception);

        var customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setTimestamp(LocalDateTime.now());
        customErrorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        customErrorResponse.setMessage(exception.getMessage());

        return new ResponseEntity<CustomErrorResponse>(customErrorResponse, HttpStatus.NOT_FOUND);
    }
}
