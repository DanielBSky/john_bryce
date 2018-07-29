package com.brodsky.bussinessLogic.exceptions;

public class EndOfAmountException extends Exception {
    @Override
    public String getMessage() {
        return "Amount of coupons is 0";
    }
}
