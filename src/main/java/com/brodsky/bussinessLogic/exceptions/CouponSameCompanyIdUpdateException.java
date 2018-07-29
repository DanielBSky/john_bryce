package com.brodsky.bussinessLogic.exceptions;

public class CouponSameCompanyIdUpdateException extends Exception {
    public String getMessage() {
        return "Can't update the coupon. CompanyId need to be the same.";
    }
}
