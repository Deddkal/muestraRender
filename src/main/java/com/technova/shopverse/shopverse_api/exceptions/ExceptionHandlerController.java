package com.technova.shopverse.shopverse_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> productNotFoundExceptionHandler( ProductNotFoundException pnfe) {
        return new ResponseEntity<>(pnfe.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> categoryNotFoundExceptionHandler( CategoryNotFoundException cnfe) {
        return new ResponseEntity<>(cnfe.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDataFromProductException.class)
    public ResponseEntity<String> invalidDataFromProductExceptionHandler( InvalidDataFromProductException idfp) {
        return new ResponseEntity<>(idfp.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDataFromCategoryException.class)
    public ResponseEntity<String> invalidDataFromCategoryExceptionHandler( InvalidDataFromCategoryException idfc) {
        return new ResponseEntity<>(idfc.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
