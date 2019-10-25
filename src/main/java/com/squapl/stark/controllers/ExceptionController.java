package com.squapl.stark.controllers;

import com.squapl.stark.Exception.EntityAlreadyPresentException;
import com.squapl.stark.Exception.EntityNotFoundException;
import com.squapl.stark.util.APIResponseObj;
import com.squapl.stark.util.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleError(Exception exception) {
        log.error("Error STARK " + exception.getMessage());
        exception.printStackTrace();
        ErrorMessage errorMessage = new ErrorMessage("999", "Something Went Wrong");
        return new ResponseEntity<>(new APIResponseObj("ERROR", "", errorMessage), HttpStatus.OK);
        //return new ResponseEntity<>(new APIResponseObj("ERROR", "", errorMessage), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(EntityAlreadyPresentException.class)
    public final ResponseEntity handleEntityAlreadyPresentException(EntityAlreadyPresentException exception) {
        // exception.printStackTrace();
        log.error("Error EntityAlreadyPresentException... " + exception.getMessage());
        ErrorMessage errorMessage = new ErrorMessage("8001", exception.getMessage() + " Already taken.");
        return new ResponseEntity<>(new APIResponseObj("ERROR", exception.getMessage() + " Name already taken !", ""), HttpStatus.OK);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity handleEntityNotFoundException(EntityNotFoundException exception) {
        // exception.printStackTrace();
        log.error("Error EntityNotFoundException... " + exception.getMessage());
        ErrorMessage errorMessage = new ErrorMessage("8001", "Entity " + exception.getMessage() + " Not Found.");
        return new ResponseEntity<>(new APIResponseObj("ERROR", "Entity " + exception.getMessage() + " Not Found !", ""), HttpStatus.OK);
    }


}
