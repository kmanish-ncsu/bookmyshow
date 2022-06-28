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

//    @ExceptionHandler(MissingRequestCookieException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public ErrorResponse handleMissingIdTokenInCookieException(MissingRequestCookieException ide){
//        return handleException(ide);
//    }
//
//    @ExceptionHandler(RoutingConfigNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ResponseBody
//    public ErrorResponse handleOemRoutingConfigNotFoundException(RoutingConfigNotFoundException e){
//        return handleException(e);
//    }
//
//    @ExceptionHandler(RoutingConfigAlreadyExistsException.class)
//    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
//    @ResponseBody
//    public ErrorResponse handleDealerRoutingConfigAlreadyExistsException(RoutingConfigAlreadyExistsException dre){
//        return handleException(dre);
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
//    public ErrorResponse handleOtherExceptions(Exception e){
//        return handleException(e);
//    }
//
    private ErrorResponse handleException(Exception e){
        return new ErrorResponse(e.getMessage());
    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(ex.getBindingResult().getObjectName(),LoggingCorrelationIdAccessor.get(),errors);
//        return new ResponseEntity<>(validationErrorResponse, headers, status);
//    }

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
