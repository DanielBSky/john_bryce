package com.brodsky.bussinessLogic.test;

import com.brodsky.bussinessLogic.CustomerFacade;

class TestCustomerFacade {

    public static void main(String[] args) {
        try {
            CustomerFacade customerFacade = new CustomerFacade(4);

//        System.out.println(customerFacade.login
//                ("danetta@gmail.com", "danetta_proud"));

//        Coupon coupon = new Coupon(32, 4, 2, "Secure", "The principal goal of Group Testing (GT)",
//                LocalDate.parse("2018-10-20"),
//                LocalDate.of(2018, 11, 20), 100, 25, "alkd");
//
//        customerFacade.purchaseCoupon(coupon);


//        System.out.println(customerFacade.getCustomerCoupons());
//        System.out.println(customerFacade.getCustomerCoupons(3));
//        System.out.println(customerFacade.getCustomerCoupons(25));
            System.out.println(customerFacade.getCustomerDetails());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
