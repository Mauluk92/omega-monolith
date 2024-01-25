package it.aleph.omegamonolith.controller.advice;

import it.aleph.omegamonolith.error.OmegaAPIError;
import it.aleph.omegamonolith.exception.CutterProcessingException;
import it.aleph.omegamonolith.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
public class OmegaControllerAdvice {

    private final static String MESSAGE_CONSTRAINT_VIOLATIONS = "Some constraints violations occurred.";
    private final static String MESSAGE_RESOURCE_NOT_FOUND = "Resource not found";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public OmegaAPIError handleConstraintViolations(MethodArgumentNotValidException ex, HttpServletRequest request) {
        OmegaAPIError omegaAPIError = OmegaAPIError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(Instant.now())
                .path(request.getRequestURL().toString())
                .message(MESSAGE_CONSTRAINT_VIOLATIONS)
                .errorMessages(new ArrayList<>())
                .build();
        omegaAPIError.getErrorMessages().addAll(ex.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList());
        return omegaAPIError;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public OmegaAPIError handleResourceNotFound(NotFoundException ex, HttpServletRequest request){
        return OmegaAPIError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(Instant.now())
                .path(request.getRequestURL().toString())
                .message(MESSAGE_RESOURCE_NOT_FOUND)
                .errorMessages(ex
                        .getIdListNotFound()
                        .stream()
                        .map(id -> ex.getMessage().concat(id.toString()))
                        .collect(Collectors.toList()))
                .build();
    }

    @ExceptionHandler(CutterProcessingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public OmegaAPIError handleCutterProcessingException(CutterProcessingException ex, HttpServletRequest request){
        return OmegaAPIError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(Instant.now())
                .path(request.getRequestURL().toString())
                .message(ex.getMessage())
                .errorMessages(ex.getMessageCauseList())
                .build();
    }

}
