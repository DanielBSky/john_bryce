package com.brodsky.bussinessLogic.exceptions;

public class CustomerNotExistException extends Exception {
    public String getMessage() {
        return "Customer doesn't exist.";
    }
}
