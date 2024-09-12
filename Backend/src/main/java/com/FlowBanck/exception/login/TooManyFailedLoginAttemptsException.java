package com.FlowBanck.exception.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TooManyFailedLoginAttemptsException extends RuntimeException {

    public TooManyFailedLoginAttemptsException(String message){
        super(message);
    }
}
