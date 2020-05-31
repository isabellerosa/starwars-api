package rosa.isa.starwarsapi.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import rosa.isa.starwarsapi.exceptions.PlanetAlreadyExistsException;
import rosa.isa.starwarsapi.exceptions.PlanetNotFoundException;
import rosa.isa.starwarsapi.exceptions.StarWarsExternalServiceException;
import rosa.isa.starwarsapi.models.CustomErrorResponse;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@Hidden
@Slf4j
@ControllerAdvice
public class PlanetControllerAdvice {

    @ExceptionHandler(PlanetAlreadyExistsException.class)
    public ResponseEntity<CustomErrorResponse> planetAlreadyExistsExceptionHandler(PlanetAlreadyExistsException planetAlreadyExistsException) {
        log.error("Planet already exists", planetAlreadyExistsException);

        var customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setTimestamp(LocalDateTime.now());
        customErrorResponse.setErrorCode(HttpStatus.CONFLICT.value());
        customErrorResponse.setMessage(planetAlreadyExistsException.getMessage());

        return new ResponseEntity<>(customErrorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("Planet is invalid", methodArgumentNotValidException);

        var errorMessage = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(java.util.stream.Collectors.joining("; "));

        var customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setTimestamp(LocalDateTime.now());
        customErrorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        customErrorResponse.setMessage(errorMessage);

        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomErrorResponse> constraintViolationExceptionHandler(ConstraintViolationException constraintViolationException) {
        log.error("Illegal argument received", constraintViolationException);

        var customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setTimestamp(LocalDateTime.now());
        customErrorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
        customErrorResponse.setMessage(constraintViolationException.getMessage());

        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PlanetNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> planetNotFoundExceptionHandler(PlanetNotFoundException planetNotFoundException) {
        log.error("Planet not found", planetNotFoundException);

        var customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setTimestamp(LocalDateTime.now());
        customErrorResponse.setErrorCode(HttpStatus.NOT_FOUND.value());
        customErrorResponse.setMessage(planetNotFoundException.getMessage());

        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StarWarsExternalServiceException.class)
    public ResponseEntity<CustomErrorResponse> starWarsExternalServiceExceptionHandler(StarWarsExternalServiceException starWarsExternalServiceException) {
        log.error("An error occurred in Star Wars external service", starWarsExternalServiceException);

        var customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setTimestamp(LocalDateTime.now());
        customErrorResponse.setErrorCode(HttpStatus.BAD_GATEWAY.value());
        customErrorResponse.setMessage(starWarsExternalServiceException.getMessage());

        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> generalExceptionHandler(Exception exception) {
        log.error("An unknown error occurred", exception);

        var customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setTimestamp(LocalDateTime.now());
        customErrorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        customErrorResponse.setMessage(exception.getMessage());

        return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
