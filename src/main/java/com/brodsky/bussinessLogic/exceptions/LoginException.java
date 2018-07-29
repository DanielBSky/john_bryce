package com.brodsky.bussinessLogic.exceptions;

public class LoginException extends Exception {
    @Override
    public String getMessage() {
        return "Wrong name or password";
    }
}
