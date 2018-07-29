package com.brodsky.javaBeans;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Coupon> coupons = new ArrayList<>();
        Company company = new Company(1, "IBM", "ibm@gmail.com", "ibm123", coupons);
//        Coupon coupon = new Coupon(1, 1, CouponType.ELECTRICITY, "electro", "electro item", );
        Customer customer = new Customer();

        System.out.println(company.toString());
    }
}
