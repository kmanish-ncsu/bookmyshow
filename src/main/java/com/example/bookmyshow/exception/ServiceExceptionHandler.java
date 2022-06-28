package com.example.bookmyshow.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleInvalidDataException(ConstraintViolationException ide){
        return handleException(ide);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleCustomerNotFoundException(CustomerNotFoundException ide){
        return handleException(ide);
    }

    private ErrorResponse handleException(Exception e){
        return new ErrorResponse(e.getMessage());
    }

    public static class ErrorResponse{
        private String message;

        public ErrorResponse(String error) {
            this.message = error;
        }

        public String getMessage() {
            return message;
        }

    }

    public static class ValidationErrorResponse{
        private String objectName;
        private String loggingCorrelationId;
        private Map<String,String> fieldErrors;

        public ValidationErrorResponse(String objectName, String loggingCorrelationId, Map<String, String> fieldErrors) {
            this.objectName = objectName;
            this.loggingCorrelationId = loggingCorrelationId;
            this.fieldErrors = fieldErrors;
        }

        public String getObjectName() {
            return objectName;
        }

        public String getLoggingCorrelationId() {
            return loggingCorrelationId;
        }

        public Map<String, String> getFieldErrors() {
            return fieldErrors;
        }
    }
}
