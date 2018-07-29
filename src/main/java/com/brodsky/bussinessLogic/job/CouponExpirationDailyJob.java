package com.brodsky.bussinessLogic.job;

import com.brodsky.DAO.CouponsDAO;
import com.brodsky.DAO.DBDAO.CouponsDBDAO;
import com.brodsky.javaBeans.Coupon;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class CouponExpirationDailyJob implements Runnable {

    private CouponsDAO couponsDAO;
    private boolean quite = false;

    CouponExpirationDailyJob() {
        this.couponsDAO = new CouponsDBDAO();
    }

    public static void main(String[] args) {
        System.out.println(LocalTime.now().
                isAfter(LocalTime.of(04,00)));
    }


    @Override
    public void run() {
        ArrayList<Coupon> coupons = null;

        while (!quite) {
            System.out.println(LocalTime.now());

            try {
                LocalTime localTime = LocalTime.now();

                if(localTime.isAfter(LocalTime.of(04,00)) &&
                        localTime.isBefore(LocalTime.of(04,30))) {

                    coupons = couponsDAO.getAllCoupons();

                    coupons.stream().filter(c ->
                            LocalDate.now().isAfter(c.getEndDate())).
                            forEach(c -> {
                                try {
                                    couponsDAO.deleteCoupon(c.getId());
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            });
                }

                Thread.sleep(1000 * 60 * 28);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void stop() {
        this.quite = true;
    }
}
