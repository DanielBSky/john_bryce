package com.brodsky.bussinessLogic.exceptions;

public class CustomerEmailAlreadyExistException extends Exception {

    public String getMessage() {
        return "Customer email already exists.";
    }

}
