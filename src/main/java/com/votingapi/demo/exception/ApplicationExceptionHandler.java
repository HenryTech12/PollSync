package com.votingapi.demo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(UsernameNotFoundException.class)
    public String handleUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException) {
        logger.error(usernameNotFoundException.getMessage());
        return usernameNotFoundException.getMessage();
    }

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(NullPointerException nullPointerException) {
        logger.error(nullPointerException.getMessage());
        return nullPointerException.getMessage();
    }

    @ExceptionHandler(NumberFormatException.class)
    public String handleNumberFormatException(NumberFormatException numberFormatException) {
        logger.error(numberFormatException.getMessage());
        return numberFormatException.getMessage();
    }
}
