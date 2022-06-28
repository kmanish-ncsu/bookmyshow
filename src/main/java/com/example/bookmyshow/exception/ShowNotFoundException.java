package com.example.bookmyshow.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ShowNotFoundException extends RuntimeException{

    public ShowNotFoundException(String message) {
        super(message);
    }
    public ShowNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
