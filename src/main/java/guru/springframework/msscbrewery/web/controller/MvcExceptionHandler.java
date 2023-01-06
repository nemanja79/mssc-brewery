package guru.springframework.msscbrewery.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validationExceptionHandler(ConstraintViolationException e ){

        List<String> errorsList = new ArrayList<>(e.getConstraintViolations().size());
        e.getConstraintViolations().forEach(constraintViolation -> {
            errorsList.add(constraintViolation.getPropertyPath()+" : "+constraintViolation.getMessage());
        });
        return new ResponseEntity<>(errorsList, HttpStatus.BAD_REQUEST);
    }
}
