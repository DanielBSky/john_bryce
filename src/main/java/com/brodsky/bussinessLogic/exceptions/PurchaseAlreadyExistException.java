package com.brodsky.bussinessLogic.exceptions;

public class PurchaseAlreadyExistException extends Exception {
    @Override
    public String getMessage() {
        return "Purchase already exists";
    }
}
