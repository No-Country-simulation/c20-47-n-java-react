package com.FlowBanck.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.awt.*;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RoleNotFoundException extends RuntimeException{

    public RoleNotFoundException(String message) {
        super(message);
    }
}
