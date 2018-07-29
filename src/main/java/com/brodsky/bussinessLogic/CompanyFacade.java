package com.brodsky.bussinessLogic;

import com.brodsky.DAO.DBDAO.Categories;
import com.brodsky.DAO.DBDAO.CompaniesDBDAO;
import com.brodsky.DAO.DBDAO.CouponsDBDAO;
import com.brodsky.bussinessLogic.exceptions.CompanyCouponNotExistException;
import com.brodsky.bussinessLogic.exceptions.CouponAlreadyExistException;
import com.brodsky.javaBeans.Company;
import com.brodsky.javaBeans.Coupon;

import java.util.ArrayList;

public class CompanyFacade extends ClientFacade {

    private int companyID;

    public CompanyFacade(int companyID) {
        this.companyID = companyID;
        companiesDAO = new CompaniesDBDAO();
        couponsDAO = new CouponsDBDAO();
    }

    public int getCompanyID() {
        return companyID;
    }

    @Override
    public boolean login(String email, String password) throws Exception {
        if (email == null || password == null) {
            return false;
        }
        if (companiesDAO.isCompanyExists(email, password)) {
            return true;
        }

        return false;
    }


    public void addCoupon(Coupon coupon) throws Exception {

        coupon.setCompanyId(companyID);

        if (couponsDAO.isCouponTitleExistsInThisCompany(coupon)) {
            throw new CouponAlreadyExistException();
        }

        couponsDAO.addCoupon(coupon);
    }

    public Coupon updateCoupon(Coupon coupon) throws Exception {

        System.out.println(companyID);

        if (!couponsDAO.isCouponIdExistsInThisCompany
                (companyID, coupon.getId())) {
            throw new CompanyCouponNotExistException();
        }

        couponsDAO.updateCoupon(coupon);

        Coupon updCoupon = couponsDAO.getOneCoupon(coupon.getId());

        return updCoupon;

    }

    public Coupon deleteCoupon(int couponID) throws Exception {
        Coupon coupon = null;

        coupon = couponsDAO.
                getCompanyCoupon(companyID, couponID);
        if (coupon == null) {
            throw new CompanyCouponNotExistException();
        }

        couponsDAO.deleteCoupon(couponID);

        return coupon;
    }

    public Coupon getCompanyCoupon(int couponId) throws Exception {
        if (!couponsDAO.isCouponIdExistsInThisCompany
                (companyID, couponId)) {
            throw new CompanyCouponNotExistException();
        }

        Coupon coupon = couponsDAO.getOneCoupon(couponId);

        return coupon;
    }


    public ArrayList<Coupon> getCompanyCoupons() throws Exception {

        return couponsDAO.getCompanyCoupons(companyID);
    }


    public ArrayList<Coupon> getCompanyCoupons(Categories categories) throws Exception {

        return couponsDAO.getCompanyCouponsByCategory
                (companyID, categories.getID());

    }


    public ArrayList<Coupon> getCompanyCoupons(double price) throws Exception {
        ArrayList<Coupon> coupons = getCompanyCoupons();
        ArrayList<Coupon> filteredCoupons = new ArrayList<>();

        if (coupons.isEmpty()) {
            return coupons;
        }

        coupons.stream().filter(c -> c.getPrice() <= price).
                forEach(c -> filteredCoupons.add(c));

        return filteredCoupons;
    }


    public Company getCompanyDetails() throws Exception {
         return companiesDAO.getOneCompany(companyID);
    }
}

