package ru.dnoskov.rsifmo.webservice.services.advices;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ru.dnoskov.rsifmo.webservice.exceptions.*;
import ru.dnoskov.rsifmo.webservice.services.throttling.ThrottlingException;

@ControllerAdvice
public class Advicer {

    @ExceptionHandler(EmptyArgumentException.class)
    public ResponseEntity<Object> handleException(EmptyArgumentException e) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("exception", e.getClass().getSimpleName());
        body.put("message", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(IncorrectArgumentException.class)
    public ResponseEntity<Object> handleException(IncorrectArgumentException e) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("exception", e.getClass().getSimpleName());
        body.put("message", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(PersonWithSuchIdNotFoundException.class)
    public ResponseEntity<Object> handleException(PersonWithSuchIdNotFoundException e) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("exception", e.getClass().getSimpleName());
        body.put("message", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(WorkWithSQLException.class)
    public ResponseEntity<Object> handleException(WorkWithSQLException e) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("exception", e.getClass().getSimpleName());
        body.put("message", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(ThrottlingException.class)
    public ResponseEntity<Object> handleException(ThrottlingException e) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("exception", e.getClass().getSimpleName());
        body.put("message", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.TOO_MANY_REQUESTS);
    }
}
