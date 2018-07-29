package com.brodsky.DAO;

import com.brodsky.javaBeans.Coupon;

import java.util.ArrayList;

public interface CouponsDAO {
    void addCoupon(Coupon coupon) throws Exception;
    boolean isCouponExistsById(int id) throws Exception;

    boolean isCouponIdExistsInThisCompany(int companyId, int couponId) throws Exception;

    boolean isCouponTitleExistsInThisCompany(Coupon coupon) throws Exception;
    void updateCoupon(Coupon coupon) throws Exception;
    void deleteCoupon(int couponID) throws Exception;
    ArrayList<Coupon> getAllCoupons() throws Exception;
    Coupon getOneCoupon(int couponID) throws Exception;
    ArrayList<Coupon> getCustomerCoupons(int customerId) throws Exception;
    Coupon getCompanyCoupon(int companyId, int CouponId) throws Exception;
    ArrayList<Coupon> getCompanyCoupons(int couponID) throws Exception;
    public ArrayList<Coupon> getCompanyCouponsByCategory(int companyId, int categoryId) throws Exception;
    boolean isPurchaseExists(int customerID, int couponID) throws Exception;
    void addCouponPurchase(int customerID, int couponID) throws Exception;
    void deleteCouponPurchase(int customerID, int couponID) throws Exception;
}
