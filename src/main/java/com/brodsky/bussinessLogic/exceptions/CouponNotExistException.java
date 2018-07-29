package com.brodsky.bussinessLogic.exceptions;

public class CouponNotExistException extends Exception {
    public String getMessage() {
        return "Coupon doesn't exist.";
    }
}
