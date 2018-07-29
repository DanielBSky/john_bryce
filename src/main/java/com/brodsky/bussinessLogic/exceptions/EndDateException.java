package com.brodsky.bussinessLogic.exceptions;

public class EndDateException extends Exception {
    @Override
    public String getMessage() {
        return "The date of using the coupon is expired";
    }
}
