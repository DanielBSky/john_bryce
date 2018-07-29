package com.brodsky.bussinessLogic;

import com.brodsky.DAO.DBDAO.Categories;
import com.brodsky.DAO.DBDAO.CompaniesDBDAO;
import com.brodsky.DAO.DBDAO.CouponsDBDAO;
import com.brodsky.DAO.DBDAO.CustomersDBDAO;
import com.brodsky.bussinessLogic.exceptions.CouponNotExistException;
import com.brodsky.bussinessLogic.exceptions.EndOfAmountException;
import com.brodsky.bussinessLogic.exceptions.PurchaseAlreadyExistException;
import com.brodsky.javaBeans.Coupon;
import com.brodsky.javaBeans.Customer;

import java.util.ArrayList;

public class CustomerFacade extends ClientFacade{
    private int customerId;

    public CustomerFacade(int customerId) {
        this.customerId = customerId;
        companiesDAO = new CompaniesDBDAO();
        couponsDAO = new CouponsDBDAO();
        customersDAO = new CustomersDBDAO();
    }

    @Override
    public boolean login(String email, String password) throws Exception {

        if (customersDAO.isCustomerExists(email, password)) {
            return true;
        }

        return false;
    }


    public void purchaseCoupon(Coupon coupon) throws Exception {
        Coupon dbCoupon = couponsDAO.getOneCoupon(coupon.getId());

        if(dbCoupon == null) {
            throw new CouponNotExistException();
        }

        if (couponsDAO.isPurchaseExists(customerId, coupon.getId())) {
            throw new PurchaseAlreadyExistException();
        }

        if(dbCoupon.getAmount() == 0) {
            throw new EndOfAmountException();
        }


//            if(LocalDate.now().isAfter(coupon.getEndDate())) {
//                throw new EndDateException();
//            }

        couponsDAO.addCouponPurchase(customerId, coupon.getId());

        coupon.setAmount(dbCoupon.getAmount() - 1);

        couponsDAO.updateCoupon(dbCoupon);

        return;

    }

    public ArrayList<Coupon> getCustomerCoupons() throws Exception {

        return couponsDAO.getCustomerCoupons(customerId);

    }


    public ArrayList<Coupon> getCustomerCoupons(Categories categories) throws Exception {
        ArrayList<Coupon> coupons = new ArrayList<>();
        ArrayList<Coupon> filteredCoupons = new ArrayList<>();

        coupons = couponsDAO.getCustomerCoupons(customerId);
        coupons.stream().filter(c -> c.getCategoryId() == categories.getID()).
                forEach(c -> filteredCoupons.add(c));

        return filteredCoupons;
    }

    public ArrayList<Coupon> getCustomerCoupons(double price) throws Exception {
        ArrayList<Coupon> coupons = null;
        ArrayList<Coupon> filteredCoupons = new ArrayList<>();

        coupons = couponsDAO.getCustomerCoupons(customerId);
        coupons.stream().filter(c -> c.getPrice() <= price).
                forEach(c -> filteredCoupons.add(c));

        return filteredCoupons;
    }

    public Customer getCustomerDetails() throws Exception {
        return customersDAO.getOneCustomer(customerId);
    }
}
