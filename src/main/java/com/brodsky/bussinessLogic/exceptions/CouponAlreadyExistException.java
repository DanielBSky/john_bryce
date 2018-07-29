package com.brodsky.bussinessLogic.exceptions;

public class CouponAlreadyExistException extends Exception {
    public String getMessage() {
        return "Coupon with this title already exists in that company.";
    }
}
