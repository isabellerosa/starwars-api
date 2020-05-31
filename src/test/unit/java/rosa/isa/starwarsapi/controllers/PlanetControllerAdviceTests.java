package unit.java.rosa.isa.starwarsapi.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import rosa.isa.starwarsapi.controllers.PlanetControllerAdvice;
import rosa.isa.starwarsapi.exceptions.PlanetAlreadyExistsException;
import rosa.isa.starwarsapi.exceptions.PlanetNotFoundException;
import rosa.isa.starwarsapi.exceptions.StarWarsExternalServiceException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlanetControllerAdviceTests {

    private final PlanetControllerAdvice controllerAdvice = new PlanetControllerAdvice();

    @Mock
    private MethodArgumentNotValidException methodArgumentException;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private ConstraintViolationException constraintViolationException;

    @Test
    public void planetAlreadyExists_whenTriggered_thenBuildCorrectResponse() {
        var message = "Planet Already Exists";
        var response = controllerAdvice.planetAlreadyExistsExceptionHandler(
                new PlanetAlreadyExistsException(message));

        assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
        assertEquals(response.getBody().getErrorCode(), HttpStatus.CONFLICT.value());
        assertEquals(response.getBody().getMessage(), message);
    }

    @Test
    public void planetNotFound_whenTriggered_thenBuildCorrectResponse() {
        var message = "Planet Not Found";

        var response = controllerAdvice.planetNotFoundExceptionHandler(
                new PlanetNotFoundException(message));

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(response.getBody().getErrorCode(), HttpStatus.NOT_FOUND.value());
        assertEquals(response.getBody().getMessage(), message);
    }

    @Test
    public void starWarsExternalService_whenTriggered_thenBuildCorrectResponse() {
        var message = "External Service Error";

        var response = controllerAdvice.starWarsExternalServiceExceptionHandler(
                new StarWarsExternalServiceException(message));

        assertEquals(response.getStatusCode(), HttpStatus.BAD_GATEWAY);
        assertEquals(response.getBody().getErrorCode(), HttpStatus.BAD_GATEWAY.value());
        assertEquals(response.getBody().getMessage(), message);
    }

    @Test
    public void argumentNotValid_whenTriggered_thenBuildCorrectResponse() {
        var message = "Invalid field message";
        var fieldErrors = new ArrayList<FieldError>();
        fieldErrors.add(new FieldError("test", "testfield", message));

        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);
        when(methodArgumentException.getBindingResult()).thenReturn(bindingResult);

        var response = controllerAdvice.methodArgumentNotValidExceptionHandler(
                methodArgumentException);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getErrorCode(), HttpStatus.BAD_REQUEST.value());
        assertEquals(response.getBody().getMessage(), message);
    }

    @Test
    public void constraintViolation_whenTriggered_thenBuildCorrectResponse() {
        var message = "Illegal Argument";

        when(constraintViolationException.getMessage()).thenReturn(message);

        var response = controllerAdvice.constraintViolationExceptionHandler(
                constraintViolationException);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getErrorCode(), HttpStatus.BAD_REQUEST.value());
        assertEquals(response.getBody().getMessage(), message);
    }

    @Test
    public void generalException_whenTriggered_thenBuildCorrectResponse() {
        var message = "General Exception";

        var response = controllerAdvice.generalExceptionHandler(
                new Exception(message));

        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(response.getBody().getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertEquals(response.getBody().getMessage(), message);
    }
}