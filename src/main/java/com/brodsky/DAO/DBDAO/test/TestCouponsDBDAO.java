package com.brodsky.DAO.DBDAO.test;

import com.brodsky.DAO.DBDAO.CouponsDBDAO;
import com.brodsky.javaBeans.Coupon;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestCouponsDBDAO {

    public static void main(String[] args) {
        CouponsDBDAO couponsDBDAO = new CouponsDBDAO();

        try {

//            ArrayList<Coupon> coupons = getTenCoupons();
//
//            for(Coupon coupon : coupons) {
//                couponsDBDAO.addCoupon(coupon);
//            }

//            System.out.println(couponsDBDAO.isCouponExistsById(12));
//
//            couponsDBDAO.deleteCoupon(20);
//
//            couponsDBDAO.updateCoupon(new Coupon(1, 1, 5, "Secure Group Testing",
//                    null,
//                    LocalDate.of(2018, 10, 20),
//                    LocalDate.of(2018, 11, 20),
//                    100, 25, "alkd"));
//
//            System.out.println(couponsDBDAO.getOneCoupon(12));
//
//            ArrayList<Coupon> coupons = couponsDBDAO.getAllCoupons();
//
//            for (Coupon coupon : coupons) {
//                System.out.println(coupon);
//            }

//            couponsDBDAO.addCouponPurchase(11, 11);

//            couponsDBDAO.deleteCouponPurchase(11, 11);

//            System.out.println(couponsDBDAO.
//                    isCouponTitleExistsInThisCompany(null));

//            System.out.println(couponsDBDAO.
//                    getCompanyCouponsByCategory(4, 100));

            System.out.println(couponsDBDAO.isPurchaseExists(5, 56));


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private static ArrayList<Coupon> getTenCoupons() {

        ArrayList<Coupon> coupons = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            coupons.add(new Coupon(0, 3, 2, "Secure Group Testing", "The principal goal of Group Testing (GT) is to identify a small subset\n" +
                    "of \"defective\" items from a large population, by grouping items into\n" +
                    "as few test pools as possible. The test outcome of a pool is positive\n" +
                    "if it contains at least one defective item, and is negative otherwise.\n" +
                    "GT algorithms are utilized in numerous applications, and in many of\n" +
                    "them maintaining the privacy of the tested items, namely, keeping\n" +
                    "secret whether they are defective or not, is critical.",
                    LocalDate.of(2018, 10, 20),
                    LocalDate.of(2018, 11, 20),
                    100, 25, "alkd"));
        }
        return coupons;

    }
}
