package com.brodsky.bussinessLogic.exceptions;

public class CompanyCouponNotExistException extends Exception {
    public String getMessage() {
        return "Coupon doesn't exist in this company";
    }
}